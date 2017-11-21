package com.dreamland.redux.action;

/**
 * Created by XMD on 2016/6/27.
 */
abstract public class RequestAction<T extends RequestAction.RequestParameter> extends AsyncAction<T> {
    public RequestAction(String action, T parameter) {
        super(action, parameter);
    }

    public static class RequestParameter {
        public final String who;
        public RequestParameter(String who) {
            this.who = who;
        }

        @Override
        public String toString() {
            return "RequestParameter{" +
                    "who='" + who + '\'' +
                    '}';
        }
    }
}
