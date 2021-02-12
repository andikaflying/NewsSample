package app.andika.newssample.ui

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import app.andika.newssample.R
import app.andika.newssample.databinding.FragmentNewsBinding
import app.andika.newssample.databinding.FragmentNewsDetailBinding
import app.andika.newssample.model.Article
import app.andika.newssample.utilities.touch.OnSwipeTouchListener
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>()  {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedArticle = NewsDetailFragmentArgs.fromBundle(requireArguments()).selectedArticle
        displayDetail(selectedArticle)
        setSwipeListener(view)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news_detail
    }

    fun displayDetail(selectedArticle: Article) {
        binding.tvAuthor.setText(selectedArticle.author)
        binding.tvContent.setText(Html.fromHtml(selectedArticle.content).toString())
        binding.tvTitle.setText(selectedArticle.title)
        Glide.with(this)
            .load(selectedArticle.urlToImage)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.ivNews)
    }

    fun setSwipeListener(view: View) {
        view.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                goToOtherPage("right")
                Toast.makeText(requireActivity(), "right", Toast.LENGTH_SHORT).show();
            }

            override fun onSwipeLeft() {
                goToOtherPage("left")
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

    fun goToOtherPage(side : String) {
        val fragment = NewsDetailFragment()
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}