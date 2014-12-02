var Home = function() {

    var anoInteractiveChart = new Date().getFullYear();
    var anoRevenueChart = new Date().getFullYear();

    var initInteractiveChart = function() {

        if (!jQuery.plot) {
            return;
        }
        //Interactive Chart
        interactiveChart();

        function interactiveChart() {
            function randValue() {
                return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
            }
            $("#ano").text(function() {
                return anoInteractiveChart;
            });

            var proc = [];
            var pf = [];
            var pj = [];
            $.ajax({
                url: "/webresources/reaver/getMovimentacao",
                dataType: "json",
                cache: false,
                data: {
                    ano: anoInteractiveChart,
                    usuario: $.cookie("usuario")
                }
            })
                    .done(function(data) {
                        $.each(data, function() {
                            proc = [
                                [1, $(this).attr("pjud1")],
                                [2, $(this).attr("pjud2")],
                                [3, $(this).attr("pjud3")],
                                [4, $(this).attr("pjud4")],
                                [5, $(this).attr("pjud5")],
                                [6, $(this).attr("pjud6")],
                                [7, $(this).attr("pjud7")],
                                [8, $(this).attr("pjud8")],
                                [9, $(this).attr("pjud9")],
                                [10, $(this).attr("pjud10")],
                                [11, $(this).attr("pjud11")],
                                [12, $(this).attr("pjud12")]
                            ];
                            pf = [
                                [1, $(this).attr("pf1")],
                                [2, $(this).attr("pf2")],
                                [3, $(this).attr("pf3")],
                                [4, $(this).attr("pf4")],
                                [5, $(this).attr("pf5")],
                                [6, $(this).attr("pf6")],
                                [7, $(this).attr("pf7")],
                                [8, $(this).attr("pf8")],
                                [9, $(this).attr("pf9")],
                                [10, $(this).attr("pf10")],
                                [11, $(this).attr("pf11")],
                                [12, $(this).attr("pf12")]
                            ];
                            pj = [
                                [1, $(this).attr("pj1")],
                                [2, $(this).attr("pj2")],
                                [3, $(this).attr("pj3")],
                                [4, $(this).attr("pj4")],
                                [5, $(this).attr("pj5")],
                                [6, $(this).attr("pj6")],
                                [7, $(this).attr("pj7")],
                                [8, $(this).attr("pj8")],
                                [9, $(this).attr("pj9")],
                                [10, $(this).attr("pj10")],
                                [11, $(this).attr("pj11")],
                                [12, $(this).attr("pj12")]
                            ];
                        });

                        var plot = $.plot($("#interactive_chart"), [{
                                data: proc,
                                label: "Processos",
                                lines: {
                                    lineWidth: 1,
                                },
                                shadowSize: 0

                            }, {
                                data: pf,
                                label: "P. F&iacute;sicas",
                                lines: {
                                    lineWidth: 1,
                                },
                                shadowSize: 0
                            }, {
                                data: pj,
                                label: "P. Jur&iacute;dicas",
                                lines: {
                                    lineWidth: 1,
                                },
                                shadowSize: 0
                            }
                        ], {
                            series: {
                                lines: {
                                    show: true,
                                    lineWidth: 2,
                                    fill: true,
                                    fillColor: {
                                        colors: [{
                                                opacity: 0.05
                                            }, {
                                                opacity: 0.01
                                            }
                                        ]
                                    }
                                },
                                points: {
                                    show: true,
                                    radius: 3,
                                    lineWidth: 1
                                },
                                shadowSize: 2
                            },
                            grid: {
                                hoverable: true,
                                clickable: true,
                                tickColor: "#eee",
                                borderColor: "#eee",
                                borderWidth: 1
                            },
                            colors: ["#d12610", "#37b7f3", "#52e136"],
                            xaxis: {
                                ticks: 11,
                                tickDecimals: 0,
                                tickColor: "#eee",
                            },
                            yaxis: {
                                ticks: 11,
                                tickDecimals: 0,
                                tickColor: "#eee",
                            }
                        });

                        var previousPoint = null;
                        $("#interactive_chart").bind("plothover", function(event, pos, item) {
                            $("#x").text(pos.x.toFixed(2));
                            $("#y").text(pos.y.toFixed(2));

                            if (item) {
                                if (previousPoint != item.dataIndex) {
                                    previousPoint = item.dataIndex;

                                    $("#tooltip").remove();
                                    var x = item.datapoint[0].toFixed(2),
                                            y = item.datapoint[1].toFixed(2);

                                    showTooltip(item.pageX, item.pageY, item.series.label + " = " + y);
                                }
                            } else {
                                $("#tooltip").remove();
                                previousPoint = null;
                            }
                        });
                        function showTooltip(x, y, contents) {
                            $('<div id="tooltip">' + contents + '</div>').css({
                                position: 'absolute',
                                display: 'none',
                                top: y + 5,
                                left: x + 15,
                                border: '1px solid #333',
                                padding: '4px',
                                color: '#fff',
                                'border-radius': '3px',
                                'background-color': '#333',
                                opacity: 0.80
                            }).appendTo("body").fadeIn(200);
                        }
                    });
        }
    };

    var initPizzaChart = function() {
        var data = [];

        $.ajax({
            url: "/webresources/reaver/getPizza",
            dataType: "json",
            cache: false,
            data: {
                usuario: $.cookie("usuario")
            }
        })
                .done(function(dado) {
                    $.each(dado, function() {

                        data[0] = {
                            label: "Andamento",
                            data: $(this).attr("Andamento")
                        },
                        data[1] = {
                            label: "Arquivado",
                            data: $(this).attr("Arquivado")
                        },
                        data[2] = {
                            label: "Extinto",
                            data: $(this).attr("Extinto")
                        },
                        data[3] = {
                            label: "Julgado",
                            data: $(this).attr("Julgado")
                        },
                        data[4] = {
                            label: "Suspenso",
                            data: $(this).attr("Suspenso")
                        };
                        
                        // GRAPH 2
                        $.plot($("#pizza_chart"), data, {
                            series: {
                                pie: {
                                    show: true,
                                    radius: 3 / 4,
                                    label: {
                                        show: true,
                                        radius: 3 / 4,
                                        formatter: function(label, series) {
                                            return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">' + label + '<br/>' + Math.round(series.percent) + '%</div>';
                                        },
                                        background: {
                                            opacity: 0.5,
                                            color: '#000'
                                        }
                                    }
                                }
                            },
                            legend: {
                                show: false
                            }
                        });
                    });
                });

    }

    var initRevenueChart = function() {
        if (!jQuery.plot) {
            return;
        }

        var revenue = [];
        $.ajax({
            url: "/webresources/reaver/getArrecadacao",
            dataType: "json",
            cache: false,
            data: {
                ano: anoRevenueChart,
                usuario: $.cookie("usuario")
            }
        })
                .done(function(data) {
                    $.each(data, function() {
                        if ($('#site_activities').size() != 0) {
                            //site activities
                            var previousPoint2 = null;
                            $('#site_activities_loading').hide();
                            $('#site_activities_content').show();

                            $("#anoRevenue").text(function() {
                                return anoRevenueChart;
                            });

                            var data = [
                                ['JAN', $(this).attr("arrecadacao1")],
                                ['FEV', $(this).attr("arrecadacao2")],
                                ['MAR', $(this).attr("arrecadacao3")],
                                ['ABR', $(this).attr("arrecadacao4")],
                                ['MAI', $(this).attr("arrecadacao5")],
                                ['JUN', $(this).attr("arrecadacao6")],
                                ['JUL', $(this).attr("arrecadacao7")],
                                ['AGO', $(this).attr("arrecadacao8")],
                                ['SET', $(this).attr("arrecadacao9")],
                                ['OUT', $(this).attr("arrecadacao10")],
                                ['NOV', $(this).attr("arrecadacao11")],
                                ['DEZ', $(this).attr("arrecadacao12")]
                            ];
                            
                            $("#previsao").text($(this).attr("value12"));
                            $("#arrecadacao").text($(this).attr("arrecadacao12"));
                            getMoneyMask(".revenue");
                            $("#processosCount").text($(this).attr("count"));

                            var plot_statistics = $.plot($("#site_activities"),
                                    [{
                                            data: data,
                                            lines: {
                                                fill: 0.2,
                                                lineWidth: 0,
                                            },
                                            color: ['#BAD9F5']
                                        }, {
                                            data: data,
                                            points: {
                                                show: true,
                                                fill: true,
                                                radius: 4,
                                                fillColor: "#9ACAE6",
                                                lineWidth: 2
                                            },
                                            color: '#9ACAE6',
                                            shadowSize: 1
                                        }, {
                                            data: data,
                                            lines: {
                                                show: true,
                                                fill: false,
                                                lineWidth: 3
                                            },
                                            color: '#9ACAE6',
                                            shadowSize: 0
                                        }],
                                    {
                                        xaxis: {
                                            tickLength: 0,
                                            tickDecimals: 0,
                                            mode: "categories",
                                            min: 0,
                                            font: {
                                                lineHeight: 18,
                                                style: "normal",
                                                variant: "small-caps",
                                                color: "#6F7B8A"
                                            }
                                        },
                                        yaxis: {
                                            ticks: 5,
                                            tickDecimals: 0,
                                            tickColor: "#eee",
                                            font: {
                                                lineHeight: 14,
                                                style: "normal",
                                                variant: "small-caps",
                                                color: "#6F7B8A"
                                            }
                                        },
                                        grid: {
                                            hoverable: true,
                                            clickable: true,
                                            tickColor: "#eee",
                                            borderColor: "#eee",
                                            borderWidth: 1
                                        }
                                    });

                            $("#site_activities").bind("plothover", function(event, pos, item) {
                                $("#x").text(pos.x.toFixed(2));
                                $("#y").text(pos.y.toFixed(2));
                                if (item) {
                                    if (previousPoint2 !== item.dataIndex) {
                                        previousPoint2 = item.dataIndex;
                                        $("#tooltip").remove();
                                        var x = item.datapoint[0].toFixed(2),
                                                y = item.datapoint[1].toFixed(2);
                                        showChartTooltip(item.pageX, item.pageY, item.datapoint[0], 'R$ '+item.datapoint[1]);
                                    }
                                }
                            });

                            $('#site_activities').bind("mouseleave", function() {
                                $("#tooltip").remove();
                            });
                        }
                    });
                });

        function showChartTooltip(x, y, xValue, yValue) {
            $('<div id="tooltip" class="chart-tooltip">' + yValue + '<\/div>').css({
                position: 'absolute',
                display: 'none',
                top: y - 40,
                left: x - 40,
                border: '0px solid #ccc',
                padding: '2px 6px',
                'background-color': '#fff'
            }).appendTo("body").fadeIn(200);
        }
    }

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
                    indice: indice,
                    usuario: $.cookie("usuario")
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
                                var tabela = $(this).attr("tabela");
                                var operacao = $(this).attr("operacao") === "U" ? "warning" : $(this).attr("operacao") === "C" ? "success" : $(this).attr("operacao") === "D" ? "danger" : "default";
                                html += "<li class='" + tabela.toLowerCase() + "'><div class='col1'><div class='cont'><div class='cont-col1'><div class='label label-sm label-" + operacao + "'>";
                                html += (tabela === "PF") ? "<i class='fa fa-user'></i>" : (tabela === "PJ") ? "<i class='fa fa-institution'></i>" : (tabela === "PJS") ? "<i class='fa fa-exchange'></i>" : "<i class='fa fa-legal'></i>";
                                html += "</div></div><div class='cont-col2'><div class='desc'>" + $(this).attr("mensagem") + "</div></div></div><a href='javascript:;' class='cont-search " + tabela.toLowerCase() + "-info'>";
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

            $('.interactive-back-reload').click(function() {
                $("#ano").text(function() {
                    anoInteractiveChart--;
                    return anoInteractiveChart;
                });
                initInteractiveChart();
            });
            $('.interactive-next-reload').click(function() {
                if (anoInteractiveChart + 1 <= new Date().getFullYear()) {
                    $("#ano").text(function() {
                        anoInteractiveChart++;
                        return anoInteractiveChart;
                    });
                    initInteractiveChart();
                }
            });
            $('.revenue-back-reload').click(function() {
                $("#anoRevenue").text(function() {
                    anoRevenueChart--;
                    return anoRevenueChart;
                });
                initRevenueChart();
            });
            $('.revenue-next-reload').click(function() {
                if (anoRevenueChart + 1 <= new Date().getFullYear()) {
                    $("#anoRevenue").text(function() {
                        anoRevenueChart++;
                        return anoRevenueChart;
                    });
                    initRevenueChart();
                }
            });

            Metronic.addResizeHandler(function() {
                initPizzaCharts();
            });
            initInteractiveChart();
            initPizzaChart();
            initRevenueChart();
        },
    };
}();
