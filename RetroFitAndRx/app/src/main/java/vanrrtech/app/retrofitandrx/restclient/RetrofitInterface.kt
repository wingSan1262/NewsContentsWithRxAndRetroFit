package vanrrtech.app.retrofitandrx.restclient

import retrofit2.http.*
import vanrrtech.app.retrofitandrx.datamodels.NewApiJsonDataHolder
import java.util.*


interface RetrofitInterface {

//    @POST("tes.php")
//    @FormUrlEncoded
//    fun getResponseTes(@Field("param") body: String?): Call<RestApiResponseModelHolder?>?

    //everything?q=bitcoin&apiKey=c70d16a18af1458691e24d4be76e7736

    @GET("everything?apiKey=f2aefd4d5a474ac386ff27389ff67d43")
    fun getNewsContent(@Query("q") body: String?): io.reactivex.Observable<NewApiJsonDataHolder>

//    @GET
//    fun fetchUsers(@Url url: String?): Observable<UserResponse?>?
}