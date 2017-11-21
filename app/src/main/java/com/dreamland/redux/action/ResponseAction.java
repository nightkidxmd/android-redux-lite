package com.dreamland.redux.action;

/**
 * Created by XMD on 2016/6/27.
 */
abstract public class ResponseAction<T extends ResponseAction.ResponseParameter> extends SyncAction<T> {
    public ResponseAction(String action, T parameter) {
        super(action, parameter);
    }

    public static class ResponseParameter {
        public final boolean isSuccess;
        public final String reason;
        public final int errorCode;

        public ResponseParameter(boolean isSuccess, String reason, int errorCode) {
            this.isSuccess = isSuccess;
            this.reason = reason;
            this.errorCode = errorCode;

        }

        @Override
        public String toString() {
            return "ResponseParameter{" +
                    "isSuccess=" + isSuccess +
                    ", reason='" + reason + '\'' +
                    ", errorCode=" + errorCode +
                    '}';
        }
    }

}
