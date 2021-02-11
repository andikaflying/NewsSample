package app.andika.newssample.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.andika.newssample.databinding.ItemNewsBinding
import app.andika.newssample.model.Article
import app.andika.newssample.ui.NewsFragment
import app.andika.newssample.ui.NewsFragmentDirections
import com.bumptech.glide.Glide

class NewsAdapter(public val activity: Activity) : ListAdapter<Article, NewsAdapter.ViewHolder>(VideoListDiffCallback()) {
    private val TAG = NewsAdapter::class.java.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return NewsAdapter.ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = getItem(position)

        holder.apply {
            bind(news, createDetailOnClickListener(news), activity)
            itemView.tag = news
        }
    }

    private fun createDetailOnClickListener(news: Article): View.OnClickListener {
        return View.OnClickListener {
            val directions = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(news)
            it.findNavController().navigate(directions)
        }
    }

    class ViewHolder(val itemNewsBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemNewsBinding.root) {

        fun bind(news: Article, detailListener: View.OnClickListener, activity: Activity) {
            itemNewsBinding.tvAuthor.setText("Author : " + (if (news.author == null) "-" else news.author))
            itemNewsBinding.tvTitle.setText(news.title)
            itemNewsBinding.apply {
                clickListener = detailListener
            }

            Glide.with(activity)
                .load(news.urlToImage)
                .into(itemNewsBinding.ivImage)
        }
    }

    class VideoListDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.publishedAt == newItem.publishedAt
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return newItem == oldItem
        }
    }
}