package com.example.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.api.model.newsResponse.News
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter(var newsList: List<News?>? = null) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList!![position]
        holder.bind(news)
        if (onNewsClick != null) {
            holder.itemView.rootView.setOnClickListener(View.OnClickListener {
                onNewsClick?.onItemClick(news)
            })
        }

//        Glide.with(holder.itemView)
//            .load(news?.urlToImage)
//            .placeholder(R.drawable.ic_launcher_foreground)
//            .into(holder.itemBinding.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = newsList?.size ?: 0
    fun bindNews(articles: List<News?>?) {
        newsList = articles
        notifyDataSetChanged()

    }

    class ViewHolder(val itemBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(news: News?) {
            itemBinding.news = news
            itemBinding.executePendingBindings()
        }

    }

    var onNewsClick: OnNewsClick? = null

    fun interface OnNewsClick {
        fun onItemClick(news: News?)
    }
}