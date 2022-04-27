package com.example.flo_download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo_download.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private var gson : Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        setInit(album)

        binding.albumBtnBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        binding.albumBtnLikeOffIv.setOnClickListener {
            setPlayerLike(true)
        }
        binding.albumBtnLikeOnIv.setOnClickListener {
            setPlayerLike(false)
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

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

    private fun setInit(album: Album){
        binding.albumAlbumImgIv.setImageResource(album.coverImg!!)
        binding.albumTitleTv.text = album.title.toString()
        binding.albumArtistTv.text = album.singer.toString()
    }
}