package vanrrtech.app.retrofitandrx.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.adapters.NewsListViewAdapter
import vanrrtech.app.retrofitandrx.adapters.TotalCovidRecycleAdapter
import vanrrtech.app.retrofitandrx.databinding.FragmentCovidBinding
import vanrrtech.app.retrofitandrx.datamodels.CovidJsonDataHolder
import vanrrtech.app.retrofitandrx.datamodels.TotalCovidDataHolder
import vanrrtech.app.retrofitandrx.modelview.CovidModelView
import vanrrtech.app.retrofitandrx.restclient.RetrofitClientKt
import vanrrtech.app.retrofitandrx.restclient.RetrofitInterface
import java.util.*

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
        return  mBinding?.root
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

    override fun update(o: Observable?, arg: Any?) {
        (mRecyclerView?.adapter as TotalCovidRecycleAdapter).setDataList(mModelView?.mTotalCovidDataHolder!!)
    }
}