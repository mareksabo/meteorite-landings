package sk.mt.mrek.meteorite.meteoritelandings.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * @author Marek Sabo
 */

@RealmClass
open class Meteorite(
        @PrimaryKey
        open var id: Long = 0,
        open var name: String = "",
        open var nametype: String = "",
        open var recclass: String = "",
        open var mass: Double = 0.0,
        open var fall: String = "",
        open var year: Date = Date(0L),
        open var reclat: Double = 0.0,
        open var reclong: Double = 0.0
) : RealmObject() {

    /*
     * Since the Realm does not support data classes, equals, hashCode and toString had to be
     * generated manually, setters had to be enabled, default values had to be added.
     * Ideas how to keep the class immutable are appreciated.
     */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Meteorite

        if (name != other.name) return false
        if (id != other.id) return false
        if (nametype != other.nametype) return false
        if (recclass != other.recclass) return false
        if (mass != other.mass) return false
        if (fall != other.fall) return false
        if (year != other.year) return false
        if (reclat != other.reclat) return false
        if (reclong != other.reclong) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + nametype.hashCode()
        result = 31 * result + recclass.hashCode()
        result = 31 * result + mass.hashCode()
        result = 31 * result + fall.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + reclat.hashCode()
        result = 31 * result + reclong.hashCode()
        return result
    }

    override fun toString(): String =
            "Meteorite(name='$name', id=$id, nametype='$nametype', recclass='$recclass', " +
                    "mass=$mass, fall='$fall', year=$year, reclat=$reclat, reclong=$reclong)"
}
