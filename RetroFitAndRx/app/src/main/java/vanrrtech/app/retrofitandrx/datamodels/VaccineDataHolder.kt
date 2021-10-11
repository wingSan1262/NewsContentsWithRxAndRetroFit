package vanrrtech.app.retrofitandrx.datamodels

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class VaccineDataHolder (
    @SerializedName("vaksinasi1")
    var vaksin1: Double?,
    @SerializedName("vaksinasi2")
    var vaksin2: Double?,
    @SerializedName("totalsasaran")
    var totalsasaran: Double?,
    )

