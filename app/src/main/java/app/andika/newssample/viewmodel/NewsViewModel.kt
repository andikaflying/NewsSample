package app.andika.newssample.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.andika.newssample.repository.NewsRepository

class NewsViewModel @ViewModelInject public constructor(
    private val newsRepository: NewsRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
)
    : ViewModel(), LifecycleObserver {

    private val TAG = NewsViewModel::class.java.name
}