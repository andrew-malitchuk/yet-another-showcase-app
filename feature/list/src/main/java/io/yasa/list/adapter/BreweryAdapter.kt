package io.yasa.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import io.yasa.list.databinding.ItemBreweryBinding
import io.yasa.models.data.model.BreweryUiModel

class BreweryAdapter(val context: Context, var onClick: ((item: BreweryUiModel) -> Unit)? = null) :
    PagingDataAdapter<BreweryUiModel, BreweryItemViewHolder>(ListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryItemViewHolder {
        return BreweryItemViewHolder(
            ItemBreweryBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: BreweryItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}