package com.example.flo_download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_download.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songNuguBtnDownIv.setOnClickListener {
            finish()
        }
        binding.songNuguBtnPlayIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songNuguBtnPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songNuguBtnRepeatUnactiveIv.setOnClickListener {
            setRepeatStatus(1)
        }
        binding.songNuguBtnRepeatOneIv.setOnClickListener {
            setRepeatStatus(2)
        }
        binding.songNuguBtnRepeatActiveIv.setOnClickListener {
            setRepeatStatus(0)
        }

        binding.songLikeOffIv.setOnClickListener {
            setLikeStatus(false)
        }
        binding.songLikeOnIv.setOnClickListener {
            setLikeStatus(true)
        }

        binding.songUnlikeOffIv.setOnClickListener {
            setUnlikeStatus(false)
        }
        binding.songUnlikeOnIv.setOnClickListener {
            setUnlikeStatus(true)
        }

        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songTitleTv.text=intent.getStringExtra("title")
            binding.songSingerTv.text=intent.getStringExtra("singer")
        }

    }

    fun setPlayerStatus(isPlaying : Boolean){
        if (isPlaying){
            binding.songNuguBtnPlayIv.visibility = View.VISIBLE
            binding.songNuguBtnPauseIv.visibility = View.GONE
        }
        else{
            binding.songNuguBtnPlayIv.visibility = View.GONE
            binding.songNuguBtnPauseIv.visibility = View.VISIBLE
        }
    }

    fun setRepeatStatus(isRepeatClick : Int){
        if(isRepeatClick == 0){
            binding.songNuguBtnRepeatUnactiveIv.visibility = View.VISIBLE
            binding.songNuguBtnRepeatOneIv.visibility = View.GONE
            binding.songNuguBtnRepeatActiveIv.visibility = View.GONE
        }
        else if (isRepeatClick == 1){
            binding.songNuguBtnRepeatUnactiveIv.visibility = View.GONE
            binding.songNuguBtnRepeatOneIv.visibility = View.VISIBLE
            binding.songNuguBtnRepeatActiveIv.visibility = View.GONE
        }
        else{
            binding.songNuguBtnRepeatUnactiveIv.visibility = View.GONE
            binding.songNuguBtnRepeatOneIv.visibility = View.GONE
            binding.songNuguBtnRepeatActiveIv.visibility = View.VISIBLE
        }
    }

    fun setLikeStatus(isLike : Boolean){
        if(isLike){
            binding.songLikeOffIv.visibility = View.VISIBLE
            binding.songLikeOnIv.visibility = View.GONE
        }
        else{
            binding.songLikeOffIv.visibility = View.GONE
            binding.songLikeOnIv.visibility = View.VISIBLE
        }
    }

    fun setUnlikeStatus(isUnlike : Boolean){
        if(isUnlike){
            binding.songUnlikeOffIv.visibility = View.VISIBLE
            binding.songUnlikeOnIv.visibility = View.GONE
        }
        else{
            binding.songUnlikeOffIv.visibility = View.GONE
            binding.songUnlikeOnIv.visibility = View.VISIBLE
        }
    }
}