package com.praktyki.backend.web.error;

public class ApiSubError {

    private String mMessage;

    private String mSuggestedAction;

    public String getMessage() {
        return mMessage;
    }

    public String getSuggestedAction() {
        return mSuggestedAction;
    }

    private ApiSubError() {}

    public ApiSubError(String message, String suggestedAction) {
        mMessage = message;
        mSuggestedAction = suggestedAction;
    }

    public static class Builder {
        private ApiSubError mApiSubError = new ApiSubError();

        public Builder setMessage(String message) {
            mApiSubError.mMessage = message;
            return this;
        }

        public Builder setSuggestedAction(String action) {
            mApiSubError.mSuggestedAction = action;
            return this;
        }
    }
}
