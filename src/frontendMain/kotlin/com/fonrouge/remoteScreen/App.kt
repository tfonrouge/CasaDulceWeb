package com.fonrouge.remoteScreen

import com.fonrouge.fsLib.layout.centeredMessage
import com.fonrouge.fsLib.view.AppScope
import com.fonrouge.fsLib.view.KVWebManager
import com.fonrouge.fsLib.view.showView
import com.fonrouge.remoteScreen.config.ConfigViewCasaDulceHome
import com.fonrouge.remoteScreen.config.ConfigViewImpl
import io.kvision.*
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.panel.flexPanel
import io.kvision.panel.root
import io.kvision.routing.routing
import io.kvision.state.bind
import io.kvision.toast.Toast
import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition
import io.kvision.utils.px
import io.kvision.utils.rem
import kotlinx.browser.document
import kotlinx.coroutines.launch

const val version = "1.0"

class App : Application() {

    override fun start(state: Map<String, Any>) {

        require("app.css")

        KVWebManager.initialize {
            refreshViewListPeriodic = true
            refreshViewItemPeriodic = true
            frontEndAppName = "Casa Dulce"
            frontEndVersion = version
            motto = "Estamos para servirle..."
            configViewHome = ConfigViewCasaDulceHome
            iConfigView = ConfigViewImpl()
        }

        root("kvapp") {
            margin = 1.rem
            header().bind(KVWebManager.viewStateObservableValue) {
                flexPanel(
                    direction = FlexDirection.ROW,
                    justify = JustifyContent.SPACEBETWEEN,
                    alignItems = AlignItems.CENTER
                ) {
                    borderBottom = Border(width = 3.px, style = BorderStyle.SOLID, color = Color("blue"))
                    image("Logotipo-Casa-Dulce.png") {
                        height = 5.rem
                        marginRight = 1.rem
                        onClick { routing.navigate("/") }
                    }
                    h1("Casa Dulce: ${it?.configView?.label}")
                    div().bind(ModelCasaDulce.obsProfile) {
                        if (it.username != null) {
                            button(text = "Logout", icon = "fas fa-sign-out-alt", style = ButtonStyle.OUTLINEDANGER) {
                                marginRight = 1.rem
                            }.onClick {
                                document.location?.href = "/logout"
                            }
                            label(content = ModelCasaDulce.obsProfile.value.name) {
                                colorName = Col.DARKGRAY
                            }
                        } else {
                            div()
                        }
                    }
                    marginBottom = 1.rem
                }
            }
            main().bind(KVWebManager.viewStateObservableValue) { viewState ->
                if (viewState != null) {
                    showView(viewState)
                } else {
                    centeredMessage("Page not Found ... ")
                }
            }
        }
        AppScope.launch {
            Toast.success(
                message = ModelCasaDulce.ping("hello"),
                options = ToastOptions(positionClass = ToastPosition.BOTTOMCENTER)
            )
        }
//        routing.resolve("")
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        BootstrapSelectModule,
        BootstrapSpinnerModule,
        BootstrapIconsModule,
        FontAwesomeModule,
        RichTextModule,
        ChartModule,
        TabulatorModule,
        ToastModule,
        PrintModule,
        CoreModule
    )
}
