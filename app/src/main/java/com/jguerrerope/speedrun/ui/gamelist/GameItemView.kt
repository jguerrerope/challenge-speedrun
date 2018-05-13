package com.jguerrerope.speedrun.ui.gamelist

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.jguerrerope.speedrun.R
import com.jguerrerope.speedrun.domain.Game
import kotlinx.android.synthetic.main.view_item_game.view.*

class GameItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_item_game, this, true)
        useCompatPadding = true
        radius = 10f
        cardElevation = 4f
    }

    /**
     * Bind a Game to our view.
     *
     * @param item The item Game with the information to be shown
     */
    fun bind(item: Game) {
        gameTitle.text = item.title

        val options = RequestOptions()
                .placeholder(R.drawable.game_placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        Glide
                .with(context)
                .load(item.imageLarge)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .apply(options)
                .into(gameImageView)
    }
}
