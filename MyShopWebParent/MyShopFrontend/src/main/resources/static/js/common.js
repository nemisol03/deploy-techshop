$(document).ready(function () {
    $('#btn-logout').on('click',function (e) {
        e.preventDefault();
        showModalLogout("Logout","Do you really want to logout?");
    })
})
function showModalLogout(title,message) {
    $('#modal_logout .modal-title').text(title);
    $('#modal_logout .modal-body').text(message);
    $('#modal_logout').modal('show');
}

function showModalOption(title,message) {
    $('#modal_yes_no .modal-title').text(title);
    $('#modal_yes_no .modal-body').text(message);
    $('#modal_yes_no').modal('show');
}

function showModalNotify(title,message) {
    $('#modal_notify .modal-title').text(title);
    $('#modal_notify .modal-body').text(message);
    $('#modal_notify').modal('show');
}