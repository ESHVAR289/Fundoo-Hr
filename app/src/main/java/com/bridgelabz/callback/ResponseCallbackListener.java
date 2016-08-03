package com.bridgelabz.callback;

import com.bridgelabz.model.TimeEntryResponse;

public interface ResponseCallbackListener {
    void messageResponse(TimeEntryResponse mTimeEntryResponseData);

    void confirmationResponse(String message);

    void attendanceErrorResponse(String err);
}
