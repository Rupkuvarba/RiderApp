package com.app.eho.utils.font

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.util.TypedValue
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import com.app.eho.R
import java.lang.Exception

object Fonts {

    fun getFontTypeface(context: Context?): Typeface {
        try {
            return ResourcesCompat.getFont(context!!, R.font.dm_sans_medium)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Typeface.DEFAULT
    }

    fun getFontTypefaceBold(context: Context?): Typeface? {
        try {
            return ResourcesCompat.getFont(context!!, R.font.dm_sans_medium)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Typeface.DEFAULT_BOLD
    }

    fun setRegularFont(context: Context?, textView: TextView) {
        val typeface = getFontTypeface(context)
        textView.typeface = typeface
    }

    fun setRegularFont(context: Context?, textView: CheckBox) {
        val typeface = getFontTypeface(context)
        textView.typeface = typeface
    }

    fun setRegularFont(context: Context, searchView: SearchView) {
        try {
            val typeface = getFontTypeface(context)
            //Find textView from searchView and set typeface and size
            val searchText = searchView.findViewById<TextView>(R.id.search_src_text)
            searchText.setTypeface(typeface)
            searchText.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.text_size_normal)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setRegularBoldFont(context: Context?, textView: TextView) {
        val typeface = getFontTypefaceBold(context)
        if (typeface != null) {
            textView.typeface = typeface
        }
    }

    fun setRegularFontOnMenuItemText(context: Context?, menuItem: MenuItem, color: Int) {
        val typeface: Typeface = getFontTypeface(context)
        val ssTitleSettings = SpannableString(menuItem.title)
        ssTitleSettings.setSpan(
            CustomTypeFaceSpan("", typeface, color),
            0,
            ssTitleSettings.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        menuItem.title = ssTitleSettings
    }

    fun getTypefaceBold(context: Context?, string: CharSequence): SpannableString? {
        return getSpannableString(getFontTypefaceBold(context), string)
    }

    fun getTypefaceMedium(context: Context?, string: CharSequence): SpannableString? {
        return getSpannableString(getFontTypeface(context), string)
    }

    private fun getSpannableString(typeface: Typeface?, string: CharSequence): SpannableString? {
        val s = SpannableString(string)
        if (typeface != null) {
            s.setSpan(
                CustomTypeFaceSpan("", typeface, Color.BLACK),
                0,
                s.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return s
    }

}
