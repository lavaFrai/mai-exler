<#include "head.ftl">
<#include "header.ftl">
<#include "ad.ftl">

<link rel="stylesheet" href="/static/css/article.css">
<div id="page-content" class="page-width">
    <div id="article-title" class="column">
        <div class="mobile-adaptive">
            <span id="article-author">${article_author}</span>
            <div class="desktop" style="width: 4px"></div>
            <span id="article-published">${article_published}</span>
        </div>
        <div class="row">
            ${article_title}
        </div>
    </div>

    <div id="article-content" class="column">
        ${article_content}
    </div>
</div>

<#include "footer.ftl">