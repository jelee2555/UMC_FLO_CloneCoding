package com.example.flo_download

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_download.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var  timer : Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initSong()
        setPlayer(song)

        binding.songNuguBtnDownIv.setOnClickListener {
            finish()
        }
        binding.songNuguBtnPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songNuguBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
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
    }

    //사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause(){
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songPlaybarSb.progress * song.playtime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()  //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null  //미디어 플레이어 해제
    }

    private fun initSong(){
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playtime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songTitleTv.text=intent.getStringExtra("title")
        binding.songSingerTv.text=intent.getStringExtra("singer")
        binding.songStartTv.text= String.format("%02d:%02d", song.second / 60, song.second %60)
        binding.songEndTv.text= String.format("%02d:%02d", song.playtime / 60, song.playtime %60)
        binding.songPlaybarSb.progress = (song.second * 1000 / song.playtime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(song.playtime, song.isPlaying)
        timer.start()
    }
    inner class Timer(private val playtime : Int, var isPlaying: Boolean = true) : Thread(){

        private var second : Int = 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){
                    if(second >= playtime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread{
                            binding.songPlaybarSb.progress = ((mills / playtime) * 100).toInt()
                        }
                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }

            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying){
            binding.songNuguBtnPlayIv.visibility = View.GONE
            binding.songNuguBtnPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.songNuguBtnPlayIv.visibility = View.VISIBLE
            binding.songNuguBtnPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
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