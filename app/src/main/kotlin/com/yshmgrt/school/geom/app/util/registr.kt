package com.yshmgrt.school.geom.app.util

import com.yshmgrt.school.geom.app.solver.APlusBSolver
import com.yshmgrt.school.geom.app.solver.CircleAreaSolver
import com.yshmgrt.school.geom.shared.model.*
import java.awt.Point

var types = hashMapOf(
        "point" to Point::class,
        "circle" to Circle::class,
        "polyline" to Polyline::class,
        "segment" to Segment::class
)

var solvers = listOf(
        APlusBSolver::class,
        CircleAreaSolver::class
)