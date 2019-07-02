package com.nimtego.plectrum.data.entity

import com.nimtego.plectrum.presentation.mvp.view_model.main_tab_model.ChildViewModel
import com.nimtego.plectrum.presentation.mvp.view_model.main_tab_model.DashBoardModelContainer

data class TabContentModel(val contentList: List<DashBoardModelContainer<ChildViewModel>>) {
}