var PJSuc = function() {


    return {
        init: function() {

            $('#tabela').val('PJ');
            
            jsf.ajax.addOnEvent(function(data) {
                if (data.status == "success") {
                    if ($(data.source).hasClass("execute-ajax")) {
                        $('.modal-pj').click();
                    }
                }
            });

            $('.view-pj').click(function() {
                if ($(this).parent().parent().find('#sucedida').val() !== '') {
                    $(this).parent().find('#pj-sucedida').val($(this).parent().parent().find('#sucedida').val());
                    $(this).parent().find('.execute-ajax').click();
                } else {
                    $(this).parent().find('#pj-sucessora').val($(this).parent().parent().find('#sucessora').val());
                    $(this).parent().find('.execute-ajax').click();
                }
            });

            verPessoaJuridica();
            $('.cnpj').change(verPessoaJuridica);
            function verPessoaJuridica() {
                $.each($('.cnpj'), function() {
                    if ($(this).val() !== '') {
                        $(this).closest('.row').find('.btn').show();
                    } else {
                        $(this).closest('.row').find('.btn').hide();
                    }
                })
            }

            $.ajax({
                url: "/webresources/reaver/getPessoasJuridicas",
                dataType: "json",
                cache: false
            })
                    .done(function(data) {
                        $('.cnpj').select2({
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
                                            return (metric.text.toLowerCase().indexOf(term) !== -1);
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

            $('.menu-pj').addClass('active open');
            $('.menu-pj a').append('<span class="selected"></span>');
            $('.menu-pj a .arrow').addClass('open');
            $('.sub-menu-pj-suc').addClass('active');
        }
    };

}();
