package com.yshmgrt.school.geom.shared.annotation

@Retention(AnnotationRetention.RUNTIME)
annotation class Field(val name : String = "", val id : Int = -1)
@Retention(AnnotationRetention.RUNTIME)
annotation class Action(val name : String = "", val id : Int = -1)
@Retention(AnnotationRetention.RUNTIME)
annotation class Text
@Retention(AnnotationRetention.RUNTIME)
annotation class ActionWithResult(val name : String = "", val id : Int = -1)