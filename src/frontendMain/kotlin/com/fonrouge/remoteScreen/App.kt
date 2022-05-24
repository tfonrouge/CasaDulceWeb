package com.fonrouge.remoteScreen

import io.kvision.*
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.panel.flexPanel
import io.kvision.panel.root
import io.kvision.routing.Routing
import io.kvision.routing.routing
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.utils.px
import io.kvision.utils.rem
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    enum class State {
        Home,
        CustomerOrderList,
        ProductCatalog,
        CustomerCatalog
    }

    private val obs: ObservableValue<State> = ObservableValue(State.Home)

    override fun start(state: Map<String, Any>) {

        Routing.init()

        routing
            .on("/", { obs.value = State.Home })
            .on("/customerOrderList", { obs.value = State.CustomerOrderList })
            .on("/productCatalog", { obs.value = State.ProductCatalog })
            .on("/customerCatalog", { obs.value = State.CustomerCatalog })
            .resolve()

        root("kvapp") {
            margin = 1.rem
            header().bind(obs) {
                flexPanel(
                    direction = FlexDirection.ROW,
                    justify = JustifyContent.SPACEBETWEEN,
                    alignItems = AlignItems.CENTER
                ) {
                    borderBottom = Border(width = 3.px, style = BorderStyle.SOLID, color = Color("blue"))
                    image("Logotipo-Casa-Dulce.png") {
                        height = 5.rem
                        marginRight = 1.rem
                    }
                    h1("Casa Dulce: ${it.name}")
                    div().bind(Model.obsProfile) {
                        if (it.username != null) {
                            button(text = "Logout", icon = "fas fa-sign-out-alt", style = ButtonStyle.OUTLINEDANGER) {
                                marginRight = 1.rem
                            }.onClick {
                                document.location?.href = "/logout"
                            }
                            label(content = Model.obsProfile.value.name) {
                                colorName = Col.DARKGRAY
                            }
                        } else {
                            div()
                        }
                    }
                    marginBottom = 1.rem
                }
            }
            main().bind(obs) {
                when (it) {
                    State.Home -> add(ViewHome())
                    State.CustomerOrderList -> add(ViewCustomerOrderList())
                    State.ProductCatalog -> add(ViewProductCatalog())
                    State.CustomerCatalog -> add(ViewCustomerCatalog())
                }
            }
        }
        AppScope.launch {
            Model.ping("hello")
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
        ImaskModule,
        RichTextModule,
        ChartModule,
        TabulatorModule,
        MomentModule,
        ToastModule,
        PrintModule,
        CoreModule
    )
}
