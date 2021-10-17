package vanrrtech.app.retrofitandrx.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import vanrrtech.app.retrofitandrx.datamodels.NewsItemDataModelForJSON
import vanrrtech.app.retrofitandrx.views.FightCovidWelcomeFragment
import vanrrtech.app.retrofitandrx.views.ReadNewsWelcomeFragment
import vanrrtech.app.retrofitandrx.views.ViewPagerCardFragments
import vanrrtech.app.retrofitandrx.views.WelcomeEnterNewsApiToken

private val TAB_TITLES = arrayOf(
    "News",
    "COVID"
)

enum class guide{
    NEWS_GUIDE, COVID_GUIDE,
}

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class WelcomeGuideMessage(fm: FragmentManager, cb: CB) : FragmentPagerAdapter(fm) {


    var mcb : CB? = null
    init {
        mcb = cb
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a SignUpFragment (defined as a static inner class below).

        when(position){
            guide.NEWS_GUIDE.ordinal -> {
//                mcb?.updateStartButton(false)
                val frag = ReadNewsWelcomeFragment.newInstance("", "")
                return frag
            }
            guide.COVID_GUIDE.ordinal -> {
//                mcb?.updateStartButton(false)
                return FightCovidWelcomeFragment.newInstance("", "")
            }
        }
//        mcb?.updateStartButton(true))
        return  WelcomeEnterNewsApiToken.newInstance("","")
    }

    override fun getItemPosition(`object`: Any): Int {
//        val fragment: ViewPagerCardFragments = `object` as ViewPagerCardFragments
//        val pos = fragment.pos
//        fragment.setArgAgain(mList?.get(pos!!)?.mTitle, mList?.get(limitReload!!)?.mDate!!, mList?.get(limitReload!!)?.mUrlImage, mList?.get(limitReload!!)?.mUrl!!)
//
//        if (flagReload == true && pos!! <= limitReload!!){
//            return POSITION_NONE
//        } else {
//            flagReload = false
            return POSITION_UNCHANGED
//        }
    }




    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}

interface CB {
    fun updateStartButton(boolean: Boolean)
}