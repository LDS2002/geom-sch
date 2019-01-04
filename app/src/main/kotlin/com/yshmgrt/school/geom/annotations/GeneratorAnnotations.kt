package com.yshmgrt.school.geom.annotations

import com.yshmgrt.school.geom.model.IShape
import com.yshmgrt.school.geom.model.ShapeModel
import com.yshmgrt.school.geom.util.NoSelectionModel
import com.yshmgrt.school.geom.util.updateCanvas
import com.yshmgrt.school.geom.view.ChooseMenuView
import javafx.beans.property.*
import javafx.scene.Node
import javafx.scene.control.TextField
import tornadofx.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberProperties

annotation class Field(val name : String = "", val id : Int = -1)
@Retention(AnnotationRetention.RUNTIME)
annotation class Action(val name : String = "", val id : Int = -1)
@Retention(AnnotationRetention.RUNTIME)
annotation class Text
@Retention(AnnotationRetention.RUNTIME)
annotation class ActionWithResult(val name : String = "", val id : Int = -1)

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
                        is SimpleStringProperty -> eventtextfield { bind(value) }
                        is SimpleDoubleProperty -> eventtextfield { bind(value) }
                        is SimpleListProperty<*> -> listview(value as SimpleListProperty<Any>) {
                            selectionModel = NoSelectionModel<Any>()
                            cellFormat {
                                graphic = vbox {
                                    add(GenericForm(item!!).root)
                                    hbox {
                                        button("Add").action {
                                            value.value.add(index + 1, value.value[0]::class.createInstance())
                                            updateCanvas()
                                        }
                                        button("Delete").action {
                                            value.value.remove(index, index + 1)
                                            updateCanvas()
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
fun Node.eventtextfield(f : TextField.() -> Unit) {
    textfield().apply(f).apply {
        onTextChange { updateCanvas() }
        onFocusChanged { updateCanvas() }
    }
}