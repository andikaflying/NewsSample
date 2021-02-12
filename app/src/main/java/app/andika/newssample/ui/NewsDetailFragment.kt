package app.andika.newssample.ui

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import app.andika.newssample.R
import app.andika.newssample.databinding.FragmentNewsDetailBinding
import app.andika.newssample.model.Article
import app.andika.newssample.utilities.touch.OnSwipeTouchListener
import app.andika.newssample.viewmodel.NewsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>()  {
    private val TAG = NewsDetailFragment::class.java.name
    val newsViewModel: NewsViewModel by viewModels()
    var newsIndex: Int = 0
    var maxNewsIndex: Int = 0
    val RIGHT = "right"
    val LEFT = "left"
    val INDEX = "index"
    val MAX_NEWS_INDEX = "max list index"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            if (requireArguments().containsKey(INDEX) && requireArguments().containsKey(MAX_NEWS_INDEX)) {
                newsIndex = requireArguments().getInt(INDEX)
                maxNewsIndex = requireArguments().getInt(MAX_NEWS_INDEX)
            } else {
                newsIndex = NewsDetailFragmentArgs.fromBundle(requireArguments()).index
                maxNewsIndex = NewsDetailFragmentArgs.fromBundle(requireArguments()).maxList - 1
            }

            Log.e(TAG, "Max news index = " + maxNewsIndex + "newsIndex = " + newsIndex)
        }

        newsViewModel.getNews(newsIndex).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.e(TAG, "News = " + it.toString())
                displayDetail(it)
            }
        })

        setSwipeListener(view)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news_detail
    }

    fun displayDetail(selectedArticle: Article) {
        binding.tvAuthor.setText(selectedArticle.author)

        if (selectedArticle.content != null) {
            binding.tvContent.setText(Html.fromHtml(selectedArticle.content).toString())
        }

        binding.tvTitle.setText(selectedArticle.title)
        Glide.with(this)
            .load(selectedArticle.urlToImage)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.ivNews)
    }

    fun setSwipeListener(view: View) {
        view.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                goToOtherPage(RIGHT)
                Toast.makeText(requireActivity(), "right", Toast.LENGTH_SHORT).show();
            }

            override fun onSwipeLeft() {
                goToOtherPage(LEFT)
                Toast.makeText(requireActivity(), "left", Toast.LENGTH_SHORT).show();
            }

            override fun onSwipeTop() {
                Toast.makeText(requireActivity(), "top", Toast.LENGTH_SHORT).show();
            }

            override fun onSwipeBottom() {
                Toast.makeText(requireActivity(), "bottom", Toast.LENGTH_SHORT).show();
            }
        })
    }

    fun goToOtherPage(side: String) {
        var otherPageIndex = 0;
        if (side.equals(RIGHT)) {
            otherPageIndex = newsIndex - 1; //go to next page
        } else if (side.equals(LEFT)) {
            otherPageIndex = newsIndex + 1; //go to next page
        }

        if ((otherPageIndex >= 0) && (otherPageIndex <= maxNewsIndex)) {
            val bundle = Bundle()
            bundle.putInt(INDEX, otherPageIndex)
            bundle.putInt(MAX_NEWS_INDEX, maxNewsIndex)
            val fragment = NewsDetailFragment()
            fragment.arguments = bundle
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}