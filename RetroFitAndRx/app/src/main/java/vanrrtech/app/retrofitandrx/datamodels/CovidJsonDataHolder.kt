package vanrrtech.app.retrofitandrx.datamodels

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class CovidJsonDataHolder (
    /**
     * {"response":400,"body":"this is the body message","error":-4}
//     */
//    @SerializedName("status")
//    @Expose
//    var status: String?,
//
//    @SerializedName("totalResults")
//    @Expose
//    var totalResults: Int?,

    @SerializedName("update")
    @Expose
    var update: UpdateCovidDataModel
)

