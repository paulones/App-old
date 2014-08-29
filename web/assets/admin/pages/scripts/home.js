var Home = function() {

    return {
        init: function() {
            $('.menu-home').addClass('active');
            $('.menu-home a').append('<span class="selected"></span>');
            
            $.ajax({
                url: "/webresources/reaver/getLogs",
                dataType: "json",
                data: {
                    quantidade: 200,
                    indice: 0
                },
                cache: false
            })
                    .done(function(data) {
                        alert(data);
            })
        }
    };
}();