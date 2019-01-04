package com.yshmgrt.school.geom.shared.model

import javafx.beans.property.SimpleStringProperty
import javafx.scene.canvas.GraphicsContext
import javax.json.JsonObject
import com.yshmgrt.school.geom.shared.annotation.Field
import tornadofx.*
import com.yshmgrt.school.geom.shared.util.P2D

interface IShape : JsonModel {

    val type : String

    val nameProperty : SimpleStringProperty
    @Field("name", 0)
    var name : String

    fun transform(f : (P2D) -> P2D) : IShape
    fun draw(parent : GraphicsContext)
    fun create() : IShape
    fun xMin() : Double
    fun xMax() : Double
    fun yMin() : Double
    fun yMax() : Double
    override fun toJSON(json: JsonBuilder)
    override fun updateModel(json: JsonObject)
}