
toggleProfileHome = true;
function toggleEditProfile() {
    var editProfile = document.querySelector(".profile-wrapper");
    if (toggleProfileHome) {
        editProfile.style.right = "0px";
    } else {
        editProfile.style.right = "-270px";
    }
    toggleProfileHome = !toggleProfileHome;
}

toggleUserHistory = true;

function historyToggle() {
    var historyListElement = document.querySelector(".history-table-wrapper");
    var storeListWrapper = document.querySelector(".store-list-wrapper");

    if (toggleUserHistory) {
        historyListElement.style.top = "184px";
        storeListWrapper.style.opacity = "0"
    } else {
        historyListElement.style.top = "-425px";
        storeListWrapper.style.opacity = "1"
    }
    toggleUserHistory = !toggleUserHistory;
}

function ifNotLoggedInSuggestRegister(userName) {
    if (userName.length < 4) {
        var element = document.querySelector(".suggest-register");
        var backgroundElement = document.querySelector(".bg-cover-opacity");
        backgroundElement.style.transition = '0.6s';

        element.style.top = "180px";
        backgroundElement.style.opacity = '0.6';
    }

}

function updateProdList(productQuantity){
    debugger;
    if(+productQuantity < 2){
      var t;  
    }
}