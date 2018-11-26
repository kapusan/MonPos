package id.rackspira.seedisaster.ui.main.home

import android.annotation.SuppressLint
import id.rackspira.seedisaster.data.network.ApiService
import id.rackspira.seedisaster.data.network.ApiServiceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val view: HomeView) {

    private val apiService = ApiServiceImpl().getRetrofitInstance()!!.create(ApiService::class.java)

    @SuppressLint("CheckResult")
    fun getListBencana(kode: String, limit: String) {
        apiService.getBencanaByKodeLimit(kode, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    view.getListBencana(list)
                },
                {er ->
                    er.message?.let {
                        view.onError(it)
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun getJenisBencana() {
        apiService.getJenisBencana()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({list->
                view.getListJenis(list)
            }, { error ->
                view.onError(error.localizedMessage)
            })
    }

}