package com.fonrouge.casaDulceWeb.config

import com.fonrouge.fsLib.config.ConfigViewHome
import com.fonrouge.casaDulceWeb.views.ViewHomeCasaDulce

object ConfigViewCasaDulceHome : ConfigViewHome<ViewHomeCasaDulce>(
    name = "",
    label = "",
    viewFunc = ViewHomeCasaDulce::class
)
