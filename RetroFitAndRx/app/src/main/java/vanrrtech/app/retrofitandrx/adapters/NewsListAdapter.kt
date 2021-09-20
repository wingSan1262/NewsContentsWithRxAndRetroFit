package vanrrtech.app.retrofitandrx.adapters

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import vanrrtech.app.retrofitandrx.datamodels.NewApiJsonDataHolder
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON
import vanrrtech.app.retrofitandrx.datamodels.ViewTypeDataHolder
import vanrrtech.app.retrofitandrx.restclient.RetrofitClientKt
import vanrrtech.app.retrofitandrx.restclient.RetrofitInterface
import java.util.*
import java.util.concurrent.Callable
import kotlin.collections.HashMap


class NewsListAdapter() : Observable(){
    var completeNewsList = HashMap<String, ViewTypeDataHolder>()
    var dataSizeParsingCounter : Int? = 0
    var mInterface : RetrofitInterface = RetrofitClientKt.getClient().create(RetrofitInterface::class.java)
    var compositeDisposable = CompositeDisposable()
    var mCompleteArrayList = ArrayList<ViewTypeDataHolder>()


    init {
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_PAGER_VIEW, "World News", 0))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Android", 1))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Bitcoin", 2))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_PAGER_VIEW, "Palestine", 3))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Indonesia", 4))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Stock", 5))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_PAGER_VIEW, "USA", 6))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Hajj", 7))
        mCompleteArrayList.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Jazz", 8))
//        completeNewsList.put("World News", ViewTypeDataHolder(RecycleViewAdapter.NEWS_PAGER_VIEW, "World News"))
//        completeNewsList.put("Android", ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Android"))
//        completeNewsList.put("Bitcoin", ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Bitcoin"))
//        completeNewsList.put("Indonesia", ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "Indonesia"))
        dataSizeParsingCounter = mCompleteArrayList.size
    }

    fun initFetchData (){
        for (i in 0..mCompleteArrayList.size-1){
            fetchAndParse(mCompleteArrayList.get(i))
        }
    }

    fun fetchAndParse(whatNewsToGet : ViewTypeDataHolder){
        var postion = whatNewsToGet?.pos
        var query = whatNewsToGet.mTitle?.lowercase(Locale.getDefault())
        if (whatNewsToGet.mTitle?.contains("world", true) == true){
            query = "world"
        }
        val disposable: Disposable = mInterface.getNewsContent(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<NewApiJsonDataHolder?> {
                @Throws(Exception::class)
                override fun accept(mResult: NewApiJsonDataHolder?) {
                    // Succed
                    // processing JSON
                    if(mResult?.status.equals("ok")){
                        parsingJson(mResult?.articles, whatNewsToGet.mTitle, postion)
                    }
                }
            }, object : Consumer<Throwable?> {
                @Throws(Exception::class)
                override fun accept(throwable: Throwable?) {
                    //Error
                    Log.e("Error Retrofit", throwable.toString())
                }
            })
        compositeDisposable.add(disposable)
    }


    fun parsingJson(article : List<NewsItemDataModelForJSON>?, key : String?, pos : Int?){
        var newsItemListData : ArrayList<NewsItemDataModelForJSON>? = ArrayList<NewsItemDataModelForJSON>()
        io.reactivex.Observable.fromCallable(object : Callable<Boolean?> {
            @Throws(java.lang.Exception::class)
            override fun call(): Boolean? {
                for (i in 0 until article?.size!!-1) {
                    newsItemListData?.add(article.get(i))
                }
                mCompleteArrayList.get(pos!!).setNewsItemList(newsItemListData)
                return true
            }
        }) // Execute in IO thread, i.e. background thread.
            .subscribeOn(Schedulers.io()) // report or post the result to main thread.
            .observeOn(AndroidSchedulers.mainThread()) // execute this RxJava
            .subscribe(object : io.reactivex.Observer<Boolean?>{
                override fun onSubscribe(d: Disposable?) {

                }
                override fun onNext(t: Boolean?) {

                }
                override fun onError(e: Throwable?) {

                }
                override fun onComplete() {
                    dataSizeParsingCounter = dataSizeParsingCounter?.minus(1)
                    if(dataSizeParsingCounter == 0){
                        setChanged()
                        notifyObservers()
                        dataSizeParsingCounter = completeNewsList.size
                    }

                }
            })
    }

    private fun unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun convertToArrayListBecauseLazyItIs(){
        io.reactivex.Observable.fromCallable(object : Callable<Boolean?> {
            @Throws(java.lang.Exception::class)
            override fun call(): Boolean? {
                val iterator: Iterator<*> = completeNewsList.iterator()
                while (iterator.hasNext()) {
                    val mapEntry = iterator.next() as Map.Entry<*, *>
                    mCompleteArrayList.add(mapEntry.value as ViewTypeDataHolder)
                }
                return true
            }
        }) // Execute in IO thread, i.e. background thread.
            .subscribeOn(Schedulers.io()) // report or post the result to main thread.
            .observeOn(AndroidSchedulers.mainThread()) // execute this RxJava
            .subscribe(object : io.reactivex.Observer<Boolean?>{
                override fun onSubscribe(d: Disposable?) {

                }
                override fun onNext(t: Boolean?) {

                }
                override fun onError(e: Throwable?) {

                }
                override fun onComplete() {
                }
            })
    }
    fun reset() {
        unSubscribeFromObservable()
    }
}