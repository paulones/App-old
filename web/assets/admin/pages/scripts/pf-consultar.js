var PFCon = function() {

    return {
        init: function() {
            $('.menu-pf').addClass('active open');
            $('.menu-pf a').append('<span class="selected"></span>');
            $('.menu-pf a .arrow').addClass('open');
            $('.sub-menu-pf-con').addClass('active');
        }
    };
}();