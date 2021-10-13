package vanrrtech.app.retrofitandrx.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import vanrrtech.app.retrofitandrx.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(Runnable { checkAndStart() }, 5000)
    }

    private fun checkAndStart() {

    }
}