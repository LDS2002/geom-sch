package com.yshmgrt.school.geom.view

import com.yshmgrt.school.geom.controller.GeoController
import com.yshmgrt.school.geom.util.P2D
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import java.lang.Double.max
import java.lang.Double.min

class git GeometryCanvas(private val controller: GeoController) : Canvas() {

    private var xMin = 0.0
    private var xMax = 0.0
    private var yMin = 0.0
    private var yMax = 0.0
    private var transform : (P2D) -> P2D = { it -> it}

    private val kBorder = 0.85

    fun updateBounds() {
        with(controller) {
            if (shapes.size > 0) {
                xMin = shapes[0].xMin()
                xMax = shapes[0].xMax()
                yMin = shapes[0].yMin()
                yMax = shapes[0].yMax()
            }
            shapes.forEach { it ->
                xMin = min(xMin, it.xMin())
                xMax = max(xMax, it.xMax())
                yMin = min(yMin, it.yMin())
                yMax = max(yMax, it.yMax())

            }
            if (xMin == xMax)
            {
                xMin -= 1
                xMax += 1
            }
            if (yMin == yMax)
            {
                yMin -= 1
                yMax += 1
            }
        }
        val width = xMax - xMin
        val height = yMax - yMin
        var scale = min(this.width / width, this.height / height)
        scale *= kBorder
        val centerX = (xMin + xMax) / 2
        val centerY = (yMin + yMax) / 2
        transform = {it ->
            P2D( this@GeometryCanvas.width / 2 + scale * (it.x - centerX),
                    this@GeometryCanvas.height / 2 - scale * (it.y - centerY))
        }
    }

    fun drawShapes() {
        clear()
        controller.shapes.forEach { i ->
            i.transform(transform).draw(graphicsContext2D)
        }
    }

    private fun clear() {
        width = 500.0
        height = 500.0
        graphicsContext2D?.apply {
            fill = Color.WHITE
            fillRect(0.0, 0.0, width, height)
        }
    }
}