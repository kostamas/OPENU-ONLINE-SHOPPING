
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