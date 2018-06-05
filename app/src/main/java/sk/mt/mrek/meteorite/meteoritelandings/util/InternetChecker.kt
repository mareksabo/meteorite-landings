package sk.mt.mrek.meteorite.meteoritelandings.util

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 * @author Marek Sabo
 */
object InternetChecker {

    /**
     * Connects to Google DNS to check for internet connection.
     *
     * @return true observable if internet is available, false otherwise
     */
    fun hasInternetConnection(): Single<Boolean> {
        return Single.fromCallable {
            try {
                checkConnection()
                true
            } catch (e: IOException) {
                false
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    @Throws(IOException::class)
    private fun checkConnection() {
        val timeoutMs = 1500
        val socketAddress = InetSocketAddress("8.8.8.8", 53)
        val socket = Socket()

        socket.connect(socketAddress, timeoutMs)
        socket.close()
    }

}