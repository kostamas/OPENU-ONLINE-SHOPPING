var toggleProfileStore = true;
var toggleProfileProduct = true;
function toggleEditProfileStore() {
    var editProfile = document.querySelector(".profile-wrapper");
    if (toggleProfileStore) {
        editProfile.style.right = "0";
    } else {
        editProfile.style.right = "-270px";
    }
    toggleProfileStore = !toggleProfileStore;
}

function toggleEditProfileProduct() {
    var editProfile = document.querySelector(".profile-wrapper");
    if (toggleProfileProduct) {
        editProfile.style.right = "0px";
    } else {
        editProfile.style.right = "-270px";
    }
    toggleProfileProduct = !toggleProfileProduct;
}

function switchMode(selectorToShow, selectorToHide) {
    if (Array.isArray(selectorToShow) && Array.isArray(selectorToHide)) {

        for (var i = 0; i < selectorToHide.length; i++) {
            document.querySelector(selectorToHide[i]).style.display = "none";   /////////// by selector !!!!
        }
        for (var i = 0; i < selectorToShow.length; i++) {
            document.querySelector(selectorToShow[i]).style.display = "block";
        }

    } else {
        var elementToHide = document.querySelector(selectorToHide);
        var elementToShow = document.querySelector(selectorToShow);

        elementToHide.style.display = "none";
        elementToShow.style.display = "block"
    }
}



function updateProduct(gridClassName, hideBtnClass, left, height) {
    document.querySelector(gridClassName).style.left = left;
    document.querySelector(hideBtnClass).style.display = 'none';
    document.querySelector(".product-list-mask").style.height = height;
    document.querySelector(".product-list-mask").style.opacity = '0.5';
}

function returnProductsView(className) {
    document.querySelector(".product-list-mask").style.height = '0';
    document.querySelector(className).style.left = '-350px';                // settings component  (store/product)
    setTimeout(function () {
        document.querySelector(".update-btn").style.display = 'inline-block';
        document.querySelector(".create-btn").style.display = 'inline-block';
    }, 500);
}
