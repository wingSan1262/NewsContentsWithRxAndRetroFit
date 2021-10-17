package vanrrtech.app.retrofitandrx.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vanrrtech.app.retrofitandrx.adapters.RecycleViewAdapter
import vanrrtech.app.retrofitandrx.databinding.FragmentNewsBinding
import vanrrtech.app.retrofitandrx.datamodels.ViewTypeDataHolder
import vanrrtech.app.retrofitandrx.modelview.NewsModelView
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment(), java.util.Observer{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var mModelView : NewsModelView? = null

    var mBinding : FragmentNewsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mModelView = NewsModelView(requireContext())
        setUpObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentNewsBinding.inflate(inflater, container, false)

        var listViewTypes = ArrayList<ViewTypeDataHolder>()
        listViewTypes.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_PAGER_VIEW, "null", 0))
        listViewTypes.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "null", 1))
        listViewTypes.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "null", 2))
        listViewTypes.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_PAGER_VIEW, "null", 3))
        listViewTypes.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "null", 4))
        listViewTypes.add(ViewTypeDataHolder(RecycleViewAdapter.NEWS_CARD_LIST_VIEW, "null", 5))

        val adapter = RecycleViewAdapter(requireContext(), listViewTypes, this)
        var recyclerView = mBinding?.newsRecycleView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = adapter

        return mBinding?.root
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        mModelView?.startFetchingData()
    }

    fun setUpObserver(){
        mModelView?.addObserver(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun update(o: Observable?, arg: Any?) {
        var listViewTypes = ArrayList<ViewTypeDataHolder>()
        listViewTypes.addAll(mModelView?.mCompleteNewsList!!)
        var recyclerViewAdapter = mBinding?.newsRecycleView?.adapter as RecycleViewAdapter
        recyclerViewAdapter.setDataList(listViewTypes)
        Log.i("updating", "from modelview")
    }
}