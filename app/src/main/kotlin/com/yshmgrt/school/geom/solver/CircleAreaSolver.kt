package com.yshmgrt.school.geom.solver

import com.yshmgrt.school.geom.annotations.ActionWithResult
import com.yshmgrt.school.geom.annotations.Field
import com.yshmgrt.school.geom.annotations.Text
import com.yshmgrt.school.geom.model.Circle
import com.yshmgrt.school.geom.util.sqr
import javafx.beans.property.SimpleObjectProperty

class CircleAreaSolver : ISolver {
    @Text
    override var name = "Circle area"
    @Field("Circle", 0)
    var shapeProperty = SimpleObjectProperty<Circle>(Circle())
    @ActionWithResult("Solve", 0)
    override fun solve(): Double {
        return shapeProperty.value.r.sqr().times(Math.PI)
    }
}