const salluNav = document.querySelector('.sallu-nav');
const mainBody = document.querySelector('.main-body');
const salluAside = document.querySelector('.sallu-aside');

const salluNavHt = getComputedStyle(salluNav).height;
const salluAsideWd = getComputedStyle(salluAside).width;

mainBody.style.setProperty('--nav-height', salluNavHt);
salluAside.style.setProperty('--nav-height', salluNavHt);
mainBody.style.setProperty('--aside-width', salluAsideWd);