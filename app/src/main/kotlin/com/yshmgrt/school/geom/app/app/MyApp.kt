package com.yshmgrt.school.geom.app.app

import com.yshmgrt.school.geom.app.view.MainView
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}