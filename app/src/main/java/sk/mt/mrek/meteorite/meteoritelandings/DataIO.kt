package sk.mt.mrek.meteorite.meteoritelandings

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import org.jetbrains.anko.doAsync
import sk.mt.mrek.meteorite.meteoritelandings.model.Meteorite

/**
 * Loads and stores the data about meteorites.
 *
 * @author Marek Sabo
 */
object DataIO {

    private val nasaApiService by lazy { NasaApiService.create() }

    fun loadData(onSuccess: Consumer<List<Meteorite>>, onError: Consumer<Throwable>): Disposable? {
        return nasaApiService
                .retrieveLandedMeteorites(
                        "yyhmUSd5r6OVuZK3GBZSyfSal",
                        "year >= '1011-01-01T00:00:00'",
                        "mass DESC")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            updateRealmDb(it)
                            onSuccess.accept(it)
                        },
                        onError::accept)
    }

    private fun updateRealmDb(meteorites: List<Meteorite>) {
        doAsync {
            Realm.getDefaultInstance().use { realm ->
                realm.beginTransaction()
                realm.insertOrUpdate(meteorites)
                realm.commitTransaction()
            }
        }
    }

}