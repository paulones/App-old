var PFCad = function() {

    var handleValidation = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

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
                cpf: {
                    cpf: true,
                    required: false
                },
                gender: {
                    required: false
                },
                rg: {
                    minlength: 11,
                    required: false
                },
                oe: {
                    minlength: 3,
                    required: false
                },
                fathername: {
                    minlength: 2,
                    required: false
                },
                mothername: {
                    minlength: 2,
                    required: false
                },
                nationality: {
                    required: false,
                },
                elector: {
                    elector: true,
                    required: false
                },
                natuf: {
                    required: false
                },
                natcity: {
                    required: false
                },
                natforeign: {
                    required: false,
                },
                inss: {
                    minlength: 14,
                    required: false
                },
                conjuge: {
                    minlength: 2,
                    required: false,
                },
                obs: {
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
                rg: {
                    minlength: "Digite um RG v&aacute;lido."
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

        table.on('keyup', '.initial-date', function() {
            if ($(this).val().length == 10) {
                checkDates();
            }
        });
        table.on('keyup', '.final-date', function() {
            if ($(this).val().length == 10) {
                checkDates();
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

            $.validator.addMethod("cpf", validaCPF, "Digite um CPF v&aacute;lido.");
            $.validator.addMethod("elector", validaTitulo, "Digite um t&iacute;tulo de eleitor v&aacute;lido.");

            handleValidation();
            handleTable();

            $('#cpf').mask("999.999.999-99");
            $('#rg').mask("9.999.999-9");
            $('#elector').mask("999999999999");
            $('#cep').mask("99999-999");
            $('#inss').mask("999.99999.99-9");
            $('.date').mask("99/99/9999");
            $('.money').maskMoney({allowNegative: true, thousands: '.', decimal: ',', affixesStay: false});

            $('.menu-pf').addClass('active open');
            $('.menu-pf a').append('<span class="selected"></span>');
            $('.menu-pf a .arrow').addClass('open');
            $('.sub-menu-pf-cad').addClass('active');

            var masks = [$('#cpf'), $('#rg'), $('#elector'), $('#cep'), $('#oe'), $('#inss')];
            $('#form').submit(function() {
                $.each(masks, function() {
                    if ($(this).val() == "") {
                        $(this).closest('.form-group').removeClass("has-success").removeClass('has-error');
                        $(this).parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                    }
                });
            });

            $('.vinculate').click(function(e) {
                e.preventDefault();
            })

            nationality();
            $('#nationality').change(nationality);

            function nationality() {
                if ($('#nationality').val() == 3) {
                    $('.natuf').hide();
                    $('.natcity').hide();
                    $('.natforeign').show();
                } else {
                    $('.natuf').show();
                    $('.natcity').show();
                    $('.natforeign').hide();
                }
            }

            civil();
            $('#civil').change(civil);
            function civil() {
                if ($('#civil').val() == 1) {
                    $('.conjuge').hide();
                } else {
                    $('.conjuge').show();
                }
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

            function validaTitulo(value, element) {
                if (value == ""){
                    return true;
                }
                var dig1 = 0;
                var dig2 = 0;
                var tam = value.length;
                var digitos = value.substr(tam - 2, 2);
                var estado = value.substr(tam - 4, 2);
                var titulo = value.substr(0, tam - 2);
                var exce = (estado == '01') || (estado == '02');
                dig1 = (titulo.charCodeAt(0) - 48) * 9 + (titulo.charCodeAt(1) - 48) * 8 +
                        (titulo.charCodeAt(2) - 48) * 7 + (titulo.charCodeAt(3) - 48) * 6 +
                        (titulo.charCodeAt(4) - 48) * 5 + (titulo.charCodeAt(5) - 48) * 4 +
                        (titulo.charCodeAt(6) - 48) * 3 + (titulo.charCodeAt(7) - 48) * 2;
                var resto = (dig1 % 11);
                if (resto == 0) {
                    if (exce) {
                        dig1 = 1;
                    } else {
                        dig1 = 0;
                    }
                } else {
                    if (resto == 1) {
                        dig1 = 0;
                    } else {
                        dig1 = 11 - resto;
                    }
                }
                dig2 = (titulo.charCodeAt(8) - 48) * 4 + (titulo.charCodeAt(9) - 48) * 3 + dig1 * 2;
                resto = (dig2 % 11);
                if (resto == 0) {
                    if (exce) {
                        dig2 = 1;
                    } else {
                        dig2 = 0;
                    }
                } else {
                    if (resto == 1) {
                        dig2 = 0;
                    } else {
                        dig2 = 11 - resto;
                    }
                }
                if ((digitos.charCodeAt(0) - 48 == dig1) && (digitos.charCodeAt(1) - 48 == dig2)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    };
}();
    