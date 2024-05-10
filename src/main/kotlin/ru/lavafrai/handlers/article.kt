package ru.lavafrai.handlers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import ru.lavafrai.Server
import ru.lavafrai.exler.Article
import ru.lavafrai.models.ArticleService
import ru.lavafrai.utils.getUserIPAgent

const val lorem5p = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ligula ullamcorper malesuada proin libero nunc consequat interdum varius sit. Arcu cursus vitae congue mauris. Morbi tristique senectus et netus et malesuada fames. Consequat id porta nibh venenatis cras sed felis eget. Et ultrices neque ornare aenean euismod elementum nisi quis eleifend. Bibendum arcu vitae elementum curabitur vitae. Eros donec ac odio tempor orci dapibus ultrices in iaculis. Habitasse platea dictumst quisque sagittis purus sit. Enim nulla aliquet porttitor lacus luctus accumsan tortor posuere ac. Enim sed faucibus turpis in eu. At imperdiet dui accumsan sit amet nulla. Interdum varius sit amet mattis vulputate enim. Elementum nibh tellus molestie nunc non blandit massa enim nec. Quis viverra nibh cras pulvinar.</p>" +
        "<p>Augue neque gravida in fermentum et. Ultricies mi quis hendrerit dolor magna. Odio ut enim blandit volutpat maecenas. Massa tempor nec feugiat nisl pretium. In est ante in nibh mauris cursus mattis. Feugiat vivamus at augue eget arcu dictum varius. Donec ultrices tincidunt arcu non sodales. Adipiscing at in tellus integer feugiat scelerisque. Sed pulvinar proin gravida hendrerit lectus. Egestas purus viverra accumsan in nisl nisi scelerisque eu ultrices. Dui nunc mattis enim ut tellus elementum sagittis vitae et. Sit amet nulla facilisi morbi tempus iaculis. Adipiscing elit ut aliquam purus sit amet luctus venenatis lectus. Suspendisse sed nisi lacus sed viverra tellus in hac habitasse. Cursus in hac habitasse platea dictumst quisque sagittis.</p>" +
        "<p>Egestas purus viverra accumsan in nisl nisi. Sed euismod nisi porta lorem mollis. Tincidunt vitae semper quis lectus nulla at. Ornare arcu odio ut sem nulla pharetra diam sit amet. Volutpat maecenas volutpat blandit aliquam etiam. Nulla malesuada pellentesque elit eget gravida cum sociis. Enim diam vulputate ut pharetra. Integer enim neque volutpat ac tincidunt. Cras sed felis eget velit aliquet sagittis id consectetur. Turpis nunc eget lorem dolor sed viverra ipsum. Aliquam faucibus purus in massa tempor. Nunc sed velit dignissim sodales ut eu. Ac ut consequat semper viverra nam libero justo.</p>" +
        "<p>Pharetra magna ac placerat vestibulum lectus mauris. Faucibus scelerisque eleifend donec pretium vulputate sapien nec sagittis. Praesent tristique magna sit amet purus. Faucibus purus in massa tempor nec feugiat nisl pretium. At elementum eu facilisis sed odio morbi quis. Risus at ultrices mi tempus. Donec enim diam vulputate ut pharetra sit amet aliquam. Libero id faucibus nisl tincidunt eget. Molestie at elementum eu facilisis sed. Scelerisque mauris pellentesque pulvinar pellentesque. Augue eget arcu dictum varius duis at consectetur lorem. Velit sed ullamcorper morbi tincidunt ornare massa. Et malesuada fames ac turpis egestas integer eget aliquet. Ipsum consequat nisl vel pretium lectus quam id leo. Est velit egestas dui id ornare arcu odio. Massa sapien faucibus et molestie ac feugiat sed lectus. Nibh sed pulvinar proin gravida. Cras tincidunt lobortis feugiat vivamus.</p>" +
        "<p>Fames ac turpis egestas sed tempus urna et. Tempus imperdiet nulla malesuada pellentesque elit eget gravida cum. A cras semper auctor neque vitae tempus quam pellentesque. Proin sagittis nisl rhoncus mattis rhoncus urna neque viverra. Risus at ultrices mi tempus. Potenti nullam ac tortor vitae purus faucibus. Nunc id cursus metus aliquam eleifend mi. Libero nunc consequat interdum varius sit amet mattis vulputate enim. Amet massa vitae tortor condimentum lacinia quis vel. Volutpat blandit aliquam etiam erat. In tellus integer feugiat scelerisque varius morbi enim. Fermentum posuere urna nec tincidunt praesent semper. Est lorem ipsum dolor sit amet consectetur adipiscing. Mauris augue neque gravida in. Mi tempus imperdiet nulla malesuada pellentesque. Vestibulum morbi blandit cursus risus at ultrices.</p>"

fun Routing.article() {
    val articleService = ArticleService(Server.database)

    route("/articles", HttpMethod.Get) {
        get {
            call.respond(articleService.getAll().joinToString { it.id.toString() + "\n" })
        }
    }

    route("/article/{id}", HttpMethod.Get) {
        get {
            val userIPAgent = getUserIPAgent(call)
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.NotFound)
            val article = articleService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            launch {
                articleService.view(id, userIPAgent)
            }

            call.respond(FreeMarkerContent("article.ftl", mapOf("article" to article)))
        }
    }
}