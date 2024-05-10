package ru.lavafrai

import org.jetbrains.exposed.sql.Database


object Server {
    init {
        println("Connecting to database ${System.getenv("JDBC_DATABASE_URL")}")
    }

    val database: Database = Database.connect(
        url = System.getenv("JDBC_DATABASE_URL") ?: throw RuntimeException("JDBC_DATABASE_URL env variable is unfilled"),
        user = System.getenv("JDBC_DATABASE_USER") ?: throw RuntimeException("JDBC_DATABASE_USERNAME env variable is unfilled"),
        password = System.getenv("JDBC_DATABASE_PASSWORD") ?: throw RuntimeException("JDBC_DATABASE_PASSWORD env variable is unfilled"),
    )
}