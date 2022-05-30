package com.fonrouge.remoteScreen

import io.kvision.navigo.Match

val Match.action: ViewAction?
    get() {
        val action = params["action"] as? String
        if (action != null) {
            return ViewAction.valueOf(action)
        }
        return null
    }
