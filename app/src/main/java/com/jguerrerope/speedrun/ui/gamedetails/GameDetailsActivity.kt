package com.jguerrerope.speedrun.ui.gamedetails

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.jguerrerope.speedrun.R
import com.jguerrerope.speedrun.di.Injectable
import com.jguerrerope.speedrun.domain.Game
import com.jguerrerope.speedrun.domain.PlayerRun
import com.jguerrerope.speedrun.domain.Status
import com.jguerrerope.speedrun.extension.observe
import kotlinx.android.synthetic.main.activity_game_details.*
import kotlinx.android.synthetic.main.activity_game_details_content.*
import javax.inject.Inject


class GameDetailsActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GameDetailsViewModel
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.transition_enter_right, R.anim.transition_no_animation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        intent?.extras?.getParcelable<Game>(EXTRA_GAME)?.let {
            game = it
            setUpViews()
            setUpViewModels()
        } ?: throw RuntimeException("bad initialization. not found some extras")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.transition_no_animation, R.anim.transition_exit_right)
    }

    private fun setUpViews() {
        setUpToolbar()
        bindGame(game)
    }

    private fun setUpViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GameDetailsViewModel::class.java)

        viewModel.playerRun.observe(this) {
            it ?: return@observe
            progress.isVisible = it.status == Status.LOADING

            if (it.status == Status.FAILED) {
                notPlayerRunAvailable()
                Snackbar.make(toolbar, it.msg!!, Snackbar.LENGTH_LONG)
                        .show()
            } else if (it.status == Status.SUCCESS) {
                bindPlayerRun(it.data!!)
            }
        }
        viewModel.loadPlayerRun(game.id)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_white)
        collapsingToolbar.title = ""
    }

    private fun bindGame(game: Game) {
        gameTitle.text = game.title
        val options = RequestOptions()
                .placeholder(R.drawable.game_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.DATA)

        Glide
                .with(this)
                .load(game.imageLarge)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .apply(options)
                .into(gameImageView)


        Glide
                .with(this)
                .load(game.imageLogo)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .apply(options)
                .into(logoGameImageView)
    }

    private fun bindPlayerRun(playerRun: PlayerRun) {
        playerNames.text = playerRun.name
        runTime.text = playerRun.time
        videoCardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playerRun.video))
            startActivity(intent)
        }
    }

    private fun notPlayerRunAvailable() {
        playerNames.isVisible = false
        runTime.isVisible = false
        videoCardView.isVisible = false
    }

    companion object {
        const val EXTRA_GAME = "EXTRA_GAME"
    }
}
