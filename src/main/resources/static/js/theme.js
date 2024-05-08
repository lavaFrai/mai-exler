function getPreferredTheme() {
    const preferredTheme = window.matchMedia('(prefers-color-scheme: dark)');
    return preferredTheme.matches ? 'dark' : 'light';
}

function getTheme() {
    let theme = localStorage.getItem('theme');
    if (theme === null) {
        theme = getPreferredTheme();
    }
    return theme;
}

function setTheme(theme) {
    localStorage.setItem('theme', theme);
    if (theme === 'dark') document.documentElement.classList.add('dark');
    else document.documentElement.classList.remove('dark');
}

function toggleTheme() {
    setTheme(getTheme() === 'dark' ? 'light' : 'dark');
}

setTheme(getTheme());
console.log('Theme:', getTheme());