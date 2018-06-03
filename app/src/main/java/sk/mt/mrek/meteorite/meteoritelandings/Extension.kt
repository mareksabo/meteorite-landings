package sk.mt.mrek.meteorite.meteoritelandings

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)!!

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Date.toSimpleString(): String =
        SimpleDateFormat("dd.MM.yyy", Locale.ENGLISH).format(this)
