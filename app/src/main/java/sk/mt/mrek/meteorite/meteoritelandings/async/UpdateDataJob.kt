package sk.mt.mrek.meteorite.meteoritelandings.async

import android.util.Log
import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import io.reactivex.functions.Consumer
import sk.mt.mrek.meteorite.meteoritelandings.DataIO.loadData
import java.util.concurrent.TimeUnit

/**
 *
 * @see <a href="https://medium.com/mindorks/android-scheduling-background-services-a-developers-nightmare-c573807c2705">
 *     Taken from medium.com</a>
 *
 * @author Marek Sabo
 */
class UpdateDataJob: Job() {

    companion object {

        const val TAG = "UpdateMeteoritesJob"

        fun scheduleJob() {
            val jobRequests = JobManager.instance().getAllJobRequestsForTag(TAG)
            if (!jobRequests.isEmpty()) return

            JobRequest.Builder(TAG)
                    .setPeriodic(TimeUnit.DAYS.toMillis(1))
                    .setUpdateCurrent(true)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setRequirementsEnforced(true)
                    .build()
                    .schedule()
        }
    }

    override fun onRunJob(params: Params): Result {
        Log.d(TAG, "running loading data job")
        loadData(Consumer {  }, Consumer {  })
        return Job.Result.SUCCESS
    }



}
