package sk.mt.mrek.meteorite.meteoritelandings

import java.util.*

object Model {
    data class MeteoriteLanding(
            val name: String,
            val id: Long,
            val nametype: String,
            val recclass: String,
            val mass: Double,
            val fall: String,
            val year: Date,
            val coordinates: Coordinates
    )

    data class Coordinates(val latitude: Double, val longitude: Double)
}
