package com.nimtego.plectrum.presentation.mvp.model.information_view

@Deprecated("Change conceptions")
data class SongDetailsModel (
    val songName: String? = null,
    val songArtistName: String? = null,
    val songArtwork: String? = null,
    val songAlbumName: String? = null,
    val albumId: Int = 0,
    val albumArtWorkUrl: String? = null,
    val songPrice: Double? = null,
    val releaseDate: String? = null,
    val wikiInformation: String? = null)