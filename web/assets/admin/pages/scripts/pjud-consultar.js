var PjudCon = function() {

    var element;
    var pages = [];

    var initTable = function() {
        var table = $('#table');
        /*
         * Initialize DataTables, with no sorting on the 'details' column
         */
        var oTable = table.dataTable({
            "columnDefs": [{
                    "orderable": false,
                    "targets": [0, 5]
                }],
            "ajax": "/webresources/reaver/getProcessosJudiciaisTable",
            "columns": [
                {"data": "row-details"},
                {"data": "numero_do_processo"},
                {"data": "comarca"},
                {"data": "numero_da_cda"},
                {"data": "executado"},
                {"data": "delete"},
                {"data": "detalhes", "class": "display-hide"},
                {"data": "pjud", "class": "display-hide"}
            ],
            "orderClasses": false,
            "deferRender": true,
            "order": [
            ],
            "language": {
                "emptyTable": "Nenhum Processo Judicial cadastrado.",
                "zeroRecords": "Nenhum Processo Judicial encontrado.",
                "info": "Exibindo de _START_ a _END_ Processos Judiciais. Total de _TOTAL_.",
                "infoEmpty": "Exibindo 0 Processos Judiciais.",
                "infoFiltered": "(filtrando um total de _MAX_ registros).",
                "search": "Procurar:",
                "lengthMenu": " Exibir _MENU_",
            },
            "lengthMenu": [
                [10, 15, 20, 50, 100],
                [10, 15, 20, 50, 100] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
        });
        var tableWrapper = $('#table_wrapper'); // datatable creates the table wrapper by adding with id {your_table_jd}_wrapper

        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown

        table.on('click', ' tbody td .row-details', function() {
            if ($(this).hasClass('row-details-open')) {
                /* This row is already open - close it */
                $(this).addClass("row-details-close").removeClass("row-details-open");
                $(this).parent().parent().next().remove();
            } else {
                /* Open this row */
                if (!executing) {
                    $('#pjud-id').val($(this).parent().siblings(":last").text());
                    $('.info-refresher').click();
                    element = $(this);
                }
            }
        });

        var index;
        table.on('click', '.button-delete', function(e) {
            e.preventDefault();
            index = $('.button-delete').index(this);
            $('.delete-modal-activator').click();
        });

        $('.cancel').click(function(e) {
            e.preventDefault();
        });
        $('.remove').click(function(e) {
            e.preventDefault();
            $('#pjud-id').val($($('.button-delete').get(index)).parent().next().next().text());
            $('.info-delete').click();
        });

    }

    var initVinculationsTable = function() {
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
    }

    var initHistoryTable = function() {
        var table = $('.pjud-history-table');
        /*
         * Initialize DataTables, with no sorting on the 'details' column
         */
        var oTable = table.dataTable({
            destroy: true,
            paginate: false,
            "order": [
            ],
            "drawCallback": function(settings) {
                if ($('#largehistorypjudexecutado').length > 0) {
                    carregarHistoricoExecutado();
                } else if ($('#largehistorypjudprocesso').length > 0) {
                    carregarHistoricoProcesso();
                } else if ($('#largehistorypjudatoprocessual').length > 0) {
                    carregarHistoricoAtoProcessual();
                }
            },
            "deferRender": true,
            "ordering": false,
            "columns": [
                {"class": "width-expand expand-box"},
                {"class": "width-data data-de-modificacao"},
                {"class": "width-descricao description"},
                {"class": "width-usuario"},
                {"class": "detail display-hide"},
            ],
            "language": {
                "emptyTable": "Nenhum hist&oacute;rico registrado.",
                "zeroRecords": "Nenhum hist&oacute;rico encontrado.",
                "info": "Exibindo de _START_ a _END_ registros do hist&oacute;rico. Total de _TOTAL_.",
                "infoEmpty": "Exibindo 0 registros do hist&oacute;rico.",
                "infoFiltered": "(filtrando um total de _MAX_ registros).",
                "search": "Procurar:",
                "lengthMenu": " Exibir _MENU_",
            },
            lengthMenu: false,
        });
        var tableWrapper = $('#pjud-executado-history-table_wrapper'); // datatable creates the table wrapper by adding with id {your_table_jd}_wrapper
        var tableWrapper2 = $('#pjud-processo-history-table_wrapper');
        var tableWrapper3 = $('#pjud-atoprocessual-history-table_wrapper');
        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown
        tableWrapper2.find('.dataTables_length select').select2();
        tableWrapper3.find('.dataTables_length select').select2();

        /* Add event listener for opening and closing details
         * Note that the indicator for showing which row is open is not controlled by DataTables,
         * rather it is done here
         */
        table.on('click', 'tbody td .row-details', function() {
            if ($(this).hasClass('row-details-open')) {
                /* This row is already open - close it */
                $(this).addClass("row-details-close").removeClass("row-details-open");
                $(this).parent().parent().append($(this).parent().parent().next().children());
                $(this).parent().parent().next().remove();
                $(this).parent().parent().children(".detail").hide();
            } else {
                /* Open this row */
                $(this).addClass("row-details-open").removeClass("row-details-close");
                $('<tr>').insertAfter($(this).parent().parent());
                $(this).parent().parent().children(".detail").attr("colspan", "6");
                $(this).parent().parent().children(".detail").appendTo($(this).parent().parent().next());
                $(this).parent().parent().next().children(".detail").show();
            }
        });
    }

    $(document).on('hide.bs.modal', '.modal', function() {
        $('.modal').remove();
    });

    function carregarHistoricoExecutado() {
        getMoneyMask(".accordion");
        if ($('.pj-id').length !== 0) {
            var lastIndex = $('.pj-id').length - 1;
            var index = 0;
            $.each($('.pj-tab'), function() {
                var pjId = $(this).find('.pj-id').val();
                var parent = "#" + $(this).attr('id');
                if (pjId !== undefined) {
                    $.ajax({
                        url: "/webresources/reaver/getSucessoes",
                        data: {
                            id: pjId
                        },
                        dataType: "json",
                        cache: false
                    })
                            .done(function(data) {
                                var sucedidas = "";
                                var atual = "";
                                var sucessoras = "";
                                var icon = parent.indexOf('modal') !== -1 ? '<i class="fa fa-bank"></i>' : '<i class="fa fa-search"></i>';
                                var modal = parent.indexOf('modal') !== -1 ? '' : 'pj-info';
                                $.each(data, function() {
                                    var status;
                                    if (String(pjId) === String($(this).attr('sucessora_id'))) {
                                        status = $(this).attr('sucedida_status') === 'A' ? '' : '<strong> (DESATIVADA)</strong>';
                                        sucedidas += '<li class="sucedidas dd-item dd3-item"><input class="suc-id display-hide" value="' + $(this).attr('sucessao_id') + '" type="text"/>'
                                                + '<div class="dd-handle dd3-handle ' + modal + '">' + icon + '<input class="object-id display-hide" value="' + $(this).attr('sucedida_id') + '" type="text"/></div>'
                                                + '<div class="dd3-content"><strong>' + $(this).attr('sucedida_nome') + ':</strong> ' + $(this).attr('sucedida_cnpj') + status + '</div></li>';
                                    } else if (String(pjId) === String($(this).attr('sucedida_id'))) {
                                        status = $(this).attr('sucessora_status') === 'A' ? '' : '<strong> (DESATIVADA)</strong>';
                                        sucessoras += '<li class="sucessoras dd-item dd3-item"><input class="suc-id display-hide" value="' + $(this).attr('sucessao_id') + '" type="text"/>'
                                                + '<div class="dd-handle dd3-handle ' + modal + '">' + icon + '<input class="object-id display-hide" value="' + $(this).attr('sucessora_id') + '" type="text"/></div>'
                                                + '<div class="dd3-content"><strong>' + $(this).attr('sucessora_nome') + ':</strong> ' + $(this).attr('sucessora_cnpj') + status + '</div></li>';
                                    }
                                });
                                if (sucedidas === "" && sucessoras === "") {
                                    $(parent).find('.nestable-list').children().remove();
                                    $(parent).find('.nestable-list').append('<div layout="block" class="alert alert-warning">N&atilde;o h&aacute; sucess&otilde;es para esta Pessoa Jur&iacute;dica.</div>');
                                } else {
                                    var status = $(parent).find('.pj-status').val() === 'A' ? '' : '<strong> (DESATIVADA)</strong>';
                                    atual += '<li class="atual dd-item dd3-item"><div class="dd-handle dd3-handle"><i class="fa fa-bank"></i></div>'
                                            + '<div class="dd3-content"><strong>' + $(parent).find('.nome').html().trim() + ':</strong> ' + $(parent).find('.cnpj').html().trim() + status + '</div></li>';
                                    $(parent).find('.dd-list').append(sucedidas);
                                    if (sucedidas !== "") {
                                        atual = '<ol class="dd-list">' + atual + '</ol>';
                                        $(parent).find('.sucedidas').last().append(atual);
                                    } else {
                                        $(parent).find('.dd-list').append(atual);
                                    }
                                    if (sucessoras !== "") {
                                        sucessoras = '<ol class="dd-list">' + sucessoras + '</ol>';
                                        $(parent).find('.atual').append(sucessoras);
                                    }
                                    $(parent).find('.sucedidas').children(".dd3-content").append('<strong class="sucessao-label-right">(Sucedida)</strong>');
                                    $(parent).find('.sucessoras').children(".dd3-content").append('<strong class="sucessao-label-right">(Sucessora)</strong>');
                                }
                                if (index === lastIndex) {
                                    getHistorico("Executado");
                                }
                                index++;
                            });
                }
            });
        } else {
            getHistorico("Executado");
        }
    }

    function carregarHistoricoProcesso() {
        getMoneyMask(".tab-processo");
        getMoneyMask(".accordion");
        getHistorico("Processo");
    }

    function carregarHistoricoAtoProcessual() {
        getMoneyMask(".accordion");
        getHistorico("AtoProcessual");
    }

    function getHistorico(tipo) {
        var atual;
        $.each($('.data-de-modificacao'), function() {
            if ($(this).html().indexOf('Atual') !== -1) {
                $(this).parent().find('.main').addClass('current');
                $(this).parent().children('.description').removeClass('description');
                atual = $(this).parent().find('.form-body');
            } else {
                $(this).parent().find('.main').addClass('past');
            }
        });
        $.each($('.past'), function() {
            var history = this;
            var description = "";
            if (tipo === "Executado") {
                var informacoesgerais = false;
                var domicilioeleitoral = false;
                var endereco = false;
                var outrasInformacoesExecutado = false;
                var sucessao = false;
                var bens = false;
                var vinculossociais = false;
                var vinculosadministrativos = false;
                var participacao = false;
                $.each($(this).find('.form-control-static'), function(index) {
                    if ($(atual).find('.form-control-static').eq(index).html().trim() !== $(this).html().trim()) {
                        $(this).parent().parent().css("color", "#a94442");
                        if ($(this).parents('.endereco')) {
                            endereco = true;
                        } else if ($(this).parents('.outrasinformacoes')) {
                            outrasInformacoesExecutado = true;
                        }
                    }
                });
                var tipoDoExecutado = false;
                if ($(atual).find('.form-control-executado-static').length !== $(history).find('.form-control-executado-static').length) {
                    tipoDoExecutado = true;
                    $(history).find('.form-control-executado-static').parent().parent().css("color", "#a94442");
                    $(history).find('td').css("color", "#a94442");
                    $(history).find('th').css("color", "#a94442");
                    $(history).find('td').children().css("color", "#a94442");
                    $(history).find('.tab-pj-info').css("color", "#a94442");
                } else {
                    $.each($(this).find('.form-control-executado-static'), function(index) {
                        if ($(atual).find('.form-control-executado-static').eq(index).html().trim() !== $(this).html().trim()) {
                            $(this).parent().parent().css("color", "#a94442");
                            $(history).find('.tab-pj-info').css("color", "#a94442");
                            if ($(this).parents('.informacoesgerais')) {
                                informacoesgerais = true;
                            } else if ($(this).parents('.domicilioeleitoral')) {
                                domicilioeleitoral = true;
                            }
                        }

                    });
                }

                var checkBensChanges = checkChangesLabel('.form-control-bem-static', '.main');
                bens = (!bens) ? checkBensChanges : true;
                var checkSucedidasChanges = checkSucessoes('.sucedidas');
                var checkSucessorasChanges = checkSucessoes('.sucessoras');
                if (!tipoDoExecutado) {
                    if ($(this).parents('.pj-tab')) {
                        var checkPfjChanges = checkChangesTableVinculos('.rows-pfj tr');
                        var checkPjjChanges = checkChangesTableVinculos('.rows-pjj tr');
                        var checkPjjPartChanges = checkChangesTableVinculos('.rows-pjj-part tr');
                        sucessao = (!sucessao) ? checkSucedidasChanges : true;
                        sucessao = (!sucessao) ? checkSucessorasChanges : true;
                        vinculosadministrativos = (!vinculosadministrativos) ? checkPfjChanges : true;
                        vinculosadministrativos = (!vinculosadministrativos) ? checkPjjChanges : true;
                        participacao = (!participacao) ? checkPjjPartChanges : true;
                    } else if ($(this).parents('.pf-tab')) {
                        var checkPffChanges = checkChangesTableVinculos('.rows-pff tr');
                        var checkPfjChanges = checkChangesTableVinculos('.rows-pfj tr');
                        vinculosadministrativos = (!vinculosadministrativos) ? checkPfjChanges : true;
                        vinculossociais = (!vinculossociais) ? checkPffChanges : true;
                    }
                }


                if (tipoDoExecutado) {
                    description += "Tipo do executado, ";
                } else {
                    if (informacoesgerais) {
                        if ($(this).children().hasClass('pf-tab')) {
                            description += "Informa&ccedil;&otilde;es Pessoais, ";
                        } else {
                            description += "Informa&ccedil;&otilde;es Empresariais, ";
                        }

                    }
                    if (domicilioeleitoral) {
                        description += "Domic&iacute;lio Eleitoral, ";
                    }
                    if (endereco) {
                        description += "Endere&ccedil;o, ";
                    }
                    if (vinculosadministrativos) {
                        if ($(this).children().hasClass('pf-tab')) {
                            description += "V&iacute;nculos Empresariais, ";
                        } else {
                            description += "V&iacute;nculos Administrativos, ";
                        }

                    }
                    if (vinculossociais) {
                        description += "V&iacute;nculos Sociais, ";
                    }
                    if (participacao) {
                        description += "Participa&ccedil;&atilde;o em Outras Empresas, ";
                    }
                    if (bens) {
                        description += "Bens, ";
                    }
                    if (outrasInformacoesExecutado) {
                        description += "Outras Informa&ccedil;&otilde;es, ";
                    }
                    if (sucessao) {
                        description += "Sucess&atilde;o, ";
                    }
                }
            } else if (tipo === "Processo") {
                var processo = false;
                var vinculos = false;
                var creditofiscal = false;
                var outrasInformacoesProcesso = false;
                $.each($(this).find('.form-control-static'), function(index) {
                    if ($(atual).find('.form-control-static').eq(index).html().trim() !== $(this).html().trim()) {
                        $(this).parent().parent().css("color", "#a94442");
                        if ($(this).parents('.processo')) {
                            processo = true;
                        } else if ($(this).parents('.creditofiscal')) {
                            creditofiscal = true;
                        } else if ($(this).parents('.outrasinformacoes')) {
                            outrasInformacoesProcesso = true;
                        }
                    }
                });

                var checkProcessoChanges = checkChangesLabel('.form-control-vinculo-static', '.main');
                vinculos = checkProcessoChanges;

                if (processo) {
                    description += "Processo, ";
                }
                if (vinculos) {
                    description += "V&iacute;nculos Processuais, ";
                }
                if (creditofiscal) {
                    description += "Cr&eacute;dito Fiscal, ";
                }
                if (outrasInformacoesProcesso) {
                    description += "Outras informa&ccedil;&otilde;es, ";
                }
            } else if (tipo === "AtoProcessual") {
                var outrasInformacoesAto = false;
                var citacoes = false;
                var redirecionamento = false;
                var penhora = false;
                var aquisicao = false;
                $.each($(this).find('.form-control-static'), function(index) {
                    if ($(atual).find('.form-control-static').eq(index).html().trim() !== $(this).html().trim()) {
                        $(this).parent().parent().css("color", "#a94442");
                        outrasInformacoesAto = true;
                    }
                });

                var checkCitacoesChanges = checkChangesCitacoes('.accordion-citacao');
                var checkRedirecionamentoLabelChange = checkChangesLabel('.form-control-redirecionamento-static', '.main');
                var checkRedirecionamentoChanges = checkChangesTableRedirecionamento('.rows-redirecionamento tr');
                var checkPenhoraChanges = checkChangesPenhoras('.accordion-penhora');
                var checkAquisicaoChanges = checkChangesAquisicao('.accordion-aquisicao');
                citacoes = checkCitacoesChanges;
                redirecionamento = checkRedirecionamentoLabelChange;
                redirecionamento = (!redirecionamento) ? checkRedirecionamentoChanges : true;
                penhora = checkPenhoraChanges;
                aquisicao = checkAquisicaoChanges;

                if (citacoes) {
                    description += "Cita&ccedil;&atilde;o, ";
                }
                if (redirecionamento) {
                    description += "Redirecionamento, ";
                }
                if (penhora) {
                    description += "Penhora, ";
                }
                if (aquisicao) {
                    description += "Aquisi&ccedil;&atilde;o de Bens, ";
                }
                if (outrasInformacoesAto) {
                    description += "Outras Informa&ccedil;&otilde;es, "
                }
            }

            description = description.substring(0, description.length - 2) + ".";
            if (description.indexOf(",") !== -1) {
                description = description.substring(0, description.lastIndexOf(",")) + " e" + description.substring(description.lastIndexOf(",") + 1, description.length);
            } else if (description === ".") {
                description = "";
            }
            $(this).closest('.detail').parent().children('.description').append(description);

            function checkChangesTableVinculos(tr) {
                var changed = false;
                if ($(atual).find(tr).length !== $(history).find(tr).length) {
                    changed = true;
                }
                $(history).find(tr).find('td').css("color", "#a94442");
                $.each($(atual).find(tr), function() {
                    var atual = this;
                    var exists = false;
                    $.each($(history).find(tr), function() {
                        var cpfAtual = $(atual).find('td').eq(0).html().trim();
                        var cpfHistorico = $(this).find('td').eq(0).html().trim();
                        if (cpfAtual === cpfHistorico) {
                            $(this).find('td').css("color", "black");
                            exists = true;
                            $.each($(this).find('td'), function(index) {
                                if (index !== 0 && $(atual).find('td').eq(index).html().trim() !== $(this).html().trim()) {
                                    $(this).css("color", "#a94442");
                                    $(this).parent().parent().parent().children('thead').children().children('th').eq(index).css('color', '#a94442');
                                    changed = true;
                                }
                            })
                        } else {
                            if (($(atual).find('td').length > 1 && $(this).find('td').length === 1) || ($(this).find('td').length > 1 && $(atual).find('td').length === 1)) {
                                changed = true;
                            }
                        }
                    });
                    if (!exists) {
                        changed = true;
                    }
                });
                if (changed) {
                    $(history).find('.tab-pj-info').css("color", "#a94442");
                    return true;
                } else {
                    return false;
                }
            }

            function checkChangesTableRedirecionamento(tr) {
                var changed = false;
                $(history).find('.socio-data').css("color", "#a94442");
                if ($(atual).find('.table-redirecionamento').length === 0 && $(history).find('.table-redirecionamento').length > 0) {
                    $(history).find('.table-redirecionamento').find('td').css('color', '#a94442');
                    $(history).find('.table-redirecionamento').find('th').css('color', '#a94442');
                    $(history).find('.portlet_ato_processual').find('.tab_1').css("color", "red");
                    changed = true;
                } else if ($(atual).find('.table-redirecionamento').length > 0 && $(history).find('.table-redirecionamento').length === 0) {
                    $(history).find('.alert-redirecionamento').css('color', '#a94442');
                    $(history).find('.portlet_ato_processual').find('.tab_1').css("color", "red");
                }
                $.each($(atual).find(tr), function() {
                    var atual = this;
                    var exists = false;
                    $.each($(history).find(tr), function() {
                        var pessoaAtual = $(atual).find('td').eq(0).html().trim();
                        var pessoaHistorico = $(this).find('td').eq(0).html().trim();
                        if (pessoaAtual === pessoaHistorico) {
                            exists = true;
                            if ($(atual).find('.socio-data').html().trim() === $(this).find('.socio-data').html().trim()) {
                                $(this).find('.socio-data').css("color", "black");
                            }
                        }
                    });
                    if (!exists) {
                        changed = true;
                    }
                });
                $.each($(history).find(tr), function() {
                    if ($(this).find(".socio-data").css("color") !== "rgb(0, 0, 0)") {
                        changed = true;
                        $(history).find('.socio-data-title').css("color", "#a94442");
                        $(history).find('.portlet_ato_processual').find('.tab_1').css("color", "red");
                    }
                });
                if (changed) {
                    return true;
                } else {
                    return false;
                }
            }

            function checkChangesCitacoes(accordion) {
                var changed = false;
                var tipoCitacoes = ['ar', 'oficial', 'edital', 'enderecosocio'];
                $.each($(atual).find(accordion), function() {
                    var accordion_atual = this;
                    $.each($(history).find(accordion), function(index) {
                        if ($(accordion_atual).hasClass('accordion-' + tipoCitacoes[index])) {
                            if ($(this).hasClass('accordion-' + tipoCitacoes[index])) {
                                if ($(accordion_atual).find('.panel').length > 0 && $(this).find('.panel').length === 0) {
                                    $(this).parent().find('.alert-' + tipoCitacoes[index]).css('color', '#a94442');
                                    changed = true;
                                    $(this).closest('.portlet_ato_processual').find('.tab_1').css("color", "red");
                                }
                                if ($(accordion_atual).find('.panel').length !== $(this).find('.panel').length) {
                                    changed = true;
                                    $(this).closest('.portlet_ato_processual').find('.tab_1').css("color", "red");
                                }
                                var accordion_history = this;
                                $(this).find('.panel').addClass('panel-danger').removeClass('panel-default');
                                $(this).find('.form-control-citacao-static').closest('.form-group').css('color', '#a94442');
                                $.each($(accordion_atual).find('.panel'), function() {
                                    var panel_atual = this;
                                    $.each($(accordion_history).find('.panel'), function() {
                                        var panel_history = this;
                                        $.each($(panel_atual).find('.form-control-citacao-static'), function() {
                                            var atual = this;
                                            $.each($(panel_history).find('.form-control-citacao-static'), function() {
                                                if ($(atual).text().trim() === $(this).text().trim()) {
                                                    $(this).parent().parent().css("color", "black");
                                                    $(this).closest('.panel').addClass('panel-default').removeClass('panel-danger');
                                                }
                                            });
                                        });
                                    });
                                });
                            }
                        }
                    });
                });
                $.each($(history).find('.form-control-citacao-static'), function() {
                    if ($(this).css("color") === "rgb(169, 68, 66)") {
                        changed = true;
                        $(this).closest('.portlet_ato_processual').find('.tab_1').css("color", "red");
                        $(this).closest('.panel').removeClass('panel-default').addClass('panel-danger');
                    }
                });
                if (changed) {
                    return true;
                } else {
                    return false;
                }
            }

            function checkChangesPenhoras(accordion) {
                var changed = false;
                $.each($(atual).find(accordion), function() {
                    var accordion_atual = this;
                    $.each($(history).find(accordion), function(index) {
                        if ($(accordion_atual).find('.panel').length > 0 && $(this).find('.panel').length === 0) {
                            $(this).parent().find('.alert-penhora').css('color', '#a94442');
                            changed = true;
                            $(this).closest('.portlet_ato_processual').find('.tab_2').css("color", "red");
                        }
                        if ($(accordion_atual).find('.panel').length !== $(this).find('.panel').length) {
                            changed = true;
                            $(this).closest('.portlet_ato_processual').find('.tab_2').css("color", "red");
                        }
                        var accordion_history = this;
                        $(this).find('.panel').addClass('panel-danger').removeClass('panel-default');
                        $(this).find('.form-control-penhora-static').closest('.form-group').css('color', '#a94442');
                        $.each($(accordion_atual).find('.panel'), function() {
                            var panel_atual = this;
                            $.each($(accordion_history).find('.panel'), function() {
                                var panel_history = this;
                                $.each($(panel_atual).find('.form-control-penhora-static'), function() {
                                    var atual = this;
                                    $.each($(panel_history).find('.form-control-penhora-static'), function() {
                                        if ($(atual).text().trim() === $(this).text().trim()) {
                                            $(this).parent().parent().css("color", "black");
                                            $(this).closest('.panel').addClass('panel-default').removeClass('panel-danger');
                                        }
                                    });
                                });
                            });
                        });
                    });
                });
                $.each($(history).find('.form-control-penhora-static'), function() {
                    if ($(this).css("color") === "rgb(169, 68, 66)") {
                        changed = true;
                        $(this).closest('.portlet_ato_processual').find('.tab_2').css("color", "red");
                        $(this).closest('.panel').removeClass('panel-default').addClass('panel-danger');
                        if ($(this).hasClass('bem-penhorado')) {
                            $(this).closest('.panel').find('.bem-penhorado-info').find('.form-group').css("color", "#a94442")
                        }
                    }
                });
                if (changed) {
                    return true;
                } else {
                    return false;
                }
            }

            function checkChangesAquisicao(accordion) {
                var changed = false;
                $.each($(atual).find(accordion), function() {
                    var accordion_atual = this;
                    $.each($(history).find(accordion), function(index) {
                        if ($(accordion_atual).find('.panel').length > 0 && $(this).find('.panel').length === 0) {
                            $(this).parent().find('.alert-aquisicao').css('color', '#a94442');
                            changed = true;
                            $(this).closest('.portlet_ato_processual').find('.tab_2').css("color", "red");
                        }
                        if ($(accordion_atual).find('.panel').length !== $(this).find('.panel').length) {
                            changed = true;
                            $(this).closest('.portlet_ato_processual').find('.tab_2').css("color", "red");
                        }
                        var accordion_history = this;
                        $(this).find('.panel').addClass('panel-danger').removeClass('panel-info');
                        $(this).find('.form-control-aquisicao-static').closest('.form-group').css('color', '#a94442');
                        $.each($(accordion_atual).find('.panel'), function() {
                            var panel_atual = this;
                            $.each($(accordion_history).find('.panel'), function() {
                                var panel_history = this;
                                $.each($(panel_atual).find('.panel-body').children('span'), function() {
                                    var sub_atual = this;
                                    $.each($(panel_history).find('.panel-body').children('span'), function() {
                                        var sub_history = this;
                                        if ($(sub_atual).attr("class") === $(sub_history).attr("class")) {
                                            $.each($(sub_atual).find('.form-control-aquisicao-static'), function() {
                                                var atual = this;
                                                $.each($(sub_history).find('.form-control-aquisicao-static'), function() {
                                                    if ($(atual).text().trim() === $(this).text().trim()) {
                                                        $(this).parent().parent().css("color", "black");
                                                        $(this).closest('.panel').addClass('panel-info').removeClass('panel-danger');
                                                    }
                                                });
                                            });
                                        }
                                    });
                                });
                            });
                        });
                    });
                });
                $.each($(history).find('.form-control-aquisicao-static'), function() {
                    if ($(this).css("color") === "rgb(169, 68, 66)") {
                        changed = true;
                        $(this).closest('.portlet_ato_processual').find('.tab_2').css("color", "red");
                        $(this).closest('.panel').removeClass('panel-info').addClass('panel-danger');
                    }
                });
                if (changed) {
                    return true;
                } else {
                    return false;
                }
            }

            function checkChangesLabel(label, tab) {
                var changed = false;
                if ($(atual).find(label).length !== $(history).find(label).length) {
                    changed = true;
                    if ($(history).find(label).length === 0) {
                        if (label === ".form-control-redirecionamento-static"){
                            $(history).parent().find(tab).find('.alert-redirecionamento').css("color", "#a94442");
                        } else {
                            $(history).parent().find(tab).find('.alert').css("color", "#a94442");
                        }
           ''         } else if ($(atual).find(label).length === 0) {
                        $(label).parent().parent().css("color", "#a94442");
                    }
                }
                $(history).find(label).parent().parent().css("color", "#a94442");
                $.each($(atual).find(label), function() {
                    var atual = this;
                    var exists = false;
                    $.each($(history).find(label), function() {
                        if ($(atual).text().trim() === $(this).text().trim()) {
                            $(this).parent().parent().css("color", "black");
                            exists = true;
                        }
                    });
                    if (!exists) {
                        changed = true;
                    }
                });
                $.each($(history).find(label), function() {
                    if ($(this).css("color") !== "rgb(0, 0, 0)") {
                        $(this).closest(".panel-default").removeClass("panel-default").addClass("panel-danger");
                        if (label === ".form-control-citacao-static") {
                            $(history).closest('.portlet-body').find('.tab_1').css("color", "red");
                        }
                    }
                });
                if (changed) {
                    return true;
                } else {
                    return false;
                }
            }

            function checkSucessoes(sucessao) {
                var changed = false;

                //Se for PF
                if ($(atual).find('.nestable-list').length === 0 && $(history).find('.nestable-list').length !== 0) {
                    $(history).find('.nestable-list').children('.alert').css("color", "#a94442");
                    $(history).find('.tab-pj-sucessao').css('color', "#a94442");
                    changed = true;
                }
                //Se for PJ com um deles sem sucessão
                if ($(atual).find('.dd-list').length === 0 && $(history).find('.dd-list').length !== 0) {
                    $(history).find(sucessao).children('.dd3-content').css("color", "#a94442");
                    $(history).find('.tab-pj-sucessao').css('color', "#a94442");
                    changed = true;
                } else if ($(history).find('.dd-list').length === 0 && $(atual).find('.dd-list').length !== 0) {
                    $(history).find('.nestable-list').children('.alert').css("color", "#a94442");
                    $(history).find('.tab-pj-sucessao').css('color', "#a94442");
                    changed = true;
                }

                //Comparação de sucessões
                if ($(atual).find(sucessao).length !== $(history).find(sucessao).length) {
                    $(history).find('.tab-pj-sucessao').css('color', "#a94442");
                    changed = true;
                }
                $(history).find(sucessao).children('.dd3-content').css("color", "#a94442");
                $.each($(atual).find(sucessao), function() {
                    var atual = this;
                    var exists = false;
                    $.each($(history).find(sucessao), function() {
                        if ($(this).children('.dd3-content').children('strong').html().trim() === $(atual).children('.dd3-content').children('strong').html().trim()) {
                            exists = true;
                            $(this).children('.dd3-content').css("color", "#333");
                            $(this).children('.dd3-content').hover(function(e) {
                                $(this).css("color", e.type === "mouseenter" ? "#2ea8e5" : "#333");
                            });
                        }
                    });
                    if (!exists) {
                        changed = true;
                    }
                });

                if (changed) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    return {
        init: function() {

            initTable();

            $('select[name=table_length]').change(function() {
                $.each($('.row-details'), function() {
                    if ($(this).hasClass("row-details-open")) {
                        $(this).addClass("row-details-close").removeClass("row-details-open");
                    }
                });
                $('.detailed-info').remove();
            });

            $('#table_filter label input').keypress(function() {
                $.each($('.row-details'), function() {
                    if ($(this).hasClass("row-details-open")) {
                        $(this).addClass("row-details-close").removeClass("row-details-open");
                    }
                });
                $('.detailed-info').remove();
            });

            jsf.ajax.addOnEvent(function(data) {
                if (data.status === 'success') {
                    if ($(data.source).attr('class') === 'info-refresher') {
                        getMoneyMask('#info');
                        if ($('#info').find('#executado').val() === "PJ") {
                            getSucessoes("#pj-id", "#info", element);
                        } else {
                            $(element).addClass("row-details-open").removeClass("row-details-close");
                            $("<tr class='detailed-info'><td class='detail' colspan='6'></td></tr>").insertAfter($(element).parent().parent());
                            $('#info').children().clone().appendTo($(element).parent().parent().next().children());
                            $(element).parent().parent().children(".detail").appendTo($(element).parent().parent().next());
                            initVinculationsTable();
                        }
                        citado();
                    } else if ($(data.source).hasClass("load-history-executado")) {
                        pages = [];
                        $('.modal-history-pjud-executado').click();
                        initHistoryTable();
                        initVinculationsTable();
                    } else if ($(data.source).hasClass("load-history-processo")) {
                        pages = [];
                        $('.modal-history-pjud-processo').click();
                        initHistoryTable();
                        initVinculationsTable();
                    } else if ($(data.source).hasClass("load-history-atoprocessual")) {
                        pages = [];
                        $('.modal-history-pjud-atoprocessual').click();
                        initHistoryTable();
                        initVinculationsTable();
                    }
                }
            });

            $('.menu-pjud').addClass('active open');
            $('.menu-pjud a').append('<span class="selected"></span>');
            $('.menu-pjud a .arrow').addClass('open');
            $('.sub-menu-pjud-con').addClass('active');
        }
    };
}();
