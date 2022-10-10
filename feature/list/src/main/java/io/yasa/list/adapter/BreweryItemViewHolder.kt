package io.yasa.list.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation

import io.yasa.list.databinding.ItemBreweryBinding
import io.yasa.models.data.model.BreweryUiModel

class BreweryItemViewHolder(
    private val viewBinding: ItemBreweryBinding,
    var onClick: ((item: BreweryUiModel) -> Unit)? = null
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: BreweryUiModel) {
        with(viewBinding) {
            tvTitle.text = item.name
            tvDescription.text = item.fullAddress
            ivLogo.load("https://loremflickr.com/320/240"){
                transformations(CircleCropTransformation())
            }
            root.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }

}

