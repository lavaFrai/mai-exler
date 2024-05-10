package ru.lavafrai.utils

import io.ktor.server.application.*
import io.ktor.util.pipeline.*

fun getUserIPAgent(call: ApplicationCall): String {
    return call.request.local.remoteHost + ":" + (call.request.headers["X-Real-IP"] ?: "")
}