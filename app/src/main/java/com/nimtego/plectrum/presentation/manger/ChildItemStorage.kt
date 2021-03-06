package com.nimtego.plectrum.presentation.manger

import com.nimtego.plectrum.presentation.mvp.model.main_tab_model.ChildViewModel

interface ChildItemStorage {
    fun getCurrentChildItem(): ChildViewModel?
    fun changeCurrentChildItem(currentChildItem: ChildViewModel)
}