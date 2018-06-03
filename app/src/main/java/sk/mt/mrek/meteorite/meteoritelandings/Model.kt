package sk.mt.mrek.meteorite.meteoritelandings

import java.io.Serializable
import java.util.*

/**
 * @author Marek Sabo
 */
object Model {
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
    ) : Serializable
}
