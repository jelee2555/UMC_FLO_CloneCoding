package com.example.flo_download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo_download.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    private val information = arrayListOf("저장된 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val LockerAdapater = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = LockerAdapater

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
            tab, positon ->
            tab.text = information[positon]
        }.attach()

        return binding.root
    }
}