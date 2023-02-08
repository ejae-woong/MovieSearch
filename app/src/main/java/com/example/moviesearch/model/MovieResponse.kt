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
    val title: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("pubdate")
    val pubDate: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("actor")
    val actor: String,
    @SerializedName("userRating")
    val userRating: Double
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