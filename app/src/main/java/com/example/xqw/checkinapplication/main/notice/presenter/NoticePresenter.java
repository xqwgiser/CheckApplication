package com.example.xqw.checkinapplication.main.notice.presenter;

import android.util.Log;

import com.example.xqw.checkinapplication.base.IPresenterFactory;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.notice.model.INoticeModel;
import com.example.xqw.checkinapplication.main.notice.model.NoticeModel;
import com.example.xqw.checkinapplication.main.notice.view.INoticeView;

import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by xqw on 2016/5/27.
 */
public class NoticePresenter implements IPresenterFactory<INoticePresenter>{
    private INoticeView noticeView;
    private INoticeModel noticeModel;

    public NoticePresenter(INoticeView noticeView) {
        this.noticeView = noticeView;
        noticeModel=new NoticeModel();
    }

    @Override
    public INoticePresenter create() {
        return new INoticePresenter() {
            @Override
            public void onViewAttached() {
                Subscriber<ItemEntity<List<Map>>> subscriber=new Subscriber<ItemEntity<List<Map>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("msg","error",e);
                    }

                    @Override
                    public void onNext(ItemEntity<List<Map>> listItemEntity) {
                        noticeView.showNoticeList(listItemEntity.getRows());
                    }
                };
                noticeModel.getNoticeList(subscriber,"service.XxggService","queryXxggList","1","10");
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
