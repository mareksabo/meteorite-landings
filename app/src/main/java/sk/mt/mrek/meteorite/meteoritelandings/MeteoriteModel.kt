package sk.mt.mrek.meteorite.meteoritelandings

import java.util.*

object MeteoriteModel {
    data class MeteoriteLanding(
            val name: String,
            val id: Long,
            val nametype: String,
            val recclass: String,
            val mass: Double,
            val fall: String,
            val year: Date,
            val reclat: Double,
            val reclong: Double
    )
}
