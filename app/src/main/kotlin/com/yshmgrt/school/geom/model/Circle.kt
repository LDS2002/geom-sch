package com.yshmgrt.school.geom.model

import com.yshmgrt.school.geom.annotations.Field
import com.yshmgrt.school.geom.annotations.GenericForm
import com.yshmgrt.school.geom.util.P2D
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.canvas.GraphicsContext
import tornadofx.*
import javax.json.JsonObject
import kotlin.math.abs

class Circle() : IShape {
    override val type = "circle"

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("type", type)
            add("name", name)
            add("x", x)
            add("y", y)
            add("r", r)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name") ?: ""
            x = double("x") ?: 0.0
            y = double("y") ?: 0.0
            r = double("r") ?: 0.0
        }
    }

    override fun xMin() = x - r
    override fun xMax() = x + r
    override fun yMin() = y - r
    override fun yMax() = y + r

    override fun getForm() = GenericForm(this)

    override fun create() = Point()

    @Field("x", 1)
    var xProperty = SimpleDoubleProperty(0.0)
    @Field("y", 2)
    var yProperty = SimpleDoubleProperty(0.0)
    @Field("r", 3)
    var rProperty = SimpleDoubleProperty(0.0)

    var x : Double by xProperty
    var y : Double by yProperty
    var r : Double by rProperty

    @Field("name", 0)
    override val nameProperty = SimpleStringProperty("")
    override var name : String by nameProperty

    constructor(x : Double, y : Double, r : Double, name : String = "") : this(){
        this.name = name
        this.x = x
        this.y = y
        this.r = r
    }

    override fun transform(f : (P2D) -> P2D) : Circle {
        val x = f(P2D(this.x, this.y)).x
        val y = f(P2D(this.x, this.y)).y
        val x1 = f(P2D(this.x - r, this.y)).x
        return Circle(x, y, abs(x - x1), name)
    }

    override fun draw(parent : GraphicsContext) = with(parent) {
        fill = c(0.0, 0.0, 0.0)
        fillOval(x - 3, y - 3, 6.0, 6.0)
        strokeOval(x - r / 2, y - r / 2, r, r)
    }
}