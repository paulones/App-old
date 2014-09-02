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
                        if (data.length == 0) {
                            html += "<li><div class='col1'><div class='cont'><div class='cont-col1'><div class='label label-sm bg-red'><i class='fa fa-exclamation-circle'></i>";
                            html += "</div></div><div class='cont-col2'><div class='desc'>Nenhuma atividade realizada</div></div></div></div></div></div></li>";
                        } else {
                            $.each(data, function() {
                                var operacao = $(this).attr("operacao") === "U" ? "warning" : $(this).attr("operacao") === "C" ? "success" : $(this).attr("operacao") === "D" ? "danger" : "default";
                                html += "<li class='" + $(this).attr("tabela") + "'><div class='col1'><div class='cont'><div class='cont-col1'><div class='label label-sm label-" + operacao + "'>";
                                html += ($(this).attr("tabela") === "PF") ? "<i class='fa fa-user'></i>" : ($(this).attr("tabela") === "PJ") ? "<i class='fa fa-institution'></i>" : "<i class='fa fa-legal'></i>";
                                html += "</div></div><div class='cont-col2'><div class='desc'>" + $(this).attr("mensagem") + "</div></div></div><a href='javascript:;' class='cont-search show-feed'>";
                                html += "<div class='cont-col1'><div class='label label-sm label-default'><i class='fa fa-search'></i><input type='hidden' class='object-id' value='" + $(this).attr("idfk") + "'> Visualizar</input>";
                                html += "</div></div></a></div><div class='col2'><time class='date timeago' datetime='" + $(this).attr("data") + "'></time></div></a></li>";
                            });
                        }
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
        }
    };
}();