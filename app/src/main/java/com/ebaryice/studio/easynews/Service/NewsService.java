package com.ebaryice.studio.easynews.Service;

import com.ebaryice.studio.easynews.Bean.NewsBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public interface NewsService {
    //头条新闻
    @GET("toutiao/index")
    Observable<NewsBean> getNews(@Query("type")String type, @Query("key") String key);
}
