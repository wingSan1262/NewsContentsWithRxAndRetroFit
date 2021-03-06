package vanrrtech.app.retrofitandrx.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.databinding.FragmentViewPagerCardFragmentsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAMURL = "url"
private const val ARG_TYPE = "type"

private const val ARG_GIF_RES_ID = "res_id"

private const val ARG_PARAMPOS = "parampos"




/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerCardFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPagerCardFragments : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var urlNews: String? = null
    private var typeNews : Int? = null
     var pos: Int? = null
    var mGifResId : Int? = null

    var mBinding : FragmentViewPagerCardFragmentsBinding? = null

    fun setArgAgain(newParam1 : String?, newParam2 : String, newParam3 : String?, newUrlNews : String){
        param1 = newParam1
        param2 = newParam2
        param3 = newParam3
        urlNews = newUrlNews
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            pos = it.getInt(ARG_PARAMPOS)
            urlNews = it.getString(ARG_PARAMURL)
            typeNews = it.getInt(ARG_TYPE)
            mGifResId = it.getInt(ARG_GIF_RES_ID)
        }
    }

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentViewPagerCardFragmentsBinding.inflate(inflater, container, false)
        mBinding?.headline?.text = param1
        mBinding?.newsPagerDate?.text = param2

        mBinding?.progressBarCircular?.visibility = View.VISIBLE

        if(pos!!%2 != 0){
            mBinding?.containerNewsPager?.setBackgroundColor(Color.parseColor("#ddcabaf3"))
        } else {
            mBinding?.headline?.setTextColor(resources.getColor(R.color.white, null))
            mBinding?.newsPagerDate?.setTextColor(resources.getColor(R.color.white, null))
        }

        if(typeNews == ViewPagerCardFragments.COVID_CONTENT){
            mBinding?.containerNewsPager?.setBackgroundColor(Color.parseColor("#BBf0f1f3"))
            mBinding?.headline?.setTextColor(resources.getColor(R.color.black, null))
            mBinding?.newsPagerDate?.setTextColor(resources.getColor(R.color.black, null))
        } else if (typeNews == ViewPagerCardFragments.COVID_CAMPAIGN){
            mBinding?.containerNewsPager?.setBackgroundColor(Color.TRANSPARENT)
            mBinding?.headline?.visibility = View.INVISIBLE
            mBinding?.newsPagerDate?.visibility = View.INVISIBLE
            mBinding?.progressBarCircular?.visibility = View.INVISIBLE
        }



        // covid color #4287f5
        var mImgView = mBinding?.cardFragmentBackgroundImage
        if( !param3.equals("") && typeNews != COVID_CAMPAIGN){

            Picasso.with(this.context).load(param3).into(mImgView, object :
                Callback {
                override fun onSuccess() {
                    mImgView?.visibility = View.VISIBLE
                    mBinding?.progressBarCircular?.visibility = View.INVISIBLE
                }

                override fun onError() {
                    mImgView?.visibility = View.INVISIBLE
                    mBinding?.progressBarCircular?.visibility = View.VISIBLE
                }

            })
        } else {
            Glide.with(requireContext()).load(mGifResId).into(mImgView!!)
        }
        mBinding?.root?.setOnClickListener {
            if(urlNews != null){
                var intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", urlNews)
                startActivity(intent)
            }
        }

        return mBinding?.root
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        public const val COVID_CONTENT = 777
        const val COVID_CAMPAIGN = 776
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewPagerCardFragments.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: Int, newsURL:String?) =
            ViewPagerCardFragments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAMURL, newsURL)
                    putInt(ARG_PARAMPOS, param4)
                }
            }

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: Int, newsURL:String?, type:Int?) =
            ViewPagerCardFragments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAMURL, newsURL)
                    putInt(ARG_PARAMPOS, param4)
                    putInt(ARG_TYPE, type!!)
                }
            }
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: Int, newsURL:String?, type:Int?, gifResId : Int?) =
            ViewPagerCardFragments().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putString(ARG_PARAMURL, newsURL)
                    putInt(ARG_PARAMPOS, param4)
                    putInt(ARG_TYPE, type!!)
                    putInt(ARG_GIF_RES_ID, gifResId!!)
                }
            }
    }
}