package com.yshmgrt.school.geom.app.solver

import com.yshmgrt.school.geom.shared.annotation.ActionWithResult
import com.yshmgrt.school.geom.shared.annotation.Field
import com.yshmgrt.school.geom.shared.annotation.Text
import com.yshmgrt.school.geom.shared.model.ISolver
import javafx.beans.property.SimpleDoubleProperty

class APlusBSolver : ISolver {
    @Text
    override var name = "Sum"
    @Field("a", 0)
    val a = SimpleDoubleProperty(0.0)
    @Field("b", 1)
    val b = SimpleDoubleProperty(0.0)
    @ActionWithResult("Solve", 0)
    override fun solve(): Double {
        return a.value + b.value
    }
}