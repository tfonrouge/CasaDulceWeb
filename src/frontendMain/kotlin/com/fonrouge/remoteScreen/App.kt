package com.fonrouge.remoteScreen

import io.kvision.*
import io.kvision.core.*
import io.kvision.html.*
import io.kvision.navigo.Match
import io.kvision.panel.flexPanel
import io.kvision.panel.root
import io.kvision.routing.Routing
import io.kvision.routing.routing
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import io.kvision.toast.Toast
import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition
import io.kvision.utils.px
import io.kvision.utils.rem
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class WebState(
    val state: State,
    val match: Match? = null
)

enum class State {
    Home,
    CustomerOrderHdrList,
    CustomerOrderHdrItem,
    ProductCatalog,
    CustomerCatalog
}

class App : Application() {

    private val webState: ObservableValue<WebState> = ObservableValue(WebState(State.Home))

    override fun start(state: Map<String, Any>) {

        Routing.init()

        routing
            .on("/", { webState.value = WebState(State.Home) })
            .on("/${State.CustomerOrderHdrList}", { webState.value = WebState(State.CustomerOrderHdrList) })
            .on("/${State.CustomerOrderHdrItem}", {
                webState.value = WebState(state = State.CustomerOrderHdrItem, match = it)
            })
            .on("/${State.ProductCatalog}", { webState.value = WebState(State.ProductCatalog) })
            .on("/${State.CustomerCatalog}", { webState.value = WebState(State.CustomerCatalog) })
            .resolve()

        root("kvapp") {
            margin = 1.rem
            header().bind(webState) {
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
                    h1("Casa Dulce: ${it.state.name}")
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
            main().bind(webState) {
                when (it.state) {
                    State.Home -> add(ViewHome())
                    State.CustomerOrderHdrList -> add(ViewCustomerOrderHdrList())
                    State.ProductCatalog -> add(ViewInventoryItmCatalog())
                    State.CustomerCatalog -> add(ViewCustomerCatalog())
                    State.CustomerOrderHdrItem -> add(ViewCustomerOrderHdrItem(it.match))
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
