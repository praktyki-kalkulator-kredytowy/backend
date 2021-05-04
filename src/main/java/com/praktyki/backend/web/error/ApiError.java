package com.praktyki.backend.web.error;

import org.springframework.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;

public class ApiError {

    private static final String DEFAULT_SUGGESTED_ACTION = "Please try again later";

    private static final String DEFAULT_ERROR_MESSAGE = "Sorry, something went wrong";

    public static Builder builder() {
        return new Builder();
    }

    private HttpStatus mStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    private String mMessage = DEFAULT_ERROR_MESSAGE;

    private String mSuggestedAction = DEFAULT_SUGGESTED_ACTION;

    private List<ApiSubError> mSubErrors = new LinkedList<>();

    public HttpStatus getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    private ApiError() {}

    public String getSuggestedAction() {
        return mSuggestedAction;
    }

    public static class Builder {
        private ApiError mApiError = new ApiError();

        private Builder() {}

        public Builder setStatus(HttpStatus status) {
            mApiError.mStatus = status;
            return this;
        }

        public Builder setMessage(String message) {
            mApiError.mMessage = message;
            return this;
        }

        public Builder setSuggestedAction(String action) {
            mApiError.mSuggestedAction = action;
            return this;
        }

        public Builder setSubErrors(List<ApiSubError> subErrors) {
            mApiError.mSubErrors = subErrors;
            return this;
        }

        public Builder addSubError(ApiSubError error) {
            mApiError.mSubErrors.add(error);
            return this;
        }

        public ApiError build() {
            return mApiError;
        }
    }
}
