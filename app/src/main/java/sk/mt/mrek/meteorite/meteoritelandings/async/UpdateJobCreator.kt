package sk.mt.mrek.meteorite.meteoritelandings.async

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator


/**
 *
 * @see <a href="https://medium.com/mindorks/android-scheduling-background-services-a-developers-nightmare-c573807c2705">
 *     Taken from medium.com</a>
 *
 * @author Marek Sabo
 */
class UpdateJobCreator : JobCreator {

    override fun create(tag: String): Job? = when (tag) {
        UpdateDataJob.TAG -> UpdateDataJob()
        else -> null
    }

}
