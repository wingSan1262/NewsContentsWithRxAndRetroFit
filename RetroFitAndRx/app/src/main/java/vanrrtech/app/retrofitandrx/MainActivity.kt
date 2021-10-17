package vanrrtech.app.retrofitandrx

import android.os.Bundle
import vanrrtech.app.retrofitandrx.databinding.MainActivityBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import retrofit2.Retrofit
import vanrrtech.app.retrofitandrx.adapters.NewsPagerAdapter
import vanrrtech.app.retrofitandrx.restclient.RetrofitClientKt
import vanrrtech.app.retrofitandrx.restclient.RetrofitInterface


class MainActivity : AppCompatActivity() {

    var mClient : Retrofit? = null

    var mBinding : MainActivityBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // replacing content view
        mBinding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)

        mClient = RetrofitClientKt.getClientNews()

        var mInterface : RetrofitInterface = mClient!!.create(RetrofitInterface::class.java)
        val sectionsPagerAdapter = NewsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = mBinding!!.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = mBinding!!.tabs
        tabs.setupWithViewPager(viewPager)
        tabs.tabMode = TabLayout.MODE_SCROLLABLE


        // dummy test

//        tabs.setSelectedTabIndicatorColor(Color.parseColor("#7148e1"));
//        tabs.setSelectedTabIndicatorHeight( ((5 * getResources().getDisplayMetrics().density).toInt()));
    }
}