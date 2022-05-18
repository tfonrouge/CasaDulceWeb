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
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher

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
            header {
            }.bind(obs) {
                flexPanel(direction = FlexDirection.ROW, justify = JustifyContent.CENTER, alignItems = AlignItems.CENTER) {
                    borderBottom = Border(width = 3.px, style = BorderStyle.SOLID, color = Color("blue"))
                    image("Logotipo-Casa-Dulce.png") {
                        height = 8.rem
                        marginRight = 1.rem
                    }
                    h1("Casa Dulce: ${it.name}")
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
        routing.resolve("")
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
