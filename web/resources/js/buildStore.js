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



function editNewStoreIfCreditCardOk(gridClassName, hideBtnClass, left, height, isAdminHaveCredit) {
    if (eval(isAdminHaveCredit)) {  // show store settings
        document.querySelector(gridClassName).style.left = left;
        document.querySelector(hideBtnClass).style.display = 'none';
        document.querySelector(".product-list-mask").style.height = height;
        document.querySelector(".product-list-mask").style.opacity = '0.5';
    } else {  // admin have no credit card...
        var element = document.querySelector(".suggest-get-subscription");
        var backgroundElement = document.querySelector(".bg-cover-opacity");
        backgroundElement.style.transition = '0.6s';

        element.style.top = "180px";
        backgroundElement.style.opacity = '0.6';
    }
}

function returnProductsView(className) {
    document.querySelector(".product-list-mask").style.height = '0';
    document.querySelector(className).style.left = '-350px';                // settings component  (store/product)
    setTimeout(function () {
        document.querySelector(".update-btn").style.display = 'inline-block';
        document.querySelector(".create-btn").style.display = 'inline-block';
    }, 500);
}


toggleUserHistory2 = true;

function historyToggle2() {
    var historyListElement = document.querySelector(".history-table-wrapper");
    var storeListWrapper = document.querySelector(".store-list-wrapper");

    if (toggleUserHistory2) {
        historyListElement.style.top = "184px";
        storeListWrapper.style.opacity = "0"
    } else {
        historyListElement.style.top = "-425px";
        storeListWrapper.style.opacity = "1"
    }
    toggleUserHistory2 = !toggleUserHistory2;
}

function updateProduct(gridClassName, hideBtnClass, left, height) {
    document.querySelector(gridClassName).style.left = left;
    document.querySelector(hideBtnClass).style.display = 'none';
    document.querySelector(".product-list-mask").style.height = height;
    document.querySelector(".product-list-mask").style.opacity = '0.5';
}

var creditTimeoutMsg;
var validCreditTimeout;
function updateUIByadminCredit(errorMessage) {
    if (creditTimeoutMsg) {
        clearTimeout(creditTimeoutMsg);
    }
    if (validCreditTimeout) {  // admin gave valid credit card - wait for animation ,odal to close...
        return;
    }
    if (errorMessage && errorMessage.length > 1) {
        if (errorMessage === 'valid credit') {
            var errorMessageElement = document.querySelector('.credit-error-message');
            var sorryIconElement = document.querySelector('.sorry-icon');
            var errorMessageWrapperElement = document.querySelector('.credit-error-message-wrapper');
            var creditInputWrraperElement = document.querySelector('.buy-subscription-btn-wrapper');
            var noThanksBtnElement = document.querySelector('.no-thanks-btn');

            sorryIconElement.style.display = 'none';
            noThanksBtnElement.style.display = 'none';

            errorMessageElement.innerHTML = 'Thank you and good luck!';
            errorMessageElement.style.color = 'green';

            errorMessageWrapperElement.style.opacity = '1';
            errorMessageWrapperElement.style.top = '110px';
            errorMessageWrapperElement.style.left = '97px';
            errorMessageWrapperElement.style.fontSize = '20px';
            creditInputWrraperElement.style.marginTop = '50px';

            validCreditTimeout = setTimeout(function () {
                var suggestSubscriptionElement = document.querySelector('.suggest-get-subscription');
                suggestSubscriptionElement.style.opacity = '0';
                setTimeout(function () {
                    var bgModalElement = document.querySelector('.bg-cover-opacity');
                    bgModalElement.style.display = 'none';
                    suggestSubscriptionElement.style.display = 'none';
                    location.reload();
                }, 600);
            }, 2000);
        } else {
            var errorMessageWrapperElement = document.querySelector('.credit-error-message-wrapper');
            var errorMessageElement = document.querySelector('.credit-error-message');
            errorMessageElement.innerHTML = errorMessage;
            errorMessageWrapperElement.style.opacity = '1';
            creditTimeoutMsg = setTimeout(function () {
                errorMessageWrapperElement.style.opacity = '0';
            }, 2500);
        }
    }
}

function showModalOnHover(event, text) {
    var x = event.screenX - 20, y = event.screenY - 55;
    var textElement = document.querySelector('.store-name-modal');
    textElement.style.top = y + 'px';
    textElement.style.left = x + 'px';
    textElement.style.display = 'block';
    textElement.innerHTML = text;
    
}

function hideStoreNameModal(){
      var textElement = document.querySelector('.store-name-modal');
    textElement.style.display = 'none';
}

function openDropList() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
