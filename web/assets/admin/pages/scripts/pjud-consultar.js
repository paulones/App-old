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
                $('#pjud-id').val($(this).parent().siblings(":last").text());
                $('.info-refresher').click();
                element = $(this);
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

    return {
        init: function() {

            if (window.location.search != "") {
                getMoneyMask(".tab1");
                var atual;
                initHistoryTable();
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
                                processo = true;
                            } else if ($(this).parents('.tab2').length > 0) {
                                executado = true;
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
                        executado = true;
                    } else {
                        $.each($(this).find('.form-control-executado-static'), function(index) {
                            if ($(atual).find('.form-control-executado-static').eq(index).html().trim() !== $(this).html().trim()) {
                                $(this).parent().parent().css("color", "#a94442");
                                executado = true;
                            }
                        });
                    }
                    var checkPfjChanges = checkChangesTable('.rows-pfj tr');
                    var checkPjjChanges = checkChangesTable('.rows-pjj tr');
                    var checkBensChanges = checkChangesLabel('.form-control-bem-static', '.tab3');
                    var checkProcessoChanges = checkChangesLabel('.form-control-vinculo-static', '.tab1');
                    bens = (!bens) ? checkBensChanges : true;
                    processo = (!processo) ? checkProcessoChanges : true;
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
                            return true;
                        } else {
                            return false;
                        }
                    }
                    ;

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
                if (data.status === 'success') {
                    if ($(data.source).attr('class') === 'info-refresher') {
                        getMoneyMask('#info');
                        if ($('#executado').val() === "PJ") {
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
