package com.imaec.alicorntest.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindItemList")
fun RecyclerView.bindItemList(itemList: List<Any>?) {
    itemList ?: return

    (adapter as? BaseSingleViewAdapter<Any>)?.run {
        submitList(itemList)
    }
}
