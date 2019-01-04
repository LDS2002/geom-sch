package com.yshmgrt.school.geom.shared.model

import tornadofx.ItemViewModel

class ShapeModel : ItemViewModel<IShape>() {
    var name = bind{item?.nameProperty}
}