package com.reece.goodbois.adpater

import androidx.recyclerview.widget.DiffUtil
import com.reece.goodbois.model.Breed

class SearchAdapterDiffUtil(
    private val oldList: List<Breed>, private val newList: List<Breed>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].name == newList[newItemPosition].name

}