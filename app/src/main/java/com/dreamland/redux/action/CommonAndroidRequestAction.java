package com.dreamland.redux.action;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by XMD on 2016/7/15.
 */
public class CommonAndroidRequestAction extends RequestAction<CommonAndroidRequestAction.CommonAndroidRequestActionParameter> {
    public CommonAndroidRequestAction(String action, CommonAndroidRequestActionParameter parameter) {
        super(action, parameter);
    }

    public static class CommonAndroidRequestActionParameter extends RequestAction.RequestParameter{
        public Bundle data;
        public Context context;

        public CommonAndroidRequestActionParameter(String who, Context context, Bundle data) {
            super(who);
            this.context = context;
            this.data = data;
        }

        @Override
        public String toString() {
            return "CommonAndroidRequestActionParameter{" +
                    "data=" + data +
                    ", context=" + context +
                    "} " + super.toString();
        }
    }
}
