package com.example.newsapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemCategoryBinding

class CategoryAdapter(var items: List<CategoryDataClass>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(var viewBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(categoryDataClass: CategoryDataClass?) {
            viewBinding.category = categoryDataClass
            viewBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        onItemClickListener.let {
            holder.viewBinding.layoutItem.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }
        }

    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, item: CategoryDataClass)
    }
}