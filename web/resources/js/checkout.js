var creditTimeoutMsg;
var validCreditTimeout;
function updateUIByauserCredit(errorMessage) {
    if (creditTimeoutMsg) {
        clearTimeout(creditTimeoutMsg);
    }
    if (validCreditTimeout) {  // admin gave valid credit card - wait for animation ,odal to close...
        return;
    }
    if (errorMessage && errorMessage.length > 1) {
        if (errorMessage === 'valid credit') {
            var errorMessageElement = document.querySelector('.credit-error-message');
            var sorryIconElement = document.querySelector('.credit-card-icon');
            var errorMessageWrapperElement = document.querySelector('.credit-error-message-wrapper');
            var creditInputWrraperElement = document.querySelector('.buy-subscription-btn-wrapper');
            var noThanksBtnElement = document.querySelector('.no-thanks-btn');

            sorryIconElement.style.display = 'none';
            noThanksBtnElement.style.display = 'none';

            errorMessageElement.innerHTML = 'Thank you!';
            errorMessageElement.style.color = 'green';
            
            errorMessageWrapperElement.style.opacity = '1';
            errorMessageWrapperElement.style.top = '110px';
            errorMessageWrapperElement.style.left = '167px';
            errorMessageWrapperElement.style.fontSize = '20px';

            creditInputWrraperElement.style.marginTop = '90px';

            validCreditTimeout = setTimeout(function () {
                var suggestSubscriptionElement = document.querySelector('.suggest-get-subscription');
                suggestSubscriptionElement.style.opacity = '0';
                setTimeout(function () {
                    var bgModalElement = document.querySelector('.bg-cover-opacity');
                    bgModalElement.style.display = 'none';
                    suggestSubscriptionElement.style.display = 'none';
                }, 600);
            }, 2000);
        } else if (errorMessage === 'no user credit') {
            var element = document.querySelector(".suggest-get-subscription");
            var backgroundElement = document.querySelector(".bg-cover-opacity");
            backgroundElement.style.transition = '0.6s';
            element.style.top = "180px";
            backgroundElement.style.opacity = '0.6';
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