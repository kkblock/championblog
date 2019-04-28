jQuery(document).ready(function ($) {
    if ($("meta[name=toTop]").attr("content") === "true") {
        if ($(this).scrollTop() === 0) {
            $("#toTop").hide();
        }
        $(window).scroll(function (event) {
            /* Act on the event */
            if ($(this).scrollTop() === 0) {
                $("#toTop").hide();
            }
            if ($(this).scrollTop() !== 0) {
                $("#toTop").show();
            }
        });
        $("#toTop").click(function (event) {
            /* Act on the event */
            $("html,body").animate({
                    scrollTop: "0px"
                },
                666
            )
        });
    }
});