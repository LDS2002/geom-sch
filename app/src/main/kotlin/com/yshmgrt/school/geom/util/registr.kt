package com.yshmgrt.school.geom.util

import com.yshmgrt.school.geom.model.Circle
import com.yshmgrt.school.geom.model.Point
import com.yshmgrt.school.geom.model.Polyline
import com.yshmgrt.school.geom.model.Segment
import com.yshmgrt.school.geom.solver.APlusBSolver
import com.yshmgrt.school.geom.solver.CircleAreaSolver

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