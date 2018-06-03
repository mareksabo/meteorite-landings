package sk.mt.mrek.meteorite.meteoritelandings

import android.app.Application
import android.util.Log
import io.realm.Realm


/**
 * @author Marek Sabo
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}