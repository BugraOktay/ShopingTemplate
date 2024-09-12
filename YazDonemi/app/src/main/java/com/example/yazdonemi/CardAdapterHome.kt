package com.example.yazdonemi

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


data class CardHomeItem(val kod:Any?, val ad: Any?, val fiyat:Any?, val katagori:Any?, val img:Any?,val nereden:Any?)

class CardAdapterHome(private val items: MutableList<CardHomeItem>, private val x:Activity) : RecyclerView.Adapter<CardAdapterHome.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        val db = Firebase.firestore
        holder.title.text = item.ad.toString()
        val favoriUrun = hashMapOf(
            "kod" to item.kod
        )
        //////////////////////////////////////////////////////////////////////
        Glide.with(x)
            .load(item.img)
            .into(holder.img)
        //////////////////////////////////////////////////////////////////////
        holder.card.setOnClickListener(){
            (x as MainActivity).loadFragment(HomeDetayFragment())
        }
        //////////////////////////////////////////////////////////////////////
        val query = db.collection("users").document(auth.currentUser!!.uid).collection("sepet").document(item.kod.toString())
        var isbos = false
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                isbos = document != null && document.exists()
                // Belge varsa isbos true, yoksa false
                if (isbos) {
                    holder.favoriEkle.background=ContextCompat.getDrawable(x,R.drawable.sepet0)
                }
            } else {
                println("Hata: ${task.exception}")
            }
        }
        holder.favoriEkle.setOnClickListener(){
            isbos=!isbos
            if(isbos && auth.currentUser != null){
                holder.favoriEkle.background=ContextCompat.getDrawable(x,R.drawable.sepet0)
                db.collection("users").document(auth.currentUser!!.uid).collection("sepet").document(item.kod.toString()).set(favoriUrun)
            }
            else if(auth.currentUser != null){
                holder.favoriEkle.background=ContextCompat.getDrawable(x, R.drawable.sepet1)
                //Favorilerden sil
                db.collection("users").document(auth.currentUser!!.uid).collection("sepet").document(item.kod.toString()).delete()
                if(item.nereden=="sepet"){
                    val itemPosition = holder.bindingAdapterPosition
                    items.removeAt(itemPosition)
                    notifyItemRemoved(itemPosition)
                }

            }
            else{
                Toast.makeText(x,"Lütfen Giriş Yapın",Toast.LENGTH_SHORT).show()
            }

        }
        //////////////////////////////////////////////////////////////////////

    }

    override fun getItemCount(): Int = items.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.productName)
        val img: ImageView = itemView.findViewById(R.id.productIMG)
        val card: CardView = itemView.findViewById(R.id.cardView)
        val favoriEkle: ImageButton = itemView.findViewById(R.id.favoriEkle)
    }

}
