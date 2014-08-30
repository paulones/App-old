var Home = function() {

    return {
        init: function() {
            $('.menu-home').addClass('active');
            $('.menu-home a').append('<span class="selected"></span>');

            var quantidade = 200;
            var indice = 0;
            $.ajax({
                url: "/webresources/reaver/getLogs",
                dataType: "json",
                data: {
                    quantidade: quantidade,
                    indice: indice
                },
                cache: false
            })
                    .done(function(data) {
                        var html = "";
                        $.each(data, function() {
                            var operacao = $(this).attr("operacao") === "U" ? "warning" : $(this).attr("operacao") === "C" ? "success" : $(this).attr("operacao") === "D" ? "danger" : "default";
                            html += "<li class='" + $(this).attr("tabela") + "'><div class='col1'><div class='cont'><div class='cont-col1'><div class='label label-sm label-" + operacao + "'>";
                            html += ($(this).attr("tabela") === "PF") ? "<i class='fa fa-user'></i>" : ($(this).attr("tabela") === "PJ") ? "<i class='fa fa-building'></i>" : "<i class='fa fa-legal'></i>";
                            html += "</div></div><div class='cont-col2'><div class='desc'>" + $(this).attr("mensagem") + "</div></div></div><a href='javascript:;' class='cont-search show-feed'><div class='cont-col1'><div class='label label-sm label-default'><i class='fa fa-search'></i><input type='hidden' class='idfk' value='" + $(this).attr("idfk") + "'></input></div></div></a></div><div class='col2'><time class='date timeago' datetime='" + $(this).attr("data") + "'></time></div></a></li>";
                        });
                        $(".feeds").append(html);
                        $("time.timeago").timeago();
                    });
            $('input[name=filter]').click(function() {
                $('input[name=filter]').each(function() {
                    if ($(this).prop('checked') == false) {
                        $('.' + $(this).val()).hide();
                    } else {
                        $('.' + $(this).val()).show();
                    }
                });
            });

            var search;
            $(document).on('click', '.show-feed', function() {
                $('#idfk').val($(this).find('.idfk').val());
                $('#tabela').val($(this).parent().parent().attr("class"));
                $('.info-refresher').click();
                search = this;
            });

            jsf.ajax.addOnEvent(function(data) {
                if (data.status === 'success') {
                    if ($(data.source).attr("class") === "info-refresher") {
                        $('.modal-' + $(search).parent().parent().attr("class").toLowerCase()).click();
                        $('.vinculations').dataTable({
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
        }
    };
}();