/*
 * ************************************************************
 * 文件：MultiListViewModel.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.viewmodel;

import com.cody.component.handler.BaseViewModel;
import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.list.data.MaskViewData;
import com.cody.component.list.factory.MultiDataSourceFactory;
import com.cody.component.list.source.DataSourceWrapper;
import com.cody.component.list.listener.OnListListener;
import com.cody.component.list.listener.OnRequestPageListener;
import com.cody.component.list.define.Operation;
import com.cody.component.list.define.PageInfo;
import com.cody.component.list.define.RequestStatus;
import com.cody.component.list.data.ItemMultiViewData;
import com.cody.component.list.exception.ParameterNullPointerException;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，获取列表数据
 */
public abstract class MultiListViewModel<IVD extends ItemMultiViewData, ItemBean> extends BaseViewModel
        implements OnRequestPageListener<ItemBean>, Function<ItemBean, IVD>, OnListListener {
    private MaskViewData mMaskViewData = new MaskViewData();
    private MultiDataSourceFactory<IVD, ItemBean> mSourceFactory = new MultiDataSourceFactory<>(this, this);
    private DataSourceWrapper<ItemBean> mWrapper;
    private LiveData<PagedList<IVD>> mPagedList = new LivePagedListBuilder<>(mSourceFactory.map(), initPageListConfig()).build();

    public MaskViewData getMaskViewData() {
        return mMaskViewData;
    }

    public LiveData<PagedList<IVD>> getPagedList() {
        return mPagedList;
    }

    private DataSourceWrapper<ItemBean> getWrapper() {
        if (mWrapper == null && mSourceFactory.getDataSource() != null && mSourceFactory.getDataSource().getValue() != null) {
            mWrapper = new DataSourceWrapper<>(mSourceFactory.getDataSource().getValue());
        }
        return mWrapper;
    }

    @Override
    public SafeMutableLiveData<Operation> getOperation() {
        if (getWrapper() != null) {
            return getWrapper().getOperation();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }

    @Override
    public SafeMutableLiveData<RequestStatus> getRequestStatus() {
        if (getWrapper() != null) {
            return getWrapper().getRequestStatus();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }

    @Override
    public void refresh() {
        if (getWrapper() != null) {
            getWrapper().refresh();
        }
    }

    @Override
    public void retry() {
        if (getWrapper() != null) {
            getWrapper().retry();
        }
    }

    /**
     * 分页数据配置
     */
    protected PagedList.Config initPageListConfig() {
        return (new PagedList.Config.Builder())
                .setPrefetchDistance(PageInfo.DEFAULT_PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PageInfo.DEFAULT_PAGE_SIZE)
                .setPageSize(PageInfo.DEFAULT_PAGE_SIZE)
                .build();
    }
}