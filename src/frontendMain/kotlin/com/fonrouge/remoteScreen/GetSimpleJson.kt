package com.fonrouge.remoteScreen

import io.kvision.form.FormPanel

fun FormPanel<*>.getSimpleJson(): String {
   val json = js("{}")
   form.fields.forEach {
       json[it.key] = it.value.getValue()
   }
   return JSON.stringify(json)
}
