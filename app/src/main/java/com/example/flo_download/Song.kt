package com.example.flo_download

data class Song(
    val title: String = "",
    val singer: String = "",
    var second : Int = 0,
    var playtime : Int = 0,
    var isPlaying : Boolean = false,
    var music : String = ""
)
