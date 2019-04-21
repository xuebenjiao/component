/*
 * ************************************************************
 * 文件：IntegerLiveData.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月15日 23:02:14
 * 上次修改时间：2019年04月15日 23:01:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.safe;

/**
 * Created by xu.yi. on 2019/4/15.
 * component
 */
public class IntegerLiveData extends SafeMutableLiveData<Integer> {
    public IntegerLiveData(final Integer value) {
        super(value);
    }
}