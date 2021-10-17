package vanrrtech.app.retrofitandrx.views

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import vanrrtech.app.retrofitandrx.MainActivity
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.adapters.CB
import vanrrtech.app.retrofitandrx.adapters.WelcomeGuideMessage
import vanrrtech.app.retrofitandrx.restclient.RetrofitClientKt

class WelcomeActivity : AppCompatActivity() , CB, View.OnClickListener{

    var mViewPager : ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        mViewPager = findViewById<ViewPager>(R.id.welcome_view_pager)
        mViewPager?.adapter = WelcomeGuideMessage(supportFragmentManager, this)

        findViewById<TextView>(R.id.next_button).setOnClickListener(this)
        findViewById<TextView>(R.id.prev_button).setOnClickListener(this)
        findViewById<Button>(R.id.start_button).setOnClickListener(this)

        val mTabLayout = findViewById<TabLayout>(R.id.welcome_vp_tablayout)
        mTabLayout.setupWithViewPager(mViewPager, true)

        mViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                updateNavButton()
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    override fun updateStartButton(boolean: Boolean) {
        findViewById<Button>(R.id.start_button).visibility = View.INVISIBLE
        if(boolean == true){
            findViewById<Button>(R.id.start_button).visibility = View.VISIBLE
        }
    }

    fun startApp(apiKey : String){
        RetrofitClientKt.mApiKey = apiKey
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



    fun updateNavButton() {
        if(mViewPager?.currentItem == 0){
            findViewById<TextView>(R.id.next_button).visibility = View.VISIBLE
            findViewById<TextView>(R.id.prev_button).visibility = View.INVISIBLE
            findViewById<Button>(R.id.start_button).visibility = View.INVISIBLE
            return
        }
        if(mViewPager?.currentItem == 2){
            findViewById<TextView>(R.id.next_button).visibility = View.INVISIBLE
            findViewById<TextView>(R.id.prev_button).visibility = View.VISIBLE
            findViewById<Button>(R.id.start_button).visibility = View.VISIBLE
            return
        }
        findViewById<TextView>(R.id.next_button).visibility = View.VISIBLE
        findViewById<TextView>(R.id.prev_button).visibility = View.VISIBLE
        findViewById<Button>(R.id.start_button).visibility = View.INVISIBLE
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.next_button -> {
                mViewPager?.setCurrentItem(mViewPager?.currentItem!! + 1, true)
            }
            R.id.prev_button -> {
                mViewPager?.setCurrentItem(mViewPager?.currentItem!! - 1, true)
            }
            R.id.start_button -> {
                (supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.welcome_view_pager + ":" + mViewPager?.getCurrentItem()) as WelcomeEnterNewsApiToken).startFromTextField()
            }
        }
    }
}