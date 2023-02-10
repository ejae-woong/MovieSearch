package com.example.moviesearch.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MovieResponse(
    @SerializedName("lastBuildDate") var lastBuildDate: String,
    @SerializedName("total") var total: Int,
    @SerializedName("start") var start: Int,
    @SerializedName("display") var display: Int,
    @SerializedName("items") var movieList: ArrayList<Movie>
) : Serializable {
    override fun toString() =
        "lastBuildDate : $lastBuildDate, " +
                "total : $total, " +
                "start : $start, " +
                "display : $display, " +
                "items : $movieList"
}

data class Movie(
    @SerializedName("title")
    var title: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("pubDate")
    val pubDate: Int,
    @SerializedName("director")
    val director: String,
    @SerializedName("actor")
    val actor: String,
    @SerializedName("userRating")
    val userRating: String
) {
    override fun toString() =
        "title : $title, " +
                "link : $link, " +
                "image : $image, " +
                "subtitle : $subtitle, " +
                "pubDate : $pubDate, " +
                "director : $director, " +
                "actor : $actor, " +
                "userRating : $userRating, "

}