package ru.lavafrai.models

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.lavafrai.exler.Article

class ArticleService(database: Database) {
    suspend fun ResultRow.toArticle(): Article {
        val watches = getViewsById(this[ArticlesTable.id].value)

        return Article(
            this[ArticlesTable.id].value,
            this[ArticlesTable.title],
            this[ArticlesTable.author],
            this[ArticlesTable.content],
            this[ArticlesTable.published],
            watches,
        )
    }

    object ArticlesTable : IntIdTable("articles") {
        val title = varchar("title", 255)
        val author = varchar("author", 255)
        val content = text("content")
        val published = long("published")
    }

    object ArticleViewsTable : Table("article_views") {
        val articleId = reference("article_id", ArticlesTable.id)
        val userIPIdentifier = text("user_ip_identifier")
    }

    init {
        transaction(database) {
            SchemaUtils.create(
                ArticlesTable,
                ArticleViewsTable,
            )
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

    suspend fun getViewsById(id: Int): Int = dbQuery {
        ArticleViewsTable.select {
            ArticleViewsTable.articleId eq id
        }.count().toInt()
    }

    suspend fun viewed(id: Int, userIPIdentifier: String): Boolean = dbQuery {
        ArticleViewsTable.select {
            ArticleViewsTable.articleId eq id and (ArticleViewsTable.userIPIdentifier eq userIPIdentifier)
        }.count() > 0
    }

    suspend fun view(id: Int, userIPIdentifier: String) = dbQuery {
        if (!viewed(id, userIPIdentifier)) ArticleViewsTable.insert {
            it[articleId] = id
            it[ArticleViewsTable.userIPIdentifier] = userIPIdentifier
        }
    }
}