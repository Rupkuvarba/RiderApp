package com.app.eho.utils.display

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.app.eho.R

object Toaster {
    @RequiresApi(Build.VERSION_CODES.Q)
    fun show(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)

        /*
        toast.view depricated in api 30 so change background using html like
        Toast.makeText(applicationContext,
            HtmlCompat.fromHtml("<font color='red'>custom toast message</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
            Toast.LENGTH_LONG).show()

            setColorFilter is deprecated on API29

        */
        toast.view?.background?.colorFilter = BlendModeColorFilter(Color.RED, BlendMode.SRC_ATOP)
        /*toast.view?.background?.setColorFilter(
            ContextCompat.getColor(context, R.color.white), BlendMode.SRC_IN
        )*/
        val textView = toast.view?.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.black))
        toast.show()
    }

    /*public class MyDrawableCompat {
        public static void setColorFilter(@NonNull Drawable drawable, @ColorInt int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
            } else {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    MyDrawableCompat.setColorFilter(mydrawable.getBackground(), color);*/

}