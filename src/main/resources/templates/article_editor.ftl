<#include "fragments/head.ftl">
<#include "fragments/header.ftl">
<#include "fragments/content.ftl">

<link rel="stylesheet" href="https://unpkg.com/jodit@4.2.5/es2021/jodit.min.css"/>
<script src="https://unpkg.com/jodit@4.2.5/es2021/jodit.min.js"></script>
<@content>
    <div id="editor">

    </div>

    <div style="margin-top: 16px">
        <!--button onclick="openPreview()">Предпросмотр</button-->
    </div>
</@content>
<script>
    const editor = Jodit.make('#editor', {
        "toolbar": true,
        "spellcheck": true,
        "zIndex": 1,
    })

    editor.events.on('change', function () {
        localStorage.setItem('editor', editor.value)
    })

    if (localStorage.getItem('editor')) {
        editor.value = localStorage.getItem('editor')
    }

    function openPreview() {
        alert(editor.value)
    }
</script>

<#include "fragments/footer.ftl">