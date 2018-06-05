package sk.mt.mrek.meteorite.meteoritelandings.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.column_meteorite.view.*
import org.jetbrains.anko.startActivity
import sk.mt.mrek.meteorite.meteoritelandings.R
import sk.mt.mrek.meteorite.meteoritelandings.activities.MeteoriteMapActivity
import sk.mt.mrek.meteorite.meteoritelandings.models.Meteorite
import sk.mt.mrek.meteorite.meteoritelandings.utils.Constant.PICKED_METEORITE
import sk.mt.mrek.meteorite.meteoritelandings.utils.format
import sk.mt.mrek.meteorite.meteoritelandings.utils.inflate


/**
 * @author Marek Sabo
 */
class MeteoriteAdapter(_meteorites: List<Meteorite>) :
        RecyclerView.Adapter<MeteoriteAdapter.ConceptHolder>() {

    companion object {
        @JvmField
        val TAG: String = MeteoriteAdapter::class.java.simpleName
    }

    private var meteorites: List<Meteorite> = _meteorites

    override fun getItemCount() = meteorites.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConceptHolder =
            ConceptHolder(parent.inflate(R.layout.column_meteorite, false))

    override fun onBindViewHolder(holder: ConceptHolder, position: Int) =
            holder.bindConcept(meteorites[position])

    fun loadItems(otherMeteorites: List<Meteorite>) {
        meteorites = otherMeteorites
        Log.i(TAG, "current meteorites amount: ${meteorites.size}")
        notifyDataSetChanged()
    }

    class ConceptHolder(private var v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var meteorite: Meteorite? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemView.context.startActivity<MeteoriteMapActivity>(
                    PICKED_METEORITE to Gson().toJson(meteorite))
        }

        fun bindConcept(meteorite: Meteorite) {
            this.meteorite = meteorite
            v.meteoriteName.text = meteorite.name
            v.meteoriteWeight.text = meteorite.mass.format(2) // No i18n needed yet
        }
    }

}