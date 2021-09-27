package vanrrtech.app.retrofitandrx.datamodels

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class TotalCovidDataHolder (
    /**
     * "jumlah_positif": 4204116,
    "jumlah_dirawat": 45803,
    "jumlah_sembuh": 4017055,
    "jumlah_meninggal": 141258
//     */

    @SerializedName("jumlah_positif")
    @Expose
    var positiveCase: Int?,
    @SerializedName("jumlah_dirawat")
    @Expose
    var hospitalized: Int?,
    @SerializedName("jumlah_sembuh")
    @Expose
    var recovered: Int?,
    @SerializedName("jumlah_meninggal")
    @Expose
    var deceased: Int?
)