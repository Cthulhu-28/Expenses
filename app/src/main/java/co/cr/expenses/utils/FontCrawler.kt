package co.cr.expenses.utils

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children

class FontCrawler (private var typeFace: Typeface){

    fun replaceFonts(viewGroup: ViewGroup){
        viewGroup.children.forEach {
            if(it is ViewGroup)
                replaceFonts(it)
            else if(it is TextView){
                it.typeface = typeFace
            }
        }
    }
}