function windowAddMouseWheel() {
    //给页面绑定滑轮滚动事件
    if (document.addEventListener) {
        document.addEventListener('DOMMouseScroll', scrollFunc, false);
    }
    //滚动滑轮触发scrollFunc方法
    window.onmousewheel = document.onmousewheel = scrollFunc;
}

/**
 * 鼠标滚动事件
 * @param e
 */
var scrollFunc = function (e) {
    e = e || window.event;
    if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件
        if (e.wheelDelta > 0) { //当滑轮向上滚动时
            $('div.menu.main').fadeIn();
        }
        if (e.wheelDelta < 0) { //当滑轮向下滚动时
            $('div.menu.main').fadeOut();
        }
    } else if (e.detail) {  //Firefox滑轮事件
        if (e.detail> 0) { //当滑轮向上滚动时
            $('div.menu.main').fadeIn();
        }
        if (e.detail< 0) { //当滑轮向下滚动时
            $('div.menu.main').fadeOut();
        }
    }
};