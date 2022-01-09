package com.app.eho.ui.modules.navigatedrawer.drawer.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.eho.R
import com.app.eho.data.model.Ambulance
import java.lang.Exception

public interface OnAmbulanceItemClickListener{
    fun onItemClicked(ambulance: Ambulance)
}

// Holds the views for adding it to image and text
class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    //val imageView: ImageView = itemView.findViewById(R.id.imageview)
    val tvName: TextView = itemView.findViewById(R.id.tv_name)
    val tvRate: TextView = itemView.findViewById(R.id.tv_rate)
    val tvAway: TextView = itemView.findViewById(R.id.tv_away)
    val clItem : ConstraintLayout = itemView.findViewById(R.id.constraint_item)

    @SuppressLint("ResourceAsColor")
    fun bind(ItemsViewModel: Ambulance)
    {
        // sets the text to the textview from our itemHolder class
        tvName.text = ItemsViewModel.name
        tvRate.text = ItemsViewModel.rate
        tvAway.text = ItemsViewModel.away

    }
}

class AmbulanceAdapter(var ambulances:MutableList<Ambulance>, val ambulanceItemClickListener: OnClickListener) : RecyclerView.Adapter<ViewHolder>() {

    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ambulance, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            val itemsViewModel = ambulances.get(position)
            /*holder.itemView.setOnClickListener {
                holder.clItem.setBackgroundColor(R.color.app_theme_selected)
                if(ambulanceItemClickListener != null) {
                    ambulanceItemClickListener.onClick(itemsViewModel)
                }
            }*/
            holder.bind(itemsViewModel)
            holder.itemView.setOnClickListener {
               Log.d("Tag", "Show background color "+lastItemSelectedPos+ " selected: "+selectedItemPos)

                selectedItemPos = position

                if(lastItemSelectedPos != -1) {
                    notifyItemChanged(lastItemSelectedPos)
                }

                lastItemSelectedPos = selectedItemPos
                notifyItemChanged(selectedItemPos)
            }

            Log.d("Tag", "Show background color1 "+lastItemSelectedPos+ " selected: "+selectedItemPos+ " position: "+position)
            if(selectedItemPos != -1 && selectedItemPos == position){
                Log.d("Tag", "Show background color2")
                //holder.clItem.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_black_less))
                holder.clItem.setBackgroundColor(Color.LTGRAY)
            }else{
                Log.d("Tag", "Show background color3")
                holder.clItem.setBackgroundColor(Color.WHITE)
                //holder.clItem.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return ambulances.size
    }

    class OnClickListener(val clickListener: (meme: Ambulance) -> Unit) {
        fun onClick(meme: Ambulance) = clickListener(meme)
    }

    public fun getSelectedItem() : Ambulance? {

        if(ambulances.size > 0){
            if(selectedItemPos != -1){
                return ambulances[selectedItemPos];
            }
        }
        return null;
    }

}