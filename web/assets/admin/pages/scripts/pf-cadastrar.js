var PFCad = function() {

    var handleValidation = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

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
                cpf: {
                    minlength: 14,
                    required: true
                },
                gender: {
                    required: true
                },
                rg: {
                    minlength: 11,
                    required: true
                },
                oe: {
                    minlength: 3,
                    required: true
                },
                fathername: {
                    minlength: 2,
                    required: false
                },
                mothername: {
                    minlength: 2,
                    required: false
                },
                elector: {
                    minlength: 12,
                    required:false
                },
                natuf: {
                    required:false
                },
                natcity: {
                    required:false
                },
                address: {
                    minlength: 3,
                    required:false
                },
                complement: {
                    minlength: 3,
                    required:false
                },
                number: {
                    number: true,
                    required:false
                },
                neighborhood: {
                    minlength: 1,
                    required:false
                },
                cep: {
                    minlength:9,
                    required:false
                },
                enduf: {
                    required:false
                },
                endcity: {
                    required:false
                }
            },
            messages: {
                gender: {
                    required: "Selecione um sexo."
                },
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
                $(element)
                        .closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group   
            },
            unhighlight: function(element) { // revert the change done by hightlight

            },
            success: function(label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                icon.removeClass("fa-warning").addClass("fa-check");
                icon.removeAttr("data-original-title");
            },
            submitHandler: function(form) {
                success.show();
                error.hide();
            }
        });
    }

    return {
        init: function() {

            handleValidation();

            $('#cpf').mask("999.999.999-99");
            $('#rg').mask("9.999.999-9");
            $('#elector').mask("999999999999");
            $('#cep').mask("99999-999");
            
            $('.menu-pf').addClass('active open');
            $('.menu-pf a').append('<span class="selected"></span>');
            $('.menu-pf a .arrow').addClass('open');
            $('.sub-menu-pf-cad').addClass('active');
        }
    };
}();
    