package com.app.eho.ui.modules.navigatedrawer.drawer.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.eho.R
import com.app.eho.data.model.Ambulance
import java.lang.Exception

class AmbulanceAdapter1(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Ambulance, AmbulanceAdapter1.ViewHolder>(MyDiffUtil){
    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    companion object MyDiffUtil : DiffUtil.ItemCallback<Ambulance>() {
        override fun areItemsTheSame(oldItem: Ambulance, newItem: Ambulance): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Ambulance, newItem: Ambulance): Boolean {
            return oldItem.id == newItem.id
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvRate: TextView = itemView.findViewById(R.id.tv_rate)
        val tvAway: TextView = itemView.findViewById(R.id.tv_away)
        val clItem : ConstraintLayout = itemView.findViewById(R.id.constraint_item)

        fun bind(ItemsViewModel: Ambulance)
        {
            // sets the text to the textview from our itemHolder class
            tvName.text = ItemsViewModel.name
            tvRate.text = ItemsViewModel.rate
            tvAway.text = ItemsViewModel.away
        }
    }

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
            val meme = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(meme)
            }
            holder.bind(meme)
            /*holder.bin

            // sets the image to the imageview from our itemHolder class
            //holder.imageView.setImageResource(ItemsViewModel.image)

            // sets the text to the textview from our itemHolder class
            holder.tvName.text = ItemsViewModel.name
            holder.tvRate.text = ItemsViewModel.rate
            holder.tvAway.text = ItemsViewModel.away

            *//*if (selectedItemPos == position) {
                holder.clItem.setBackgroundColor(R.color.app_theme_selected)
            } else {
                holder.clItem.setBackgroundColor(R.color.window_background)
            }*//*

            holder.clItem.setOnClickListener {
                *//*selectedItemPos = position
                if(lastItemSelectedPos == -1)
                    lastItemSelectedPos = selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                }
                notifyItemChanged(selectedItemPos)*//*
            }*/

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    // return the number of the items in the list
    /*override fun getItemCount(): Int {
        return ambulances.size
    }*/

    class OnClickListener(val clickListener: (meme: Ambulance) -> Unit) {
        fun onClick(meme: Ambulance) = clickListener(meme)
    }

}