package id.rackspira.seedisaster.data.network

import id.rackspira.seedisaster.data.network.entity.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("jenis_bencana")
    fun getJenisBencana(): Observable<List<ListJenisBencana>>

    @GET("master_prop")
    fun getPropinsi(): Observable<List<ListProp>>

    @GET("master_kab")
    fun getKabupaten(@Query("p") propinsi: String): Observable<List<ListKabupaten>>

    //get all bencana kode = ALL
    @GET("bencana")
    fun getBencanaByKode(@Query("j") kode: String): Observable<List<ListBencana>>

    @GET("bencana")
    fun getBencanaByPropinsi(@Query("j") kode: String, @Query("p") propinsi: String): Observable<List<ListBencana>>

    @GET("bencana")
    fun getBencanaByKabupaten(@Query("j") kode: String,
                              @Query("p") propinsi: String,
                              @Query("b") kab: String): Observable<List<ListBencana>>

    @GET("bencana")
    fun getBencanaByKodeLimit(@Query("j") kode: String,
                              @Query("l") limit: String): Observable<List<ListBencana>>

    @GET("disasters")
    fun getBencanaTerbaru(@Query("appname") appname: String,
//                          @Query("filter[field]") country: String,
//                          @Query("filter[value]") negara: String,
                          @Query("limit") limit: String,
                          @Query("preset") preset: String): Observable<BaseNewsBencana>

    @GET("disasters/{id}")
    fun getBencanaById(@Path("id")id: String): Observable<BaseDetailNewsBencana>
}