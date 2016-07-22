package com.bridgelabz.callback;

import com.bridgelabz.model.ConfirmationResponse;
import com.bridgelabz.model.TimeEntryResponse;

/**
 * Created by bridgeit007 on 21/7/16.
 */

public interface ResponseCallbackListener {
    void messageResponse(TimeEntryResponse mTimeEntryResponseData);
    void confirmationResponse(ConfirmationResponse confirmationResponse);
}
