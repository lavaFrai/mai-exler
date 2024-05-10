package ru.lavafrai.models

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.lavafrai.exler.Article

class ArticleService(database: Database){
    fun ResultRow.toArticle(): Article {
        val article by lazy { 1 }
        val watches by lazy { 1 }

        return Article(
            this[ArticlesTable.id].value,
            this[ArticlesTable.title],
            this[ArticlesTable.author],
            this[ArticlesTable.content],
            this[ArticlesTable.published],
            article,
            watches,
        )
    }

    object ArticlesTable : IntIdTable("articles") {
        val title = varchar("title", 255)
        val author = varchar("author", 255)
        val content = text("content")
        val published = long("published")
    }

    init {
        transaction(database) {
            SchemaUtils.create(ArticlesTable)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(user: Article): Int = dbQuery {
        ArticlesTable.insert {
            it[title] = user.title
            it[author] = user.author
            it[content] = user.content
            it[published] = user.published
        }[ArticlesTable.id].value
    }

    suspend fun getAll(): List<Article> = dbQuery {
        ArticlesTable.selectAll().map {
            it.toArticle()
        }
    }

    suspend fun getById(id: Int): Article? = dbQuery {
        ArticlesTable.select { ArticlesTable.id eq id }.map {
            it.toArticle()
        }.singleOrNull()
    }

    suspend fun update(id: Int, article: Article): Int = dbQuery {
        ArticlesTable.update({ ArticlesTable.id eq id }) {
            it[title] = article.title
            it[author] = article.author
            it[content] = article.content
            it[published] = article.published
        }
    }
}