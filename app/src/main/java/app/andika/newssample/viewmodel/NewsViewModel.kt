package app.andika.newssample.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import app.andika.newssample.model.AllNewsResponse
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


}