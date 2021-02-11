package app.andika.newssample.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.andika.newssample.core.api.APIList
import app.andika.newssample.model.AllNewsResponse
import app.andika.newssample.utilities.API_KEY
import app.andika.newssample.utilities.ENDPOINT_DISPLAY_NEWS
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    @ApplicationContext var context: Context,
    val apiList: APIList
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
                TODO("Not yet implemented")
            }

        })

        return newsListResponse
    }

}