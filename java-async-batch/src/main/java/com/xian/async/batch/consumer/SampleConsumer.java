package com.xian.async.batch.consumer;

import com.a.eye.datacarrier.consumer.IConsumer;
import com.xian.async.batch.model.SampleData;

import java.util.List;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 11:21 下午 2021/1/1
 */
public class SampleConsumer implements IConsumer <SampleData> {


    @Override
    public void init() {

    }

    @Override
    public void consume(List <SampleData> list) {
        for(SampleData one : list) {
            one.setIntValue(this.hashCode());
        }
    }

    @Override
    public void onError(List <SampleData> list, Throwable throwable) {

    }

    @Override
    public void onExit() {

    }
}
