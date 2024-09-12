package com.example.yazdonemi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.yazdonemi.databinding.FragmentHesapBinding
import com.google.firebase.auth.FirebaseAuth


class Fragment_Hesap : Fragment() {
    private var _binding: FragmentHesapBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHesapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            (activity as MainActivity).loadFragment(Fragment_profil())
        }

        binding.btn2.setOnClickListener {
            val email = binding.Email.text.toString()
            val password = binding.Password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Boş Alan Bırakmayın", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
                        (activity as MainActivity).loadFragment(Fragment_profil())
                    } else {
                        Toast.makeText(requireContext(), "Kayıt Başarısız", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btn.setOnClickListener {
            val email = binding.Email.text.toString()
            val password = binding.Password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Boş Alan Bırakmayın", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                        (activity as MainActivity).loadFragment(Fragment_profil())
                    } else {
                        Toast.makeText(requireContext(), "Giriş Başarısız", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
