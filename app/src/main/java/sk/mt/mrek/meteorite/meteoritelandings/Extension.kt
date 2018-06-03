package sk.mt.mrek.meteorite.meteoritelandings

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Utility class containing extension functions.
 *
 * @author Marek Sabo
 */
object Extension {}

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)