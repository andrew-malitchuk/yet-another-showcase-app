package io.yasa.list.adapter.loadstate

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import io.yasa.list.databinding.ItemLoadStateBinding
import logcat.logcat

class BreweryLoadStateViewHolder(
    private val viewBinding: ItemLoadStateBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(loadState: LoadState) {
        with(viewBinding) {
            logcat("loadState") { "$loadState" }
            if (loadState is LoadState.Error) {
                tvError.text = loadState.error.localizedMessage
            }
            cpbiProgress.isVisible = (loadState is LoadState.Loading)
            btnRetry.apply {
                setOnClickListener { retry.invoke() }
                isVisible = (loadState !is LoadState.Loading)
            }
            tvError.isVisible = (loadState !is LoadState.Loading)
        }
    }

}

