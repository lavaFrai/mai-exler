<#include "buttons.ftl">

<header>
    <div class="page-width">
        <div class="row">
            <div id="mobile-site-menu" class="mobile" onclick="openMenu()"></div>
            <a href="/" id="site-title">Экслер МАИ</a>
        </div>

        <i class="fa-solid fa-moon theme-change-button light-theme" onclick="toggleTheme()"></i>
        <i class="fa-solid fa-sun theme-change-button dark-theme" onclick="toggleTheme()"></i>
    </div>
</header>
<nav id="page-navigation" onclick="if (isMobile()) closeMenu()">
    <div>
        <a href="/teachers">О Преподавателях</a>
        <a href="/info">Информация</a>
        <a href="/landscape">Ландшафт</a>
        <a href="/mailogo">Символика МАИ</a>
        <a href="/publications">Публикации</a>
        <a href="/community">МАИ и Маёвцы</a>
    </div>
</nav>