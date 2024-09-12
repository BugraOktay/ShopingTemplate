package com.example.yazdonemi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient


class HomeDetayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_detay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView=view.findViewById<WebView>(R.id.webview)
        // WebView ayarları
        webView.webViewClient = WebViewClient() // Tıklanan linklerin WebView içinde açılmasını sağlar
        webView.settings.javaScriptEnabled = true // Eğer JavaScript desteği gerekiyorsa

        // Belirli bir URL'yi yükleme
        webView.loadUrl("https://youtube.com")
    }


}