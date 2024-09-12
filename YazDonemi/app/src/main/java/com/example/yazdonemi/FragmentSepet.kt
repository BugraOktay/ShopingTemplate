package com.example.yazdonemi

import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore


class FragmentSepet : Fragment() {
    val auth = Firebase.auth
    val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sepet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ProgressBar>(R.id.progressBar2).visibility=View.VISIBLE

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.layoutManager = gridLayoutManager
        // Kart verilerini oluşturun

        val items : MutableList<CardHomeItem> =mutableListOf()

        auth.currentUser?.let {
            db.collection("users").document(it.uid).collection("sepet")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document != null) {
                            val kod = document.getString("kod").toString()
                            for(i in Veriler.cardData){
                                if(i["kod"]==kod)
                                    items.add(CardHomeItem(i["kod"],i["ad"],i["fiyat"],i["katagori"],i["img"],"sepet"))
                            }
                        }
                    }
                    val adapter = CardAdapterHome(items,requireActivity())
                    recyclerView.adapter = adapter
                    view.findViewById<ProgressBar>(R.id.progressBar2).visibility=View.GONE
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }




    }



}
