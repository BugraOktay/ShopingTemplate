package com.example.yazdonemi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yazdonemi.databinding.FragmentHomeBinding
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class Fragment_Home : Fragment() {
    val db = Firebase.firestore
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    companion object {
        private var fragmentOpenCount = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        fragmentOpenCount++
        return binding.root
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.RecyclerViewHome.layoutManager = gridLayoutManager




        val item = mutableListOf<CardHomeItem>()
        if(fragmentOpenCount==1){
            db.collection("products").get().addOnSuccessListener {
                it.documents.forEach() {
                    it.data?.let { it1 -> Veriler.cardData.add(it1) }
                }

                downloadAllImagesFromFolder("Resimler",item)
            }
        }
        else{
            returnSayfa(item)
        }



    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun downloadAllImagesFromFolder(folderPath: String, itemX: MutableList<CardHomeItem>) {
        val storageRef = FirebaseStorage.getInstance().reference.child(folderPath)
        binding.progressBar.visibility = View.VISIBLE
        // Klasördeki tüm dosyaları listeleyin
        storageRef.listAll().addOnSuccessListener { listResult ->
            // Klasördeki tüm dosyaları indirin
            val downloadTasks = listResult.items.map { item ->
                item.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    Veriler.map[item.name] = imageUrl
                }.addOnFailureListener {
                    println("hata1")
                }
            }

            // Tüm indirme işlemlerinin tamamlanmasını  bekleyin
            Tasks.whenAll(downloadTasks).addOnCompleteListener {
                // UI güncellemelerini ana iş parçacığında yapın
                requireActivity().runOnUiThread {
                    Veriler.cardData.forEach(){
                        val x = Veriler.map[it["img"]]
                        it["img"]=x.toString()
                    }
                    for(i in Veriler.cardData){
                        itemX.add(CardHomeItem(i["kod"],i["ad"],i["fiyat"],i["katagori"],i["img"],"home"))
                    }
                    val adapter = CardAdapterHome(itemX, requireActivity())
                    binding.RecyclerViewHome.adapter = adapter
                    binding.progressBar.visibility = View.GONE
                    (activity as MainActivity).bottomNavigationView.menu.forEach { it.isEnabled = true }//menüler arası geçişi aç
                }
            }
        }.addOnFailureListener {
            println("hata2")
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun returnSayfa(item: MutableList<CardHomeItem>){
        for(i in Veriler.cardData){
            item.add(CardHomeItem(i["kod"],i["ad"],i["fiyat"],i["katagori"],i["img"],"home"))
        }
        val adapter = CardAdapterHome(item, requireActivity())
        binding.RecyclerViewHome.adapter = adapter
    }


}
