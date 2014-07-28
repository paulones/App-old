var PJud = function() {

    return {
        //main function
        init: function() {
            $('.menu-proc-jud').addClass('active');
            $('.menu-proc-jud a').append('<span class="selected"></span>');
        }
    };
}();