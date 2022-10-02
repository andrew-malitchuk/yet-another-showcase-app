package io.yasa.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.yasa.list.Foobar
import io.yasa.list.databinding.ItemBreweryBinding

class BreweryAdapter(val context: Context, var onClick: ((item: Foobar) -> Unit)? = null) :
    ListAdapter<Foobar, BreweryItemViewHolder>(ListDiffUtil()) {

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
        holder.bind(getItem(position))
    }

}