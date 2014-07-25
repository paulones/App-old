var Lock = function() {

    var handlePassword = function() {
        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                password: {
                    required: true
                }
            },
            messages: {
                password: {
                    required: "Digite uma senha."
                }
            },
            highlight: function(element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                $('.form-group').removeClass('has-error');
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },
            submitHandler: function(form) {
                $('#password').val(CryptoJS.MD5($('#password').val()));
                $('.submit-login').click();
            }
        });

        $('.login-form button').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('#password').val(CryptoJS.MD5($('#password').val()));
                    $('.submit-login').click();
                    //$('.login-form').submit();
                }
                return false;
            }
        });

    }
    return {
        //main function to initiate the module
        init: function() {

            handlePassword();

            if (Modernizr.localstorage) {
                window.localStorage["pagina_anterior"] = $.cookie('pagina_anterior');
            }


            window.setInterval(function() {
                if ($.cookie('usuario') == null) {
                    window.location = "login.xhtml";
                }
                if ($.cookie('pagina_anterior') == null && window.localStorage['pagina_anterior'] != null) {
                    if (Modernizr.localstorage) {
                        $.cookie("pagina_anterior", window.localStorage['pagina_anterior'], {expires: 1, path: '/'});
                    }
                }
            }, 10000);

            window.setInterval(function() {
                window.location.reload();
            }, 1500000);

            $.backstretch([
                "../../assets/admin/pages/media/bg/1.jpg",
                "../../assets/admin/pages/media/bg/2.jpg",
                "../../assets/admin/pages/media/bg/3.jpg",
                "../../assets/admin/pages/media/bg/4.jpg"
            ], {
                fade: 1000,
                duration: 8000
            });
        }

    };

}();