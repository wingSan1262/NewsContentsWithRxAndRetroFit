package vanrrtech.app.retrofitandrx.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON
import vanrrtech.app.retrofitandrx.views.ViewPagerCardFragments

private val TAB_TITLES = arrayOf(
    "News",
    "COVID"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, mListData : ArrayList<NewsItemDataModelForJSON>) : FragmentPagerAdapter(fm) {

    var mList : ArrayList<NewsItemDataModelForJSON>? = null
    var limitReload : Int? = 0
    var flagReload = false
    var type : Int = -1
    var mArrayGifList = ArrayList<Int>()
    init {
        mList = mListData
    }

    fun setList (list : ArrayList<NewsItemDataModelForJSON>?){
        mList = list
        flagReload = true
        notifyDataSetChanged()
    }

    fun setTypeContent (typeNews : Int){
        type = typeNews
    }

    fun setGifList(gifList : ArrayList<Int>){
        mArrayGifList = gifList
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a SignUpFragment (defined as a static inner class below).
        val fragment: Fragment

        if(type == ViewPagerCardFragments.COVID_CONTENT){
            fragment = ViewPagerCardFragments.newInstance(mList?.get(position)?.mTitle!!,
                mList?.get(position)?.mDate!!,
                mList?.get(position)?.mUrlImage!!,
                position, mList?.get(position)?.mUrl!!, type
            )
        } else if (type == ViewPagerCardFragments.COVID_CAMPAIGN){
            fragment = ViewPagerCardFragments.newInstance("",
                "",
                "",
                position, "", type, mArrayGifList.get(position)
            )
        }else {
            fragment = ViewPagerCardFragments.newInstance(mList?.get(position)?.mTitle!!,
                mList?.get(position)?.mDate!!,
                mList?.get(position)?.mUrlImage!!,
                position, mList?.get(position)?.mUrl!!
            )
        }


        limitReload = position
        return fragment
    }

    override fun getItemPosition(`object`: Any): Int {
        val fragment: ViewPagerCardFragments = `object` as ViewPagerCardFragments
        val pos = fragment.pos
        fragment.setArgAgain(mList?.get(pos!!)?.mTitle, mList?.get(limitReload!!)?.mDate!!, mList?.get(limitReload!!)?.mUrlImage, mList?.get(limitReload!!)?.mUrl!!)

        if (flagReload == true && pos!! <= limitReload!!){
            return POSITION_NONE
        } else {
            flagReload = false
            return POSITION_UNCHANGED
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        if(type == ViewPagerCardFragments.COVID_CAMPAIGN){
            return mArrayGifList.size
        }
        return mList!!.size
    }
}