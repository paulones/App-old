var TableEditable = function () {

    var handleTable = function () {


        var table = $('#vinculations');

        var oTable = table.dataTable({
            paginate: false,
            lengthMenu:false,
            info:false,
            filter:false,
            // set the initial value
            "pageLength": 10,

            "language": {
                "emptyTable": "Sem V&iacute;nculos."
            },
            "ordering":false
        });

        var tableWrapper = $("#vinculations_wrapper");

        tableWrapper.find(".dataTables_length select").select2({
            showSearchInput: false //hide search box with special css class
        }); // initialize select2 dropdown


        table.on('click', '.delete', function (e) {
            e.preventDefault();

            var nRow = $(this).parents('tr')[0];
            oTable.fnDeleteRow(nRow);
        });
    }

    return {

        //main function to initiate the module
        init: function () {
            handleTable();
        }

    };

}();