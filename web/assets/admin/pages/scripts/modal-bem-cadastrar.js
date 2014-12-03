var ModalBEMCad = function() {

    var checkBemDates = function() {
        var date_error = ".modal_bem_date-bem-error";
        if ($(this).val().length == 10) {
            var result;
            var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
            $.each($('.modal_bem_aquisicao-date'), function() {
                var initialdate = $(this).val();
                var finaldate = $(this).closest('.row').find('.modal_bem_extincao-date').val();
                if (initialdate.match(reg) && finaldate.match(reg)) {
                    if (finaldate != "" && initialdate != "") {
                        var final = finaldate.split("/")[2] + "-" + finaldate.split("/")[1] + "-" + finaldate.split("/")[0];
                        var initial = initialdate.split("/")[2] + "-" + initialdate.split("/")[1] + "-" + initialdate.split("/")[0];
                        if (final < initial) {
                            $(this).closest('.row').find('.modal_bem_extincao-date').val("");
                            $(date_error).html("Digite uma data de aquisi&ccedil;&atilde;o inferior &agrave; data de transfer&ecirc;ncia / extin&ccedil;&atilde;o.");
                            $(date_error).show();
                            return result = false;
                        } else {
                            $(date_error).hide();
                            return result = true;
                        }
                    } else if (initialdate != "") {
                        $(date_error).hide();
                        return result = true;
                    }
                } else if (initialdate != "" && !initialdate.match(reg)) {
                    $(this).val("");
                    $(date_error).html("Digite uma data de aquisi&ccedil;&atilde;o v&aacute;lida.");
                    $(date_error).show();
                    return result = false;
                } else if (finaldate != "" && !finaldate.match(reg)) {
                    $(this).closest('.row').find('.modal_bem_extincao-date').val("");
                    $(date_error).html("Digite uma data de transfer&ecirc;ncia / extin&ccedil;&atilde;o v&aacute;lida.");
                    $(date_error).show();
                    return result = false;
                } else {
                    $(date_error).hide();
                    return result = true;
                }
            });
            return result;
        }
    }

    $(document).on("click", ".modal_bem_submit", function(e) {
        e.preventDefault();
        $(".modal_bem_register").click();
    });

    return {
        init: function() {

            $("#modal_bem_tipobem").select2({allowClear: true});
            $('.modal_bem_date').mask("99/99/9999");
            maskMoney();

            function maskMoney() {
                $('.money').maskMoney({
                    prefix: 'R$ ',
                    symbol: 'R$', // Simbolo
                    decimal: ',', // Separador do decimal
                    precision: 2, // Precisão
                    thousands: '.', // Separador para os milhares
                    allowZero: false, // Permite que o digito 0 seja o primeiro caractere
                    showSymbol: true // Exibe/Oculta o símbolo
                });
                $.each($('.money'), function() {
                    if ($(this).val() !== "") {
                        $(this).maskMoney('mask');
                    }
                });
            }

            $('.modal_bem_aquisicao-date,.modal_bem_extincao-date').keyup(checkBemDates);

        }
    };
}();
