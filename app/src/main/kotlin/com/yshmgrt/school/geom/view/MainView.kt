package com.yshmgrt.school.geom.view

import com.yshmgrt.school.geom.annotations.GenericForm
import com.yshmgrt.school.geom.app.Styles
import com.yshmgrt.school.geom.controller.GeoController
import com.yshmgrt.school.geom.model.IShape
import com.yshmgrt.school.geom.model.ShapeModel
import com.yshmgrt.school.geom.util.*
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.control.ListView
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import tornadofx.*
import kotlin.reflect.full.createInstance


class MainView : View("Hello TornadoFX") {

    private val controller : GeoController by inject()
    private val model = ShapeModel()
    private var canvas : GeometryCanvas by singleAssign()
    private var list : ListView<IShape> by singleAssign()
    private var fragment : Fragment? = null
    private lateinit var hb : HBox

    override val root = vbox {
        minWidth = 1000.0
        minHeight = 600.0
        menubar {
            menu("File") {
                item("New").action {
                    controller.create()
                    updateLists()
                }
                item("Open").action {
                    controller.load()
                    updateLists()
                }
                item("Save").action { controller.save() }
                item("Save as").action { controller.saveAs() }
            }
        }
        hb = hbox {
            drawer(side = Side.LEFT) {
                minWidth = 200.0
                maxWidth = 200.0
                item("Shapes") {
                    list = listview(controller.shapes) {
                        addClass(Styles.listStyle)
                        cellFormat { shape ->
                            graphic = hbox {
                                label(shape.nameProperty).addClass(Styles.listStyle).apply {
                                    alignment = Pos.CENTER_LEFT
                                }
                                imageview(Image("delete.png")).setOnMouseClicked {
                                    controller.shapes.remove(index, index + 1)
                                    updateLists()
                                }.apply {
                                    alignment = Pos.CENTER_RIGHT
                                }
                                setOnMouseClicked { updateLists() }
                            }
                        }
                        bindSelected(model)
                        isFillHeight = true
                    }
                }
                item("Solvers") {
                    listview(solvers.observable()) {
                        addClass(Styles.listStyle)
                        cellFormat {
                            text = item.createInstance().name
                        }
                        onUserSelect(clickCount = 1) {
                            changeFragment(GenericForm(it.createInstance()))
                        }
                        addClass(Styles.listStyle)
                    }
                }
            }
            vbox {
                canvas = GeometryCanvas(controller)
                add(canvas)
                listview(types.values.toList().observable()) {
                    selectionModel = NoSelectionModel()
                    addClass(Styles.listStyle)
                    orientation = Orientation.HORIZONTAL
                    cellFormat {
                        graphic = button(item?.simpleName ?: "") {
                            action {
                                controller.add(item.createInstance())
                                updateLists()
                            }
                        }
                    }
                }
            }
            fragment = EmptyFragment().apply{
                minWidth = 300.0
                maxWidth = 300.0
            }
            add(fragment!!)
        }
        updateLists()
    }

    init {
        subscribe<UpdateCanvasEvent> {
            updateCanvas()
        }
        subscribe<UpdateListsEvent> {
            updateLists()
        }
    }

    private fun updateLists() = runLater {
        updateCanvas()
        list.refresh()
        changeFragment(model.item?.getForm())
    }

    private fun updateCanvas() = runLater {
        canvas.apply {
            updateBounds()
            drawShapes()
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        updateLists()
    }

    override fun onSave() {
        super.onSave()
        controller.save()
    }

    override fun onDock() {
        super.onDock()
        updateLists()
    }

    private fun changeFragment(f : Fragment?) {
        fragment?.removeFromParent()
        fragment = f ?: EmptyFragment()
        hb.add(fragment!!)
    }
}