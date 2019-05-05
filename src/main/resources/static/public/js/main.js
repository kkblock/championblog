$(document).ready(function () {
    //固定顶部
    $('.main.menu').visibility({
        type: 'fixed'
    });

    $('.articleleftstiky').sticky({
        context: '#articlestiky',
        pushing: true,
        offset: 80
    });

    //载入highlight函数
    hljs.initHighlightingOnLoad();

    /**
     * 读取文章
     * @param obj
     */
    postReading = function (obj) {
        var name = $(obj).attr('name');
        console.log(name);
        window.location.href = name;
    };

    $('.post-content table').addClass('ui celled table');
    $('.post-content h1').addClass('ui header orange');
    $('.post-content h2').addClass('ui header blue');
    $('.post-content h3').addClass('ui header orange');
    $('.post-content h4').addClass('ui header blue');
    $('.post-content h5').addClass('ui header orange');
    $('.post-content h6').addClass('ui header blue');
    // $('.post-content > p').addClass('ui basic segment');
    $('.post-content ul').addClass('ui list');
    $('.post-content ol').addClass('ui list');
    $('.post-content li').addClass('item');

});