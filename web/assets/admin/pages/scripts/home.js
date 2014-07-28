var Home = function() {

    return {
        init: function() {
            $('.menu-home').addClass('active');
            $('.menu-home a').append('<span class="selected"></span>');
        }
    };
}();