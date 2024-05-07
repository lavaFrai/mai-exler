package ru.lavafrai.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*




fun Application.configureDatabases() {
    val database: Database = Database.connect(
        url = System.getenv("JDBC_DATABASE_URL") ?: throw RuntimeException("JDBC_DATABASE_URL env variable is unfilled"),
        user = "root",
        password = ""
    )

    fun Application.getDatabase(): Database {
        return database
    }
}
