package com.yshmgrt.school.geom.app.util

import javafx.scene.control.TextField

fun TextField.onTextChange(f : () -> Unit) {
    setOnKeyPressed { _ ->
        f()
    }
}

fun TextField.onFocusChanged(f : () -> Unit) {
    focusedProperty().addListener{ _ ->
        f()
    }
}