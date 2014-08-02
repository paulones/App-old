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
                    required: false
                },
                natuf: {
                    required: false
                },
                natcity: {
                    required: false
                },
                address: {
                    minlength: 3,
                    required: false
                },
                complement: {
                    minlength: 3,
                    required: false
                },
                number: {
                    number: true,
                    required: false
                },
                neighborhood: {
                    minlength: 1,
                    required: false
                },
                cep: {
                    minlength: 9,
                    required: false
                },
                enduf: {
                    required: false
                },
                endcity: {
                    required: false
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
                $(element).closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group   
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
    var handleTable = function() {


        var table = $('#vinculations');

        var oTable = table.dataTable({
            paginate: false,
            lengthMenu: false,
            info: false,
            filter: false,
            // set the initial value
            "pageLength": 10,
            "language": {
                "emptyTable": "Sem V&iacute;nculos."
            },
            "ordering": false
        });

        var tableWrapper = $("#vinculations_wrapper");

        tableWrapper.find(".dataTables_length select").select2({
            showSearchInput: false //hide search box with special css class
        }); // initialize select2 dropdown

        var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
        table.on('focusout', '.final-date', function(e) {
            e.preventDefault();
            var finaldate = $(this).val();
            if (finaldate.match(reg)) {
                var initialdate = $(this).closest('tr').children('td').children('.initial-date').val();
                if (finaldate != "" && initialdate != "") {
                    var final = finaldate.split("/")[2] + "-" + finaldate.split("/")[1] + "-" + finaldate.split("/")[0];
                    var initial = initialdate.split("/")[2] + "-" + initialdate.split("/")[1] + "-" + initialdate.split("/")[0];
                    if (final < initial) {
                        $(this).val("");
                        $('.final-date-error').show();
                        $('.initial-date-error').hide();
                    } else {
                        $('.final-date-error').hide();
                        $('.initial-date-error').hide();
                    }
                }
            } else {
                $(this).val("");
            }
        });
        table.on('focusout', '.initial-date', function(e) {
            e.preventDefault();
            var initialdate = $(this).val();
            var finaldate = $(this).closest('tr').children('td').children('.final-date').val();
            if (initialdate.match(reg)) {
                if (finaldate != "" && initialdate != "") {
                    var final = finaldate.split("/")[2] + "-" + finaldate.split("/")[1] + "-" + finaldate.split("/")[0];
                    var initial = initialdate.split("/")[2] + "-" + initialdate.split("/")[1] + "-" + initialdate.split("/")[0];
                    if (final < initial) {
                        $(this).val("");
                        $('.initial-date-error').show();
                        $('.final-date-error').hide();
                    } else {
                        $('.final-date-error').hide();
                        $('.initial-date-error').hide();
                    }
                }
            } else {
                $(this).val("");
            }
        });

        table.on('click', '.delete', function(e) {
            e.preventDefault();
            $('.final-date-error').hide();
            $('.initial-date-error').hide();
            var nRow = $(this).parents('tr')[0];
            oTable.fnDeleteRow(nRow);
        });
    }

    return {
        init: function() {

            handleValidation();
            handleTable();

            $('#cpf').mask("999.999.999-99");
            $('#rg').mask("9.999.999-9");
            $('#elector').mask("999999999999");
            $('#cep').mask("99999-999");
            $('.date').mask("99/99/9999");
            $('.money').maskMoney({allowNegative: true, thousands: '.', decimal: ',', affixesStay: false});

            $('.menu-pf').addClass('active open');
            $('.menu-pf a').append('<span class="selected"></span>');
            $('.menu-pf a .arrow').addClass('open');
            $('.sub-menu-pf-cad').addClass('active');

            $('#form').submit(function() {
                if ($('#elector').val() == "") {
                    $('#elector').closest('.form-group').removeClass("has-success").removeClass('has-error');
                    $('#elector').parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                }
                if ($('#cep').val() == "") {
                    $('#cep').closest('.form-group').removeClass("has-success").removeClass('has-error');
                    $('#cep').parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                }
            })
        }
    };
}();
    