var PJCon = function() {

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
            "ajax": {
                url: "/webresources/reaver/getPessoasJuridicasTable",
                dataType: "json",
                cache: false,
                data: {
                    usuario: $.cookie("usuario")
                }
            },
            "columns": [
                {"data": "row-details"},
                {"data": "nome"},
                {"data": "cnpj"},
                {"data": "nomeFantasia"},
                {"data": "tipoEmpresarial"},
                {"data": "delete"},
                {"data": "detalhes", "class": "display-hide"},
                {"data": "pj", "class": "display-hide"}
            ],
            "orderClasses": false,
            "deferRender": true,
            "order": [],
            "language": {
                "emptyTable": "Nenhuma Pessoa Jur&iacute;dica cadastrada.",
                "zeroRecords": "Nenhuma Pessoa Jur&iacute;dica encontrada.",
                "info": "Exibindo de _START_ a _END_ Pessoas Jur&iacute;dicas. Total de _TOTAL_.",
                "infoEmpty": "Exibindo 0 Pessoas Jur&iacute;dicas.",
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
                    $('#pj-id').val($(this).parent().siblings(":last").text());
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
            $('#pj-id').val($($('.button-delete').get(index)).parent().next().next().text());
            $('.info-delete').click();
        });
    }

    var initHistoryTable = function(table) {
        var tableMain = $(table);
        var tableDetails = $('.vinculations');

        var oTable2 = tableDetails.dataTable({
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
        /*
         * Initialize DataTables, with no sorting on the 'details' column
         */
        var oTable = tableMain.dataTable({
            "columnDefs": [{
                    "orderable": false,
                    "targets": [0, 2, 3]
                }],
            "order": [
            ],
            "columns": [
                {"class": "width-expand"},
                {"class": "width-data"},
                {"class": "width-descricao"},
                {"class": "width-usuario"},
                {},
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
        var tableWrapper = $(table + '_wrapper'); // datatable creates the table wrapper by adding with id {your_table_jd}_wrapper

        tableWrapper.find('.dataTables_length select').select2(); // initialize select2 dropdown

        /* Add event listener for opening and closing details
         * Note that the indicator for showing which row is open is not controlled by DataTables,
         * rather it is done here
         */
        tableMain.on('click', ' tbody td .row-details', function() {
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

    return {
        init: function() {

            if (window.location.search != "") {
                var atual;
                initHistoryTable('#table');
                $.each($('.data-de-modificacao'), function() {
                    if ($(this).html().indexOf('Atual') !== -1) {
                        $(this).parent().find('.form-body').addClass('current');
                        $(this).parent().children('.description').removeClass('description');
                        atual = $(this).parent().find('.form-body');
                    } else {
                        $(this).parent().find('.form-body').addClass('past');
                    }
                });
                $.each($('.past'), function() {
                    var history = this;
                    var description = "";
                    var informacoes = false;
                    var endereco = false;
                    var vinculoAdministrativo = false;
                    var bens = false;
                    var participacao = false;
                    $.each($(this).find('.form-control-static'), function(index) {
                        if ($(atual).find('.form-control-static').eq(index).html().trim() !== $(this).html().trim()) {
                            $(this).parent().parent().css("color", "#a94442");
                            if ($(this).parents('.informacoes-empresariais').length > 0) {
                                informacoes = true;
                            } else if ($(this).parents('.endereco').length > 0) {
                                endereco = true;
                            }
                        }
                    });

                    vinculoAdministrativo = checkChanges('.rows-pfj tr');
                    vinculoAdministrativo = checkChanges('.rows-pjj tr');
                    bens = checkChangesBens('.form-control-bem-static', '.accordion');
                    participacao = checkChanges('.rows-pjj-part tr');

                    function checkChanges(tr) {
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
                            return true;
                        } else {
                            return false;
                        }
                    }

                    function checkChangesBens(label, tab) {
                        var changed = false;
                        if ($(atual).find(label).length !== $(history).find(label).length) {
                            changed = true;
                            if ($(history).find(label).length === 0) {
                                $(history).find('.alert').css("color", "#a94442");
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
                                } else {
                                }
                            });
                            if (!exists) {
                                changed = true;
                            }
                        });
                        $.each($(history).find(label), function() {
                            if ($(this).css("color") !== "rgb(0, 0, 0)") {
                                $(this).closest(".panel-default").removeClass("panel-default").addClass("panel-danger")
                            }
                        });
                        if (changed) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    if (informacoes) {
                        description += "Informa&ccedil;&otilde;es Empresariais, ";
                    }
                    if (endereco) {
                        description += "Endere&ccedil;o, ";
                    }
                    if (vinculoAdministrativo) {
                        description += "V&iacute;nculos Administrativos, "
                    }
                    if (participacao) {
                        description += "Participa&ccedil;&atilde;o em outras Empresas, "
                    }
                    if (bens) {
                        description += "Bens, ";
                    }
                    description = description.substring(0, description.length - 2) + ".";
                    if (description.indexOf(",") !== -1) {
                        description = description.substring(0, description.lastIndexOf(",")) + " e" + description.substring(description.lastIndexOf(",") + 1, description.length);
                    } else if (description === ".") {
                        description = "";
                    }
                    $(this).closest('.detail').parent().children('.description').append(description);
                });
                getMoneyMask(".accordion");
            } else {
                initTable();

                var index;
                $(document).on('click', '.button-delete-sucessao', function(e) {
                    e.stopImmediatePropagation();
                    e.preventDefault();
                    index = $('.button-delete-sucessao').index(this);
                    $('.delete-sucessao-modal-activator').click();
                });

                $('.cancel-sucessao').click(function(e) {
                    e.preventDefault();
                });
                $('.remove-sucessao').click(function(e) {
                    e.preventDefault();
                    $('#pj-sucessao-id').val($($('.button-delete-sucessao').get(index)).parent().parent().children('.suc-id').val());
                    $('.info-delete-sucessao').click();
                });

                $(document).on('click', '.button-history-sucessao', function(e) {
                    e.stopImmediatePropagation();
                    e.preventDefault();
                    if (!executing) {
                        $('#pj-sucessao-id').val($(this).parent().parent().children('.suc-id').val());
                        $('.info-history-sucessao').click();
                    }
                });
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
                if (data.status === 'success') {
                    if ($(data.source).attr('class') === 'info-refresher') {
                        getSucessoes('#pj-id', '#info', $(element), true, true);
                    } else if ($(data.source).attr('class') === 'info-history-sucessao') {
                        $('.show-history-modal-activator').click();
                        var atual;
                        initHistoryTable('#sucessao-table');
                        $.each($('.data-de-modificacao'), function() {
                            if ($(this).html().indexOf('Atual') !== -1) {
                                $(this).parent().find('.form-body').addClass('current');
                                $(this).parent().children('.description').removeClass('description');
                                atual = $(this).parent().find('.form-body');
                            } else {
                                $(this).parent().find('.form-body').addClass('past');
                            }
                        });
                        $.each($('.past'), function() {
                            var description = "";
                            var informacoes = "";
                            $.each($(this).find('.form-control-static'), function(index) {
                                if ($(atual).find('.form-control-static').eq(index).html().trim() !== $(this).html().trim()) {
                                    $(this).parent().parent().css("color", "#a94442");
                                    informacoes = true;
                                }
                            });

                            if (informacoes) {
                                description += "Informa&ccedil;&otilde;es da Sucess&atilde;o, ";
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
                }
            });

            $('.menu-pj').addClass('active open');
            $('.menu-pj a').append('<span class="selected"></span>');
            $('.menu-pj a .arrow').addClass('open');
            $('.sub-menu-pj-con').addClass('active');
        }
    };
}();

