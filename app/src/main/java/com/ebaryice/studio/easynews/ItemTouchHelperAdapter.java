package com.ebaryice.studio.easynews;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public interface ItemTouchHelperAdapter {
    //数据交换
    void onItemMove(int fromPosition,int toPosition);
    //数据删除
    void onItemDissmiss(int position);
}
