package app.andika.newssample.databases

import androidx.lifecycle.LiveData
import androidx.room.*
import app.andika.newssample.model.Article

@Dao
interface NewsDAO {

    @Query("SELECT * FROM article")
    fun getAll(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public fun insertAll(articles : List<Article>)

    @Update
    public fun update(article: Article)

    @Delete
    public fun delete(article: Article)

    @Query("DELETE FROM article")
    public fun deleteAll()

    @Query("SELECT * FROM article LIMIT :index,1")
    fun getNews(index: Int): LiveData<Article>
}