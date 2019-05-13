/*
 * ************************************************************
 * 文件：PageListDataSourceFactory.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.source.PageListKeyedDataSource;

/**
 * Created by xu.yi. on 2019/4/8.
 * 根据接口返回的信息进行分页加载数据工厂基类
 * 泛型为分页Item的类类型
 */
public class PageListDataSourceFactory<Bean> extends DataSource.Factory<PageInfo, Bean> {
    private MutableLiveData<PageListKeyedDataSource> mDataSource = new MutableLiveData<>();
    private OnRequestPageListener<Bean> mOnRequestPageListener;

    public PageListDataSourceFactory(OnRequestPageListener<Bean> onRequestPageListener) {
        mOnRequestPageListener = onRequestPageListener;
    }

    @NonNull
    @Override
    public DataSource<PageInfo, Bean> create() {
        PageListKeyedDataSource<Bean> dataSource = new PageListKeyedDataSource<>(mOnRequestPageListener);
        mDataSource.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<PageListKeyedDataSource> getDataSource() {
        return mDataSource;
    }
}
