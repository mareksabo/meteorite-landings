package sk.mt.mrek.meteorite.meteoritelandings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    private val nasaApiService by lazy { NasaApiService.create() }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun loadData() {
        disposable = nasaApiService
                .retrieveLandedMeteorites(
                        "yyhmUSd5r6OVuZK3GBZSyfSal",
                        "year >= '2011-01-01T00:00:00'",
                        "mass DESC")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result -> toast("found ${result.size} meteorites") },
                        { throwable -> throwable.message?.let { toast(it) } }
                )
    }
}