package com.app.androidPixabay.ui.pixabay.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.androidPixabay.R
import com.app.androidPixabay.commons.communicator.HitsItem
import com.app.androidPixabay.databinding.AdapterPixabayBinding
import com.app.androidPixabay.domain.entities.HitsList

class PixabayAdapter :
    PagedListAdapter<HitsList, PixabayAdapter.MyViewHolder>(PixabayItemDiffCallback()) {
    private var onHitsItemClickListener: HitsItem? = null

    // OVERRIDE ---
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: AdapterPixabayBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context)
            , R.layout.adapter_pixabay, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onHitsItemClickListener) }
    }

    override fun getItemViewType(position: Int): Int = position

    fun setOnHitsItemClickListener(onHitsItemClickListener: HitsItem) {
        this.onHitsItemClickListener = onHitsItemClickListener
    }

    inner class MyViewHolder(private val binding: AdapterPixabayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hitsList: HitsList, clickListener: HitsItem?) {
            binding.hitsList = hitsList
            itemView.setOnClickListener { clickListener?.onHitsItemClickListener(hitsList) }
        }
    }
}

class PixabayItemDiffCallback : DiffUtil.ItemCallback<HitsList>() {
    override fun areItemsTheSame(oldItem: HitsList, newItem: HitsList): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HitsList, newItem: HitsList): Boolean =
        oldItem == newItem
}
