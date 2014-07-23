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

            window.setInterval(function() {
                if ($.cookie('usuario') == null) {
                    window.location = "login.xhtml";
                }
            }, 3000);

            window.setInterval(function() {
                window.location = "bloquear_tela.xhtml";
            }, 3000);//1740000);

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