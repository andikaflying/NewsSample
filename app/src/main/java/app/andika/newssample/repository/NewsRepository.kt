package app.andika.newssample.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.andika.newssample.core.api.APIList
import app.andika.newssample.databases.NewsDAO
import app.andika.newssample.model.AllNewsResponse
import app.andika.newssample.model.Article
import app.andika.newssample.utilities.API_KEY
import app.andika.newssample.utilities.ENDPOINT_DISPLAY_NEWS
import app.andika.newssample.utilities.runOnBackgroundThread
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import javax.inject.Inject

class NewsRepository @Inject constructor(
    @ApplicationContext var context: Context,
    val apiList: APIList,
    val newsDAO: NewsDAO
) {

    fun displayNewsList(query: String) : LiveData<AllNewsResponse> {
        val newsListResponse: MutableLiveData<AllNewsResponse> = MutableLiveData()

        apiList.displayAllNews(ENDPOINT_DISPLAY_NEWS + "q=" + query + "&apiKey=" + API_KEY).enqueue(object : Callback<AllNewsResponse> {

            override fun onResponse(call: Call<AllNewsResponse>, response: Response<AllNewsResponse>) {
                if (response.isSuccessful) {
                    newsListResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<AllNewsResponse>, t: Throwable) {
                newsListResponse.value = AllNewsResponse(t)
            }

        })

        return newsListResponse
    }

    fun getAllNews() : LiveData<List<Article>> {
        return newsDAO.getAll()
    }

    fun saveAllNews(articleList: List<Article>) {
        val insertCallable = Callable { newsDAO.insertAll(articleList) }
    }

    fun updateNews(article: Article) {
        runOnBackgroundThread {
            newsDAO.update(article)
        }
    }

    fun deleteAll() {
        runOnBackgroundThread {
            newsDAO.deleteAll()
        }
    }

}