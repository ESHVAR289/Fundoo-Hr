package com.bridgelabz.callback;

import com.bridgelabz.model.ConfirmationResponse;
import com.bridgelabz.model.TimeEntryResponse;

public interface ResponseCallbackListener {
    void messageResponse(TimeEntryResponse mTimeEntryResponseData);

    void confirmationResponse(ConfirmationResponse confirmationResponse);

    void onFailureMessageResponse(Throwable t);

    void onFailureOtpConfirmation();
}
