package ru.lavafrai

import org.jetbrains.exposed.sql.Database


object Server {
    val database: Database = Database.connect(url = System.getenv("JDBC_DATABASE_URL") ?: throw RuntimeException("JDBC_DATABASE_URL env variable is unfilled"))
}