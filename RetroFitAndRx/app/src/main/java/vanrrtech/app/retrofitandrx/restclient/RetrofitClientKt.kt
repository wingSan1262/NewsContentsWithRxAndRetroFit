package vanrrtech.app.retrofitandrx.restclient

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClientKt {

    companion object {
        var mRetrofitClient : Retrofit? = null

        public fun getClient () : Retrofit{
            if(mRetrofitClient == null){
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                mRetrofitClient = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/") // .baseUrl("https://vanrrbackend.000webhostapp.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return mRetrofitClient!!
        }
    }
}