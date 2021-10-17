package vanrrtech.app.retrofitandrx.restclient

import retrofit2.Call
import retrofit2.http.*
import vanrrtech.app.retrofitandrx.datamodels.CovidJsonDataHolder
import vanrrtech.app.retrofitandrx.datamodels.NewApiJsonDataHolder
import vanrrtech.app.retrofitandrx.datamodels.VaccineDataHolder


interface RetrofitInterface {

//    @POST("tes.php")
//    @FormUrlEncoded
//    fun getResponseTes(@Field("param") body: String?): Call<RestApiResponseModelHolder?>?

    //everything?q=bitcoin&apiKey=c70d16a18af1458691e24d4be76e7736

    @GET("everything")
    fun getNewsContent(@Query("q") body: String?, @Query("from") date: String?, @Query("apiKey") apiKey: String?): io.reactivex.Observable<NewApiJsonDataHolder>

    @GET("update.json")
    fun getCovidContent(): Call<CovidJsonDataHolder?>?

    @GET("vaksin")
    fun getVaccineContent(): Call<VaccineDataHolder?>?

//    @GET
//    fun fetchUsers(@Url url: String?): Observable<UserResponse?>?
}