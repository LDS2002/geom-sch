package com.yshmgrt.school.geom.controller

import com.yshmgrt.school.geom.model.*
import com.yshmgrt.school.geom.util.P2D
import com.yshmgrt.school.geom.util.extension
import com.yshmgrt.school.geom.util.types
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File
import javax.json.*
import kotlin.reflect.full.createInstance

/**
 * Main controller of the project
 * @property shapes List of all shapes.
 * */
class GeoController : Controller() {
    var shapes : ObservableList<IShape> = observableList(
            Polyline(observableList(P2D(0.0, 0.0)), "A"),
            Polyline(observableList(P2D(1.1, 2.2), P2D(1.3, 2.4), P2D(3.0, 0.0)), "AB"),
            Point(P2D(0.1, 0.2), "C"),
            Segment(observableList(P2D(1.1, 2.2), P2D(1.3, 2.4)), "AQ"),
            Circle(0.0, 0.0, 1.0, "Circle")
    )

    var file : File? = null

    fun create() {
        chooseFile(filters = extension, mode = FileChooserMode.Save).apply {
            if (size > 0) this[0].apply {
                file = this
                this.createNewFile()
                shapes.clear()
            }
        }
    }

    fun load() {
        chooseFile(filters = extension).apply {
            if (size > 0) {
                shapes.clear()
                loadJsonArray(this[0].toPath()).forEach { it ->
                    shapes.add(types[it.asJsonObject().getString("type")]!!.createInstance().apply { updateModel(it.asJsonObject()) })
                }
            }
        }
    }

    fun saveAs() {
        chooseFile(filters = extension, mode = FileChooserMode.Save).apply {
            if (size > 0) this[0].apply {
                file = this
                this.createNewFile()
                val obj = Json.createArrayBuilder().apply {
                    shapes.forEach { it -> add(it.toJSON()) }
                }.build().toString()
                this.writeText(obj)
            }
        }
    }

    fun save() {
        file!!.apply {
            this.createNewFile()
            val obj = Json.createArrayBuilder().apply {
                shapes.forEach { it -> add(it.toJSON()) }
            }.build().toString()
            this.writeText(obj)
        }
    }

    fun add(shape : IShape) {
        shapes.add(shape)
    }
}