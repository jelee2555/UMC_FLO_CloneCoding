package com.example.flo_download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo_download.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumBtnBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        binding.albumBtnLikeOffIv.setOnClickListener {
            setPlayerLike(true)
        }
        binding.albumBtnLikeOnIv.setOnClickListener {
            setPlayerLike(false)
        }

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
    fun setPlayerLike(isLike : Boolean){
        if(isLike){
            binding.albumBtnLikeOffIv.visibility=View.GONE
            binding.albumBtnLikeOnIv.visibility=View.VISIBLE
        }
        else{
            binding.albumBtnLikeOffIv.visibility=View.VISIBLE
            binding.albumBtnLikeOnIv.visibility=View.GONE
        }
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