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

    //$.each($('.post-content h1'),function(i,n){
    //         $(n).replaceWith('<div class="ui large header h1">' + $(n).text() + '</div>');
    //     });
    //     $.each($('.post-content h2'),function(i,n){
    //         $(n).replaceWith('<div class="ui large header h2">' + $(n).text() + '</div>');
    //     });
    //     $.each($('.post-content h3'),function(i,n){
    //         $(n).replaceWith('<div class="ui large header h3">' + $(n).text() + '</div>');
    //     });
    //     $.each($('.post-content h4'),function(i,n){
    //         $(n).replaceWith('<div class="ui large header h4">' + $(n).text() + '</div>');
    //     });
    //     $.each($('.post-content h5'),function(i,n){
    //         $(n).replaceWith('<div class="ui large header h5">' + $(n).text() + '</div>');
    //     });
    //     $.each($('.post-content h6'),function(i,n){
    //         $(n).replaceWith('<div class="ui large header h6">' + $(n).text() + '</div>');
    //     });
    $('.post-content table').addClass('ui celled table');
    $('.post-content h1').addClass('ui header large blue');
    $('.post-content h2').addClass('ui header large blue');
    $('.post-content h3').addClass('ui header medium').prepend('<i class="mini brown hashtag icon"></i>');
    $('.post-content h4').addClass('ui header medium').prepend('<i class="mini gyey hashtag icon"></i>');
    $('.post-content h5').addClass('ui header large blue');
    $('.post-content h6').addClass('ui header large blue');
    // $('.post-content > p').addClass('ui basic segment');
    $('.post-content ul').addClass('ui list');
    $('.post-content ol').addClass('ui list');
    $('.post-content li').addClass('item');

});