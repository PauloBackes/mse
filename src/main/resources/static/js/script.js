$(document).ready(function () {

    $(window).load(function () {
        var mensagem = $("#message").text();
        if (mensagem !== "") {
            $("#modal-success").modal('toggle')
        }

        removerEquipamento();
    });
})

const URL_BASE = window.location.origin + "/mse";

function removerEquipamento() {
    $(".remover-equipamento").click(function () {
        var idCirurgia = buscarParametroUrl('id');
        var codigoEquipamento = $(this).parents()[1].cells[0].firstChild.getAttribute("value");
        console.log(idCirurgia);
        modalRemoverItem(codigoEquipamento);

    })
}

function modalRemoverItem(codigoItem) {
    $("#modal-remover-item").modal('toggle');

    $("#botao-remover-item").click(function () {
        var dados = {
            "codigoItem": codigoItem,
            "idCirurgia": buscarParametroUrl('id'),
            "tipoItem": "Equipamento"
        };
        $.post(URL_BASE + "/remover/item", dados, function (data) {
            location.reload();
        }).fail(function (data) {
            alert("Erro ao remover o item");
        });


    })
}

function buscarParametroUrl(param) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(param);
}