package com.yshmgrt.school.geom.app

import com.yshmgrt.school.geom.view.MainView
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
    }
    override fun stop() {
        super.stop()
    }
    init {
        reloadStylesheetsOnFocus()
    }
}