
function updateQuantity(productQuantity,productId) {
    quantity = productQuantity.val();
    url = contextPath + "cart/update/" +productId + "/" + quantity;

    $.ajax(
        {
            type: 'post',
            url: url,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader,csrfValue);
            }

        }
    ).done(function (subTotal) {
        formatedNumber = $.number(subTotal,2) + " $";
        $('#sub-total' + productId).text(formatedNumber);
        updateTotal();
    }).fail(function () {
        showModalNotify("Error","Something went wrong!");
    })


}
function updateTotal() {
    total = 0.0;
    $('.sub-total').each(function (index,subTotal) {

        subTotalFloat = parseFloat($(subTotal).text().replace(",",""));
        total+=subTotalFloat;
    }) ;
    formatedNumber = $.number(total,2) + " $";
    $('.total-cost').text('Total:' + formatedNumber);
}
