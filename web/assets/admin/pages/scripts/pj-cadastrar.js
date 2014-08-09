var PJCad = function() {

    var handleValidation = function() {
        var form = $('#form');
        var error = $('.form-error', form);
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
                },
                situacao: {
                    required: false
                },
                iniDate: {
                    iniDate: true,
                    required: false
                },
                inactive: {
                    required: false
                },
                group: {
                    required: false
                },
                nire: {
                    maxlength: 11,
                    required: false
                },
                cnae: {
                    maxlength: 9,
                    required: false
                },
                activity1: {
                    minlength: 2,
                    required: false
                },
                activity2: {
                    minlength: 2,
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
                name: {
                    required: "Entre com um nome."
                },
                cnpj: {
                    minlength: "CNPJ inv&aacute;lido.",
                    required: "Informe o CNPJ."
                },
                state: {
                    minlength: "Inscri&ccedil;&atilde;o estatal inv&aacute;lida.",
                    required: "Informe a inscrição estatal."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit
                $(".date-error").hide();
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
                $(".date-error").hide();
                error.hide();
                $(".register").click();
            }
        });
    }

    var checkDates = function() {
        if ($(this).val().length == 10) {
            var result;
            var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
            $.each($('.initial-date'), function() {
                var initialdate = $(this).val();
                var finaldate = $(this).closest('tr').children('td').children('.final-date').val();
                if (initialdate.match(reg) && finaldate.match(reg)) {
                    if (finaldate != "" && initialdate != "") {
                        var final = finaldate.split("/")[2] + "-" + finaldate.split("/")[1] + "-" + finaldate.split("/")[0];
                        var initial = initialdate.split("/")[2] + "-" + initialdate.split("/")[1] + "-" + initialdate.split("/")[0];
                        if (final < initial) {
                            $(this).closest('tr').children('td').children('.final-date').val("");
                            $('.date-error').html("Digite uma data de in&iacute;cio inferior &agrave; data de t&eacute;rmino.");
                            $('.date-error').show();
                            return result = false;
                        } else {
                            $('.date-error').hide();
                            return result = true;
                        }
                    } else if (initialdate != "") {
                        $('.date-error').hide();
                        return result = true;
                    }
                } else if (initialdate != "" && !initialdate.match(reg)) {
                    $(this).val("");
                    $('.date-error').html("Digite uma data de in&iacute;cio v&aacute;lida.");
                    $('.date-error').show();
                    return result = false;
                } else if (finaldate != "" && !finaldate.match(reg)) {
                    $(this).closest('tr').children('td').children('.final-date').val("");
                    $('.date-error').html("Digite uma data de t&eacute;rmino v&aacute;lida.");
                    $('.date-error').show();
                    return result = false;
                } else {
                    $('.date-error').hide();
                    return result = true;
                }
            });
            return result;
        }
    };
    
    var checkCapital = function() {
        if ($(this).val() > 100) {
            $('.date-error').html("O percentual de participa&ccedil;&atilde;o n&atilde;o pode exceder 100%.");
            $('.date-error').show();
            $(this).val("");
        } else {
            $('.date-error').hide();
        }
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

        table.on('keyup', '.initial-date, .final-date', function() {
            checkDates();
        });

        table.on('keyup', '.capital', checkCapital);
    }

    return {
        init: function() {

            $.validator.addMethod("iniDate", validaData, "Digite uma data v&aacute;lida.");

            handleValidation();
            handleTable();

            $('#cnpj').mask("99.999.999/9999-99");
            $('#state').mask("999.999.999.999");
            $('#iniDate').mask("99/99/9999");
            $('#nire').mask("99999999999");
            $('#cnae').mask("9999-9/99");
            $('.date').mask("99/99/9999");

            $('.menu-pj').addClass('active open');
            $('.menu-pj a').append('<span class="selected"></span>');
            $('.menu-pj a .arrow').addClass('open');
            $('.sub-menu-pj-cad').addClass('active');

            var masks = [$('#cpf'), $('#state'), $('#date'), $('#nire'), $('#cnae')];
            $('#form').submit(function() {
                $.each(masks, function() {
                    if ($(this).val() == "") {
                        $(this).closest('.form-group').removeClass("has-success").removeClass('has-error');
                        $(this).parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                    }
                });
            });

            jsf.ajax.addOnEvent(function(data) {
                if (data.status === 'success') {
                    if ($(data.source).attr("id") === "enduf") {
                        $('.endcity').select2();
                    } else if ($(data.source).attr("class") === "vinculate" || $(data.source).attr("class") === "delete") {
                        $('.date').mask("99/99/9999");
                        $('.funcao').select2();
                        $('.capital').keyup(checkCapital);
                        $('.initial-date,.final-date').keyup(checkDates);
                        $('.date-error').hide();
                        if ($('.rows').children().length == 0) {
                            $('.rows').append('<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">Sem V&iacute;nculos.</td></tr>');
                        }
                    }
                }
            });

            $('#vinculate').click(function(e) {
                e.preventDefault();
                if ($('#pessoafisica').val() !== "") {
                    $('.vinculate').click();
                }
            });

            situacao();
            $('#inativo').click(situacao);
            $('#ativo').click(situacao);
            function situacao() {
                if ($('#inativo').is(":checked")) {
                    $('.inactive').show();
                } else {
                    $('.inactive').hide();
                }
            }

            function validaData(value, element) {
                var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
                return value.match(reg) ? true : false;
            }
        }
    };
}();

