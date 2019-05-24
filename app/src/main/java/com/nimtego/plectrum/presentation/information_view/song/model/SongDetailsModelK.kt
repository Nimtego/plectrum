package com.nimtego.plectrum.presentation.information_view.song.model

data class SongDetailsModelK (
    val songName: String? = null,
    val songArtistName: String? = null,
    val songArtwork: String? = null,
    val songAlbumName: String? = null,
    val albumId: Int = 0,
    val albumArtWorkUrl: String? = null,
    val songPrice: Double? = null,
    val releaseDate: String? = null,
    val wikiInformation: String? = null)