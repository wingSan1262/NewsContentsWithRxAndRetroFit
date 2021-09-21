package vanrrtech.app.retrofitandrx.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON
import vanrrtech.app.retrofitandrx.datamodels.ViewTypeDataHolder
import vanrrtech.app.retrofitandrx.views.NewsFragment
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class RecycleViewAdapter (context: Context, viewTypeList : ArrayList<ViewTypeDataHolder>, fragment: NewsFragment)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    var mForumArrayList : ArrayList<UserDataModel.ForumData>? = null
//    var mContext : Context? = null
//    var myOnClickListner : onClickListner? = null

    companion object {
        const val NEWS_PAGER_VIEW = 1
        const val NEWS_CARD_LIST_VIEW = 2
    }

    private var listChildViewHolder: ArrayList<RecyclerView.ViewHolder>? = ArrayList<RecyclerView.ViewHolder>()
    private var context: Context? = null
    var dataList : ArrayList<ViewTypeDataHolder>? = null
    var mfragment: NewsFragment? = null

    init {
        this.context = context
        dataList = viewTypeList
        mfragment = fragment
//        mForumArrayList = ForumArrayList
//        mContext = context
//        myOnClickListner = onClickListener
    }

    @JvmName("setDataList1")
    public fun setDataList(newDataList : ArrayList<ViewTypeDataHolder>?){
        this.dataList = newDataList
        for (i in 0..(listChildViewHolder?.size)!! -1){
            if(listChildViewHolder?.get(i)?.itemViewType == NEWS_PAGER_VIEW) {
                var mHolder = listChildViewHolder?.get(i) as View1ViewHolder
                (mHolder.viewPager?.adapter as SectionsPagerAdapter).setList(dataList?.get(i)?.mNewsItemData)
                mHolder.mTv?.text = dataList?.get(i)?.mTitle
            } else {
                var mHolder = listChildViewHolder?.get(i) as View2ViewHolder
                (mHolder.recyclerView?.adapter as NewsListViewAdapter).setList(dataList?.get(i)?.mNewsItemData)
                mHolder.mTv?.text = dataList?.get(i)?.mTitle
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == NEWS_PAGER_VIEW) {
            return View1ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.new_pages_layout, parent, false), NEWS_PAGER_VIEW
            )
        } else {
            return View2ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.news_card_list_view, parent, false), NEWS_CARD_LIST_VIEW
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // set view, set adapter for child recycle view . . .
        if (position < listChildViewHolder!!.size){
            return
        }
        if (dataList!!.get(position).mViewTyoe == NEWS_PAGER_VIEW) {
            var mHolder = (holder as View1ViewHolder)
            mHolder.renderDataToView(mfragment!!, position)
            listChildViewHolder?.add(mHolder)
            try {
                mHolder.mTv?.text = dataList?.get(position)?.mTitle
            }catch(exception : Exception){

            }
        }
        else {
            var mHolder = (holder as View2ViewHolder)
            mHolder.renderDataToView(position)
            listChildViewHolder?.add(mHolder)
            try {
                mHolder.mTv?.text = dataList?.get(position)?.mTitle
            }catch(exception : Exception){

            }
        }


    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        var viewType = dataList!!.get(position).mViewTyoe!!
        return viewType
    }

    private inner class View1ViewHolder(itemView: View, viewType : Int) :
        RecyclerView.ViewHolder(itemView) {
        var itemViewRefrence : View? = null
        var tag : Int? = null
        var viewPager : ViewPager? = null
        var mTv : TextView? = null
        // TODO var ArrayList<DataHolderChildview> -> bikin list untuk create adapter
        // TODO list bikin data model buat jenis child view ini
        init {
            tag = viewType
            itemViewRefrence = itemView
            mTv = itemViewRefrence?.findViewById(R.id.news_pager_title)!!
        }

        fun renderDataToView(fragment: NewsFragment, idPost : Int){
            val mList = ArrayList<NewsItemDataModelForJSON>()
            mList.addAll(dataList?.get(idPost)?.mNewsItemData!!)
            viewPager = itemViewRefrence?.findViewById(R.id.news_pager)!!
            val sectionsPagerAdapter = SectionsPagerAdapter(context!!, fragment.childFragmentManager, mList)
            viewPager?.id = idPost + 20
            viewPager?.adapter = sectionsPagerAdapter
        }

    }

    private inner class View2ViewHolder(itemView: View, viewType : Int) :
        RecyclerView.ViewHolder(itemView) {

        // TODO var ArrayList<DataHolderChildview> -> bikin list untuk create adapter
        // TODO list bikin data model buat jenis child view ini
        var itemViewRefrence : View? = null
        var tag : Int? = null
        var recyclerView : RecyclerView? = null
        var mTv : TextView? = null
        //var ArrayList<DataHolderChildview> -> bikin list untuk create adapter
        init {
            tag = viewType
            itemViewRefrence = itemView
            mTv = itemViewRefrence?.findViewById(R.id.news_list_title)!!
        }

        //TODO this is stil dummy using number
        fun renderDataToView(idPost : Int){
            val mList = ArrayList<NewsItemDataModelForJSON>()
            mList.addAll(dataList?.get(idPost)?.mNewsItemData!!)

            val manager = LinearLayoutManager(context)
            var horizontalLayout = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recyclerView = itemViewRefrence?.findViewById(R.id.news_list)
            recyclerView?.layoutManager = manager
            recyclerView?.layoutManager = horizontalLayout
            val mAdapter = NewsListViewAdapter(context!!, mfragment?.childFragmentManager!!, mList)
            recyclerView?.adapter = mAdapter

        }

    }


}
