package com.yshmgrt.school.geom.app.util

import javafx.scene.control.MultipleSelectionModel
import tornadofx.*

class NoSelectionModel<T> : MultipleSelectionModel<T>() {
    override fun clearSelection(index: Int) {}

    override fun clearSelection() {}

    override fun selectLast() {}

    override fun isSelected(index: Int) = false

    override fun getSelectedIndices() = observableList<Int>()

    override fun selectAll() {}

    override fun getSelectedItems() = observableList<T>()

    override fun select(index: Int) {}

    override fun select(obj: T?) {}

    override fun isEmpty() = true

    override fun selectNext() {}

    override fun selectPrevious() {}

    override fun selectIndices(index: Int, vararg indices: Int) {}

    override fun selectFirst() {}

    override fun clearAndSelect(index: Int) {}

}