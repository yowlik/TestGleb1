package com.example.PrGroupTest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.PrGroupTest.MAIN
import com.example.PrGroupTest.MainActivity
import com.example.PrGroupTest.R
import com.example.PrGroupTest.databinding.FragmentNoInternetBinding


class NoInternetFragment : Fragment() {

    lateinit var binding: FragmentNoInternetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNoInternetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.NetworkUtils.getNetworkLiveData(requireContext()).observe(viewLifecycleOwner) { isConnected ->
            if(isConnected){
                MAIN.navController.navigate(R.id.action_noInternetFragment_to_webViewFragment)
            }
            else{

            }
            // обрабатываем
        }
    }

}