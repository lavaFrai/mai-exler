<#include "head.ftl">
<#include "header.ftl">
<#include "ad.ftl">

<link rel="stylesheet" href="/static/css/article.css">
<div id="page-content" class="page-width">
    <div id="article-title" class="column">
        <div class="mobile-adaptive">
            <span id="article-author">${article.author}</span>
            <div class="desktop" style="width: 4px"></div>
            <span id="article-published">${article.published?number_to_date?string("dd-MM-YYYY")}</span>
        </div>
        <div class="row">
            ${article.title}
        </div>
    </div>

    <div id="article-content" class="column">
        ${article.content}
    </div>

    <div id="article-metrics">
        <hr>
        <div class="row">
            <span id="article-views" class="row">
                <i class="fa-solid fa-eye"></i>
                <span id="article-views-value">${article.views}</span>
            </span>
            <span id="article-share" class="row">
                <script>
                    function shareArticle() {
                        navigator.share({
                            title: "${article.title?js_string}",
                            text: "${article.content?js_string}",
                            url: window.location.href
                        });
                    }
                </script>
                <i class="fa-solid fa-share" onclick="shareArticle()"></i>
            </span>
        </div>
    </div>
</div>

<#include "footer.ftl">