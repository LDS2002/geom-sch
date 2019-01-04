package com.yshmgrt.school.geom.shared.util

import javafx.beans.property.SimpleDoubleProperty
import tornadofx.*
import javax.json.JsonObject
import com.yshmgrt.school.geom.shared.annotation.Field

class P2D(a : Double = 0.0, b : Double = 0.0) : JsonModel {

    @Field("x", 0)
    val xProperty : SimpleDoubleProperty = SimpleDoubleProperty(a)

    @Field("y", 1)
    val yProperty : SimpleDoubleProperty = SimpleDoubleProperty(b)

    var x by xProperty
    var y by yProperty

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("x", x)
            add("y", y)
        }
    }

    override fun updateModel(json: JsonObject) {
        with(json) {
            x = double("x") ?: 0.0
            y = double("y") ?: 0.0
        }
    }
}