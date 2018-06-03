package sk.mt.mrek.meteorite.meteoritelandings

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.column_meteorite.view.*
import org.jetbrains.anko.toast


/**
 * @author Marek Sabo
 */
class MeteoriteAdapter(_meteorites: List<Model.MeteoriteLanding>) :
        RecyclerView.Adapter<MeteoriteAdapter.ConceptHolder>() {

    private val meteorites: MutableList<Model.MeteoriteLanding> = _meteorites.toMutableList()

    override fun getItemCount() = meteorites.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeteoriteAdapter.ConceptHolder =
            ConceptHolder(parent.inflate(R.layout.column_meteorite, false))

    override fun onBindViewHolder(holder: MeteoriteAdapter.ConceptHolder, position: Int) =
            holder.bindConcept(meteorites[position])

    fun addMissingItems(otherMeteorites: List<Model.MeteoriteLanding>) {
        val amountChanged = addMissingItemsToList(otherMeteorites)
        Log.i("amountChanged", "$amountChanged")
        if (amountChanged > 0) {
            val startingIndex = meteorites.size - amountChanged
            notifyItemRangeInserted(startingIndex, amountChanged)
        }
    }

    private fun addMissingItemsToList(otherMeteorites: List<Model.MeteoriteLanding>): Int {
        val newMeteorites = otherMeteorites.toMutableList()
        newMeteorites.removeAll(meteorites)
        val oldAmount = meteorites.size
        meteorites.addAll(newMeteorites)
        return meteorites.size - oldAmount
    }

    class ConceptHolder(private var v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var meteorite: Model.MeteoriteLanding? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemView.context.toast("Meteorite ${meteorite?.name} clicked")
        }


        fun bindConcept(meteorite: Model.MeteoriteLanding) {
            this.meteorite = meteorite
            v.meteoriteName.text = meteorite.name
            v.meteoriteWeight.text = "${meteorite.mass.format(2)}" // No i18n needed
        }
    }

}