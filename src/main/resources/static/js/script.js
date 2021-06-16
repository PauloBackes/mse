$(document).ready(function () {

    $(window).load(function () {
        var mensagem = $("#message").text();
        if (mensagem !== "") {
            $("#modal-success").modal('toggle')
        }

        removerEquipamento();
        removerMaterial();
    });
})

const URL_BASE = window.location.origin + "/mse";

function removerEquipamento() {
    $(".remover-equipamento").click(function () {
        var codigoEquipamento = $(this).parents()[1].cells[0].firstChild.getAttribute("value");
        var tipoItem = "Equipamento";
        modalRemoverItem(codigoEquipamento, tipoItem);

    })
}

function removerMaterial() {
    $(".remover-material").click(function () {
        var codigoMaterial = $(this).parents()[1].cells[0].firstChild.getAttribute("value");
        var tipoItem = "Material";
        modalRemoverItem(codigoMaterial, tipoItem);
    })
}

function modalRemoverItem(codigoItem, tipoItem) {
    $("#modal-remover-item").modal('toggle');

    $("#botao-remover-item").click(function () {
        var dados = {
            "codigoItem": codigoItem,
            "idCirurgia": buscarParametroUrl('id'),
            "tipoItem": tipoItem
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