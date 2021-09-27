package vanrrtech.app.retrofitandrx.modelview

import com.github.mikephil.charting.data.Entry
import vanrrtech.app.retrofitandrx.adapters.NewsListAdapter
import vanrrtech.app.retrofitandrx.datamodels.ViewTypeDataHolder
import java.util.ArrayList
import java.util.Observable

class NewsModelView : Observable(), java.util.Observer{

    var mCompleteNewsList = ArrayList<ViewTypeDataHolder>()
    var mNewsAdapter : NewsListAdapter = NewsListAdapter()

    fun notifyObeserverAndUpdateUI(){
        mCompleteNewsList.clear()
        mCompleteNewsList = mNewsAdapter.mCompleteArrayList
        setChanged()
        notifyObservers()
    }

    fun startFetchingData(){
        mNewsAdapter.addObserver(this)
        mNewsAdapter.initFetchData()
    }

    fun getMyCompletNewsList(): ArrayList<ViewTypeDataHolder> {
        return mCompleteNewsList
    }

    override fun update(o: Observable?, arg: Any?) {
        notifyObeserverAndUpdateUI()
    }

}