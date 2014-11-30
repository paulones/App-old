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
                cnpj: {
                    cnpj: true,
                    required: true
                },
                alias: {
                    minlength_optional: 1,
                    required: false
                },
                tipicidade: {
                    required: false
                },
                state: {
                    minlength_optional: 9,
                    maxlength: 15,
                    required: false
                },
                province: {
                    minlength_optional: 1,
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
                    minlength_optional: 9,
                    required: false
                },
                activity1: {
                    minlength_optional: 2,
                    required: false
                },
                activity2: {
                    minlength_optional: 2,
                    required: false
                },
                address: {
                    minlength_optional: 3,
                    required: false
                },
                complement: {
                    minlength_optional: 3,
                    required: false
                },
                number: {
                    number: true,
                    required: false
                },
                neighborhood: {
                    minlength_optional: 1,
                    required: false
                },
                cep: {
                    minlength_optional: 9,
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
                state: {
                    minlength: "Inscri&ccedil;&atilde;o estatal inv&aacute;lida.",
                },
                alias: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para o nome fantasia."
                },
                province: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para a inscri&ccedil;&atilde;o municipal."
                },
                iniDate: {
                    iniDate: "Forne&ccedil;a uma data v&aacute;lida."
                },
                nire: {
                    maxlength: "NIRE n&atilde;o deve passar de 11 d&iicute;gitos."
                },
                activity1: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para Atividade principal"
                },
                activity2: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para Atividade secund&aacute;ria."
                },
                address: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para endere&ccedil;o."
                },
                complement: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para complemento."
                },
                number: {
                    number: "N&uucute;mero deve conter somente valores num&eecute;ricos."
                },
                neighborhood: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para bairro."
                },
                cep: {
                    minlength_optional: "Por favor, forne&ccedil;a ao menos {0} caracteres para CEP."
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit
                $(".date-error").hide();
                $(".date-bem-error").hide();
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

    $('.submit-pj').click(function(e) {
        if ($('#form').validate().form()) {
            $(".date-error").hide();
            $(".date-bem-error").hide();
            $(".register").click();
        }
        return false;
    });

    var checkDates = function() {
        var dateError = ".date-error";
        if ($(this).val().length == 10) {
            var result;
            var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
            $.each($('.initial-date'), function() {
                var initialdate = $(this).val();
                var finaldate = $(this).closest('tr').children('td').children('.final-date').val();
                var criacaoEmpresa = $('.data-de-criacao').val();
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
                                    $(this).closest('tr').children('td').children('.final-date').val("");
                                    $(dateError).html("Digite uma data de in&iacute;cio inferior &agrave; data de t&eacute;rmino.");
                                    $(dateError).show();
                                    return result = false;
                                }
                            } else {
                                $(this).closest('tr').children('td').children('.final-date').val("");
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

    var checkBemDates = function() {
        var date_error = ".date-bem-error";
        if ($(this).val().length == 10) {
            var result;
            var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
            $.each($('.aquisicao-date'), function() {
                var initialdate = $(this).val();
                var finaldate = $(this).closest('.row').find('.extincao-date').val();
                if (initialdate.match(reg) && finaldate.match(reg)) {
                    if (finaldate != "" && initialdate != "") {
                        var final = finaldate.split("/")[2] + "-" + finaldate.split("/")[1] + "-" + finaldate.split("/")[0];
                        var initial = initialdate.split("/")[2] + "-" + initialdate.split("/")[1] + "-" + initialdate.split("/")[0];
                        if (final < initial) {
                            $(this).closest('.row').find('.extincao-date').val("");
                            $(date_error).html("Digite uma data de aquisi&ccedil;&atilde;o inferior &agrave; data de transfer&ecirc;ncia / extin&ccedil;&atilde;o.");
                            $(date_error).show();
                            return result = false;
                        } else {
                            $(date_error).hide();
                            return result = true;
                        }
                    } else if (initialdate != "") {
                        $(date_error).hide();
                        return result = true;
                    }
                } else if (initialdate != "" && !initialdate.match(reg)) {
                    $(this).val("");
                    $(date_error).html("Digite uma data de aquisi&ccedil;&atilde;o v&aacute;lida.");
                    $(date_error).show();
                    return result = false;
                } else if (finaldate != "" && !finaldate.match(reg)) {
                    $(this).closest('.row').find('.extincao-date').val("");
                    $(date_error).html("Digite uma data de transfer&ecirc;ncia / extin&ccedil;&atilde;o v&aacute;lida.");
                    $(date_error).show();
                    return result = false;
                } else {
                    $(date_error).hide();
                    return result = true;
                }
            });
            return result;
        }
    };
    
    var checkCapital = function() {
        var dateError = ".date-error";
        $(this).val($(this).val().replace(/,/g, "."));
        var soma = 0;
        $.each($('.capital-pfj'), function() {
            soma += Number($(this).val());
        });
        $.each($('.capital-pjj'), function() {
            soma += Number($(this).val());
        });
        if ($(this).val().match(/^\d{0,3}(?:\.\d{0,2}){0,1}$/)) {
            if ($(this).val() > 100 || soma > 100) {
                $(dateError).html("A soma dos percentuais de participa&ccedil;&atilde;o dos v&iacute;nculos administrativos n&atilde;o pode exceder 100%.");
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

        var table = $('.vinculations');

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

        table.on('keyup', '.capital-pfj', checkCapital);
        table.on('keyup', '.capital-pjj', checkCapital);
    }

    return {
        init: function() {

            $.validator.addMethod("cnpj", validaCNPJ, "Digite um CNPJ v&aacute;lido.");
            $.validator.addMethod("iniDate", validaData, "Digite uma data v&aacute;lida.");
            $.validator.addMethod("minlength_optional", validaMinLength, "Por favor, forne&ccedil;a ao menos {0} caracteres");

            handleValidation();
            handleTable();

            $('#cnpj').mask("99.999.999/9999-99");
            $('#state').mask("99999999999999");
            $('#iniDate').mask("99/99/9999");
            $('#nire').mask("99999999999");
            $('#cep').mask("99999-999");
            $('#cnae').mask("9999-9/99");
            $('.date').mask("99/99/9999");
            maskMoney();

            $('.menu-pj').addClass('active open');
            $('.menu-pj a').append('<span class="selected"></span>');
            $('.menu-pj a .arrow').addClass('open');
            if (window.location.search == "") {
                $('.sub-menu-pj-cad').addClass('active');
            } else {
                $('.sub-menu-pj-con').addClass('active');
            }

            var masks = [$('#cep'), $('#iniDate'), $('#nire'), $('#cnae')];
            $('#form').submit(function() {
                $.each(masks, function() {
                    if ($(this).val() == "") {
                        $(this).closest('.form-group').removeClass("has-success").removeClass('has-error');
                        $(this).parent('.input-icon').children('i').removeClass("fa-warning").removeClass("fa-check");
                    }
                });
            });
            
            $('#add-bem').click(function(e) {
                e.preventDefault();
                if ($('#extincaobem').val() !== "" || $('#aquisicaobem').val() !== "" || $('#valorbem').val() !== "" || $('#enderecobem').val() !== "" || $('#tipobem').val() !== "" || $('#descricaobem').val() !== "") {
                    $('.add-bem').click();
                }
            });

            jsf.ajax.addOnEvent(function(data) {
                if (data.status === 'success') {
                    if ($(data.source).attr("id") === "enduf") {
                        $('.endcity').select2({allowClear: true});
                    } else if ($(data.source).attr("class") === "delete-pfj") {
                        $('.pfj-refresher').click();
                    } else if ($(data.source).attr("class") === "delete-pjj") {
                        $('.pjj-refresher').click();
                    } else if ($(data.source).attr("class") === "vinculatePF" || $(data.source).attr("class") === "vinculatePJ"
                            || $(data.source).attr("class") === "pfj-refresher" || $(data.source).attr("class") === "pjj-refresher") {
                        $('.date').mask("99/99/9999");
                        if ($(data.source).attr("class") === "vinculatePF" || $(data.source).attr("class") === "pfj-refresher") {
                            $('.funcao').select2({allowClear: true});
                            $('.date-error').hide();
                            if ($('.rows-pfj').children().length === 0) {
                                $('.rows-pfj').append('<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">Sem V&iacute;nculos.</td></tr>');
                            }
                            $('.capital-pfj').keyup(checkCapital);
                        } else if ($(data.source).attr("class") === "vinculatePJ" || $(data.source).attr("class") === "pjj-refresher") {
                            $('.date-error').hide();
                            if ($('.rows-pjj').children().length === 0) {
                                $('.rows-pjj').append('<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">Sem V&iacute;nculos.</td></tr>');
                            }
                            $('.capital-pjj').keyup(checkCapital);
                        } 
                        $('.initial-date,.final-date').keyup(checkDates);
                    } else if ($(data.source).attr("class") === "add-bem" || $(data.source).attr("class") === "bem-refresher") {
                        $('select.tipobem').select2({allowClear: true});
                        $('.date').mask("99/99/9999");
                        maskMoney();
                        $('.aquisicao-date,.extincao-date').keyup(checkBemDates);
                    } else if ($(data.source).hasClass("delete-bem")) {
                        $('.bem-refresher').click();
                    }
                }
            });
            
            function maskMoney() {
                $('.money').maskMoney({
                    prefix: 'R$ ',
                    symbol: 'R$', // Simbolo
                    decimal: ',', // Separador do decimal
                    precision: 2, // Precisão
                    thousands: '.', // Separador para os milhares
                    allowZero: false, // Permite que o digito 0 seja o primeiro caractere
                    showSymbol: true // Exibe/Oculta o símbolo
                });
                $.each($('.money'), function() {
                    if ($(this).val() !== "") {
                        $(this).maskMoney('mask');
                    }
                });
            }

            $.ajax({
                url: "/webresources/reaver/getPessoasFisicas",
                dataType: "json",
                cache: false,
                data: {
                    usuario: $.cookie("usuario")
                }
            })
                    .done(function(data) {
                        $('.cpfVinculate').select2({
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
                cache: false,
                data: {
                    usuario: $.cookie("usuario")
                }
            })
                    .done(function(data) {
                        if (getParameterByName("id") !== "" && window.location.href.indexOf("pessoa-juridica") >= 0) {
                            data.removeValue("id", getParameterByName("id"));
                        }
                        $('.cnpjVinculate').select2({
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

            $('.initial-date,.final-date').keyup(checkDates);
            $('.aquisicao-date,.extincao-date').keyup(checkBemDates);

            $('#vinculatePF').click(function(e) {
                e.preventDefault();
                if ($('#pessoafisica').val() !== "") {
                    $('.vinculatePF').click();
                }
            });

            $('#vinculatePJ').click(function(e) {
                e.preventDefault();
                if ($('#pessoajuridica').val() !== "") {
                    $('.vinculatePJ').click();
                }
            });

            function validaMinLength(value, element, param) {
                var length = $.isArray(value) ? value.length : this.getLength($.trim(value), element);
                return length >= param || length === 0;
            }

            situacao();
            $('#situacao').change(situacao);
            $('#situacao').click(situacao);
            function situacao() {
                if ($("input[name=situacao]:checked").val() === 'I') {
                    $('.reason').show();
                } else {
                    $('.reason').hide();
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

