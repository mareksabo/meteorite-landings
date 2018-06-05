package sk.mt.mrek.meteorite.meteoritelandings

import android.app.Application
import io.realm.Realm
import com.evernote.android.job.JobManager
import sk.mt.mrek.meteorite.meteoritelandings.async.UpdateJobCreator


/**
 * @author Marek Sabo
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        JobManager.create(this).addJobCreator(UpdateJobCreator())
    }
}