package com.yshmgrt.school.geom.app.solver

import com.yshmgrt.school.geom.app.util.sqr
import com.yshmgrt.school.geom.shared.annotation.ActionWithResult
import com.yshmgrt.school.geom.shared.annotation.Field
import com.yshmgrt.school.geom.shared.annotation.Text
import com.yshmgrt.school.geom.shared.model.Circle
import com.yshmgrt.school.geom.shared.model.ISolver
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