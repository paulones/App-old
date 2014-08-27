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
    
    return {
        init: function() {
            
            if (window.location.search != "") {
                
            } else{
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
                    if ($(data.source).attr("class") === "pj-info") {
                        $('.modal-pj').click();
                    } else if ($(data.source).attr("class") === "pf-info") {
                        $('.modal-pf').click();
                    } else if ($(element).hasClass("row-details")) {
                        $(element).addClass("row-details-open").removeClass("row-details-close");
                        $("<tr class='detailed-info'><td class='detail' colspan='6'></td></tr>").insertAfter($(element).parent().parent());
                        $('#info').children().clone().appendTo($(element).parent().parent().next().children());
                        $(element).parent().parent().children(".detail").appendTo($(element).parent().parent().next());
                        $(element).parent().parent().next().find('table').dataTable({
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
                }
            });
            
            $('.menu-pjud').addClass('active open');
            $('.menu-pjud a').append('<span class="selected"></span>');
            $('.menu-pjud a .arrow').addClass('open');
            $('.sub-menu-pjud-con').addClass('active');
        }
    };
}();
