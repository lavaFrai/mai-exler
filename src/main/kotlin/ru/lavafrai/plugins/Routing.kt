package ru.lavafrai.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.lavafrai.handlers.index

fun Application.configureRouting() {
    install(AutoHeadResponse)
    routing {
        get("/") {
            call.respondText("Hello World")
        }

        staticResources("/static", "static")

        index()
    }
}
