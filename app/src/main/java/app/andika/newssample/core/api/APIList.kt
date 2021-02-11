package app.andika.newssample.core.api

import app.andika.newssample.model.AllNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIList {

    @GET
    public fun displayAllNews(@Url url: String) : Call<AllNewsResponse>
}