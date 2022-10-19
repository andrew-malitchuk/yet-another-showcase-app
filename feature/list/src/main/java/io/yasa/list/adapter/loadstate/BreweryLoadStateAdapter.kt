package io.yasa.list.adapter.loadstate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import io.yasa.list.databinding.ItemLoadStateBinding

class BreweryLoadStateAdapter(
    private val context: Context,
    private val retry: () -> Unit
) : LoadStateAdapter<BreweryLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BreweryLoadStateViewHolder {
        return BreweryLoadStateViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            retry
        )
    }

    override fun onBindViewHolder(holder: BreweryLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}