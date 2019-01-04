package com.yshmgrt.school.geom.model

import com.yshmgrt.school.geom.annotations.Field
import com.yshmgrt.school.geom.annotations.GenericForm
import com.yshmgrt.school.geom.util.P2D
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.scene.canvas.GraphicsContext
import tornadofx.*
import tornadofx.getValue
import tornadofx.setValue
import java.lang.Double.max
import java.lang.Double.min
import javax.json.JsonObject

class Segment() : IShape {
    override val type: String
        get() = "segment"

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("type", type)
            add("name", name)
            add("x1", x1)
            add("y1", y1)
            add("x2", x2)
            add("y2", y2)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name") ?: ""
            x1 = double("x1") ?: 0.0
            x2 = double("x2") ?: 0.0
            y1 = double("y1") ?: 0.0
            y2 = double("y2") ?: 0.0
        }
    }

    override fun xMin() = min(x1, x2)
    override fun xMax() = max(x1, x2)
    override fun yMin() = min(y1, y2)
    override fun yMax() = max(y1, y2)

    override fun getForm() = GenericForm(this)

    override fun create() = Segment()

    @Field("x1", 1)
    val x1Property = SimpleDoubleProperty(0.0)
    @Field("y1", 2)
    val y1Property = SimpleDoubleProperty(0.0)
    @Field("x2", 3)
    val x2Property = SimpleDoubleProperty(0.0)
    @Field("y2", 4)
    val y2Property = SimpleDoubleProperty(0.0)

    var x1 : Double by x1Property
    var y1 : Double by y1Property
    var x2 : Double by x2Property
    var y2 : Double by y2Property

    @Field("name", 0)
    override val nameProperty = SimpleStringProperty("")
    override var name : String by nameProperty


    constructor(src : ObservableList<P2D>, name : String = "") : this(){
        this.name = name
        if (src.size > 0) {
            x1 = src[0].x
            y1 = src[0].y
        }
        if (src.size > 1) {
            x2 = src[1].x
            y2 = src[1].y
        }
    }

    override fun transform(f : (P2D) -> P2D) = Segment(observableList(f(P2D(x1, y1)), f(P2D(x2, y2))), name)

    override fun draw(parent : GraphicsContext) = with(parent) {
        fill = c(0.0, 0.0, 0.0)
        fillOval(x1 - 3, y1 - 3, 6.0, 6.0)
        fillOval(x2 - 3, y2 - 3, 6.0, 6.0)
        strokeLine(x1, y1, x2, y2)
    }
}
