package com.yshmgrt.school.geom.view

import com.yshmgrt.school.geom.controller.GeoController
import com.yshmgrt.school.geom.model.IShape
import com.yshmgrt.school.geom.model.ShapeModel
import tornadofx.*

class ChooseMenuView(private var f : (IShape) -> Boolean) : Fragment("Choose shape") {
    private val controller : GeoController by inject()
    private var model : ShapeModel? = ShapeModel()
    override val root = vbox {
        listview(controller.shapes.filter(f).observable()) {
            cellFormat {
                text = "${item.type} ${item.name}"
            }
            bindSelected(model!!)
        }
        hbox {
            button("Ok").action { close() }
            button("Cancel").action {
                model = null
                close()
            }
        }
    }

    fun output() = model
}