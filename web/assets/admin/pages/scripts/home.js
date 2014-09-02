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
                            html += "</div></div><div class='cont-col2'><div class='desc'>" + $(this).attr("mensagem") + "</div></div></div><a href='javascript:;' class='cont-search show-feed'><div class='cont-col1'><div class='label label-sm label-default'><i class='fa fa-search'></i><input type='hidden' class='idfk' value='" + $(this).attr("idfk") + "'> Visualizar</input></div></div></a></div><div class='col2'><time class='date timeago' datetime='" + $(this).attr("data") + "'></time></div></a></li>";
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
        },
        
        //Tracking Curves

        initCharts: function() {
                //tracking curves:

                var sin = [],
                    cos = [];
                for (var i = 0; i < 14; i += 0.1) {
                    sin.push([i, Math.sin(i)]);
                    cos.push([i, Math.cos(i)]);
                }

                plot = $.plot($("#chart_3"), [{
                            data: sin,
                            label: "sin(x) = -0.00",
                            lines: {
                                lineWidth: 1,
                            },
                            shadowSize: 0
                        }, {
                            data: cos,
                            label: "cos(x) = -0.00",
                            lines: {
                                lineWidth: 1,
                            },
                            shadowSize: 0
                        }
                    ], {
                        series: {
                            lines: {
                                show: true
                            }
                        },
                        crosshair: {
                            mode: "x"
                        },
                        grid: {
                            hoverable: true,
                            autoHighlight: false,
                            tickColor: "#eee",
                            borderColor: "#eee",
                            borderWidth: 1
                        },
                        yaxis: {
                            min: -1.2,
                            max: 1.2
                        }
                    });

                var legends = $("#chart_3 .legendLabel");
                legends.each(function () {
                    // fix the widths so they don't jump around
                    $(this).css('width', $(this).width());
                });

                var updateLegendTimeout = null;
                var latestPosition = null;

                function updateLegend() {
                    updateLegendTimeout = null;

                    var pos = latestPosition;

                    var axes = plot.getAxes();
                    if (pos.x < axes.xaxis.min || pos.x > axes.xaxis.max || pos.y < axes.yaxis.min || pos.y > axes.yaxis.max) return;

                    var i, j, dataset = plot.getData();
                    for (i = 0; i < dataset.length; ++i) {
                        var series = dataset[i];

                        // find the nearest points, x-wise
                        for (j = 0; j < series.data.length; ++j)
                            if (series.data[j][0] > pos.x) break;

                            // now interpolate
                        var y, p1 = series.data[j - 1],
                            p2 = series.data[j];
                        if (p1 == null) y = p2[1];
                        else if (p2 == null) y = p1[1];
                        else y = p1[1] + (p2[1] - p1[1]) * (pos.x - p1[0]) / (p2[0] - p1[0]);

                        legends.eq(i).text(series.label.replace(/=.*/, "= " + y.toFixed(2)));
                    }
                }

                $("#chart_3").bind("plothover", function (event, pos, item) {
                    latestPosition = pos;
                    if (!updateLegendTimeout) updateLegendTimeout = setTimeout(updateLegend, 50);
                });
            }
        
    };
}();