package sk.mt.mrek.meteorite.meteoritelandings

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


/**
 * @author Marek Sabo
 */
class MultilineInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker?): View? = null

    override fun getInfoContents(marker: Marker?): View {
        val info = LinearLayout(context)
        info.orientation = LinearLayout.VERTICAL

        info.addView(createTitle(marker))
        info.addView(createSnippet(marker))

        return info
    }

    private fun createTitle(marker: Marker?): TextView {
        val title = TextView(context)
        title.setTextColor(Color.BLACK)
        title.gravity = Gravity.CENTER
        title.setTypeface(null, Typeface.BOLD)
        title.text = marker?.title
        return title
    }

    private fun createSnippet(marker: Marker?): TextView {
        val snippet = TextView(context)
        snippet.setTextColor(Color.GRAY)
        snippet.text = marker?.snippet
        return snippet
    }

}