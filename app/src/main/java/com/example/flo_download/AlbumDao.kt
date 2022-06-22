package com.example.flo_download

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(album:Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbum():List<Album>

    @Insert
    fun likeAlbum(like: Like)

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun isLikedAlbum(userId:Int, albumId:Int) : Int?

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId:Int, albumId:Int)

    @Query("SELECT AT.* FROM LikeTable as Lt LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId")
    fun getLikedAlbums(userId: Int) : List<Album>
}