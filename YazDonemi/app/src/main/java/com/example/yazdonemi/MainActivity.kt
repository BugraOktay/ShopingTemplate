package com.example.yazdonemi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(Fragment_Home())
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.menu.forEach { it.isEnabled = false }//menüler arası geçişi kapa
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home ->  loadFragment(Fragment_Home())
                R.id.nav_anasayfa-> loadFragment(FragmentAnaSayfa())
                R.id.nav_sepet -> loadFragment(FragmentSepet())
                R.id.nav_hesap -> loadFragment(Fragment_Hesap())

            }
            true
        }


    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.commit{
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }



}


