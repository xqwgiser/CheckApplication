package com.example.xqw.checkinapplication.main.check.presenter;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.main.check.model.CheckModel;
import com.example.xqw.checkinapplication.main.check.model.ICheckModel;
import com.example.xqw.checkinapplication.main.check.view.ICheckEditView;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/5.
 */
public class CheckEditPresenter implements IPresenterFactory<ICheckEditPresenter> {
    private ICheckModel checkModel;

    public CheckEditPresenter(ICheckEditView checkEditView) {
        this.checkEditView = checkEditView;
        checkModel = new CheckModel();
    }

    private ICheckEditView checkEditView;

    @Override
    public ICheckEditPresenter create() {
        return new ICheckEditPresenter() {
            @Override
            public void setCheckType() {
                Subscriber<List<Map>> subscriber = new Subscriber<List<Map>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Map> maps) {
                        checkEditView.setCheckType(maps);
                    }
                };
                checkModel.getCheckType(subscriber, "service.GzqdService", "queryGzqdlx");
            }

            @Override
            public void postCheckInfo() {
                checkModel.saveCheckInInfo(checkEditView.getCheckInfo());
            }


            @Override
            public void onViewAttached() {

            }

            @Override
            public void onViewDetached() {

            }

            @Override
            public void onDestroyed() {

            }
        };
    }
}
