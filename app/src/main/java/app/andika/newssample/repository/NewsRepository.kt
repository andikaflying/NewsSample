package app.andika.newssample.repository

import android.content.Context
import app.andika.newssample.core.api.APIList
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    @ApplicationContext var context: Context,
    val apiList: APIList
) {

}