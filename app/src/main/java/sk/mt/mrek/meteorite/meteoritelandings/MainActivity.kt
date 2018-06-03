package sk.mt.mrek.meteorite.meteoritelandings

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

/**
 * @author Marek Sabo
 */
class MainActivity : AppCompatActivity() {

    companion object {
        @JvmField
        val TAG: String = MainActivity::class.java.simpleName
    }

    private val nasaApiService by lazy { NasaApiService.create() }
    private var disposable: Disposable? = null
    private lateinit var meteoriteAdapter: MeteoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        meteoriteAdapter = MeteoriteAdapter(emptyList())
        meteoritesView.layoutManager = LinearLayoutManager(this)
        meteoritesView.adapter = meteoriteAdapter
        setDecorator(meteoritesView)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun setDecorator(recyclerView: RecyclerView) {
        val itemDecorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.accent_divider)!!)
        recyclerView.addItemDecoration(itemDecorator)
    }

    private fun loadData() {
        disposable = nasaApiService
                .retrieveLandedMeteorites(
                        "yyhmUSd5r6OVuZK3GBZSyfSal",
                        "year >= '2011-01-01T00:00:00'",
                        "mass DESC")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(meteorites: List<Model.MeteoriteLanding>) {
        meteoritesAmountValue.text = "${meteorites.size}"
        meteoriteAdapter.addMissingItems(meteorites)
    }

    private fun handleError(throwable: Throwable) {
        throwable.localizedMessage?.let {
            Log.i(TAG, it)
            toast(it)
        }
    }

}