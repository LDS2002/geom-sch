package com.yshmgrt.school.geom.app.controller

import com.yshmgrt.school.geom.app.util.extension
import com.yshmgrt.school.geom.app.util.types
import com.yshmgrt.school.geom.shared.model.*
import com.yshmgrt.school.geom.shared.util.P2D
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File
import javax.json.*
import kotlin.reflect.full.createInstance

/**
 * Main controller of the project
 * @property shapes List of all shapes.
 * @property file File with save.
 */
class GeoController : Controller() {
    var shapes : ObservableList<IShape> = observableList(
            Polyline(observableList(P2D(0.0, 0.0)), "A"),
            Polyline(observableList(P2D(1.1, 2.2), P2D(1.3, 2.4), P2D(3.0, 0.0)), "AB"),
            Point(P2D(0.1, 0.2), "C"),
            Segment(observableList(P2D(1.1, 2.2), P2D(1.3, 2.4)), "AQ"),
            Circle(0.0, 0.0, 1.0, "Circle")
    )

    private var file : File? = null

    /**
     * Create new file for shapes.
     */
    fun create() {
        chooseFile(filters = extension, mode = FileChooserMode.Save).apply {
            if (size > 0) this[0].apply {
                file = this
                this.createNewFile()
                shapes.clear()
            }
        }
    }

    /**
     * Load shapes from file.
     */
    fun load() {
        chooseFile(filters = extension).apply {
            if (size > 0) {
                shapes.clear()
                loadJsonArray(this[0].toPath()).forEach { it ->
                    shapes.add((types[it.asJsonObject().getString("type")]!!.createInstance() as IShape).apply { updateModel(it.asJsonObject()) } as IShape?)
                }
            }
        }
    }

    /**
     * Save shapes with new name.
     */
    fun saveAs() {
        chooseFile(filters = extension, mode = FileChooserMode.Save).apply {
            if (size > 0) {
                file = this[0]
                save()
            }
        }
    }

    /**
     * Save with current name.
     */
    fun save() {
        file!!.apply {
            this.createNewFile()
            val obj = Json.createArrayBuilder().apply {
                shapes.forEach { it -> add(it.toJSON()) }
            }.build().toString()
            this.writeText(obj)
        }
    }

    /**
     * Add new shape to controller.
     * @param shape Shape to add.
     */
    fun add(shape : IShape) {
        shapes.add(shape)
    }
}