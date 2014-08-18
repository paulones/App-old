var PjudCon = function() {
    
    return {
        init: function() {
            
            $('.menu-pjud').addClass('active open');
            $('.menu-pjud a').append('<span class="selected"></span>');
            $('.menu-pjud a .arrow').addClass('open');
            $('.sub-menu-pjud-con').addClass('active');
        }
    };
}();

