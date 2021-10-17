package vanrrtech.app.retrofitandrx.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateFormat
import java.util.*

class Utils {

    companion object{
        val USER_STORE = "user-shared-storage"
        val USER_NEWS_API_KEY = "user-news-api-key"
        public var mUtils : Utils? = null

        fun getUtils() : Utils{
            if(mUtils == null){
                mUtils = Utils()
            }

            return mUtils!!
        }
    }

    public fun getDate2DaysAgo(): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTime(java.util.Date())
        calendar.add(Calendar.DAY_OF_YEAR, -4)
        val newDate: Date = calendar.getTime()
        val df = DateFormat.format("yyyy-MM-dd", newDate)
        return df.toString()
    }

    private fun getSharedPreference(context: Context): SharedPreferences? {
        return context.applicationContext.getSharedPreferences(USER_STORE, Context.MODE_PRIVATE)
    }

    fun getKey(context: Context): String? {
        return getSharedPreference(context)?.getString(USER_NEWS_API_KEY, "")
    }

    fun saveKey(context: Context, key : String) {
        val mEditor = getSharedPreference(context)?.edit()
        mEditor?.putString(USER_NEWS_API_KEY, key)
        mEditor?.apply()
    }
}