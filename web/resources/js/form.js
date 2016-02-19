   function switchMode(selectorToShow, selectorToHide){
         var elementToHide = document.getElementById(selectorToHide);
         var elementToShow = document.getElementById(selectorToShow);
         
         elementToHide.style.display = "none";
         elementToShow.style.display = "block"
      }