var PjudCad = function() {

    var handleValidation = function() {
        var form = $('#submit_form');
        var error = $('.alert-danger', form);
        var success = $('.alert-success', form);
        var info = $('.alert-info', form);

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
                situacao: {
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
                recurso: {
                    required: false,
                },
                recursotipo: {
                    required: false,
                },
                especializacao: {
                    required: false,
                },
                distribuicao: {
                    required: false,
                },
                distribuicaodata: {
                    date: true,
                    required: true,
                },
                despachoinicial: {
                    required: false,
                },
                despachoinicialdata: {
                    date: true,
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
                    date: true,
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
                    date: true,
                    required: false,
                },
                valorarrecadado: {
                    required: false,
                },
                fontedaarrecadacao: {
                    required: false,
                },
                outrasinfos1: {
                    required: false,
                },
                executado: {
                    required: true,
                },
                cpf: {
                    cpfOrCnpj: true,
                },
                cnpj: {
                    cpfOrCnpj: true,
                },
                atoprocessual: {
                    required: false,
                },
                outrasinfos2: {
                    required: false,
                },
                outrasinfos3: {
                    required: false,
                },
                outrasinfos4: {
                    required: false,
                }
            },
            messages: {// custom messages for radio buttons and checkboxes
            },
            errorPlacement: function(error, element) { // render error placement for each input type
                if (element.attr("name") == "executado") { // for uniform radio buttons, insert the after the given container
                    error.insertAfter("#form_executado_error");
                } else if (element.attr("name") == "cpf" || element.attr("name") == "cnpj" || $(element).hasClass("vinculotipo") || $(element).attr("id") == "situacao") {
                    error.insertAfter(element);
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
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
                if ($(element).attr("id") == "distribuicaodata") {
                    $('#distribuicao').css("border-color", "#e5e5e5");
                    $('#distribuicao').focus(function() {
                        $('#distribuicao').css("border-color", "#999");
                        $('#distribuicao').css("box-shadow", "none");
                        $('#distribuicao').css("outline", "0 none");
                    });
                    $('#distribuicao').focusout(function() {
                        $('#distribuicao').css("border-color", "#e5e5e5");
                        $('#distribuicao').css("box-shadow", "none");
                        $('#distribuicao').css("outline", "0 none");
                    });

                }
            },
            unhighlight: function(element) { // revert the change done by hightlight
                if ($(element).attr("id") === "distribuicao") {

                } else {
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                }
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

        $('.button-submit').click(function(e) {
            if ($('#submit_form').validate().form()) {
                $('.submit-pjud').click();
            }
            return false;
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
//            Metronic.scrollTo($('.page-title'));
        }

        // default form wizard
        $('#form_wizard').bootstrapWizard({
            'nextSelector': '.button-next',
            'previousSelector': '.button-previous',
            onTabClick: function(tab, navigation, index, clickedIndex) {
                success.hide();
                error.hide();
                info.hide();
                if (form.valid() == false) {
                    return false;
                }
                handleTitle(tab, navigation, clickedIndex);
            },
            onNext: function(tab, navigation, index) {
                success.hide();
                error.hide();
                info.hide();
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
            },
        });
    }

    var initTable = function() {
        var table = $('.vinculations');
        table.dataTable({
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
        table.find('.group-checkable').change(function() {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function() {
                if (checked) {
                    $(this).attr("checked", true);
                    $(this).parents('tr').find('.socio-data').children().children().children().show();
                } else {
                    $(this).attr("checked", false);
                    $(this).parents('tr').find('.socio-data').children().children().children().hide();
                    $(this).parents('tr').find('.socio-data').find(".data_redirecionamento").val("");
                    $(this).parents('tr').find('.socio-data').find(".data_redirecionamento").valid();
                }
            });
            jQuery.uniform.update(set);
        });
        table.on('change', 'tbody tr .checkboxes-socios', function() {
            var checked = jQuery(this).is(":checked");
            if (checked) {
                $(this).attr("checked", true);
                $(this).parents('tr').find('.socio-data').children().children().children().show();
            } else {
                $(this).attr("checked", false);
                $(this).parents('tr').find('.socio-data').children().children().children().hide();
                $(this).parents('tr').find('.socio-data').find(".data_redirecionamento").val("");
                $(this).parents('tr').find('.socio-data').find(".data_redirecionamento").valid();
            }
        });
    }

    return {
        init: function() {

            if (!jQuery().bootstrapWizard) {
                return;
            }

            initTable();
            if ($('.p-error').length > 0) {
                $('.alert-danger').show();
            }

            $(window).scroll(function() {
                if ($(".ver-inline-menu").is(":visible")) {
                    if ($(this).scrollTop() > 400) {
                        $(".ver-inline-menu").addClass("fixed-menu");
                    } else {
                        $(".ver-inline-menu").removeClass("fixed-menu");
                    }
                }
            });

            $.validator.methods["date"] = function validaData(value, element) {
                var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
                if (value === "" || value === null) {
                    return true;
                }
                return value.match(reg) ? true : false;
            }
            $.validator.addMethod("cpfOrCnpj", validaExecutado, "Escolha um executado.");
            $.validator.addClassRules({
                vinculo: {
                    required: true
                },
                vinculotipo: {
                    required: true
                },
                ardata: {
                    date: true
                },
                data_redirecionamento: {
                    date: true
                }
            });

            handleValidation();

            $('#vinculos').mask("99");
            $('.number-tentativas').mask("99");
            $('.date').mask("99/99/9999");

            maskMoney();
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

            $('.masked-numbers').keypress(numeroDoProcesso);
            function numeroDoProcesso(e) {
                var regex = new RegExp("[0-9.\/\-]");
                var key = e.keyCode || e.which;
                key = String.fromCharCode(key);

                if (!regex.test(key) && key.charCodeAt(0) > 32) {
                    e.returnValue = false;
                    if (e.preventDefault) {
                        e.preventDefault();
                    }
                }
            }
            ;

            executado();
            $('#executado').change(executado);
            function executado() {
                $('#pessoa-fisica').hide();
                $('#pessoa-juridica').hide();
                $('.button-pessoa-fisica').hide();
                $('.button-pessoa-juridica').hide();
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

            validaCPFCNPJ();
            $('#cpf,#cnpj').change(validaCPFCNPJ);
            function validaCPFCNPJ() {
                $('#pessoa-fisica').hide();
                $('#pessoa-juridica').hide();
                if ($('#cpf').val() !== "") {
                    $('.button-pessoa-fisica').show();
                } else if ($('#cnpj').val() !== "") {
                    $('.button-pessoa-juridica').show();
                } else {
                    $('.button-pessoa-fisica').hide();
                    $('.button-pessoa-juridica').hide();
                }
                if ($(this).next('span').length > 0) {
                    if ($(this).val() !== "") {
                        $(this).closest('.form-group').removeClass("has-error");
                        $(this).next('span').hide();
                    } else {
                        $(this).closest('.form-group').addClass("has-error");
                        $(this).next('span').show();
                    }
                }
            }

            validaVinculosProcessuais();
            $('.vinculotipo').change(validaVinculosProcessuais);
            function validaVinculosProcessuais() {
                if ($(this).next('span').length > 0) {
                    if ($(this).find(":selected").text() !== "") {
                        $(this).closest('.form-group').removeClass("has-error");
                        $(this).next('span').hide();
                    } else {
                        $(this).closest('.form-group').addClass("has-error");
                        $(this).next('span').show();
                    }
                }
            }

            validaSituacao();
            $('#situacao').change(validaSituacao);
            function validaSituacao() {
                if ($(this).next('span').length > 0) {
                    if ($(this).find(":selected").text() !== "") {
                        $(this).closest('.form-group').removeClass("has-error");
                        $(this).next('span').hide();
                    } else {
                        $(this).closest('.form-group').addClass("has-error");
                        $(this).next('span').show();
                    }
                }
            }

            function validaExecutado() {
                if ($('#cpf').val() !== "" || $('#cnpj').val() !== "") {
                    return true;
                } else {
                    return false;
                }
            }

            function exibirBens(element) {
                if ($(element).val() !== "") {
                    $(element).closest(".panel").find('.socio-bem').show();
                } else {
                    $(element).closest(".panel").find('.socio-bem').hide();
                }
            }

            $(document).on('change', '.situacao-penhora, .exito-aquisicao', exibirCamposInvisiveis);
            function exibirCamposInvisiveis(event) {
                if ($(this).find("input[type=radio]:checked").val() === 'D' || $(this).find("input[type=radio]:checked").val() === 'S') {
                    $(this).parents('.condicao-campos').find('.valor-endereco').show();
                    $(this).parents('.condicao-campos').find('.motivo').hide();
                    $(this).parents('.condicao-campos').find('.motivo').find('input').val("");
                } else if ($(this).find("input[type=radio]:checked").val() === 'I' || $(this).find("input[type=radio]:checked").val() === 'N'){
                    $(this).parents('.condicao-campos').find('.valor-endereco').hide();
                    $(this).parents('.condicao-campos').find('.valor-endereco').find('input').val('');
                    $(this).parents('.condicao-campos').find('.motivo').show();
                } else {
                    $(this).parents('.condicao-campos').find('.valor-endereco').hide();
                    $(this).parents('.condicao-campos').find('.valor-endereco').find('input').val("");
                }
            }

            $(document).on('change', '.citado', citado);
            function citado(event) {
                if ($(this).find("input[type=radio]:checked").val() === 'N') {
                    $(this).parents('.panel-body').find('.motivo').show();
                    $(this).parents('.panel').addClass('panel-danger').removeClass('panel-success');
                } else {
                    $(this).parents('.panel-body').find('.motivo').hide();
                    $(this).parents('.panel').removeClass('panel-danger').addClass('panel-success');
                }
            }

            $(document).on('change', '.redirecionado', redirecionado);
            function redirecionado(event) {
                if ($(this).find("input[type=radio]:checked").val() === 'D' || $(this).find("input[type=radio]:checked").val() === 'I') {
                    $('.socios-table').show();
                } else {
                    $('.socios-table').hide();
                }
            }

            $.ajax({
                url: "/webresources/reaver/getPessoasJuridicas",
                dataType: "json",
                cache: false,
                data: {
                    usuario: $.cookie("usuario")
                }
            })
                    .done(function(data) {
                        $('#cnpj').select2({
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
            if ($('#cnpj').val() !== "") {
                $('.carregar_socios_pj').click();
            }
            $('#cnpj').change(function() {
                $('.carregar_socios_pj').click();
            })

            $.ajax({
                url: "/webresources/reaver/getPessoasFisicas",
                dataType: "json",
                cache: false,
                data: {
                    usuario: $.cookie("usuario")
                }
            })
                    .done(function(data) {
                        $('#cpf').select2({
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
            if ($('#cpf').val() !== "") {
                $('.carregar_socios_pf').click();
            }
            $('#cpf').change(function() {
                $('.carregar_socios_pf').click();
            });

            jsf.ajax.addOnEvent(function(data) {
                if (data.status === "success") {
                    if ($(data.source).hasClass("vinculos")) {
                        $('.vinculotipo').select2({
                            allowClear: true,
                        });
                        $('.vinculotipo').change(validaVinculosProcessuais);
                        $('.vinculo').keypress(numeroDoProcesso);
                    } else if ($(data.source).hasClass("button-pessoa-fisica")) {
                        $('#pessoa-fisica').show();
                        getMoneyMask('.accordion');
                        initTable();
                    } else if ($(data.source).hasClass("button-pessoa-juridica")) {
                        $('#pessoa-juridica').show();
                        getSucessoes('#cnpj', '#pessoa-juridica');
                        getMoneyMask('.accordion');
                        initTable();
                    } else if ($(data.source).hasClass("tentativas")) {
                        $.each($('.citado'), citado);
                        $('select.socios').select2({allowClear: true});
                        $('select.tipoPenhora').select2({allowClear: true});
                        $('.uniformization input[type=radio]').uniform();
                        $('.date').mask("99/99/9999");
                    } else if ($(data.source).hasClass("carregar_socios_pj")) {
                        $('.penhora-socio').trigger("change");
                        initTable();
                        $.each($('.citado'), citado);
                        $.each($('.redirecionado'), redirecionado);
                        $('select.socios').select2({allowClear: true});
                        $('select.tipoPenhora').select2({allowClear: true});
                        $('.number-tentativas').mask("99");
                        $(".checkbox-table").find("input[type=checkbox]").uniform();
                        $.each($('.socio-data'), function() {
                            if ($(this).find(".data_redirecionamento").val() !== "") {
                                $(this).parent().find('.checkboxes-socios').prop('checked', 'checked');
                                $(this).parent().find('.checkboxes-socios').parent().addClass('checked');
                                $(".checkboxes-socios").trigger("change");
                            }
                        });
                        $('select.socios').select2({allowClear: true});
                        $.each($('.penhora-socio'), function() {
                            exibirBens($(this));
                        });
                        $.each($('.situacao-penhora, .exito-aquisicao'), exibirCamposInvisiveis);
                    } else if ($(data.source).hasClass("carregar_socios_pf")) {
                        $('.penhora-socio').trigger("change");
                        $.each($('.citado'), citado);
                        $('select.tipoPenhora').select2({allowClear: true});
                        $('.number-tentativas').mask("99");
                        $('select.socios').select2({allowClear: true});
                        $.each($('.situacao-penhora, .exito-aquisicao'), exibirCamposInvisiveis);
                    } else if ($(data.source).hasClass("add-penhora") || $(data.source).hasClass("penhora-refresher")) {
                        $('.date').mask("99/99/9999");
                        $('select.bemVinculate').select2({allowClear: true});
                        $('select.socios').select2({allowClear: true});
                        $('.uniformization input[type=radio]').uniform();
                        if ($(data.source).hasClass("add-penhora")) {
                            $('#accordion-penhora').find('.panel').last().find(".collapsed").removeClass("collapsed");
                            $('#accordion-penhora').find('.panel').last().find(".panel-collapse").addClass("in");
                        }
                        $.each($('#accordion-penhora').find('.penhora-socio'), function() {
                            exibirBens($(this));
                        });
                        maskMoney();
                        $.each($('.situacao-penhora, .exito-aquisicao'), exibirCamposInvisiveis);
                    } else if ($(data.source).hasClass("delete-penhora")) {
                        $('.penhora-refresher').click();
                    } else if ($(data.source).hasClass("bem-refresher")) {
                        $('#' + bemPanel.panel).addClass("in");
                        $('#' + bemPanel.panel).parent().find(".collapsed").removeClass("collapsed");
                        $('#' + bemPanel.panel).find('.bemVinculate').val($('#modal_bem_id').val());
                        $('.date').mask("99/99/9999");
                        $.each($('#accordion-penhora').find('.penhora-socio'), function() {
                            exibirBens($(this));
                        });
                        maskMoney();
                        $('select.bemVinculate').select2({allowClear: true});
                        $('select.socios').select2({allowClear: true});
                        $('.uniformization input[type=radio]').uniform();
                        $.each($('.situacao-penhora, .exito-aquisicao'), exibirCamposInvisiveis);
                        $('.bemVinculate').trigger("change");
                    } else if ($(data.source).hasClass("penhora-socio")) {
                        exibirBens($(data.source));
                        $(data.source).closest(".panel-body").find(".bemVinculate").val($(data.source).closest(".panel-body").find(".bem-penhorado").html().trim());
                        $('.bemVinculate').trigger("change");
                        $('select.bemVinculate').select2({allowClear: true});
                    } else if ($(data.source).hasClass("bemVinculate")) {
                        $('.uniformization input[type=radio]').uniform();
                        $.each($('.exito-aquisicao'), exibirCamposInvisiveis);
                        $('.date').mask("99/99/9999");
                        maskMoney();
                    }
                }
            });

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
