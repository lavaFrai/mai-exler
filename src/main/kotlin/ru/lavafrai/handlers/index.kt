package ru.lavafrai.handlers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.index() {
    route("/", HttpMethod.Get) {
        get {
            call.respond(FreeMarkerContent("index.ftl", mapOf("user" to "")))
        }
    }
}