package io.yasa.list.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import io.yasa.list.adapter.BreweryAdapter.On
import io.yasa.list.databinding.ItemBreweryBinding
import io.yasa.models.data.model.BreweryUiModel

class BreweryAdapter(
    val context: Context,
    var onClick: ((item: BreweryUiModel) -> Unit)? = null,
    var isInPortrait: Boolean
) :
    PagingDataAdapter<BreweryUiModel, BreweryItemViewHolder>(ListDiffUtil()) {

    var focusedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryItemViewHolder {
        return BreweryItemViewHolder(
            ItemBreweryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: BreweryItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it) {
                notifyItemChanged(focusedItem)
                notifyItemChanged(position)
                focusedItem = position
                onClick?.invoke(it)
            }
        }
        if (!isInPortrait) {
            if (position == focusedItem) {
                holder.itemView.setBackgroundColor(Color.CYAN)
            } else {
                holder.itemView.setBackgroundColor(Color.WHITE)
            }
        }

        val on = On {  }
    }
    fun interface On {
        fun onBreweryItemClick()
    }
}