package com.example.PrGroupTest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import com.example.PrGroupTest.MAIN
import com.example.PrGroupTest.MainActivity
import com.example.PrGroupTest.R
import com.example.PrGroupTest.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {
    lateinit var binding: FragmentWebViewBinding
    lateinit var webView: WebView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if(binding.webView.canGoBack()){
                binding.webView.goBack()
            }
        }

        return binding.root

        // Inflate the layout for this fragment

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val myWebView: WebView = view.findViewById(R.id.webView)
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                view.loadUrl(url)
                return true
            }

        }

        myWebView.loadUrl("https://google.com")
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.useWideViewPort = true
        MainActivity.NetworkUtils.getNetworkLiveData(requireContext()).observe(viewLifecycleOwner) { isConnected ->
            if(isConnected){

            }
            else{
                MAIN.navController.navigate(R.id.action_webViewFragment_to_noInternetFragment)
            }
            // обрабатываем
        }



    }




}
