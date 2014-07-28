var PJCad = function() {

    return {
        init: function() {
            $('.menu-pj').addClass('active open');
            $('.menu-pj a').append('<span class="selected"></span>');
            $('.menu-pj a .arrow').addClass('open');
            $('.sub-menu-pj-cad').addClass('active');
        }
    };
}();

