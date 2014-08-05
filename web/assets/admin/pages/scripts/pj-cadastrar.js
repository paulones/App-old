var PJCad = function() {

    var handleValidation = function() {
        var form = $('#form');
        var error = $('.alert-danger', form);
        var success = $('.alert-success', form);

        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
                name: {
                    minlength: 2,
                    required: true
                },
                alias: {
                    minlength: 1,
                    required: false
                },
                cnpj: {
                    minlength: 18,
                    required: true
                },
                tipicidade: {
                    required: false
                },
                state: {
                    minlength: 15,
                    required: true
                },
                province: {
                    maxlength: 30,
                    required: false
                }
            },
            messages: {
                name: {
                    required: "Entre com um nome."
                },
                cnpj: {
                    minlength: "CNPJ invalido",
                    required: "Informe o CNPJ."
                },
                state: {
                    minlength: "Inscrição estatal invalida",
                    required: "Informe a inscrição estatal."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit 
                success.hide();
                error.show();
                Metronic.scrollTo(error, -200);
            },
            errorPlacement: function(error, element) { // render error placement for each input type
                if (element.parent(".input-group").size() > 0) {
                    error.insertAfter(element.parent(".input-group"));
                } else if (element.attr("data-error-container")) {
                    error.appendTo(element.attr("data-error-container"));
                } else if (element.parents('.radio-list').size() > 0) {
                    error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                } else if (element.parents('.radio-inline').size() > 0) {
                    error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
                } else if (element.parents('.checkbox-list').size() > 0) {
                    error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                } else if (element.parents('.checkbox-inline').size() > 0) {
                    error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
                } else {
                    var icon = $(element).parent('.input-icon').children('i');
                    icon.removeClass('fa-check').addClass("fa-warning");
                    icon.attr("data-original-title", error.text()).tooltip({'container': 'body'});
                }

            },
            highlight: function(element) { // hightlight error inputs
                $(element).closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group   
            },
            unhighlight: function(element) { // revert the change done by hightlight

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
            }
        });
    }
    
    var checkDates = function() {
        
    }

    return {
        init: function() {

            handleValidation();

            $('#cnpj').mask("99.999.999/9999-99");
            $('#state').mask("999.999.999.999");
            $('.date').mask("99/99/9999");

            $('.menu-pj').addClass('active open');
            $('.menu-pj a').append('<span class="selected"></span>');
            $('.menu-pj a .arrow').addClass('open');
            $('.sub-menu-pj-cad').addClass('active');

            var masks = [$('#cpf'), $('#state'), $('#date')];
            $('#form').submit(function() {
                $.each(masks, function() {
                    if ($(this).val() == "") {
                        $(this).closest('.form-group').removeClass("has-success").removeClass('has-error');
                        $(this).parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                    }
                });
            });
            
            situacao();
            $('#situacao').change(situacao);
            function situacao(){
                if ($('#situacao').val() == 2) {
                    $('.inactive').show();
                } else {
                    $('.inactive').hide();
                }
            }
        }
    };
}();

