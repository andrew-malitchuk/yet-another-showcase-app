package io.yasa.list.adapter

import androidx.recyclerview.widget.DiffUtil
import io.yasa.list.Foobar

class ListDiffUtil : DiffUtil.ItemCallback<Foobar>() {

    override fun areItemsTheSame(oldItem: Foobar, newItem: Foobar): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Foobar, newItem: Foobar): Boolean {
        return false
    }
}
