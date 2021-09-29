package vanrrtech.app.retrofitandrx.modelview

import android.graphics.Color
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vanrrtech.app.retrofitandrx.adapters.NewsListAdapter
import vanrrtech.app.retrofitandrx.datamodels.*
import vanrrtech.app.retrofitandrx.restclient.RetrofitClientKt
import vanrrtech.app.retrofitandrx.restclient.RetrofitInterface
import java.util.ArrayList
import java.util.Observable
import java.util.concurrent.Callable

class CovidModelView : Observable(){

    var mTotalCovidDataHolder : TotalCovidDataHolder? = null
    var mDailyCovidHolder : List<DailyDataClass.Companion.dailyHolder>? = null
    var compositeDisposable = CompositeDisposable()


    var mCovidGraphData = ArrayList<Entry>()
    var mDeceasedGraphdata = ArrayList<Entry>()
    var mCovidArticles : List<NewsItemDataModelForJSON>? = null

    var runningThreads : Int = 0
    fun notifyObeserverAndUpdateUI(){
        // TODO use this to notify the UI fragment
        setChanged()
        notifyObservers()
    }

    fun startFetchingData(){
        // TODO fetching data using RX
        runningThreads = 2;
        val mInterfaceClient = RetrofitClientKt.getClientCovid().create(RetrofitInterface::class.java)
        val call = mInterfaceClient.getCovidContent()
        call?.enqueue(object : Callback<CovidJsonDataHolder?> {
            override fun onResponse(
                call: Call<CovidJsonDataHolder?>?,
                response: Response<CovidJsonDataHolder?>?
            ) {
                runningThreads -= 1
                Log.i("Covid", "succeded")
                mTotalCovidDataHolder = response?.body()?.update?.total
                mDailyCovidHolder = (response?.body()?.update?.dailyGraph as List<DailyDataClass.Companion.dailyHolder>?)
                parseAndUpdateUI()
            }
            override fun onFailure(call: Call<CovidJsonDataHolder?>?, t: Throwable?) {
                Log.i("Covid", "failed")
            }
        })

        val mInterfaceNews = RetrofitClientKt.mRetrofitNewsClient?.create(RetrofitInterface::class.java)

        val disposable: Disposable = mInterfaceNews!!.getNewsContent("Corona-indonesia")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<NewApiJsonDataHolder?> {
                @Throws(Exception::class)
                override fun accept(mResult: NewApiJsonDataHolder?) {
                    // Succed
                    // processing JSON
                    if(mResult?.status.equals("ok")){
                        mCovidArticles = mResult?.articles
                        runningThreads -= 1
                        parseAndUpdateUI()
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

    fun parseAndUpdateUI(){
        if(runningThreads > 0){
            return
        }
        runningThreads = 0
        io.reactivex.Observable.fromCallable(object : Callable<Boolean?> {
            @Throws(java.lang.Exception::class)
            override fun call(): Boolean? {
                mCovidGraphData.clear()
                for(i in 0..19){
                    val x : Float = i.toFloat()
                    val covidValue = mDailyCovidHolder?.get((mDailyCovidHolder?.size!!-1) - ((i)*15))?.positive?.value
                    var deceasedValue = mDailyCovidHolder?.get((mDailyCovidHolder?.size!!-1) - ((i)*15))?.meninggal?.value
                    mCovidGraphData.add(Entry( x, covidValue?.toFloat()!!))
                    mDeceasedGraphdata.add(Entry( x, deceasedValue?.toFloat()!!))
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

                    notifyObeserverAndUpdateUI()
                }
            })



    }

    fun getMyCompleteCovidList(): TotalCovidDataHolder? {
        return mTotalCovidDataHolder
    }


}