package com.ebaryice.studio.easynews.Model;

import com.ebaryice.studio.easynews.Bean.NewsBean;
import com.ebaryice.studio.easynews.Service.NewsService;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class NewsModel {
    private String api = "http://v.juhe.cn/";
    private String key = "e8f0526d2054d4262f2c541ed6eee537";
    public interface OnGetNews{
        void onFinish(Object bean);
    }
    public void getNews(String type, final OnGetNews onGetNews){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        NewsService service = retrofit.create(NewsService.class);
        service.getNews(type,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    NewsBean bean = null;
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBean value) {
                        bean = value;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (bean!=null){
                            onGetNews.onFinish(bean);
                        }
                    }
                });
    }
}
