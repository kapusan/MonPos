package id.rackspira.seedisaster.ui.detailglobal

import android.annotation.SuppressLint
import id.rackspira.seedisaster.data.network.ApiService
import id.rackspira.seedisaster.data.network.ApiServiceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailGlobalPresenter(private val view: DetailGlobalView) {

    private val apiService = ApiServiceImpl().getRetrofitReliefInstance()!!.create(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getDetailGlobal(id: String) {
        apiService.getBencanaById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({detail ->
                view.getDetail(detail.data!![0].fields!!)
            }, {msgerr ->
                view.onError(msgerr.localizedMessage)
            })
    }

}