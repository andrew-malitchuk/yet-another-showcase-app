package io.yasa.list.adapter

import androidx.recyclerview.widget.RecyclerView
import io.yasa.list.Foobar
import io.yasa.list.databinding.ItemBreweryBinding

class BreweryItemViewHolder(
    private val viewBinding: ItemBreweryBinding,
    var onClick:((item:Foobar)->Unit)?=null
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: Foobar) {
        with(viewBinding) {

            textView.text = item.foo
            textView2.text = item.bar
            root.setOnClickListener {
                onClick?.invoke(item)
            }

        }
    }

}

