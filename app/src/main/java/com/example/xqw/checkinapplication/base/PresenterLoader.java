package com.example.xqw.checkinapplication.base;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * Created by xqw on 2016/5/3.
 */
public class PresenterLoader<T extends Presenter> extends Loader<T> {
    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    private final IPresenterFactory<T> factory;
    private T presenter;
    public PresenterLoader(Context context, IPresenterFactory<T> factory) {
        super(context);
        this.factory = factory;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(presenter!=null){
            deliverResult(presenter);
            return;
        }
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        presenter=factory.create();
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        super.onReset();
        presenter.onDestroyed();
        presenter=null;
    }
}
