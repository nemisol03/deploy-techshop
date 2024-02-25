
var indexLatestExtraImg = $('input[name="extrasFileImage"]').length - 1;
var indexLatestDetail = $('div[id^="divDetailElement"]').length - 1;
var divAllDetails = $('#divAllDetails');



    function showCategoriesByBrand() {

        brandId = brandsDropdown.children('option:selected').val();
        var url = contextPath + "brands/" + brandId + "/categories";
        $.get(url, function (categoryList) {
            $.each(categoryList, function (index, category) {
                categoriesDropdown.append(`<option value="${category.id}">${category.name}</option>`);
                // in edit mode, category must be selected by brand -> checked
                if (categoryIdEditMode && categoryIdEditMode == category.id) {
                    // in jquery use val not selected
                    categoriesDropdown.val(category.id);
                }
            }
            );
        })

    }
$(document).ready(function () {
    $('#shortDescription').richText();
    $('#fullDescription').richText();
    brandsDropdown.on('change', function () {
        categoriesDropdown.empty();
        showCategoriesByBrand();
    })
    showCategoriesByBrand();

    $('button[name="btn-cancel-form"]').on('click',function () {
        window.location = currentLocation;
    })


    $('input[name="extrasFileImage"]').each(function (index,fileInput) {

        $(fileInput).on('change',function () {
        if(!checkFileSize(this)) {
            showModalNotify("Warning","the maximum file must be less than 1MB!");
        }else {
            showExtraImageThumbnail(this,index);

        }
        })
        if(index < indexLatestExtraImg) {
            removeTagLink = `<a href="javascript:removeExtraImg(${index + 1})" class="fa-solid fa-xmark float-right "></a>`

            $('#labelHeaderImageExtra'+(index )).append(removeTagLink)
        }

    })


    $('div[id^="divDetailElement"]').each(function (index,detailElement){
        if(index < indexLatestDetail) {
            removeTagLink = `<a href="javascript:removeDetailSection(${index})" class="fa-solid fa-xmark float-right "></a>`

            $('#divDetailElement'+(index)).append(removeTagLink)
        }
    })





    $('#add-new-details').on('click',function () {
        detailElement = `
        <div class="mb-3 row" id="divDetailElement${indexLatestDetail + 1}">
            <div class="col-md-6">
                <label for="" class="form-label">Name</label>
                <input type="text" class="form-control" id="" name="nameDetails">
            </div>
            <div class="col-md-6">
                <label for="" class="form-label">Value</label>
                <input type="text" class="form-control" name="valueDetails" >
            </div>
            <input type="hidden" name="detailIDs" value="0">

        </div>
        `;
        divAllDetails.append(detailElement);
        removeTagLink = `<a href="javascript:removeDetailSection(${indexLatestDetail})" class="fa-solid fa-xmark float-right "></a>`

        $('#divDetailElement'+(indexLatestDetail)).append(removeTagLink)
        indexLatestDetail++;
    })



})

function showExtraImageThumbnail(fileInput,index) {
    var file = fileInput.files[0];
    var reader = new FileReader();

    // replace image if existing in editing mode
    var fileName = file.name;
    var imageNameHiddenField = $('#imageName' + index);
    if(imageNameHiddenField.length) {
        imageNameHiddenField.val(fileName);
    }
    reader.onload = function (e) {
        $('#extra-image-thumb'+index).attr('src',e.target.result);
    }
    reader.readAsDataURL(file);
    if(index >= indexLatestExtraImg) {
         addExtraImageThumb(index + 1);
    }


}

function addExtraImageThumb(index) {
    extraImageElement = `
        <div class="col-6  mb-3 image-wrapper ml-2" id="divExtraImage${index}" >
          
            <label for="" class="form-label" id="labelHeaderImageExtra${index}"><span>Product extra image #${index + 1}:</span></label>
            <img src="${defaultImageThumb}" class="img-thumb img-thumb--xl" id="extra-image-thumb${index}">
            <input type="file" class="form-control mt-2" name="extrasFileImage" onchange="showExtraImageThumbnail(this,${index} )" >
        </div>
    `
    $('#image-container'). append(extraImageElement);
    removeTagLink = `<a href="javascript:removeExtraImg(${index})" class="fa-solid fa-xmark  "></a>`

    $('#labelHeaderImageExtra'+(index - 1)).append(removeTagLink)
    indexLatestExtraImg++;

}
function removeExtraImg(index ) {
    $('#divExtraImage'+(index - 1)).remove();
}

function removeDetailSection(index) {
    $('#divDetailElement' + (index)).remove();
}

function checkUniqueProduct(form) {
        url = contextPath + "products/check_unique";
        productId = $('#id').val();
        productName = $('#name').val();
        productAlias = $('#alias').val();
        params = {id: productId,name: productName,alias: productAlias,_csrf: csrfValue}

        $.post(url, params, function (message) {
            if (message === 'OK') {
                form.submit();
            }else if(message ==='duplicatedAliasAndName'){
                showModalNotify("Duplicated Name or Alias","Sorry, but the product's name or alias has existed. Please choose another one");
            }else if(message === 'duplicatedName') {
                showModalNotify("Duplicated Name","Sorry, but the product's name has existed. Please choose another one");
            }else if(message === 'duplicatedAlias') {
                showModalNotify("Duplicated Alias","Sorry, but the product's alias has existed. Please choose another one");
            }else {
                showModalNotify("Error","Oops! Something went wrong")
            }

        }).fail(function () {
            showModalNotify("Error","Couldn't connect to server");
        });

        return false;
}







