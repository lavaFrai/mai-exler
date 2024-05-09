function isMobile() {
    return window.innerWidth <= 768;
}

function setMobile(value) {
    if (isMobile()) {
        document.documentElement.classList.add('mobile');
        document.documentElement.classList.remove('desktop');
    } else {
        document.documentElement.classList.remove('mobile');
        document.documentElement.classList.add('desktop');
    }

}

window.onresize = setMobile

setMobile()