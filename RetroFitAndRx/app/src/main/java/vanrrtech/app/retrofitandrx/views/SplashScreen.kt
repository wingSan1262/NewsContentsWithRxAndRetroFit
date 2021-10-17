package vanrrtech.app.retrofitandrx.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import vanrrtech.app.retrofitandrx.MainActivity
import vanrrtech.app.retrofitandrx.R
import vanrrtech.app.retrofitandrx.restclient.RetrofitClientKt
import vanrrtech.app.retrofitandrx.utils.Utils

class SplashScreen : AppCompatActivity() {

    private var mKey : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed(Runnable { checkAndStart() }, 1000)
        mKey = Utils.getUtils().getKey(this)
    }

    private fun checkAndStart() {

        var intent : Intent?
        if(mKey.equals("")){
            intent = Intent(this, WelcomeActivity::class.java)
        } else {
            RetrofitClientKt.mApiKey = mKey!!
            intent = Intent(this, MainActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}