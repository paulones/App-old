var ModalPFCad = function() {

    var handleValidation = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form = $('#modal_pf_form');
        var error = $('.form-error', form);
        var success = $('.alert-success', form);

        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
                modal_pf_name: {
                    minlength: 2,
                    required: true
                },
                modal_pf_alias: {
                    minlength_optional: 1,
                    required: false
                },
                modal_pf_cpf: {
                    cpf: true,
                    required: false
                },
                modal_pf_gender: {
                    required: false
                },
                modal_pf_rg: {
                    minlength: 7,
                    required: false
                },
                modal_pf_oe: {
                    minlength: 3,
                    required: false
                },
                modal_pf_fathername: {
                    minlength_optional: 2,
                    required: false
                },
                modal_pf_mothername: {
                    minlength_optional: 2,
                    required: false
                },
                modal_pf_nationality: {
                    required: false,
                },
                modal_pf_elector: {
                    elector: true,
                    required: false
                },
                modal_pf_natuf: {
                    required: false
                },
                modal_pf_natcity: {
                    required: false
                },
                modal_pf_natforeign: {
                    required: false,
                },
                modal_pf_estcivil: {
                    required: false,
                },
                modal_pf_inss: {
                    minlength: 9,
                    required: false
                },
                modal_pf_conjuge: {
                    minlength_optional: 2,
                    required: false,
                },
                modal_pf_obs: {
                    required: false,
                    minlength_optional: 2,
                },
                modal_pf_zone: {
                    required: false,
                },
                modal_pf_section: {
                    required: false,
                },
                modal_pf_local: {
                    required: false,
                    minlength_optional: 3,
                },
                modal_pf_electoraddress: {
                    required: false,
                    minlength_optional: 3,
                },
                modal_pf_font: {
                    required: false,
                    minlength_optional: 2,
                },
                modal_pf_eleuf: {
                    required: false,
                },
                modal_pf_elecity: {
                    required: false,
                },
                modal_pf_address: {
                    minlength_optional: 3,
                    required: false
                },
                modal_pf_complement: {
                    minlength_optional: 3,
                    required: false
                },
                modal_pf_number: {
                    number: true,
                    required: false
                },
                modal_pf_neighborhood: {
                    minlength: 1,
                    required: false
                },
                modal_pf_cep: {
                    minlength: 9,
                    required: false
                },
                modal_pf_enduf: {
                    required: false
                },
                modal_pf_endcity: {
                    required: false
                }
            },
            messages: {
                modal_pf_rg: {
                    minlength: "Digite um RG v&aacute;lido."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit 
                $(".modal_pf_date-error").hide();
                success.hide();
                error.show();
                $(".modal").animate({scrollTop: 0}, 'fast');
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
                    icon.attr("data-original-title", error.text()).tooltip().on('show', function(e) {
                        e.stopPropagation();
                    }).on('hide', function(e) {
                        e.stopPropagation();
                    });
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
                error.hide();
            }
        });
    }

    $(document).on("click", ".modal_pf_submit-pf", function(e) {
        if ($('#modal_pf_form').validate().form()) {
            $(".modal_pf_date-error").hide();
            $(".modal_pf_register").click();
        }
        return false;
    });

    var checkDates = function() {
        if ($(this).val().length == 10) {
            var result;
            var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
            $.each($('.modal_pf_initial-date'), function() {
                var initialdate = $(this).val();
                var finaldate = $(this).closest('tr').children('td').children('.modal_pf_final-date').val();
                if (initialdate.match(reg) && finaldate.match(reg)) {
                    if (finaldate != "" && initialdate != "") {
                        var final = finaldate.split("/")[2] + "-" + finaldate.split("/")[1] + "-" + finaldate.split("/")[0];
                        var initial = initialdate.split("/")[2] + "-" + initialdate.split("/")[1] + "-" + initialdate.split("/")[0];
                        if (final < initial) {
                            $(this).closest('tr').children('td').children('.modal_pf_final-date').val("");
                            $('.modal_pf_date-error').html("Digite uma data de in&iacute;cio inferior &agrave; data de t&eacute;rmino.");
                            $('.modal_pf_date-error').show();
                            return result = false;
                        } else {
                            $('.modal_pf_date-error').hide();
                            return result = true;
                        }
                    } else if (initialdate != "") {
                        $('.modal_pf_date-error').hide();
                        return result = true;
                    }
                } else if (initialdate != "" && !initialdate.match(reg)) {
                    $(this).val("");
                    $('.modal_pf_date-error').html("Digite uma data de in&iacute;cio v&aacute;lida.");
                    $('.modal_pf_date-error').show();
                    return result = false;
                } else if (finaldate != "" && !finaldate.match(reg)) {
                    $(this).closest('tr').children('td').children('.modal_pf_final-date').val("");
                    $('.modal_pf_date-error').html("Digite uma data de t&eacute;rmino v&aacute;lida.");
                    $('.modal_pf_date-error').show();
                    return result = false;
                } else {
                    $('.modal_pf_date-error').hide();
                    return result = true;
                }
            });
            return result;
        }
    };

    var checkCapital = function() {
        $(this).val($(this).val().replace(/,/g, "."));
        if ($(this).val().match(/^\d{0,3}(?:\.\d{0,2}){0,1}$/)) {
            if ($(this).val() > 100) {
                $('.modal_pf_date-error').html("A soma dos percentuais de participa&ccedil;&atilde;o n&atilde;o pode exceder 100%.");
                $('.modal_pf_date-error').show();
                $(this).val("");
            } else {
                $('.modal_pf_date-error').hide();
            }
        } else {
            $('.modal_pf_date-error').html("Digite um percentual v&aacute;lido.");
            $('.modal_pf_date-error').show();
            $(this).val("");
        }
    }

    var handleTable = function() {


        var table = $('#modal_pf_vinculations');

        var oTable = table.dataTable({
            destroy: true,
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

        table.on('keyup', '.modal_pf_capital', checkCapital);
    };

    return {
        init: function() {
            $("#modal_pf_rguf").select2({allowClear: true});
            $("#modal_pf_nationality").select2({allowClear: true});
            $("#modal_pf_natuf").select2({allowClear: true});
            $("#modal_pf_natcity").select2();
            $("#modal_pf_eleuf").select2({allowClear: true});
            $("#modal_pf_elecity").select2();
            $("#modal_pf_enduf").select2({allowClear: true});
            $("#modal_pf_endcity").select2();
            $("#modal_pf_estcivil").select2({allowClear: true});
            $("#modal-new-pf input[type=radio]").uniform();

            $.validator.addMethod("cpf", validaCPF, "CPF inv&aacute;lido ou j&aacute; existente no sistema.");
            $.validator.addMethod("elector", validaTitulo, "Digite um t&iacute;tulo de eleitor v&aacute;lido.");
            $.validator.addMethod("minlength_optional", validaMinLength, "Por favor, forne&ccedil;a ao menos {0} caracteres");

            handleValidation();
            handleTable();

            $('#modal_pf_cpf').mask("999.999.999-99");
            $('#modal_pf_rg').mask("999999999999999");
            $('#modal_pf_elector').mask("9999999999999");
            $('#modal_pf_cep').mask("99999-999");
            $('#modal_pf_inss').mask("999999999999999");
            $('#modal_pf_zone').mask("999999999999999");
            $('#modal_pf_section').mask("999999999999999");
            $('.modal_pf_date').mask("99/99/9999");

            var masks = [$('#modal_pf_cpf'), $('#modal_pf_rg'), $('#modal_pf_elector'), $('#modal_pf_cep'), $('#modal_pf_oe'), $('#modal_pf_inss'), $('#modal_pf_zone'), $('#modal_pf_section')];
            $('#modal_pf_form').submit(function() {
                $.each(masks, function() {
                    if ($(this).val() == "") {
                        $(this).closest('.form-group').removeClass("has-success").removeClass('has-error');
                        $(this).parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                    }
                });
            });
            $('#modal_pf_vinculate').click(function(e) {
                e.preventDefault();
                if ($('#modal_pf_pessoajuridica').val() !== "") {
                    $('.modal_pf_vinculate').click();
                }
            });

            jsf.ajax.addOnEvent(function(data) {
                if (data.status === 'success') {
                    if ($(data.source).attr("id") === "modal_pf_enduf") {
                        $('.modal_pf_endcity').select2({allowClear: true});
                    } else if ($(data.source).attr("id") === "modal_pf_natuf") {
                        $('.modal_pf_natcity').select2({allowClear: true});
                    } else if ($(data.source).attr("id") === "modal_pf_eleuf") {
                        $('.modal_pf_elecity').select2({allowClear: true});
                    } else if ($(data.source).attr("class") === "modal_pf_delete") {
                        $('.modal_pf_table-refresher').click();
                    } else if ($(data.source).attr("class") === "modal_pf_vinculate" || $(data.source).attr("class") === "modal_pf_table-refresher") {
                        $('.modal_pf_date').mask("99/99/9999");
                        $('select.modal_pf_funcao').select2({allowClear: true});
                        $('.modal_pf_capital').keyup(checkCapital);
                        $('.modal_pf_initial-date,.modal_pf_final-date').keyup(checkDates);
                        $('.modal_pf_date-error').hide();
                        if ($('.modal_pf_rows').children().length == 0) {
                            $('.modal_pf_rows').append('<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">Sem V&iacute;nculos.</td></tr>');
                        }
                    }
                }
            });

            $.ajax({
                url: "/webresources/reaver/getPessoasJuridicas",
                dataType: "json",
                cache: false
            })
                    .done(function(data) {
                        if (getParameterByName("id") !== "" && window.location.href.indexOf("pessoa-juridica") >= 0) {
                            data.removeValue("id", getParameterByName("id"));
                        }
                        $('.modal_pf_cnpjVinculate').select2({
                            initSelection: function(element, callback) {
                                var selection = _.find(data, function(metric) {
                                    return metric.id === element.val();
                                })
                                callback(selection);
                            },
                            query: function(options) {

                                var pageSize = 100;
                                var startIndex = (options.page - 1) * pageSize;
                                var filteredData = data;

                                if (options.term && options.term.length > 0) {
                                    if (!options.context) {
                                        var term = options.term.toLowerCase();
                                        options.context = data.filter(function(metric) {
                                            return (removeDiacritics(metric.text.toLowerCase()).indexOf(removeDiacritics(term)) >= 0);
                                        });
                                    }
                                    filteredData = options.context;
                                }

                                options.callback({
                                    context: filteredData,
                                    results: filteredData.slice(startIndex, startIndex + pageSize),
                                    more: (startIndex + pageSize) < filteredData.length
                                });
                            },
                            placeholder: "Selecione...",
                            allowClear: true,
                        });
                    });

            $('.modal_pf_initial-date,.modal_pf_final-date').keyup(checkDates);

            nationality();
            $('#modal_pf_nationality').change(nationality);

            function nationality() {
                if ($('#modal_pf_nationality').find(":selected").text() === "Estrangeiro") {
                    $('.modal_pf_natuf').hide();
                    $('#modal_pf_natuf').select2('data', null);
                    $('.modal_pf_natcity').hide();
                    $('#modal_pf_natcity').select2('data', null);
                    $('.modal_pf_natforeign').show();
                } else {
                    $('.modal_pf_natuf').show();
                    $('.modal_pf_natcity').show();
                    $('.modal_pf_natforeign').hide();
                    $('#modal_pf_natforeign').select2('data', null);
                }
            }

            civil();
            $('.modal_pf_estcivil').change(civil);
            function civil() {
                if ($('.modal_pf_estcivil').find(':selected').text() === "Solteiro(a)" || $('.estcivil').find(':selected').text() === "") {
                    $('.modal_pf_conjuge').hide();
                } else {
                    $('.modal_pf_conjuge').show();
                }
            }

            function validaMinLength(value, element, param) {
                var length = $.isArray(value) ? value.length : this.getLength($.trim(value), element);
                return length >= param || length === 0;
            }

            function validaCPF(value, element) {
                value = value.replace(/\./g, "").replace(/-/g, "");
                var Soma;
                var Resto;
                Soma = 0;
                if (value == "")
                    return true;
                if (value == "00000000000" ||
                        value == "11111111111" ||
                        value == "22222222222" ||
                        value == "33333333333" ||
                        value == "44444444444" ||
                        value == "55555555555" ||
                        value == "66666666666" ||
                        value == "77777777777" ||
                        value == "88888888888" ||
                        value == "99999999999")
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
                var status = true;
                if (value.length === 11) {
                    $.ajax({
                        async: false,
                        url: "/webresources/reaver/checkCPF",
                        dataType: "json",
                        cache: false,
                        data: {
                            cpf: value
                        },
                        success: function(data, textStatus, jqXHR) {
                            if (String(data) === "true") {
                                status = true;
                            } else {
                                status = false;
                            }
                        }
                    })
                    return status;
                }
            }

            function validaTitulo(value, element) {
                if (value == "") {
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




