$(document).ready(function () {
    //固定顶部
    $('.main.menu').visibility({
        type: 'fixed'
    });

    $('.special.card .image').dimmer({
        on: 'hover'
    });
    $('.card .dimmer').dimmer({
        on: 'hover'
    });

    //视频初始化参数
    $('.ui.embed.headvideo').embed({});

    // windowAddMouseWheel();
});