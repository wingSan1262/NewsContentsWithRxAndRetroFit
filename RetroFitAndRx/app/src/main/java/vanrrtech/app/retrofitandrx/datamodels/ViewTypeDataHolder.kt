package vanrrtech.app.retrofitandrx.datamodels

class ViewTypeDataHolder(viewType : Int, title : String, position : Int?) {

    var pos : Int? = null
    var mViewTyoe : Int? = null
    var mTitle : String ? = null
    var mNewsItemData : ArrayList<NewsItemDataModelForJSON>? = ArrayList<NewsItemDataModelForJSON>()

    init {
        mNewsItemData?.add(NewsItemDataModelForJSON())
        mNewsItemData?.add(NewsItemDataModelForJSON())
        mNewsItemData?.add(NewsItemDataModelForJSON())
        mViewTyoe = viewType
        mTitle = title
        pos = position
    }

    fun setNewsItemList(newsItemData : ArrayList<NewsItemDataModelForJSON>?){
        mNewsItemData?.clear()
        mNewsItemData?.addAll(newsItemData!!)
    }

    fun getNewsItemList() : ArrayList<NewsItemDataModelForJSON>?{
        return mNewsItemData
    }
}