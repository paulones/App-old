var Login = function() {

    var handleLogin = function() {
        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                cpf: {
                    cpf:true,
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },
            messages: {
                cpf: {
                    required: "Entre com um CPF."
                },
                password: {
                    required: "Digite uma senha."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit   
                $('.alert-danger', $('.login-form')).show();
                $('.login-error').hide();
                $('.login-success').hide();
            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },
            submitHandler: function(form) {
                $('#password').val(CryptoJS.MD5($('#password').val()));
                $('.submit-login').click();
            }
        });

        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                    $('#password').val(CryptoJS.MD5($('#password').val()));
                    $('.submit-login').click();
                }
                return false;
            }
        });
    }

    var handleForgetPassword = function() {
        $('.forget-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                email: {
                    required: true,
                    email: true
                }
            },
            messages: {
                email: {
                    required: "E-mail necessário."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit   

            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },
            submitHandler: function(form) {
                $(".submit-emailrecover").click();
            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.forget-form').validate().form()) {
                    $(".submit-emailrecover").click();
                }
                return false;
            }
        });

        jQuery('#forget-password').click(function() {
            jQuery('.login-form').hide();
            jQuery('.forget-form').show();
        });

        jQuery('#back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
        });
    }

    var handleRegister = function() {
        $('.register-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                fullname: {
                    required: true
                },
                register_email: {
                    required: true,
                    email: true
                },
                register_cpf: {
                    cpf: true,
                    required: true
                },
                register_password: {
                    required: true
                },
                rpassword: {
                    equalTo: "#register_password"
                },
//                tnc: {
//                    required: true
//                }
            },
            messages: {// custom messages for radio buttons and checkboxes
                tnc: {
                    required: "Por favor, esteja de acordo com os Termos de Serviço e Política de Privacidade."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit   

            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
                    error.insertAfter($('#register_tnc_error'));
                } else if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },
            submitHandler: function(form) {
                $('#register_password').val(CryptoJS.MD5($('#register_password').val()));
                $('#rpassword').val(CryptoJS.MD5($('#rpassword').val()));
                $('.submit-register').click();
            }
        });

        $('.register-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.register-form').validate().form()) {
                    $('#register_password').val(CryptoJS.MD5($('#register_password').val()));
                    $('#rpassword').val(CryptoJS.MD5($('#rpassword').val()));
                    $('.submit-register').click();
                }
                return false;
            }
        });

        jQuery('#register-btn').click(function() {
            jQuery('.login-form').hide();
            jQuery('.licensing-form').hide();
            jQuery('.register-form').show();
        });

        jQuery('#register-back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.licensing-form').hide();
            jQuery('.register-form').hide();
        });
    }

    var handleLicensing = function() {
        $('.licensing-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                licenca: {
                    required: true
                }
            },
            messages: {
                licenca: {
                    required: "Digite uma chave v&aacute;lida."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit   

            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },
            submitHandler: function(form) {
                $(".submit-licensing").click();
            }
        });


        $('.licensing-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.licensing-form').validate().form()) {
                    $('.submit-licensing').click();
                }
                return false;
            }
        });

        jQuery('#licensing-back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.licensing-form').hide();
            jQuery('.register-form').hide();
        });
    }

    var handlePass = function() {
        $('.pass-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                new_password: {
                    required: true
                },
                rnew_password: {
                    equalTo: "#new_password"
                }
            },
            messages: {// custom messages for radio buttons and checkboxes
            },
            invalidHandler: function(event, validator) { //display error alert on form submit   
            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                if (element.closest('.input-icon').size() === 1) {
                    error.insertAfter(element.closest('.input-icon'));
                } else {
                    error.insertAfter(element);
                }
            },
            submitHandler: function(form) {
                $('#new_password').val(CryptoJS.MD5($('#new_password').val()));
                $('#rnew_password').val(CryptoJS.MD5($('#rnew_password').val()));
                $('.submit-pass').click();
            }
        });

        $('.pass-form input').keypress(function(e) {
            if (e.which == 13) {
                if ($('.pass-form').validate().form()) {
                    $('#new_password').val(CryptoJS.MD5($('#new_password').val()));
                    $('#rnew_password').val(CryptoJS.MD5($('#rnew_password').val()));
                    $('.submit-pass').click();
                }
                return false;
            }
        });

        jQuery('#pass-back-btn').click(function() {
            jQuery('.login-form').show();
            jQuery('.pass-form').hide();
        });
    }

    return {
        //main function to initiate the module
        init: function() {

            if (Modernizr.localstorage) {
                localStorage.clear();
            }

            $.validator.addMethod("cpf", validaCPF, "Digite um CPF v&aacute;lido.");

            handleLogin();
            handleForgetPassword();
            handleRegister();
            handleLicensing();
            handlePass();

            $('.cpf').mask("999.999.999-99");

            window.setInterval(function() {
                $.post("ping.html");
            }, 1740000);

            if ($('.register-error').length > 0) {
                jQuery('.login-form').hide();
                jQuery('.register-form').show();
            }

            if ($('.licensing-error').length > 0) {
                jQuery('.login-form').hide();
                jQuery('.licensing-form').show();
            }

            if ($('#pass').length > 0) {
                jQuery('.login-form').hide();
                jQuery('.pass-form').show();
            }
            
            function validaCPF(value, element) {
                value = value.replace(/\./g, "").replace(/-/g, "");
                var Soma;
                var Resto;
                Soma = 0;
                if (value == "")
                    return true;
                if (value == "00000000000")
                    return false;
                for (i = 1; i <= 9; i++)
                    Soma = Soma + parseInt(value.substring(i - 1, i)) * (11 - i);
                Resto = (Soma * 10) % 11;
                if ((Resto == 10) || (Resto == 11))
                    Resto = 0;
                if (Resto != parseInt(value.substring(9, 10)))
                    return false;
                Soma = 0;
                for (i = 1; i <= 10; i++)
                    Soma = Soma + parseInt(value.substring(i - 1, i)) * (12 - i);
                Resto = (Soma * 10) % 11;
                if ((Resto == 10) || (Resto == 11))
                    Resto = 0;
                if (Resto != parseInt(value.substring(10, 11)))
                    return false;
                return true;
            }
            
            $.backstretch([
                "assets/admin/pages/media/bg/1.jpg",
                "assets/admin/pages/media/bg/2.jpg",
                "assets/admin/pages/media/bg/3.jpg",
                "assets/admin/pages/media/bg/4.jpg"
            ], {
                fade: 1000,
                duration: 8000
            });
        }

    };

}();
