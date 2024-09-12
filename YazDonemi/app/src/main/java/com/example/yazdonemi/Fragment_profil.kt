package com.example.yazdonemi

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class Fragment_profil : Fragment() {

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.button2).setOnClickListener(){
            auth.signOut()
            (activity as MainActivity).loadFragment(Fragment_Hesap())
        }

        val txt = view.findViewById<TextView>(R.id.textView)
        txt.text = auth.currentUser?.email

        view.findViewById<Button>(R.id.button).setOnClickListener(){

            val data = hashMapOf(
                "kod" to "1",
                "ad" to "oki",
                "fiyat" to "",
                "katagori" to "",
                "img" to "",
            )
            val db = Firebase.firestore
            db.collection("products").add(data)

        }


    }

}