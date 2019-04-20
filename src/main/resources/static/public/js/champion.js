//绑定鼠标事件
// $(document).on('mousewheel DOMMouseScroll', onMouseScroll);
//
// //鼠标滚动
// function onMouseScroll(e){
//     var wheel = e.originalEvent.wheelDelta || -e.originalEvent.detail;
//     var delta = Math.max(-1, Math.min(1, wheel) );
//     if(delta<0){//向下滚动
//         $('div.main.menu').fadeOut();
//     }else{//向上滚动
//         $('div.main.menu').fadeIn();
//     }
// }

//显示weibo qq wechat 二维码函数
function onlineshowmodal(param) {
    switch (param) {
        case 'weibo':
            $('.weibopicmodal').modal('show');
            break;
        case 'wechat':
            $('.wechatpicmodal').modal('show');
            break;
        case 'qq':
            $('.qqpicmodal').modal('show');
            break;
        default:
            console.error('modal not found');
    }
}

/**
 * 读取文章
 * @param aid 文章id
 */
postReading = function (aid) {
    console.log("获取到文章，准备进入文章细节：" + aid)
}
