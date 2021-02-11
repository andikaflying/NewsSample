package app.andika.newssample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.andika.newssample.R
import app.andika.newssample.adapter.NewsAdapter
import app.andika.newssample.databinding.FragmentNewsBinding
import app.andika.newssample.model.Article
import app.andika.newssample.utilities.QUERY
import app.andika.newssample.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>() {
    private val TAG = NewsFragment::class.java.name
    private lateinit var adapter: NewsAdapter
    val newsViewModel: NewsViewModel by viewModels()
    lateinit var newsList: MutableList<Article>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvImage.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(requireActivity())
        binding.rvImage.adapter = adapter

        newsViewModel.displayNewsList(QUERY).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                newsList = it.articles.toMutableList()
                adapter.submitList(newsList)
                adapter.notifyDataSetChanged()
            } else {
                if (adapter.itemCount > 0) {
                    newsList.clear()
                    adapter.notifyDataSetChanged()
                }
            }
        })

//        binding.imbSearch.setOnClickListener {
//            videoViewModel.search(binding.edtSearch.text.toString())
//        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news
    }
}