package id.rackspira.seedisaster.ui.main.infoglobal

import android.annotation.SuppressLint
import id.rackspira.seedisaster.data.network.ApiService
import id.rackspira.seedisaster.data.network.ApiServiceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InfoGlobalPresenter(private val view: InfoGlobalView) {

    private val apiService = ApiServiceImpl().getRetrofitReliefInstance()!!.create(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getListGlobal(limit: String) {
        apiService.getBencanaTerbaru("disasters", "country",
            "Indonesia", limit, "latest")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({hasil ->
                view.getListBencana(hasil.data!!)
            },{err ->
                view.onError(err.localizedMessage)
            })
    }

}