package com.jguerrerope.speedrun.ui.gamelist

import android.arch.lifecycle.Observer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import com.jguerrerope.speedrun.domain.Game
import com.jguerrerope.speedrun.domain.NetworkState
import com.jguerrerope.speedrun.ui.common.PagedListAdapterBase
import com.jguerrerope.speedrun.ui.common.ViewWrapper

/**
 * PagedListAdapter used to display Game
 *
 * @param onItemClick what has to be done when a GameItemView gets clicked
 */
class GamePagedListAdapter(
        private val onItemClick: (game: Game) -> Unit
) : PagedListAdapterBase<Game>(diffCallback) {

    private var hasExtraRow = false

    var itemParentWithPercentage = -1f
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager.canScrollVertically()
    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View {
        return when (viewType) {
            VIEW_TYPE_PROGRESS -> ProgressBar(parent.context)
            VIEW_TYPE_GAME -> GameItemView(parent.context)
            else -> throw RuntimeException("bad type view")
        }.apply {
            layoutParams = if (itemParentWithPercentage in 0.0..1.0) {
                val newWidth = (parent.measuredWidth * itemParentWithPercentage).toInt()
                LayoutParams(newWidth, WRAP_CONTENT)
            }else  LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
    }

    override fun onBindViewHolder(holder: ViewWrapper<View>, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_GAME) {
            (holder.view as GameItemView).apply {
                val item = getItem(position)
                if (item == null) {
                    setOnClickListener(null)
                } else {
                    setOnClickListener { onItemClick(item) }
                    bind(item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow && position == itemCount - 1) VIEW_TYPE_PROGRESS else VIEW_TYPE_GAME
    }

    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow) 1 else 0

    // We observe changes in network state. So if it starts loading for more objects we set the extra row with the
    // progress bar. And when it finish we remove it from the bottom.
    val networkStateObserver = Observer<NetworkState> {
        it?.let { state ->
            hasExtraRow = when (state) {
                NetworkState.NEXT_LOADING -> {
                    notifyItemInserted(super.getItemCount()); true
                }
                else -> {
                    if (hasExtraRow) notifyItemRemoved(itemCount); false
                }
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_PROGRESS = 1
        private const val VIEW_TYPE_GAME = 2

        private val diffCallback = object : DiffUtil.ItemCallback<Game>() {

            // Lets assume that the name is a unique identifier for a game. Even if
            // item content change at some point, the name will define if it is the
            // same item or it is another one
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
                    oldItem.title == newItem.title

            // Game is a data class so it has predefined equals method where each
            // field is used to define if two objects are equals
            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
                    oldItem == newItem
        }
    }
}