
function toggleEditProfile() {
    var editProfile = document.querySelector(".profile-wrapper");
    if (toggleProfileProduct) {
        editProfile.style.right = "0px";
    } else {
        editProfile.style.right = "-270px";
    }
    toggleProfileProduct = !toggleProfileProduct;
}