var toggle = true;
function toggleEditProfile() {
    var editProfile = document.querySelector(".profile-wrapper");
    if (toggle) {
        editProfile.style.right = "75px";
    } else {
        editProfile.style.right = "-215px";
    }
    toggle = !toggle;
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