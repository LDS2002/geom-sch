package com.yshmgrt.school.geom.app.app

import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val listStyle by cssclass()

        val bgColor = c("#eeeeee")
        val darkbgColor = c("#dddddd")
        val accColor = c("#00ddaf")
        val textColor = c("#111111")
        val lightTextColor = c("#eeeeee")
        val lightColor = c("#bbbbbb")
    }

    init {
        text {
            textFill = textColor
            fontSize = 14.0.pt
        }
        menuBar {
            backgroundColor += darkbgColor
            borderWidth += box(0.0.px)
            strokeWidth = 0.0.px
        }
        menuButton {
            borderColor += box(lightColor)
            highlightFill = accColor
            textFill = textColor
            backgroundColor += darkbgColor
            borderWidth += box(0.0.px, 0.5.px)
            minHeight = 30.0.px
            and(hover) {
                backgroundColor += accColor
                textFill = lightTextColor
            }
        }
        menuItem {
            textFill = textColor
            backgroundColor += darkbgColor
            and(hover) {
                backgroundColor += accColor
                textFill = lightTextColor
            }
        }
        form {
            backgroundColor += darkbgColor
            minWidth = 300.px
        }
        textField {
            backgroundColor += bgColor
            fontSize = 14.0.pt
        }
        listView {
            backgroundColor += darkbgColor
        }
        listCell {
            backgroundColor += darkbgColor
            and(selected) {
                backgroundColor += accColor
            }
        }
        button {
            backgroundColor += bgColor
            and(pressed) {
                backgroundColor += accColor
            }
        }
    }
}