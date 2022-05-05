package com.example.flo_download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo_download.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {
    lateinit var binding : FragmentSavedBinding
    lateinit var songDB : SongDatabase
//    private var savedData = ArrayList<Saved>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedBinding.inflate(inflater, container, false)

//        savedData.apply {
//            add(Saved("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Saved("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Saved("ASAP", "스테이씨 (STAYC)", R.drawable.asap))
//            add(Saved("밤편지", "아이유 (IU)", R.drawable.bam))
//            add(Saved("저 별", "헤이즈 (Heiez)", R.drawable.heize))
//            add(Saved("신호등", "이무진", R.drawable.sinho))
//        }
//
//        val savedRVAdapter = SavedRVAdapter(savedData)
//        binding.savedSongRv.adapter = savedRVAdapter
//        binding.savedSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//
//        savedRVAdapter.setMyItemClickListener(object : SavedRVAdapter.MyItemClickListener{
//            override fun onItemClick(saved: Saved) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onRemoveSaved(position: Int) {
//                savedRVAdapter.removeItem(position)
//            }
//        })

        songDB = SongDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.savedSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SavedRVAdapter()
        songRVAdapter.setMyItemClickListener(object : SavedRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
            }
        })

        binding.savedSongRv.adapter = songRVAdapter
        songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)
    }
}