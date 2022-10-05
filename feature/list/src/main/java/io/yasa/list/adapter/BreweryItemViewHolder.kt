package io.yasa.list.adapter

import androidx.recyclerview.widget.RecyclerView

import io.yasa.list.databinding.ItemBreweryBinding
import io.yasa.models.data.model.BreweryUiModel

class BreweryItemViewHolder(
    private val viewBinding: ItemBreweryBinding,
    var onClick:((item: BreweryUiModel)->Unit)?=null
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: BreweryUiModel) {
        with(viewBinding) {
//            textView.text = item.foo
//            textView2.text = item.bar
//            root.setOnClickListener {
//                onClick?.invoke(item)
//            }

        }
    }

}

