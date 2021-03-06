package com.dreamland.redux.middleware;


import android.support.annotation.NonNull;

import com.dreamland.redux.Store;
import com.dreamland.redux.action.Action;

/**
 * Created by XMD on 16/6/15.
 */

public interface Middleware<A extends Action> {
    void dispatch(@NonNull Store store, @NonNull A action, @NonNull Next<A> next);
}
