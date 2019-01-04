package com.yshmgrt.school.geom.model

import com.yshmgrt.school.geom.annotations.Action
import com.yshmgrt.school.geom.annotations.Field
import com.yshmgrt.school.geom.annotations.GenericForm
import com.yshmgrt.school.geom.util.P2D
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.canvas.GraphicsContext
import tornadofx.*
import javax.json.JsonObject

class Polyline() : IShape {
    override val type = "polyline"

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("type", type)
            add("name", name)
            add("src", src)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name") ?: ""
            src.setAll(getJsonArray("src").toModel())
        }
    }

    override fun xMin() = src.map {it -> it.x}.min() ?: 0.0
    override fun xMax() = src.map {it -> it.x}.max() ?: 0.0
    override fun yMin() = src.map {it -> it.y}.min() ?: 0.0
    override fun yMax() = src.map {it -> it.y}.max() ?: 0.0

    override fun getForm() = GenericForm(this)

    override fun create() = Polyline()

    @Field("", 1)
    val srcProperty = SimpleListProperty(FXCollections.observableArrayList<P2D>())
    var src: ObservableList<P2D> = FXCollections.emptyObservableList()
    init {
        srcProperty.value = src
    }

    @Field("name", 0)
    override val nameProperty = SimpleStringProperty("")
    override var name : String by nameProperty

    constructor(src : ObservableList<P2D>, name : String = "") : this(){
        this.name = name
        this.src = src.map { it -> it }.observable()
    }
    
    override fun transform(f : (P2D) -> P2D) = Polyline(src.map{it as P2D}.map(f).observable(), name)

    override fun draw(parent : GraphicsContext) = with(parent) {
        fill = c(0.0, 0.0, 0.0)
        for (i in 1 until src.size)
            strokeLine(src[i].x, src[i].y, src[i - 1].x, src[i - 1].y)
        for (i in 0 until src.size)
            fillOval(src[i].x - 3, src[i].y - 3, 6.0, 6.0)
    }

    @Action("Add", 0)
    fun addPoint() = src.add(P2D())
}
