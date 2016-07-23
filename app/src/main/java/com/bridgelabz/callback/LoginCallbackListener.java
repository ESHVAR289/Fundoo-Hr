package com.bridgelabz.callback;

public interface LoginCallbackListener {
    void mobileNoResponse(Boolean status);

    void checkForOtpConfirmation(Boolean status);

    void onFailureMobileNoResponse();

    void onFailureOtpResponse();
}
