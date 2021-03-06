package sk.mt.mrek.meteorite.meteoritelandings.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.vicpin.krealmextensions.querySorted
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import sk.mt.mrek.meteorite.meteoritelandings.DataIO.loadData
import sk.mt.mrek.meteorite.meteoritelandings.adapters.MeteoriteAdapter
import sk.mt.mrek.meteorite.meteoritelandings.R
import sk.mt.mrek.meteorite.meteoritelandings.async.UpdateDataJob
import sk.mt.mrek.meteorite.meteoritelandings.models.Meteorite

/**
 * @author Marek Sabo
 */
class MainActivity : AppCompatActivity() {

    companion object {
        @JvmField
        val TAG: String = MainActivity::class.java.simpleName
    }

    private var disposable: Disposable? = null
    private lateinit var meteoriteAdapter: MeteoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storedMeteorites = Meteorite().querySorted("mass", Sort.DESCENDING)
        meteoritesAmountValue.text = "${storedMeteorites.size}"

        setupView(storedMeteorites)
        UpdateDataJob.scheduleJob()
    }

    private fun setupView(storedMeteorites: List<Meteorite>) {
        meteoriteAdapter = MeteoriteAdapter(storedMeteorites)
        meteoritesView.layoutManager = LinearLayoutManager(this)
        meteoritesView.adapter = meteoriteAdapter
        setDecorator(meteoritesView)
    }

    private fun setDecorator(recyclerView: RecyclerView) {
        val itemDecorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.accent_divider)!!)
        recyclerView.addItemDecoration(itemDecorator)
    }

    private fun consumerResponse(): Consumer<List<Meteorite>> = Consumer {
        meteoritesAmountValue.text = "${it.size}"
        Log.d(TAG, "Data loaded successfully")
        meteoriteAdapter.loadItems(it)
    }

    private fun consumerError(): Consumer<Throwable> = Consumer {
        it.localizedMessage?.let { Log.i(TAG, it) }
        longToast("Meteorites data could not be loaded")
    }

    override fun onStart() {
        super.onStart()
        if (Realm.getDefaultInstance().isEmpty) {
            disposable = loadData(consumerResponse(), consumerError())
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}