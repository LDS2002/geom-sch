package com.yshmgrt.school.geom.solver

import com.yshmgrt.school.geom.annotations.ActionWithResult
import com.yshmgrt.school.geom.annotations.Field
import com.yshmgrt.school.geom.annotations.Text
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