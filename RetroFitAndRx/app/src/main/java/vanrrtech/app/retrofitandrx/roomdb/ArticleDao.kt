package vanrrtech.app.retrofitandrx.roomdb

import androidx.room.*
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON

@Dao
interface ArticleDao {
    @Query("Select * from news_article")
    fun loadAllSavedArticle() : List <NewsItemDataModelForJSON>
    @Insert
    fun insertArticle(article : NewsItemDataModelForJSON)
    @Update
    fun updatePerson(article : NewsItemDataModelForJSON)
    @Delete
    fun deletePerson(article : NewsItemDataModelForJSON)
}