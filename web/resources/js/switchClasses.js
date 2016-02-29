function switchMode(selectorToShow, selectorToHide) {
    var element;
    if (Array.isArray(selectorToShow) && Array.isArray(selectorToHide)) {

        for (var i = 0; i < selectorToHide.length; i++) {
            element = document.querySelector(selectorToHide[i]);
            if (element) {
                element.style.display = "none";
            }
        }
        for (var i = 0; i < selectorToShow.length; i++) {
            element = document.querySelector(selectorToShow[i]);
            if (element) {
                element.style.display = "block";
            }
        }

    } else {
        if (selectorToHide) {
            var elementToHide = document.querySelector(selectorToHide);
            if (elementToHide) {
                elementToHide.style.display = "none";
            }
        }

        if (selectorToShow) {
            var elementToShow = document.querySelector(selectorToShow);
            if (elementToShow) {
                elementToShow.style.left = "30px";
            }
        }
    }
}
var viewAdminStore = true;
function toggle(className, styleType, newValue, oldValue) {
    if (viewAdminStore) {
        document.querySelector(className).style[styleType] = newValue;
        viewAdminStore = !viewAdminStore;
    } else {
        document.querySelector(className).style[styleType] = oldValue;
        viewAdminStore = !viewAdminStore;
    }
}