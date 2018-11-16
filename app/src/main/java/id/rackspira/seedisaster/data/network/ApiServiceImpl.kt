package id.rackspira.seedisaster.data.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import id.rackspira.seedisaster.BuildConfig
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class ApiServiceImpl {
    private var retrofit: Retrofit? = null
    private var retrofitbmkg: Retrofit? = null
    private var retrofitreliefweb: Retrofit? = null

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getRetrofitBmkgInstace(): Retrofit? {
        if (retrofitbmkg == null) {
            retrofitbmkg = retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_BMKG)
                .client(OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofitbmkg
    }

    fun getRetrofitReliefInstance(): Retrofit? {
        if (retrofitreliefweb == null) {
            retrofitreliefweb = retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_RELIEFWEB)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitreliefweb
    }

}