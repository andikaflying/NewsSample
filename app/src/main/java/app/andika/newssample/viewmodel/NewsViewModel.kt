package app.andika.newssample.viewmodel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import app.andika.newssample.model.AllNewsResponse
import app.andika.newssample.model.Article
import app.andika.newssample.repository.NewsRepository

class NewsViewModel @ViewModelInject public constructor(
    private val newsRepository: NewsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val TAG = NewsViewModel::class.java.name
    private val allNewsMediatorLiveData = MediatorLiveData<AllNewsResponse>()
    lateinit var newsListLiveData: LiveData<AllNewsResponse>

    public fun displayNewsList(query : String) : MediatorLiveData<AllNewsResponse> {
        newsListLiveData = newsRepository.displayNewsList(query)
        allNewsMediatorLiveData.addSource(newsListLiveData, allNewsMediatorLiveData::setValue)

        return allNewsMediatorLiveData
    }

    fun saveAllNews(articles: List<Article>) {
        newsRepository.saveAllNews(articles)
    }

    fun updateNews(article: Article) {
        newsRepository.updateNews(article)
    }

    fun deleteAllNews() {
        newsRepository.deleteAll()
    }

    fun getNews(index : Int) : LiveData<Article> {
        return newsRepository.getNews(index)
    }

    fun getAllNews() : LiveData<List<Article>> {
        return newsRepository.getAllNews()
    }

    public fun search(keyword : String) {
        newsListLiveData.value.let {
            allNewsResponse ->
            if (keyword.isNotEmpty()) {
                val newResponse = allNewsResponse!!.copy()
                newResponse!!.articles = newResponse.articles?.filter {
                    it -> it.title?.contains(keyword) == true || it.author?.contains(keyword) == true
                }

                allNewsMediatorLiveData.value = newResponse
            } else {
                allNewsMediatorLiveData.value = allNewsResponse
            }
        }
    }
}