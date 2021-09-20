package vanrrtech.app.retrofitandrx.datamodels

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

data class NewsItemDataModelForJSON (
    @SerializedName("title") val mTitle : String? = "",
    @SerializedName("publishedAt") val mDate : String? = "",
    @SerializedName("author") val mAuthor : String? = "",
    @SerializedName("urlToImage") val mUrlImage : String? = "",
    @SerializedName("url") val mUrl : String? = "",
    @SerializedName("content") val mContent : String? = ""
)