package vanrrtech.app.retrofitandrx.adapters
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON
import vanrrtech.app.retrofitandrx.views.WebViewActivity
import java.util.ArrayList


class NewsListViewAdapter (private val context: Context, fm: FragmentManager, listData : ArrayList<NewsItemDataModelForJSON>) : RecyclerView.Adapter<NewsListViewAdapter.ViewHolder>() {

    var mFragmentManager:FragmentManager? = null
    var mList : ArrayList<NewsItemDataModelForJSON>? = null
    var mContext : Context ? = null

    init {
        mList = listData
        mFragmentManager = fm
        mContext = context
    }


    fun setList (list : ArrayList<NewsItemDataModelForJSON>?) {
        mList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.news_card_list_view_card_child, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var pBar = holder.mView?.findViewById<ProgressBar>(R.id.progress_bar_circular)
        pBar?.visibility = View.VISIBLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pBar?.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#7047df")))
        };

        holder.mView?.findViewById<TextView>(R.id.headline_card)?.text = mList?.get(position)?.mTitle
        if (mList?.get(position)?.mUrlImage != ""){
            var mImgView = holder.mView?.findViewById<ImageView>(R.id.image_card)
            Picasso.with(this.context).load(mList?.get(position)?.mUrlImage).into(mImgView, object : Callback{
                override fun onSuccess() {
                    mImgView?.visibility = View.VISIBLE
                    var pBar = holder.mView?.findViewById<ProgressBar>(R.id.progress_bar_circular)
                    pBar?.visibility = View.INVISIBLE
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        pBar?.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#7047df")))
                    };
                }

                override fun onError() {
                    var pBar = holder.mView?.findViewById<ProgressBar>(R.id.progress_bar_circular)
                    mImgView?.visibility = View.INVISIBLE
                    pBar?.visibility = View.VISIBLE
                }

            })

            holder.mView?.setOnClickListener {
                if (mList?.get(position)?.mUrl != null || mList?.get(position)?.mUrl != ""){
                    var intent = Intent(context, WebViewActivity::class.java)
                    intent.putExtra("url", mList?.get(position)?.mUrl)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return  mList!!.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var mView : View? = null

        init {
            mView = view
        }

    }

}