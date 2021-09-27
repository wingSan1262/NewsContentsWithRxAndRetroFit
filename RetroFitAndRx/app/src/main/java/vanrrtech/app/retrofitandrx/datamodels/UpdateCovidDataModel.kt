package vanrrtech.app.retrofitandrx.datamodels

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class UpdateCovidDataModel (
    /**
     * "jumlah_positif": 4204116,
    "jumlah_dirawat": 45803,
    "jumlah_sembuh": 4017055,
    "jumlah_meninggal": 141258
//     */

    @SerializedName("total")
    @Expose
    var total: TotalCovidDataHolder?,
    @SerializedName("harian")
    @Expose
    var dailyGraph: List<DailyDataClass.Companion.dailyHolder?>?

)