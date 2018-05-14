package com.jguerrerope.speedrun.ui.gamelist


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import androidx.core.view.isVisible
import com.jguerrerope.speedrun.R
import com.jguerrerope.speedrun.di.Injectable
import com.jguerrerope.speedrun.domain.NetworkState
import com.jguerrerope.speedrun.domain.Status
import com.jguerrerope.speedrun.extension.observe
import com.jguerrerope.speedrun.ui.gamedetails.GameDetailsActivity
import kotlinx.android.synthetic.main.activity_game_list.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class GameListActivity : AppCompatActivity(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GameListViewModel
    private lateinit var adapter: GamePagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.transition_fade_in, R.anim.transition_no_animation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)
        setUpViews()
        setUpViewModels()
    }

    private fun setUpViews() {
        setUpToolbar()
        adapter = GamePagedListAdapter {
            startActivity<GameDetailsActivity>(Pair(GameDetailsActivity.EXTRA_GAME, it))
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@GameListActivity)
            adapter = this@GameListActivity.adapter
        }
        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setUpViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GameListViewModel::class.java)
        viewModel.games.observe(this) {
            adapter.submitList(it)
        }
        viewModel.refreshState.observe(this) {
            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
            if (it?.status == Status.FAILED) {
                Snackbar.make(recyclerView, R.string.network_error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry) { viewModel.retry() }
                        .show()
            }
        }
        viewModel.networkState.observe(this, adapter.networkStateObserver)
        viewModel.networkState.observe(this) {
            it ?: return@observe
            progress.isVisible = it == NetworkState.INITIAL_LOADING

            if (it.status == Status.FAILED) {
                Snackbar.make(recyclerView, R.string.network_error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry) { viewModel.retry() }
                        .show()
            }
        }
    }
}
