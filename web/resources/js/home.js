
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