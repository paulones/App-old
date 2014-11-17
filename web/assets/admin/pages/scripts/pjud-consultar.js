var PjudCon = function() {

    var element;

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
        var table = $('#table');
        var tableDetails = $('.vinculations');

        var oTable2 = tableDetails.dataTable({
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
        /*
         * Initialize DataTables, with no sorting on the 'details' column
         */
        var oTable = table.dataTable({
            "columnDefs": [{
                    "orderable": false,
                    "targets": [0, 2, 3]
                }],
            "order": [
            ],
            "orderClasses": false,
            "language": {
                "emptyTable": "Nenhum hist&oacute;rico registrado.",
                "zeroRecords": "Nenhum hist&oacute;rico encontrado.",
                "info": "Exibindo de _START_ a _END_ registros do hist&oacute;rico. Total de _TOTAL_.",
                "infoEmpty": "Exibindo 0 registros do hist&oacute;rico.",
                "infoFiltered": "(filtrando um total de _MAX_ registros).",
                "search": "Procurar:",
                "lengthMenu": " Exibir _MENU_",
            },
            "lengthMenu": [
                [10, 15, 20, 50, -1],
                [10, 15, 20, 50, "Todos"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,
        });
        var tableWrapper = $('#table_wrapper'); // datatable creates the table wrapper by adding with id {your_table_jd}_wrapper

        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown

        /* Add event listener for opening and closing details
         * Note that the indicator for showing which row is open is not controlled by DataTables,
         * rather it is done here
         */
        table.on('click', ' tbody td .row-details', function() {
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
                $(this).parent().parent().children(".detail").appendTo($(this).parent().parent().next());
                $(this).parent().parent().next().children(".detail").show();
            }
        });
    }

    var getHistorico = function() {

        $.each($('.tab2'), function() {
            getMoneyMask($(this));
        });
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
            var processo = false;
            var executado = false;
            var bens = false;
            var atoProcessual = false;

            $.each($(this).find('.form-control-static'), function(index) {
                if ($(atual).find('.form-control-static').eq(index).html().trim() !== $(this).html().trim()) {
                    $(this).parent().parent().css("color", "#a94442");
                    if ($(this).parents('.tab1').length > 0) {
                        executado = true;
                        $(history).find('.tab-pj-info').css("color", "#a94442");
                    } else if ($(this).parents('.tab2').length > 0) {
                        processo = true;
                    } else if ($(this).parents('.tab3').length > 0) {
                        bens = true;
                    } else if ($(this).parents('.tab4').length > 0) {
                        atoProcessual = true;
                    }
                }
            });

            if ($(atual).find('.form-control-executado-static').length !== $(history).find('.form-control-executado-static').length) {
                $(history).find('.form-control-executado-static').parent().parent().css("color", "#a94442");
                $(history).find('td').css("color", "#a94442");
                $(history).find('th').css("color", "#a94442");
                $(history).find('td').children().css("color", "#a94442");
                $(history).find('.tab-pj-info').css("color", "#a94442");
                executado = true;
            } else {
                $.each($(this).find('.form-control-executado-static'), function(index) {
                    if ($(atual).find('.form-control-executado-static').eq(index).html().trim() !== $(this).html().trim()) {
                        $(this).parent().parent().css("color", "#a94442");
                        $(history).find('.tab-pj-info').css("color", "#a94442");
                        executado = true;
                    }

                });
            }

            var checkSucedidasChanges = checkSucessoes('.sucedidas');
            var checkSucessorasChanges = checkSucessoes('.sucessoras');
            var checkPfjChanges = checkChangesTable('.rows-pfj tr');
            var checkPjjChanges = checkChangesTable('.rows-pjj tr');
            var checkBensChanges = checkChangesLabel('.form-control-bem-static', '.tab3');
            var checkProcessoChanges = checkChangesLabel('.form-control-vinculo-static', '.tab2');
            bens = (!bens) ? checkBensChanges : true;
            processo = (!processo) ? checkProcessoChanges : true;
            executado = (!executado) ? checkSucedidasChanges : true;
            executado = (!executado) ? checkSucessorasChanges : true;
            executado = (!executado) ? checkPfjChanges : true;
            executado = (!executado) ? checkPjjChanges : true;

            function checkChangesTable(tr) {
                var changed = false;
                if ($(atual).find(tr).length !== $(history).find(tr).length) {
                    changed = true;
                }
                $.each($(atual).find(tr), function() {
                    var atual = this;
                    var exists = false;
                    $.each($(history).find(tr), function() {
                        $(this).find('td').children().css("color", "#a94442");
                        $(this).find('td').css("color", "#a94442");
                        var cpfAtual = $(atual).find('td').eq(0).children().length > 0 ? $(atual).find('td').eq(0).children().children('span').html().trim() : $(atual).find('td').eq(0).html().trim();
                        var cpfHistorico = $(this).find('td').eq(0).children().length > 0 ? $(this).find('td').eq(0).children().children('span').html().trim() : $(this).find('td').eq(0).html().trim();
                        if (cpfAtual === cpfHistorico) {
                            $(this).find('td').children().css("color", "black");
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

            function checkChangesLabel(label, tab) {
                var changed = false;
                if ($(atual).find(label).length !== $(history).find(label).length) {
                    changed = true;
                    if ($(history).find(label).length === 0) {
                        $(history).find(tab).find('.alert').css("color", "#a94442");
                    } else if ($(atual).find(label).length === 0) {
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

            if (processo) {
                $(this).find('.processo-tab').css("color", "#a94442");
                description += "Processo, ";
            }
            if (executado) {
                $(this).find('.executado-tab').css("color", "#a94442");
                description += "Executado, ";
            }
            if (bens) {
                $(this).find('.bens-tab').css("color", "#a94442");
                description += "Bens, ";
            }
            if (atoProcessual) {
                $(this).find('.atoprocessual-tab').css("color", "#a94442");
                description += "Ato Processual, "
            }
            description = description.substring(0, description.length - 2) + ".";
            if (description.indexOf(",") !== -1) {
                description = description.substring(0, description.lastIndexOf(",")) + " e" + description.substring(description.lastIndexOf(",") + 1, description.length);
            } else if (description === ".") {
                description = "";
            }
            $(this).closest('.detail').parent().children('.description').append(description);
        });
    }

    return {
        init: function() {

            if (window.location.search != "") {
                initHistoryTable();
                if ($('.pj-id').length !== 0) {
                    var lastIndex = $('.pj-id').length - 1;
                    var index = 0;
                    $.each($('.tab1'), function() {
                        var pjId = $(this).find('.pj-id').val();
                        var parent = "#" + $(this).find('.pj-tab').attr('id');
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
                                            getHistorico();
                                        }
                                        index++;
                                    });
                        }
                    });
                } else {
                    getHistorico();
                }
            } else {
                initTable();
            }

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
                switch (data.status) {
                    case "begin":
                        data.source.disabled = true;
                        break;
                    case "complete":
                        data.source.disabled = false;
                        break;
                }
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
