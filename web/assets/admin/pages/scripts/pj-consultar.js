var PJCon = function() {
    
    var initTable = function() {
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
                    "targets": [0, 5]
                }],
            "order": [
                [1, 'asc']
            ],
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
                [1, 'desc']
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
                var atual;
                initHistoryTable();
                $.each($('.data-de-modificacao'), function() {
                    if ($(this).html().contains('Atual')) {
                        $(this).parent().find('.form-body').addClass('current');
                        $(this).parent().children('.description').removeClass('description');
                        atual = $(this).parent().find('.form-body');
                    } else {
                        $(this).parent().find('.form-body').addClass('past');
                    }
                });
                $.each($('.past'), function() {
                    var description = "";
                    var informacoes = false;
                    var endereco = false;
                    var vinculo = false;
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
                    var upper = this;
                    $.each($(atual).find('.rows tr'), function() {
                        var atual = this;
                        $.each($(upper).find('.rows tr'), function() {
                            if ($(atual).find('td').eq(0).html().trim() === $(this).find('td').eq(0).html().trim()) {
                                $.each($(this).find('td'), function(index) {
                                    if ($(atual).find('td').eq(index).html().trim() !== $(this).html().trim()) {
                                        $(this).css("color", "#a94442");
                                        $(this).parent().parent().parent().children('thead').children().children('th').eq(index).css('color', '#a94442');
                                        vinculo = true;
                                    }
                                })
                            } else {
                                if (($(atual).find('td').length > 1 && $(this).find('td').length === 1)) {
                                    $(this).find('td').eq(0).css("color","#a94442");
                                    vinculo = true;
                                }
                                if (($(this).find('td').length > 1 && $(atual).find('td').length === 1)){
                                    $(this).find('td').css("color","#a94442");
                                    vinculo = true;
                                }
                            }
                        });
                    });
                    if (informacoes) {
                        description += "Informa&ccedil;&otilde;es Empresariais, ";
                    }
                    if (endereco) {
                        description += "Endere&ccedil;o, ";
                    }
                    if (vinculo) {
                        description += "V&iacute;nculos Administrativos, "
                    }
                    description = description.substring(0, description.length - 2) + ".";
                    if (description.contains(",")) {
                        description = description.substring(0, description.lastIndexOf(",")) + " e" + description.substring(description.lastIndexOf(",") + 1, description.length);
                    } else if (description === ".") {
                        description = "";
                    }
                    $(this).closest('.detail').parent().children('.description').append(description);
                });
            } else {
                initTable();

                var index;
                $('.button-delete').click(function(e) {
                    e.preventDefault();
                    index = $('.button-delete').index(this);
                    $('.delete-modal-activator').click();
                });
                $('.cancel').click(function(e) {
                    e.preventDefault();
                });
                $('.remove').click(function(e) {
                    e.preventDefault();
                    $('.delete').get(index).click();
                });
            }

            $('#table_filter label input').keypress(function() {
                $.each($('.row-details'), function() {
                    if ($(this).hasClass("row-details-open")) {
                        $(this).addClass("row-details-close").removeClass("row-details-open");
                        $(this).parent().parent().append($(this).parent().parent().next().children());
                        $(this).parent().parent().next().remove();
                        $(this).parent().parent().children(".detail").hide();
                    }
                });
            });
            
            jsf.ajax.addOnEvent(function(data) {
                if (data.status === 'success') {
                    if ($(data.source).attr("class") === "pf-info"){
                        $('.modal-pf').click();
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

