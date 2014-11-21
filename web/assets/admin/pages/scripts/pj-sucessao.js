var PJSuc = function() {

    var handleValidation = function() {
        var form = $('#form');

        form.validate({
            doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                data: {
                    date: true,
                }
            },
            messages: {// custom messages for radio buttons and checkboxes
            },
            errorPlacement: function(error, element) {
                var icon = $(element).parent('.input-icon').children('i');
                icon.removeClass('fa-check').addClass("fa-warning");
                icon.attr("data-original-title", error.text()).tooltip({'container': 'body'}); // for other inputs, just perform default behavior
            },
            invalidHandler: function(event, validator) { //display error alert on form submit
                $('.form-error').show();
            },
            highlight: function(element) { // hightlight error inputs
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function(element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            success: function(label, element) {
                var icon = $(element).parent('.input-icon').children('i');
                $(element).closest('.form-group').removeClass('has-error'); // set success class to the control group
                icon.removeClass("fa-warning");
                icon.removeAttr("data-original-title");
            },
            submitHandler: function(form) {
                $('.form-error').hide();
            }

        });
    }

    $('.submit-sucessao').click(function(e) {
        if ($('#form').validate().form()) {
            $(".suceder").click();
        }
        return false;
    });

    return {
        init: function() {

            $('.date').mask("99/99/9999");
            $.validator.methods["date"] = function validaData(value, element) {
                var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
                if (value === "" || value === null) {
                    return true;
                }
                return value.match(reg) ? true : false;
            }
            handleValidation();
            verPessoaJuridica();
            $('.cnpjVinculate').change(verPessoaJuridica);
            function verPessoaJuridica() {
                var empty = $('.cnpjVinculate').filter(function() {
                    return this.value === "";
                });
                empty.length ? $('.submit-sucessao').hide() : $('.submit-sucessao').show();
                $.each($('.cnpjVinculate'), function() {
                    if ($(this).val() !== '') {
                        $(this).closest('.row').find('.pj-info').show();
                        $(this).closest('.row').find('.pj-info').children('.object-id').val($(this).val());
                    } else {
                        $(this).closest('.row').find('.pj-info').hide();
                    }
                })
            }

            $.ajax({
                url: "/webresources/reaver/getPessoasJuridicas",
                dataType: "json",
                cache: false
            })
                    .done(function(data) {
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
