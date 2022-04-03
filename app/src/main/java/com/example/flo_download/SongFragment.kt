package com.example.flo_download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo_download.databinding.FragmentSongBinding
import com.example.flo_download.databinding.FragmentVideoBinding

class SongFragment : Fragment() {
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.albumMyTasteOffIv.setOnClickListener {
            setTasteMix(true)
        }

        binding.albumMyTasteOnIv.setOnClickListener {
            setTasteMix(false)
        }

        binding.songLalacLayout.setOnClickListener {
            Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
        }

        binding.songFluLayout.setOnClickListener {
            Toast.makeText(activity, "Flu", Toast.LENGTH_SHORT).show()
        }

        binding.songCoinLayout.setOnClickListener {
            Toast.makeText(activity,"Coin", Toast.LENGTH_SHORT).show()
        }

        binding.songSpringLayout.setOnClickListener {
            Toast.makeText(activity, "봄 안녕 봄", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    fun setTasteMix(tasteMix : Boolean){
        if(tasteMix){
            binding.albumMyTasteOffIv.visibility = View.GONE
            binding.albumMyTasteOnIv.visibility = View.VISIBLE
        }
        else{
            binding.albumMyTasteOffIv.visibility = View.VISIBLE
            binding.albumMyTasteOnIv.visibility = View.GONE
        }
    }
}

