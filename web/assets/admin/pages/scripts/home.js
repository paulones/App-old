var Home = function() {

    var initInteractiveChart = function() {

        if (!jQuery.plot) {
            return;
        }

        //Interactive Chart

        function interactiveChart() {
            function randValue() {
                return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
            }
            var proc = [
                [1, randValue()],
                [2, randValue()],
                [3, 2 + randValue()],
                [4, 3 + randValue()],
                [5, 5 + randValue()],
                [6, 10 + randValue()],
                [7, 15 + randValue()],
                [8, 20 + randValue()],
                [9, 25 + randValue()],
                [10, 30 + randValue()],
                [11, 35 + randValue()],
                [12, 25 + randValue()],
                [13, 15 + randValue()],
                [14, 20 + randValue()],
                [15, 45 + randValue()],
                [16, 50 + randValue()],
                [17, 65 + randValue()],
                [18, 70 + randValue()],
                [19, 85 + randValue()],
                [20, 80 + randValue()],
                [21, 75 + randValue()],
                [22, 80 + randValue()],
                [23, 75 + randValue()],
                [24, 70 + randValue()],
                [25, 65 + randValue()],
                [26, 75 + randValue()],
                [27, 80 + randValue()],
                [28, 85 + randValue()],
                [29, 90 + randValue()],
                [30, 95 + randValue()]
            ];
            var pf = [
                [1, randValue() - 8],
                [2, randValue() - 7],
                [3, randValue() - 9],
                [4, 3 + randValue()],
                [5, 7 + randValue()],
                [6, 24 + randValue()],
                [7, 26 + randValue()],
                [8, 38 + randValue()],
                [9, 24 + randValue()],
                [10, 36 + randValue()],
                [11, 43 + randValue()],
                [12, 53 + randValue()],
                [13, 50 + randValue()],
                [14, 12 + randValue()],
                [15, 14 + randValue()],
                [16, 16 + randValue()],
                [17, 16 + randValue()],
                [18, 13 + randValue()],
                [19, 14 + randValue()],
                [20, 18 + randValue()],
                [21, 10 + randValue()],
                [22, 12 + randValue()],
                [23, 15 + randValue()],
                [24, 26 + randValue()],
                [25, 15 + randValue()],
                [26, 25 + randValue()],
                [27, 27 + randValue()],
                [28, 29 + randValue()],
                [29, 23 + randValue()],
                [30, 32 + randValue()]
            ];
            var pj = [
                [1, randValue() - 5],
                [2, randValue() - 5],
                [3, randValue() - 5],
                [4, 6 + randValue()],
                [5, 5 + randValue()],
                [6, 20 + randValue()],
                [7, 25 + randValue()],
                [8, 36 + randValue()],
                [9, 26 + randValue()],
                [10, 38 + randValue()],
                [11, 39 + randValue()],
                [12, 50 + randValue()],
                [13, 51 + randValue()],
                [14, 12 + randValue()],
                [15, 13 + randValue()],
                [16, 14 + randValue()],
                [17, 15 + randValue()],
                [18, 15 + randValue()],
                [19, 16 + randValue()],
                [20, 17 + randValue()],
                [21, 18 + randValue()],
                [22, 19 + randValue()],
                [23, 20 + randValue()],
                [24, 21 + randValue()],
                [25, 14 + randValue()],
                [26, 24 + randValue()],
                [27, 25 + randValue()],
                [28, 26 + randValue()],
                [29, 27 + randValue()],
                [30, 31 + randValue()]
            ];

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

                        showTooltip(item.pageX, item.pageY, item.series.label + " of " + x + " = " + y);
                    }
                } else {
                    $("#tooltip").remove();
                    previousPoint = null;
                }
            });
        }

        //graph
        interactiveChart();

    }

    var initPizzaChart = function() {
        var data = [];
        var series = Math.floor(Math.random() * 10) + 1;
        series = series < 5 ? 5 : series;

        for (var i = 0; i < series; i++) {
            data[i] = {
                label: "Series" + (i + 1),
                data: Math.floor(Math.random() * 100) + 1
            }
        }

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
    }

    var initRevenueChart = function() {
        if (!jQuery.plot) {
            return;
        }

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

        if ($('#site_activities').size() != 0) {
            //site activities
            var previousPoint2 = null;
            $('#site_activities_loading').hide();
            $('#site_activities_content').show();

            var data = [
                ['JAN', 600],
                ['FEV', 1100],
                ['MAR', 1200],
                ['ABR', 860],
                ['MAI', 1200],
                ['JUN', 1450],
                ['JUL', 1800],
                ['AGO', 1200],
                ['SET', 600],
                ['OUT', 900],
                ['NOV', 700],
                ['DEZ', 300],
            ];


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
                    if (previousPoint2 != item.dataIndex) {
                        previousPoint2 = item.dataIndex;
                        $("#tooltip").remove();
                        var x = item.datapoint[0].toFixed(2),
                                y = item.datapoint[1].toFixed(2);
                        showChartTooltip(item.pageX, item.pageY, item.datapoint[0], item.datapoint[1] + 'M$');
                    }
                }
            });

            $('#site_activities').bind("mouseleave", function() {
                $("#tooltip").remove();
            });
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

            Metronic.addResizeHandler(function() {
                initPizzaCharts();
            });
            initInteractiveChart();
            initPizzaChart();
            initRevenueChart();
        },
    };
}();
