package sk.mt.mrek.meteorite.meteoritelandings

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import io.reactivex.Single


interface NasaApiService {

    @GET("y77d-th95.json")
    fun retrieveLandedMeteorites(@Query("\$\$app_token") appToken: String,
                                 @Query("\$where") where: String,
                                 @Query("\$order") order: String)
            : Single<List<MeteoriteModel.MeteoriteLanding>>

    companion object {

        private const val BASE_URL = "https://data.nasa.gov/resource/"

        fun create(): NasaApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .baseUrl(BASE_URL)
                    .client(createHttpClient())
                    .build()

            return retrofit.create(NasaApiService::class.java)
        }

        private fun createGson(): Gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()

        private fun createHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
        }
    }

}