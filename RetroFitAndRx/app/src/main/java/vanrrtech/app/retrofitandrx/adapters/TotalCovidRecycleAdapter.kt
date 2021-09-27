package vanrrtech.app.retrofitandrx.adapters
import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.datamodels.TotalCovidDataHolder
import vanrrtech.app.retrofitandrx.datamodels.ViewTypeDataHolder
import java.util.ArrayList
import kotlin.math.log


class TotalCovidRecycleAdapter (context: Context) : RecyclerView.Adapter<TotalCovidRecycleAdapter.ViewHolder>() {

    //TODO static view
    var mTotalCovid : TotalCovidDataHolder? = null
    var size = 4
    var context: Context
    var viewHolderReference : ArrayList <ViewHolder>? = ArrayList <ViewHolder>()

    init {
        mTotalCovid = TotalCovidDataHolder(null,null,null,null)
        this.context = context
    }

    @JvmName("setDataList1")
    public fun setDataList(totalCovid: TotalCovidDataHolder){
        mTotalCovid = totalCovid
        for (i in 0..viewHolderReference?.size!! - 1){
            viewHolderReference?.get(i)?.setData(totalCovid)
            viewHolderReference?.get(i)?.renderView()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.covid_monitoring_child_card, parent, false)
        return ViewHolder(view, this.context, mTotalCovid!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < viewHolderReference?.size!! && viewHolderReference?.size!! != 0){
            return
        }
        holder.setPos(position)
        holder.renderView()
        viewHolderReference?.add(holder)
    }

    override fun getItemCount(): Int {
        return size
    }

    class ViewHolder(itemView: View, context: Context, mTotalCovid : TotalCovidDataHolder) : RecyclerView.ViewHolder(itemView){
        var myView : View? = null
        var position : Int? = null
        var logo : ImageView? = null
        var title : TextView? = null
        var mContext : Context? = null
        var valueData : TextView? = null
        var mProgressBar : ProgressBar? = null
        var mTotalCovid = mTotalCovid
        init{
            mContext = context
            myView = itemView
            logo = myView?.findViewById(R.id.logo)
            title = myView?.findViewById(R.id.title_covid_data)
            valueData = myView?.findViewById(R.id.covid_total_value)
            mProgressBar = myView?.findViewById(R.id.loading)
        }
        fun setPos(pos : Int?){
            position = pos
        }
        fun setData(totalCovid: TotalCovidDataHolder){
            mTotalCovid = totalCovid
        }
        fun renderView(){
            when(position){
                0 -> { /** Covid Infection **/
                    mProgressBar?.visibility = View.VISIBLE
                    if(mTotalCovid.positiveCase != null){
                        likeTheRealRenderingStuff(mProgressBar, mContext, logo, title,
                            mTotalCovid.positiveCase, valueData, "Positive Case", R.drawable.coronavirus, "#c79e75")
                    }
                }
                1 -> { /** Covid Recovered **/
                    mProgressBar?.visibility = View.VISIBLE
                    if(mTotalCovid.positiveCase != null){
                        likeTheRealRenderingStuff(mProgressBar!!, mContext!!, logo!!, title!!,
                            mTotalCovid.recovered!!, valueData!!, "Recovered Case", R.drawable.recovered, "#00aeed")
                    }
                }
                2 -> { /** Covid Hospitalized **/
                    mProgressBar?.visibility = View.VISIBLE
                    if(mTotalCovid.positiveCase != null){
                        likeTheRealRenderingStuff(mProgressBar!!, mContext!!, logo!!, title!!,
                            mTotalCovid.hospitalized!!, valueData!!, "Horspitalized Case", R.drawable.hospital, "#f76161")
                    }
                }
                3 -> { /** Covid Passed Away **/
                    mProgressBar?.visibility = View.VISIBLE
                    if(mTotalCovid.positiveCase != null){
                        likeTheRealRenderingStuff(mProgressBar!!, mContext!!, logo!!, title!!,
                            mTotalCovid.deceased!!, valueData!!, "Deceased", R.drawable.deceased, "#A9A9A9")
                    }
                }
            }
        }

        fun likeTheRealRenderingStuff(progressBar: ProgressBar?,
                                      context: Context?, logo : ImageView?, title : TextView?,
                                      value : Int?, valueData : TextView?, titleString : String?, logoId : Int?, color : String?){
            progressBar?.visibility = View.GONE
            Picasso.with(context).load(logoId!!).into(logo)
            title?.text = titleString
            title?.setTextColor(Color.parseColor(color))
            valueData?.text = value!!.toString()
            valueData?.setTextColor(Color.parseColor(color))
        }
    }

}