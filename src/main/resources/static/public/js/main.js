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

    //为post页面设置样式
    $('.post-content table').addClass('ui celled table');


    $('#messagecloseicon')
        .on('click', function() {
            $(this)
                .closest('#messagetips')
                .transition('fade')
            ;
        })
    ;

});