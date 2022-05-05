package com.example.flo_download

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_download.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var  timer : Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson : Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()
//        setPlayer(songs[nowPos])



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
        songs[nowPos].second = ((binding.songPlaybarSb.progress * songs[nowPos].playtime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터
        editor.putInt("songId", songs[nowPos].id)

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()  //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null  //미디어 플레이어 해제
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.songNuguBtnDownIv.setOnClickListener {
            finish()
        }
        binding.songNuguBtnPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songNuguBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songNUguBtnSkipNextIv.setOnClickListener {
            moveSong(+1)
        }

        binding.songNuguBtnSkipPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songLikeOffIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)

        if (songs[nowPos].isLike){
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }


    private fun moveSong(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()  //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null  //미디어 플레이어 해제

        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId : Int): Int{
        for (i in 0 until songs.size){
            if(songs[i].id == songId){
                return 1
            }
        }
        return 0
    }

    private fun setPlayer(song: Song){
        binding.songTitleTv.text=song.title
        binding.songSingerTv.text=song.singer
        binding.songStartTv.text= String.format("%02d:%02d", song.second / 60, song.second %60)
        binding.songEndTv.text= String.format("%02d:%02d", song.playtime / 60, song.playtime %60)
        binding.songAlbumExp2Iv.setImageResource(song.coverImg!!)
        binding.songPlaybarSb.progress = (song.second * 1000 / song.playtime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playtime, songs[nowPos].isPlaying)
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
        songs[nowPos].isPlaying = isPlaying
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

    private fun setRepeatStatus(isRepeatClick : Int){
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

    private  fun setLikeStatus(isLike : Boolean){
        if(isLike){
            binding.songLikeOffIv.visibility = View.VISIBLE
            binding.songLikeOnIv.visibility = View.GONE
        }
        else{
            binding.songLikeOffIv.visibility = View.GONE
            binding.songLikeOnIv.visibility = View.VISIBLE
        }
    }

    private fun setUnlikeStatus(isUnlike : Boolean){
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