var ModalPJCad = function() {

    var handleValidation = function() {
        var form = $('#modal_pj_form');
        var error = $('.form-error', form);
        var success = $('.alert-success', form);

        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
                modal_pj_name: {
                    minlength: 2,
                    required: true
                },
                modal_pj_cnpj: {
                    cnpj: true,
                    required: true
                },
                modal_pj_alias: {
                    minlength_optional: 1,
                    required: false
                },
                modal_pj_tipicidade: {
                    required: false
                },
                modal_pj_state: {
                    minlength_optional: 9,
                    maxlength: 15,
                    required: false
                },
                modal_pj_province: {
                    minlength_optional: 1,
                    required: false
                },
                modal_pj_situacao: {
                    required: false
                },
                modal_pj_iniDate: {
                    iniDate: true,
                    required: false
                },
                modal_pj_inactive: {
                    required: false
                },
                modal_pj_group: {
                    required: false
                },
                modal_pj_nire: {
                    maxlength: 11,
                    required: false
                },
                modal_pj_cnae: {
                    minlength_optional: 9,
                    required: false
                },
                modal_pj_activity1: {
                    minlength_optional: 2,
                    required: false
                },
                modal_pj_activity2: {
                    minlength_optional: 2,
                    required: false
                },
                modal_pj_address: {
                    minlength_optional: 3,
                    required: false
                },
                modal_pj_complement: {
                    minlength_optional: 3,
                    required: false
                },
                modal_pj_number: {
                    number: true,
                    required: false
                },
                modal_pj_neighborhood: {
                    minlength_optional: 1,
                    required: false
                },
                modal_pj_cep: {
                    minlength_optional: 9,
                    required: false
                },
                modal_pj_enduf: {
                    required: false
                },
                modal_pj_endcity: {
                    required: false
                }
            },
            messages: {
                modal_pj_name: {
                    required: "Entre com um nome."
                },
                modal_pj_state: {
                    minlength: "Inscri&ccedil;&atilde;o estatal inv&aacute;lida.",
                },
                modal_pj_alias: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para o nome fantasia."
                },
                modal_pj_province: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para a inscri&ccedil;&atilde;o municipal."
                },
                modal_pj_iniDate: {
                    iniDate: "Forne&ccedil;a uma data v&aacute;lida."
                },
                modal_pj_nire: {
                    maxlength: "NIRE n&atilde;o deve passar de 11 d&iicute;gitos."
                },
                modal_pj_activity1: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para Atividade principal"
                },
                modal_pj_activity2: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para Atividade secund&aacute;ria."
                },
                modal_pj_address: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para endere&ccedil;o."
                },
                modal_pj_complement: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para complemento."
                },
                modal_pj_number: {
                    number: "N&uucute;mero deve conter somente valores num&eecute;ricos."
                },
                modal_pj_neighborhood: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para bairro."
                },
                modal_pj_cep: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para CEP."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit
                $(".modal_pj_date-error-pfj").hide();
                $(".modal_pj_date-error-pjj").hide();
                success.hide();
                error.show();
                $(".modal").animate({ scrollTop: 0 }, 'fast');
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
                    icon.attr("data-original-title", error.text()).tooltip().on('show', function(e) {e.stopPropagation();}).on('hide', function(e) {e.stopPropagation();});
                }

            },
            highlight: function(element) { // hightlight error inputs
                $(element).closest('.form-group').removeClass("has-success").addClass('has-error'); // set error class to the control group   
            },
            unhighlight: function(element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
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

    $(document).on("click",".modal_pj_submit-pj", function(e) {
        if ($('#modal_pj_form').validate().form()) {
            $(".modal_pj_date-error-pfj").hide();
            $(".modal_pj_date-error-pjj").hide();
            $(".modal_pj_register").click();
        }
        return false;
    });

    var checkDates = function() {
        var dateError;
        if ($(this).parents("#modal_pj_pessoaJuridicaJuridica").length > 0) {
            dateError = ".modal_pj_date-error-pjj";
        } else {
            dateError = ".modal_pj_date-error-pfj";
        }
        if ($(this).val().length == 10) {
            var result;
            var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
            $.each($('.modal_pj_initial-date'), function() {
                var initialdate = $(this).val();
                var finaldate = $(this).closest('tr').children('td').children('.modal_pj_final-date').val();
                var criacaoEmpresa = $('.modal_pj_data-de-criacao').val();
                var criacao = criacaoEmpresa.split("/")[2] + "-" + criacaoEmpresa.split("/")[1] + "-" + criacaoEmpresa.split("/")[0];
                var final = finaldate.split("/")[2] + "-" + finaldate.split("/")[1] + "-" + finaldate.split("/")[0];
                var initial = initialdate.split("/")[2] + "-" + initialdate.split("/")[1] + "-" + initialdate.split("/")[0];
                if (initialdate !== "") {
                    if (initialdate.match(reg)) {
                        if (criacaoEmpresa !== "") { // Caso tenha sido informado data de criação da empresa
                            if (criacaoEmpresa.match(reg)) {
                                if (criacao > initial) {
                                    $(this).val("");
                                    $(dateError).html("Digite uma data de in&iacute;cio superior &agrave; data de cria&ccedil;&atilde;o da empresa.");
                                    $(dateError).show();
                                    return result = false;
                                }
                            } else {
                                $(this).val("");
                                $(dateError).html("Digite uma data de cri&ccedil;&atilde;o da empresa v&aacute;lida.");
                                $(dateError).show();
                                return result = false;
                            }
                        }
                        if (finaldate !== "") { // Caso tenha sido informado data de final de vinculo
                            if (finaldate.match(reg)) {
                                if (final < initial) {
                                    $(this).closest('tr').children('td').children('.modal_pj_final-date').val("");
                                    $(dateError).html("Digite uma data de in&iacute;cio inferior &agrave; data de t&eacute;rmino.");
                                    $(dateError).show();
                                    return result = false;
                                }
                            } else {
                                $(this).closest('tr').children('td').children('.modal_pj_final-date').val("");
                                $(dateError).html("Digite uma data de t&eacute;rmino v&aacute;lida.");
                                $(dateError).show();
                                return result = false;
                            }
                        }
                    } else {
                        $(this).val("");
                        $(dateError).html("Digite uma data de in&iacute;cio v&aacute;lida.");
                        $(dateError).show();
                        return result = false;
                    }
                } else { // Se não foi informado "data inicial", não há necessidade de comparações
                    if (criacaoEmpresa !== "") { // Caso tenha sido informado data de criação da empresa
                        if (!criacaoEmpresa.match(reg)) {
                            $(this).val("");
                            $(dateError).html("Digite uma data de in&iacute;cio v&aacute;lida.");
                            $(dateError).show();
                            return result = false;
                        }
                    }
                    if (finaldate !== "") { // Caso tenha sido informado data de final de vinculo
                        if (!finaldate.match(reg)) {
                            $(this).val("");
                            $(dateError).html("Digite uma data de in&iacute;cio v&aacute;lida.");
                            $(dateError).show();
                            return result = false;
                        }
                    }
                }
                //Caso só tenha sido informado data inicial,
                //Nada foi informado ou está tudo certo
                $(dateError).hide();
                return result = true;
            });
            return result;
        }
    };


    var checkCapital = function() {
        var dateError;
        if ($(this).parents("#modal_pj_pessoaJuridicaJuridica").length > 0) {
            dateError = ".modal_pj_date-error-pjj";
        } else if ($(this).parents("#modal_pj_pessoaFisicaJuridica").length > 0) {
            dateError = ".modal_pj_date-error-pfj";
        }
        $(this).val($(this).val().replace(/,/g, "."));
        var soma = 0;
        $.each($('.modal_pj_capital-pfj'), function() {
            soma += Number($(this).val());
        });
        $.each($('.modal_pj_capital-pjj'), function() {
            soma += Number($(this).val());
        });
        if ($(this).val().match(/^\d{0,3}(?:\.\d{0,2}){0,1}$/)) {
            if ($(this).val() > 100 || soma > 100) {
                $(dateError).html("A soma dos percentuais de participa&ccedil;&atilde;o dos v&iacute;nculos administrativos e empresariais n&atilde;o pode exceder 100%.");
                $(dateError).show();
                $(this).val("");
            } else {
                $(dateError).hide();
            }
        } else {
            $(dateError).html("Digite um percentual v&aacute;lido.");
            $(dateError).show();
            $(this).val("");
        }
    }

    var handleTable = function() {

        var table = $('.modal_pj_vinculations');

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

        var tableWrapper = $(".vinculations_wrapper");

        tableWrapper.find(".dataTables_length select").select2({
            showSearchInput: false //hide search box with special css class
        }); // initialize select2 dropdown

        table.on('keyup', '.modal_pj_capital-pfj', checkCapital);
        table.on('keyup', '.modal_pj_capital-pjj', checkCapital);
    }

    return {
        init: function() {
            $("#modal_pj_tipicidade").select2();
            $("#modal_pj_enduf").select2({allowClear:true});
            $("#modal_pj_endcity").select2();
            $("#modal-new-pj input[type=radio]").uniform();
            
            
            
            $.validator.addMethod("cnpj", validaCNPJ, "Digite um CNPJ v&aacute;lido.");
            $.validator.addMethod("iniDate", validaData, "Digite uma data v&aacute;lida.");
            $.validator.addMethod("minlength_optional", validaMinLength, "Por favor, forne&ccedil;a ao menos {0} caracteres");

            handleValidation();
            handleTable();

            $('#modal_pj_cnpj').mask("99.999.999/9999-99");
            $('#modal_pj_state').mask("99999999999999");
            $('#modal_pj_iniDate').mask("99/99/9999");
            $('#modal_pj_nire').mask("99999999999");
            $('#modal_pj_cep').mask("99999-999");
            $('#modal_pj_cnae').mask("9999-9/99");
            $('.modal_pj_date').mask("99/99/9999");

            var masks = [$('#modal_pj_cep'), $('#modal_pj_iniDate'), $('#modal_pj_nire'), $('#modal_pj_cnae')];
            $('#modal_pj_form').submit(function() {
                $.each(masks, function() {
                    if ($(this).val() == "") {
                        $(this).closest('.form-group').removeClass("has-success").removeClass('has-error');
                        $(this).parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                    }
                });
            });

            jsf.ajax.addOnEvent(function(data) {
                if (data.status === 'success') {
                    if ($(data.source).attr("id") === "modal_pj_enduf") {
                        $('.modal_pj_endcity').select2();
                    } else if ($(data.source).attr("class") === "modal_pj_delete-pfj") {
                        $('.modal_pj_pfj-refresher').click();
                    } else if ($(data.source).attr("class") === "modal_pj_delete-pjj") {
                        $('.modal_pj_pjj-refresher').click();
                    } else if ($(data.source).attr("class") === "modal_pj_vinculatePF" || $(data.source).attr("class") === "modal_pj_vinculatePJ" || $(data.source).attr("class") === "modal_pj_pfj-refresher" || $(data.source).attr("class") === "modal_pj_pjj-refresher") {
                        $('.modal_pj_date').mask("99/99/9999");
                        if ($(data.source).attr("class") === "modal_pj_vinculatePF" || $(data.source).attr("class") === "modal_pj_pfj-refresher") {
                            $('.modal_pj_funcao').select2();
                            $('.modal_pj_date-error-pfj').hide();
                            if ($('.modal_pj_rows-pfj').children().length === 0) {
                                $('.modal_pj_rows-pfj').append('<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">Sem V&iacute;nculos.</td></tr>');
                            }
                            $('.modal_pj_capital-pfj').keyup(checkCapital);
                        } else {
                            $('.modal_pj_date-error-pjj').hide();
                            if ($('.modal_pj_rows-pjj').children().length === 0) {
                                $('.modal_pj_rows-pjj').append('<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">Sem V&iacute;nculos.</td></tr>');
                            }
                            $('.modal_pj_capital-pjj').keyup(checkCapital);
                        }

                        $('.modal_pj_initial-date,.modal_pj_final-date').keyup(checkDates);
                    }
                }
            });

            $.ajax({
                url: "/webresources/reaver/getPessoasFisicas",
                dataType: "json",
                cache: false
            })
                    .done(function(data) {
                        $('.modal_pj_cpfVinculate').select2({
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

            $.ajax({
                url: "/webresources/reaver/getPessoasJuridicas",
                dataType: "json",
                cache: false
            })
                    .done(function(data) {
                        $('.modal_pj_cnpjVinculate').select2({
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

            $('.modal_pj_initial-date,.modal_pj_final-date').keyup(checkDates);

            $('#modal_pj_vinculatePF').click(function(e) {
                e.preventDefault();
                if ($('#modal_pj_pessoafisica').val() !== "") {
                    $('.modal_pj_vinculatePF').click();
                }
            });

            $('#modal_pj_vinculatePJ').click(function(e) {
                e.preventDefault();
                if ($('#modal_pj_pessoajuridica').val() !== "") {
                    $('.modal_pj_vinculatePJ').click();
                }
            });

            function validaMinLength(value, element, param) {
                var length = $.isArray(value) ? value.length : this.getLength($.trim(value), element);
                return length >= param || length === 0;
            }

            situacao()
            $('#modal_pj_situacao').change(situacao);
            $('#modal_pj_situacao').click(situacao);
            function situacao() {
                if ($("input[name=modal_pj_situacao]:checked").val() === 'I') {
                    $('.modal_pj_reason').show();
                } else {
                    $('.modal_pj_reason').hide();
                }
            }

            function validaCNPJ(value) {
                var valida = new Array(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2);
                var dig1 = new Number;
                var dig2 = new Number;

                exp = /\.|\-|\//g
                value = value.toString().replace(exp, "");
                var digito = new Number(eval(value.charAt(12) + value.charAt(13)));

                for (i = 0; i < valida.length; i++) {
                    dig1 += (i > 0 ? (value.charAt(i - 1) * valida[i]) : 0);
                    dig2 += value.charAt(i) * valida[i];
                }
                dig1 = (((dig1 % 11) < 2) ? 0 : (11 - (dig1 % 11)));
                dig2 = (((dig2 % 11) < 2) ? 0 : (11 - (dig2 % 11)));

                if (((dig1 * 10) + dig2) != digito)
                    return false;
                else {
                    return true;
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
        }
    };
}();

