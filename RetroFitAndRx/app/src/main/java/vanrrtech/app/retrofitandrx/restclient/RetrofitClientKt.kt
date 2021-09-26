package vanrrtech.app.retrofitandrx.restclient

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClientKt {

    companion object {
        var mRetrofitNewsClient : Retrofit? = null

        public fun getClientNews () : Retrofit{
            if(mRetrofitNewsClient == null){
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                mRetrofitNewsClient = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/") // .baseUrl("https://vanrrbackend.000webhostapp.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return mRetrofitNewsClient!!
        }

        var mRetrofitCovidClient : Retrofit? = null

        public fun getClientCovid () : Retrofit{
            if(mRetrofitCovidClient == null){
                mRetrofitCovidClient = Retrofit.Builder()
                    .baseUrl("https://data.covid19.go.id/public/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mRetrofitCovidClient!!
        }
    }
}