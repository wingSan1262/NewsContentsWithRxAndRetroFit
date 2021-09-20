package vanrrtech.app.retrofitandrx.datamodels

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class RestApiResponseModelHolder (
    /**
     * {"response":400,"body":"this is the body message","error":-4}
     */
    @SerializedName("response")
    @Expose
    var response: String?,

    @SerializedName("body")
    @Expose
    var body: String?,

    @SerializedName("error")
    @Expose
    var error: String?,
)