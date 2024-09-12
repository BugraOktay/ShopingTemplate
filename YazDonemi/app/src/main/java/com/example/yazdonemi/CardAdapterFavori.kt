package com.example.yazdonemi

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class CardFavoriItem(val kod:String,val currentUserId:String, val a:Activity)

class CardAdapterFavori(private val items: MutableList<CardFavoriItem>) : RecyclerView.Adapter<CardAdapterFavori.CardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val db = Firebase.firestore
        val item = items[position]
        holder.title.text = item.kod

        holder.img.setOnClickListener(){
            val itemPosition = holder.bindingAdapterPosition
            Toast.makeText(item.a, "$itemPosition", Toast.LENGTH_SHORT).show()
        }
        holder.favoriEkle.setOnClickListener(){
            // Silinecek olan item pozisyonu

            //Favorilerden sil
            db.collection("users").document(item.currentUserId).collection("favori").document(item.kod).delete()

            val itemPosition = holder.bindingAdapterPosition
            items.removeAt(itemPosition)
            notifyItemRemoved(itemPosition)

        }

    }

    override fun getItemCount(): Int = items.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.productName)
        val img:ImageView=itemView.findViewById(R.id.productIMG)
        val favoriEkle:ImageButton=itemView.findViewById(R.id.favoriEkle)
    }
}
