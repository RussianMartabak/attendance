package com.martabak.phincon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LokasiAdapter(
    var locations : List<Lokasi>,
    var customClickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<LokasiAdapter.LokasiViewHolder>(){

    //the view holder in the recyclerview
    inner class LokasiViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        //this is important, without this you can't acccess the views





    }

    //this return a view holder that we can interact from kotlin code
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LokasiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lokasi, parent, false)
        return LokasiViewHolder(view)
    }

    //this set the view based on the data supplied
    override fun onBindViewHolder(holder: LokasiViewHolder, position: Int) {
        var headerText = holder.itemView.findViewById<TextView>(R.id.lokasiHeader)
        var subText = holder.itemView.findViewById<TextView>(R.id.lokasi_subtext)
        var picture = holder.itemView.findViewById<ImageView>(R.id.lokasi_foto)

        //settingan
        headerText.text = locations[position].header
        subText.text = locations[position].subText
        picture.setImageResource(locations[position].image)
        // need to implement view.onclicklistener interface
        var properListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                customClickListener.recyclerViewItemClicked(holder.adapterPosition)
            }

        }
        holder.itemView.setOnClickListener(properListener)



    }

    override fun getItemCount(): Int {
        return locations.size
    }

}