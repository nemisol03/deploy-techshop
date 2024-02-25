
$(document).ready(function () {
    $('.btn-delete').on('click',function (e) {
        e.preventDefault();
        entityNameId = $(this).attr(entityNameID);
        link = $(this).attr("href");
        $('#btn-confirm').attr("href",link);
        showModalOption("Confirmation","Do you want to delete this "+entityName+" with id: " + entityNameId);
    })
    $('#btn-cancel').on('click',function () {
        window.location = currentLocation;
    })
    $('#thumb').on('change',function (){
        if(!checkFileSize(this)) {
            showModalNotify("Warning","the maximum file must be less than 1MB!");
        }else {
            showImageThumbnail(this);
        }
    })
})

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        $('#image-thumb').attr('src',e.target.result);
    }
    reader.readAsDataURL(file);
}

function checkFileSize(fileInput) {
    var size = fileInput.files[0].size;
    if(size > 1024*1024) {
        return false;
    }
    return true;
}

