package com.yshmgrt.school.geom.util

import tornadofx.*

object UpdateListsEvent : FXEvent(EventBus.RunOn.BackgroundThread)
object UpdateCanvasEvent : FXEvent(EventBus.RunOn.BackgroundThread)

fun updateLists() = FX.eventbus.fire(UpdateListsEvent)
fun updateCanvas() = FX.eventbus.fire(UpdateCanvasEvent)