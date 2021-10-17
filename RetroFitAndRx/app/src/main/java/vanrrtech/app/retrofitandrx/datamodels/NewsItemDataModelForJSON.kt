package vanrrtech.app.retrofitandrx.datamodels

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


/*
"articles": [
-{
-"source": {
"id": "the-wall-street-journal",
"name": "The Wall Street Journal"
},
"author": "Howard Husock",
"title": "Tech Billionaires Ignore the Philanthropy of Things - The Wall Street Journal",
"description": "Donors can do more by investing in amenities that are available to all, not nebulous social causes.",
"url": "https://www.wsj.com/articles/tech-billionaires-philanthropy-charity-silicon-civic-little-island-high-mile-park-donation-11631890975",
"urlToImage": "https://images.wsj.net/im-402222/social",
"publishedAt": "2021-09-17T21:46:00Z",
"content": "We live in an age of megaphilanthropy. Big name Silicon Valley billionaires channel their fortunes into social causes ranging from poverty and healthcare to education and gender equality. Two philant… [+837 chars]"
},
 */

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity (tableName = "news_article")
data class NewsItemDataModelForJSON (
    @PrimaryKey (autoGenerate = false)
    @Expose
    var id: String = UUID.randomUUID().toString(),

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val mTitle : String? = "",

    @SerializedName("publishedAt")
    @ColumnInfo(name = "publishedAt")
    val mDate : String? = "",

    @SerializedName("author")
    @ColumnInfo(name = "author")
    val mAuthor : String? = "",

    @SerializedName("urlToImage")
    @ColumnInfo(name = "urlToImage")
    val mUrlImage : String? = "",

    @SerializedName("url")
    @ColumnInfo(name = "url")
    val mUrl : String? = "",

    @SerializedName("content")
    @ColumnInfo(name = "content")
    val mContent : String? = ""
)