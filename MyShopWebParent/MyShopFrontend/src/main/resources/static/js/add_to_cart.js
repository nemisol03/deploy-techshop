$(document).ready(function () {
    $('.btn-add_to_cart').on('click',function (e) {

        e.preventDefault();
        productId = $(this).attr("pid");
        addToCart(productId);
    })
})
function addToCart(productId) {
    quantity = $('#product-quantity'+ productId).val() === undefined ? 1 : $('#product-quantity'+ productId).val() ;
    console.log(quantity)
    url = contextPath + "cart/add/" +productId + "/" + quantity;

    $.ajax(
        {
            type: 'post',
            url: url,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader,csrfValue);
            }

        }
    ).done(function (response) {
        showModalNotify("Notification",response);
    }).fail(function () {
        showModalNotify("Error","Something went wrong!");
    })
}