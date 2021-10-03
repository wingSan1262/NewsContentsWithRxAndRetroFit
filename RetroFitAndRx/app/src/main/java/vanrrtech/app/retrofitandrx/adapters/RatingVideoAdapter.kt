package vanrrtech.app.retrofitandrx.adapters
//import com.squareup.picasso.Picasso

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.roomdb.ArticleDataBase
import java.util.*
import java.util.concurrent.Callable


class RatingVideoAdapter (context: Context, listVideo : ArrayList<myVideoSumthing>) : RecyclerView.Adapter<RatingVideoAdapter.ViewHolder>() {

    data class myVideoSumthing(
        var myThumb : String?,
        var myTitle : String?,
        var myDuration : String?,
        var myDesc : String?,
        var myRating : Int?,
        var myVideoUrl : String?
    )


    var mVideoList = ArrayList<myVideoSumthing>()
    var context: Context
    var viewHolderReference = ArrayList<ViewHolder>()

    init {
        mVideoList = listVideo
        this.context = context
    }

    @JvmName("setDataList1")
    //update data if user refresh the screen or after fetch new data to back end or something
    public fun setDataList(listVideo : ArrayList<myVideoSumthing>){
        mVideoList = listVideo
        for (i in 0..viewHolderReference?.size!! - 1){
            viewHolderReference?.get(i)?.setData(mVideoList.get(i))
            viewHolderReference?.get(i)?.renderView()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.news_card_list_view_card_child, parent, false)
        return ViewHolder(view, this.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < viewHolderReference?.size!! && viewHolderReference?.size!! != 0){
            return
        }
        holder.setData(mVideoList.get(position))
        holder.renderView()
        viewHolderReference?.add(holder)
    }

    override fun getItemCount(): Int {
        return mVideoList.size
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){

        // view and reference for object
        var myView : View? = null
        var thumb : ImageView? = null
        var rating : ImageView? = null
        var download : ImageView? = null
        var title : TextView? = null
        var duration : TextView? = null
        var desc : TextView? = null
        var dataVideo : myVideoSumthing? = null

        // for asynchronous thumbnaik load
        var mContext : Context? = null

        init{
            // obtain view . . .
            mContext = context
            myView = itemView

            thumb = myView?.findViewById(R.id.image_card)
            rating = myView?.findViewById(R.id.rating_view)
            download = myView?.findViewById(R.id.download_button)

            title = myView?.findViewById(R.id.video_title)
            duration = myView?.findViewById(R.id.video_duration)
            desc = myView?.findViewById(R.id.video_desc)
        }
        // updating data incase user update the content by refreshing
        fun setData(theVideoItem : myVideoSumthing){
            dataVideo = theVideoItem
        }
        fun renderView(){
            // image render
            Glide.with(mContext!!).load(dataVideo?.myThumb).into(thumb!!)

            // text render
            title?.text = dataVideo?.myTitle
            duration?.text = dataVideo?.myDuration
            desc?.text = dataVideo?.myDesc
            thumb!!.setOnClickListener {
                 // Do Download Task
                dataVideo?.myVideoUrl?.let{
                    val uri: Uri = Uri.parse(it)
                    downloadVideo(uri, mContext!!)
                }
            }

            when (dataVideo?.myRating){
                1 -> {Glide.with(mContext!!).load(R.drawable.ambulance).into(rating!!) }
                2 -> {Glide.with(mContext!!).load(R.drawable.ambulance).into(rating!!) }
                3 -> { Glide.with(mContext!!).load(R.drawable.ambulance).into(rating!!)}
                4 -> {Glide.with(mContext!!).load(R.drawable.ambulance).into(rating!!) }
                5 -> { Glide.with(mContext!!).load(R.drawable.ambulance).into(rating!!)}
            }
        }

        fun downloadVideo(it : Uri, context: Context){
            var result = true
            var downloading = true
            Glide.with(mContext!!).load(R.drawable.ic_downloading).into(download)
            io.reactivex.Observable.fromCallable(object : Callable<Boolean?> {
                @Throws(java.lang.Exception::class)
                override fun call(): Boolean? {
                    val mManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
                    val mRqRequest = DownloadManager.Request(
                        it
                    )
                    val idDownLoad = mManager!!.enqueue(mRqRequest)
                    var query: DownloadManager.Query? = DownloadManager.Query()
                    var c: Cursor? = null

                    if (query != null) {
                        query.setFilterByStatus(DownloadManager.STATUS_FAILED or DownloadManager.STATUS_PAUSED or DownloadManager.STATUS_SUCCESSFUL or DownloadManager.STATUS_RUNNING or DownloadManager.STATUS_PENDING)
                    } else {
                        result = true
                        // TODO , this return was just to finish the work thread
                        return true
                    }

                    while (downloading) {
                        // watching the download status in back ground thread
                        c = mManager.query(query)
                        if (c.moveToFirst()) {
                            val status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                downloading = false
                                result = true
                                break
                            }
                            if (status == DownloadManager.STATUS_FAILED) {
                                downloading = false
                                result = false
                                break
                            }
                        }
                    }
                    // TODO , this return was just to finish the work thread
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
                        // TODO complete Main Thread update UI
                        if(result == true){
                            download?.visibility = View.INVISIBLE
                        }

                    }
                })




        }


    }

}