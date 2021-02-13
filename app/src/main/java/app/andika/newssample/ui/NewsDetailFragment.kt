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
import app.andika.newssample.utilities.runOnBackgroundThread
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

                val observer = Observer<Article> {
                    if (it != null) {
                        Log.e(TAG, "News = " + it.toString())
                        displayDetail(it)
                    }
                }

                newsViewModel.getNews(newsIndex).observe(viewLifecycleOwner, observer)
            } else {
                val selectedArticle = NewsDetailFragmentArgs.fromBundle(requireArguments()).selectedArticle
                displayDetail(selectedArticle)

                val observer = Observer<List<Article>> {
                    if (it != null) {
                        Log.e(TAG, "Size : " + it.size)
                        newsIndex = it.indexOf(selectedArticle)
                        maxNewsIndex = it.size - 1
                        Log.e(TAG, "Max news index = " + maxNewsIndex + "newsIndex = " + newsIndex)
                        newsViewModel.getAllNews().removeObservers(this)
                    }
                }

                newsViewModel.getAllNews().observe(viewLifecycleOwner, observer)
            }

            Log.e(TAG, "Max news index = " + maxNewsIndex + "newsIndex = " + newsIndex)
        }

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

        Log.e(TAG, "is favorite = " + selectedArticle.isFavorite)
        setFavoriteIcon(selectedArticle.isFavorite!!)

        binding.imvFavorite.setOnClickListener {
            if (selectedArticle.isFavorite == true) {
                selectedArticle.isFavorite = false;
            } else {
                selectedArticle.isFavorite = true;
            }

            setFavoriteIcon(selectedArticle.isFavorite!!)

            runOnBackgroundThread {
                newsViewModel.updateNews(selectedArticle)
            }
        }
    }

    fun setSwipeListener(view: View) {
        view.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                goToOtherPage(RIGHT)
            }

            override fun onSwipeLeft() {
                goToOtherPage(LEFT)
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

    fun setFavoriteIcon(isFavorited : Boolean) {
        if (isFavorited == true) {
            binding.imvFavorite.setBackgroundTintList(resources.getColorStateList(R.color.red_500))
        } else {
            binding.imvFavorite.setBackgroundTintList(resources.getColorStateList(R.color.black))
        }
    }
}