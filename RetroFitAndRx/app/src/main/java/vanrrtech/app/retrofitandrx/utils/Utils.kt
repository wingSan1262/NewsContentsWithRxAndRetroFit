package vanrrtech.app.retrofitandrx.utils

import android.text.format.DateFormat
import java.util.*

class Utils {

    companion object{
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
}