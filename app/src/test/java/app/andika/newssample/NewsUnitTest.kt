package app.andika.newssample

import android.os.Parcelable
import app.andika.newssample.model.AllNewsResponse
import app.andika.newssample.model.Article
import app.andika.newssample.model.Source
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NewsUnitTest {
    lateinit var completedArticle: Article
    lateinit var uncompletedArticle: Article

    @Before
    fun setup() {
        completedArticle = Article("Robert Q", "This is sample content", "Some description",
                "12 February 2020", "News Title",
                "http://google.com", "http://google.com/test.jpg")
        uncompletedArticle = Article(null, "This is sample content", null,
                "12 February 2020", "News Title",
                null, null)
    }

    @Test
    fun testNewsList_ifEmptyList() {
        val allNewsResponse : AllNewsResponse = AllNewsResponse(mutableListOf(), "Success", 0)
        assertEquals(0, allNewsResponse.articles!!.size)
        assertEquals(0, allNewsResponse.totalResults)
    }

    @Test
    fun testNewsList_ifListIsAvailable() {
        var articles: MutableList<Article>? = mutableListOf()
        articles!!.add(completedArticle)
        articles!!.add(completedArticle)

        val allNewsResponse : AllNewsResponse = AllNewsResponse(articles, "Success", articles.size)
        assertEquals(2, allNewsResponse.articles!!.size)
        assertEquals(2, allNewsResponse.totalResults)
    }

    @Test
    fun testNews_ifNewsHasCompletedInformation() {
        assertNotNull(completedArticle.author)
        assertNotNull(completedArticle.content)
        assertNotNull(completedArticle.description)
        assertNotNull(completedArticle.publishedAt)
        assertNotNull(completedArticle.title)
        assertNotNull(completedArticle.url)
        assertNotNull(completedArticle.urlToImage)
    }

    @Test
    fun testNews_ifNewsHasUncompletedInformation() {
        assertNotNull(uncompletedArticle.content)
        assertNotNull(uncompletedArticle.title)
        assertNotNull(uncompletedArticle.publishedAt)
    }

    @Test
    fun testFavoriteNews_defaultValue() {
        assertEquals(false, completedArticle.isFavorite)
    }

    @Test
    fun testFavoriteNews_ifFavorited() {
        completedArticle.isFavorite = true
        assertEquals(true, completedArticle.isFavorite)
    }
}