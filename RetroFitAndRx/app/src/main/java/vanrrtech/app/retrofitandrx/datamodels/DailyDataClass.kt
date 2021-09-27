package vanrrtech.app.retrofitandrx.datamodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DailyDataClass {

    companion object {
        data class dailyHolder(
            @SerializedName("jumlah_positif")
            @Expose
            var positive: value?,
            @SerializedName("jumlah_meninggal")
            @Expose
            var meninggal: value?,
            @SerializedName("key_as_string")
            @Expose
            var date: String?,
            )

        data class value(
            @SerializedName("value")
            @Expose
            var value: Int?,
        )
    }

}