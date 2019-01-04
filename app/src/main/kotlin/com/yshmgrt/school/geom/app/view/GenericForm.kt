package com.yshmgrt.school.geom.app.view

import com.yshmgrt.school.geom.app.util.*
import com.yshmgrt.school.geom.shared.annotation.*
import com.yshmgrt.school.geom.shared.annotation.Field
import com.yshmgrt.school.geom.shared.model.IShape
import com.yshmgrt.school.geom.shared.model.ShapeModel
import com.yshmgrt.school.geom.shared.util.updateCanvas
import com.yshmgrt.school.geom.shared.util.updateLists
import javafx.beans.property.*
import javafx.scene.Node
import javafx.scene.control.TextField
import tornadofx.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberProperties

class GenericForm(src : Any) : Fragment() {
    override val root : Form = form{
        val texts = src::class.memberProperties.filter { it -> it.annotations.find { it is Text } != null }
        fieldset {
            for (text in texts) {
                label(text.getter.call(src) as? String ?: "")
            }
        }
        val fields = src::class.memberProperties.filter { it -> it.annotations.find { it is Field } != null }.sortedBy { a -> (a.annotations.find { it is Field } as Field).id }
        val fieldsMap = mutableMapOf<Int, MutableSet<KProperty1<out Any, Any?>>>()
        fields.forEach { fieldsMap[(it.annotations.find{it is Field} as Field).id]?.add(it) ?: mutableSetOf(it)}
        fieldset {
            for (field in fields) {
                val annotation = field.annotations.find { it is Field } as Field
                field(annotation.name) {
                    val value = field.getter.call(src)
                    when (value) {
                        is SimpleStringProperty -> eventTextField { bind(value) }
                        is SimpleDoubleProperty -> eventTextField { bind(value) }
                        is SimpleListProperty<*> -> listview(value as SimpleListProperty<Any>) {
                            selectionModel = NoSelectionModel<Any>()
                            cellFormat {
                                graphic = vbox {
                                    add(GenericForm(item!!).root)
                                    hbox {
                                        button("Add").action {
                                            value.value.add(index + 1, value.value[0]::class.createInstance())
                                            updateLists()
                                        }
                                        button("Delete").action {
                                            value.value.remove(index, index + 1)
                                            updateLists()
                                        }
                                    }
                                }
                            }
                        }
                        is SimpleObjectProperty<*> -> {
                            when (value.value) {
                                is IShape -> {
                                    field {
                                        val model = ShapeModel()
                                        label(model.name)
                                        button("Chose").action {
                                            ChooseMenuView { it::class == value.value::class }.apply {
                                                openModal(block = true)
                                                value.value = output()?.item ?: value.value
                                                model.item = value.value as IShape
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        val actions = src::class.declaredFunctions.filter { it -> it.annotations.find { it is Action } != null }.sortedBy { a -> (a.annotations.find { it is Action } as Action).id }
        fieldset {
            for (action in actions) {
                val annotation = action.annotations.find { it is Action } as Action
                button(annotation.name).action{ action.call(src) }
            }
        }
        val actionsWithResult = src::class.declaredFunctions.filter { it -> it.annotations.find { it is ActionWithResult } != null }.sortedBy { a -> (a.annotations.find { it is ActionWithResult } as ActionWithResult).id }
        fieldset {
            for (action in actionsWithResult) {
                val annotation = action.annotations.find { it is ActionWithResult } as ActionWithResult
                val resultLabel = label("")
                button(annotation.name).action{
                    resultLabel.text = action.call(src).toString()
                }
            }
        }
    }
}
fun Node.eventTextField(f : TextField.() -> Unit) {
    textfield().apply(f).apply {
        onTextChange { updateCanvas() }
        onFocusChanged { updateCanvas() }
    }
}