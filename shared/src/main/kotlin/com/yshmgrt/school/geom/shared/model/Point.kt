package com.yshmgrt.school.geom.shared.model

import com.yshmgrt.school.geom.shared.annotation.Field
import com.yshmgrt.school.geom.shared.util.P2D
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.canvas.GraphicsContext
import tornadofx.*
import tornadofx.getValue
import tornadofx.setValue
import javax.json.JsonObject

class Point() : IShape {
    override val type: String
        get() = "point"

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("type", type)
            add("name", name)
            add("x", x)
            add("y", y)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name") ?: ""
            x = double("x") ?: 0.0
            y = double("y") ?: 0.0
        }
    }
    override fun xMin() = x
    override fun xMax() = x
    override fun yMin() = y
    override fun yMax() = y

    override fun create() = Point()

    @Field("x", 1)
    var xProperty = SimpleDoubleProperty(0.0)
    @Field("y", 2)
    var yProperty = SimpleDoubleProperty(0.0)

    var x : Double by xProperty
    var y : Double by yProperty

    @Field("name", 0)
    override val nameProperty = SimpleStringProperty("")
    override var name : String by nameProperty

    constructor(p : P2D, name : String = "") : this(){
        this.name = name
        x = p.x
        y = p.y
    }

    override fun transform(f : (P2D) -> P2D) = Point(f(P2D(x, y)), name)

    override fun draw(parent : GraphicsContext) = with(parent) {
        fill = c(0.0, 0.0, 0.0)
        fillOval(x - 3, y - 3, 6.0, 6.0)
    }
}
