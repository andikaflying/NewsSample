package app.andika.newssample.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.andika.newssample.R
import app.andika.newssample.adapter.NewsAdapter
import app.andika.newssample.databinding.FragmentNewsBinding
import app.andika.newssample.model.Article
import app.andika.newssample.utilities.dialog.CustomProgressDialog
import app.andika.newssample.utilities.QUERY
import app.andika.newssample.utilities.connectivity.ConnectivityReceiver
import app.andika.newssample.utilities.dialog.GeneralDialog
import app.andika.newssample.viewmodel.NewsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>() {
    private val TAG = NewsFragment::class.java.name
    private lateinit var adapter: NewsAdapter
    val newsViewModel: NewsViewModel by viewModels()
    lateinit var newsList: MutableList<Article>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNews.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(requireActivity())
        binding.rvNews.adapter = adapter

        displayList()

//        binding.imbSearch.setOnClickListener {
//            videoViewModel.search(binding.edtSearch.text.toString())
//        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news
    }

    fun displayList() {
        val customProgressDialog = CustomProgressDialog(requireActivity())
        customProgressDialog.setDialog(true)

        if (ConnectivityReceiver.isConnected) {
            newsViewModel.displayNewsList(QUERY).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    customProgressDialog.setDialog(false)

                    if (it.throwable == null) {
                        if (it.articles!!.size > 0) {   //Success response
                            binding.rvNews.visibility = View.VISIBLE
                            binding.tvMessageNotFound.visibility = View.GONE

                            newsList = it.articles.toMutableList()
                            adapter.submitList(newsList)
                            adapter.notifyDataSetChanged()
                        } else {    //Success but empty list
                            binding.rvNews.visibility = View.GONE
                            binding.tvMessageNotFound.visibility = View.VISIBLE
                        }

                        newsViewModel.saveAllNews(it.articles!!)
                    } else {
                        GeneralDialog.displayNetworkErrorDialog(requireActivity())
                    }
                } else {
                    if (adapter.itemCount > 0) {
                        customProgressDialog.setDialog(false)
                        newsList.clear()
                        adapter.notifyDataSetChanged()
                    }
                }
            })
        } else {
            customProgressDialog.setDialog(false)
            GeneralDialog.displayNetworkErrorDialog(requireActivity())
        }
    }
}