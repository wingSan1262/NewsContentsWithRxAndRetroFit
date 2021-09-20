package vanrrtech.app.retrofitandrx.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import vanrrtech.app.retrofitandrx.views.MemeFragment
import vanrrtech.app.retrofitandrx.views.NewsFragment

private val TAB_TITLES = arrayOf(
    "News",
    "Meme"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class NewsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a SignUpFragment (defined as a static inner class below).
        val fragment: Fragment

        when(position) {
            0 -> fragment = NewsFragment.newInstance("null", "null")
            1 -> fragment = MemeFragment.newInstance("null", "null")
            else -> {
                fragment = NewsFragment.newInstance("null", "null")
            }
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}