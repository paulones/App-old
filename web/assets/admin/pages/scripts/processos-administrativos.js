var PAdm = function() {

    return {
        //main function
        init: function() {
            $('.menu-proc-adm').addClass('active');
            $('.menu-proc-adm a').append('<span class="selected"></span>');
        }
    };
}();