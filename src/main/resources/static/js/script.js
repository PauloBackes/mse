$(document).ready(function () {

    $( window ).load(function() {
        var mensagem = $("#message").text();
        if (mensagem !== "") {
            $("#modal-success").modal('toggle')
        }
    });
})