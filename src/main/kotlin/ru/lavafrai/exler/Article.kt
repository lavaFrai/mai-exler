package ru.lavafrai.exler

import kotlinx.serialization.Serializable

@Serializable
data class Article (
    val id: Int,
    val title: String,
    val author: String,
    val content: String,
    val published: Long,
    val views: Int,
)