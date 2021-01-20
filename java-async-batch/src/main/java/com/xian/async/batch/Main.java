package com.xian.async.batch;

import com.a.eye.datacarrier.DataCarrier;
import com.a.eye.datacarrier.partition.ProducerThreadPartitioner;
import com.xian.async.batch.config.DataCarrierManager;
import com.xian.async.batch.consumer.SampleConsumer;
import com.xian.async.batch.model.SampleData;
import com.xian.async.batch.properties.DataCarrierProperties;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 10:32 下午 2021/1/1
 */
public class Main {

    public static void main(String[] args) {
        DataCarrierManager manager = new DataCarrierManager();
        DataCarrierProperties properties = new DataCarrierProperties();
        DataCarrier<SampleData> dataCarrier = (DataCarrier <SampleData>) manager.dataCarrier( properties, new ProducerThreadPartitioner <SampleData>(),new SampleConsumer());

        for (int i = 0; i < 200; i++) {
            dataCarrier.produce(new SampleData());
        }

        try {
            Thread.sleep( 10000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
