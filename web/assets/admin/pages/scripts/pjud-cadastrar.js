var PjudCad = function() {

    var handleValidation = function() {
        var form = $('#submit_form');
        var error = $('.alert-danger', form);
        var success = $('.alert-success', form);

        form.validate({
            doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                nprocesso: {
                    required: true,
                },
                nprocessoant: {
                    required: false,
                },
                comarca: {
                    required: true,
                },
                procurador: {
                    required: false,
                },
                vara: {
                    required: false,
                },
                varaant: {
                    required: false,
                },
                especializacao: {
                    required: false,
                },
                distribuicao: {
                    required: false,
                },
                distribuicaodata: {
                    data: true,
                    required: false,
                },
                despachoinicial: {
                    required: false,
                },
                despachoinicialdata: {
                    data: true,
                    required: false,
                },
                decisaojuiz: {
                    required: false,
                },
                decisaojuizdata: {
                    data: true,
                    required: false,
                },
                ncda: {
                    required: true,
                },
                fatosgeradores: {
                    required: false,
                },
                fundamentacao: {
                    required: false,
                },
                datadeinscricao: {
                    data: true,
                    required: false,
                },
                discriminacaoimposto: {
                    required: false,
                },
                discriminacaomulta: {
                    required: false,
                },
                valorcausa: {
                    required: false,
                },
                valoratualizado: {
                    required: false,
                },
                notificacaoadministrativa: {
                    required: false,
                },
                notificacaoadministrativadata: {
                    data: true,
                    required: false,
                },
                outrasinfos1: {
                    required: false,
                },
                executado: {
                    required: true,
                },
                cpf: {
                    required: false,
                },
                cnpj: {
                    required: false,
                },
                outrasinfos2: {
                    required: false,
                },
                outrasinfos3: {
                    required: false,
                },
            },
            messages: {// custom messages for radio buttons and checkboxes

            },
            errorPlacement: function(error, element) { // render error placement for each input type
                if (element.attr("name") == "executado") { // for uniform radio buttons, insert the after the given container
                    error.insertAfter("#form_executado_error");
                } else {
                    var icon = $(element).parent('.input-icon').children('i');
                    icon.removeClass('fa-check').addClass("fa-warning");
                    icon.attr("data-original-title", error.text()).tooltip({'container': 'body'}); // for other inputs, just perform default behavior
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit   
                success.hide();
                error.show();
                Metronic.scrollTo(error, -200);
            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function(element) { // revert the change done by hightlight
                $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            success: function(label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error'); // set success class to the control group
                icon.removeClass("fa-warning");
                icon.removeAttr("data-original-title");
            },
            submitHandler: function(form) {
                success.show();
                error.hide();
                //add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
            }

        });

        var handleTitle = function(tab, navigation, index) {
            var total = navigation.find('li').length;
            var current = index + 1;
            // set wizard title
            $('.step-title', $('#form_wizard')).text('Passo ' + (index + 1) + ' de ' + total);
            // set done steps
            jQuery('li', $('#form_wizard')).removeClass("done");
            var li_list = navigation.find('li');
            for (var i = 0; i < index; i++) {
                jQuery(li_list[i]).addClass("done");
            }

            if (current == 1) {
                $('#form_wizard').find('.button-previous').hide();
            } else {
                $('#form_wizard').find('.button-previous').show();
            }

            if (current >= total) {
                $('#form_wizard').find('.button-next').hide();
                $('#form_wizard').find('.button-submit').show();
            } else {
                $('#form_wizard').find('.button-next').show();
                $('#form_wizard').find('.button-submit').hide();
            }
            Metronic.scrollTo($('.page-title'));
        }

        // default form wizard
        $('#form_wizard').bootstrapWizard({
            'nextSelector': '.button-next',
            'previousSelector': '.button-previous',
            onTabClick: function(tab, navigation, index, clickedIndex) {
                success.hide();
                error.hide();
                if (form.valid() == false) {
                    return false;
                }
                handleTitle(tab, navigation, clickedIndex);
            },
            onNext: function(tab, navigation, index) {
                success.hide();
                error.hide();

                if (form.valid() == false) {
                    return false;
                }

                handleTitle(tab, navigation, index);
            },
            onPrevious: function(tab, navigation, index) {
                success.hide();
                error.hide();

                handleTitle(tab, navigation, index);
            },
            onTabShow: function(tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                var $percent = (current / total) * 100;
                $('#form_wizard').find('.progress-bar').css({
                    width: $percent + '%'
                });
            }
        });
        // initialize select2 tags
        var filter = new RegExp("(0[123456789]|10|11|12)([/])([1-2][0-9][0-9][0-9])");
        $("#select2_tags").change(function() {
            form.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input 
        }).select2({
            tags: [],
            maximumInputLength: 7,
            minimumInputLength: 6,
            formatInputTooShort: function(term, minlength) {
                return term;
            },
            tokenizer: function(input, selection, selectCallback, opts) {
                if (input.match(/[0-9]+$/)) {
                    if (input.length == 6) {
                        input = input.substring(0, 2) + "/" + input.substring(2, 6)
                        if (!filter.test(input)) {
                            return "";
                        } else {
                            return input;
                        }
                    }
                } else {
                    return "";
                }
            }
        });
    }

    return {
        init: function() {

            if (!jQuery().bootstrapWizard) {
                return;
            }

            $.validator.addMethod("data", validaData, "Digite uma data v&aacute;lida.");
//            $.validator.addMethod("oneOrAnother", validaExecutado, "Digite uma data v&aacute;lida.");
            $.validator.addClassRules({
                bem:{
                    required:false
                },
                bemdata:{
                    data:true,
                    required:false
                }
            });

            handleValidation();

            $('.date').mask("99/99/9999");
            $('.money').maskMoney({
                prefix: 'R$ ',
                symbol: 'R$', // Simbolo
                decimal: ',', // Separador do decimal
                precision: 2, // Precisão
                thousands: '.', // Separador para os milhares
                allowZero: false, // Permite que o digito 0 seja o primeiro caractere
                showSymbol: false // Exibe/Oculta o símbolo
            })
            $('.masked-numbers').keypress(function(e) {
                var regex = new RegExp("[0-9.\/\-]");
                var key = e.keyCode || e.which;
                key = String.fromCharCode(key);

                if (!regex.test(key) && key.charCodeAt(0) > 32) {
                    e.returnValue = false;
                    if (e.preventDefault) {
                        e.preventDefault();
                    }
                }
            });

            executado();
            $('#executado').click(executado);
            function executado() {
                if ($("input[name=executado]:checked").val() === 'PF') {
                    $('.cpf').show();
                    $('.cnpj').hide();
                    $('#cnpj').select2('data', null);
                } else if ($("input[name=executado]:checked").val() === 'PJ') {
                    $('.cnpj').show();
                    $('.cpf').hide();
                    $('#cpf').select2('data', null);
                }
            }

            function validaData(value, element) {
                var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
                if (value === "" || value === null) {
                    return true;
                }
                return value.match(reg) ? true : false;
            }
            ;

            $('#form_wizard').find('.button-previous').hide();
            $('#form_wizard .button-submit').click(function() {
                 $('#submit_form').submit();
            }).hide();

            $('.menu-pjud').addClass('active open');
            $('.menu-pjud a').append('<span class="selected"></span>');
            $('.menu-pjud a .arrow').addClass('open');
            if (window.location.search == "") {
                $('.sub-menu-pjud-cad').addClass('active');
            } else {
                $('.sub-menu-pjud-con').addClass('active');
            }
        }
    };
}();