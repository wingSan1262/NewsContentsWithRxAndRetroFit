package vanrrtech.app.retrofitandrx.views

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import io.reactivex.Observer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.adapters.NewsListViewAdapter
import vanrrtech.app.retrofitandrx.adapters.SectionsPagerAdapter
import vanrrtech.app.retrofitandrx.adapters.TotalCovidRecycleAdapter
import vanrrtech.app.retrofitandrx.databinding.FragmentCovidBinding
import vanrrtech.app.retrofitandrx.datamodels.CovidJsonDataHolder
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON
import vanrrtech.app.retrofitandrx.datamodels.TotalCovidDataHolder
import vanrrtech.app.retrofitandrx.modelview.CovidModelView
import vanrrtech.app.retrofitandrx.restclient.RetrofitClientKt
import vanrrtech.app.retrofitandrx.restclient.RetrofitInterface
import vanrrtech.app.retrofitandrx.utils.AxisDateFormatter
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CovidFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CovidFragment : Fragment(), java.util.Observer {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mBinding: FragmentCovidBinding? = null
    private var mModelView : CovidModelView? = CovidModelView()
    private var mRecyclerView : RecyclerView? = null

    private var covidCampaigViewPager : ViewPager? = null
    private var mHospitalImageview : ImageView? = null
    private var hospitalTextView : TextView? = null


    private var mViewPager : ViewPager? = null

    var covidCase : LineChart? = null
    var deathCase : LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mModelView?.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        mBinding = FragmentCovidBinding.inflate(inflater, container, false)

        //setUpRecyclerView
        mRecyclerView = mBinding?.covidMonitorRv

        val manager = LinearLayoutManager(context)
        var horizontalLayout = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mRecyclerView?.layoutManager = manager
        mRecyclerView?.layoutManager = horizontalLayout
        val mAdapter = TotalCovidRecycleAdapter(requireContext())
        mRecyclerView?.adapter = mAdapter
        mModelView?.startFetchingData()

        covidCampaigViewPager = mBinding?.covidCampaignBanner
        mHospitalImageview = mBinding!!.hospitalLayout.findViewById(R.id.image_card)
        hospitalTextView = mBinding!!.hospitalLayout.findViewById(R.id.headline_card)
        (mBinding!!.hospitalLayout.findViewById(R.id.progress_bar_circular) as ProgressBar).visibility = View.INVISIBLE

        setCovidCampaign()
        Glide.with(requireContext()).load(R.drawable.ambulance).into(mHospitalImageview!!)
        hospitalTextView?.text = "click here to find nearby hospital"

        covidCase = mBinding?.chartCovid
        deathCase = mBinding?.deathChart
        mViewPager = mBinding?.covidIndonesiaNews
        return  mBinding?.root
    }

    private fun setCovidCampaign() {
        val mList = ArrayList<NewsItemDataModelForJSON>()
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), childFragmentManager, mList)
        sectionsPagerAdapter.setTypeContent(ViewPagerCardFragments.COVID_CAMPAIGN)
        val mGifList = ArrayList<Int>()
        mGifList.add(R.drawable.wear_mask)
        mGifList.add(R.drawable.wash_hand)
        mGifList.add(R.drawable.keep_distance)
        sectionsPagerAdapter.setGifList(mGifList)
        covidCampaigViewPager?.adapter = sectionsPagerAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MemeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CovidFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun update(o: Observable?, arg: Any?) {
        (mRecyclerView?.adapter as TotalCovidRecycleAdapter).setDataList(mModelView?.mTotalCovidDataHolder!!)
        setNewsPagerCovid()
        setGraph()
    }

    fun setNewsPagerCovid(){
        val mList = ArrayList<NewsItemDataModelForJSON>()
        mList.addAll(mModelView?.mCovidArticles!!)
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), childFragmentManager, mList)
        sectionsPagerAdapter.setTypeContent(ViewPagerCardFragments.COVID_CONTENT)
        mViewPager?.adapter = sectionsPagerAdapter
    }

    fun setGraph(){
        var mCovidGraphData = mModelView?.mCovidGraphData
        var mDeathGraph = mModelView?.mDeceasedGraphdata
        val mDate = mModelView?.mCovidDate
        Collections.reverse(mDate)

        val tanggal = AxisDateFormatter(mDate!!.toArray(arrayOfNulls<String>(mDate.size)))

        //Setup Legend
        val legend = covidCase?.legend
        legend?.isEnabled = true
        legend?.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legend?.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)
        legend?.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legend?.setDrawInside(false)

        val kasusLineDataSet = LineDataSet(mCovidGraphData, "Positive Case")
        kasusLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        kasusLineDataSet.color = Color.BLUE
        kasusLineDataSet.circleRadius = 5f
        kasusLineDataSet.setCircleColor(Color.BLUE)
        kasusLineDataSet.setDrawFilled(true)
        kasusLineDataSet.fillColor = Color.BLUE

        covidCase?.description?.isEnabled = false
        covidCase?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        covidCase?.data = LineData(kasusLineDataSet)
        covidCase?.animateXY(100, 500)
        covidCase?.xAxis?.valueFormatter = tanggal
        covidCase?.xAxis?.setLabelCount(4, true)


        val meninggalLineDataSet = LineDataSet(mDeathGraph, "Death Case")
        meninggalLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        meninggalLineDataSet.color = resources.getColor(R.color.purple_700)
        meninggalLineDataSet.circleRadius = 5f
        meninggalLineDataSet.setDrawFilled(true)
        meninggalLineDataSet.fillColor = resources.getColor(R.color.purple_200)
        meninggalLineDataSet.setCircleColor(resources.getColor(R.color.purple_700))

        val legendDeath = deathCase?.legend
        legendDeath?.isEnabled = true
        legendDeath?.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legendDeath?.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)
        legendDeath?.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legendDeath?.setDrawInside(false)

        deathCase?.description?.isEnabled = false
        deathCase?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        deathCase?.data = LineData(meninggalLineDataSet)
        deathCase?.animateXY(100, 500)
        deathCase?.xAxis?.valueFormatter = tanggal
        deathCase?.xAxis?.setLabelCount(4, true)

        // set
        val mVaccineBarGraph = mBinding?.vaccineBarChart
        val legendVaccine = mVaccineBarGraph!!.legend
        legendVaccine.isEnabled = true
        legendVaccine.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legendVaccine.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER)
        legendVaccine.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legendVaccine.setDrawInside(false)

        if (mModelView?.mVaccineData == null){
            return;
        }
        val vaccineData = ArrayList<BarEntry>()
        vaccineData.add(BarEntry(1f, mModelView?.mVaccineData?.vaksin2?.toFloat()!! * 100 / mModelView?.mVaccineData?.totalsasaran?.toFloat()!!))
        vaccineData.add(BarEntry(2f, mModelView?.mVaccineData?.vaksin1?.toFloat()!! * 100 / mModelView?.mVaccineData?.totalsasaran?.toFloat()!!))

        val vaccineBarDataSet = BarDataSet(vaccineData, "Vaccination (%)")
        vaccineBarDataSet.color = Color.BLUE

        // bar graph data
        var vaksinXAxis = ArrayList<String>();
        vaksinXAxis.add("vaksin 1")
        vaksinXAxis.add("vaksin 2")
        val xAxisVaksin = AxisDateFormatter(vaksinXAxis.toArray(arrayOfNulls<String>(vaksinXAxis.size)))

        mVaccineBarGraph.description.isEnabled = false
        mVaccineBarGraph.xAxis.position = XAxis.XAxisPosition.BOTTOM
        mVaccineBarGraph.data = BarData(vaccineBarDataSet)
        mVaccineBarGraph.animateXY(100, 500)
        mVaccineBarGraph.axisLeft.axisMaximum = 100F
        mVaccineBarGraph.axisRight.axisMaximum = 100F
        mVaccineBarGraph.xAxis.setLabelCount(0, true)

        mBinding?.targetVaccine?.text = "Target: ${BigDecimal(mModelView?.mVaccineData?.totalsasaran!!).toPlainString()} person"
    }
}