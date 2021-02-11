package app.andika.newssample.ui

import android.os.Bundle
import android.text.Html
import android.view.View
import app.andika.newssample.R
import app.andika.newssample.databinding.FragmentNewsBinding
import app.andika.newssample.databinding.FragmentNewsDetailBinding
import app.andika.newssample.model.Article
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>()  {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedArticle = NewsDetailFragmentArgs.fromBundle(requireArguments()).selectedArticle
        displayDetail(selectedArticle)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news_detail
    }

    fun displayDetail(selectedArticle: Article) {
        binding.tvAuthor.setText(selectedArticle.author)
        binding.tvContent.setText(Html.fromHtml(selectedArticle.content).toString())
        binding.tvTitle.setText(selectedArticle.title)
        Glide.with(this).load(selectedArticle.urlToImage).into(binding.ivNews)
    }
}