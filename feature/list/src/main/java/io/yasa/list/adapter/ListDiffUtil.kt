package io.yasa.list.adapter

import androidx.recyclerview.widget.DiffUtil
import io.yasa.models.data.model.BreweryUiModel

class ListDiffUtil : DiffUtil.ItemCallback<BreweryUiModel>() {

    override fun areItemsTheSame(oldItem: BreweryUiModel, newItem: BreweryUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BreweryUiModel, newItem: BreweryUiModel): Boolean {
        return oldItem == newItem
    }
}
