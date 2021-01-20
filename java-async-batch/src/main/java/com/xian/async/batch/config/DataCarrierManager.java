package com.xian.async.batch.config;

import com.a.eye.datacarrier.DataCarrier;
import com.a.eye.datacarrier.consumer.IConsumer;
import com.a.eye.datacarrier.partition.IDataPartitioner;
import com.xian.async.batch.properties.DataCarrierProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 11:03 下午 2021/1/1
 */
public class DataCarrierManager {

    private static Map<Class, Object> dataCarrierContext = new ConcurrentHashMap<>();

    public DataCarrier<?> dataCarrier(DataCarrierProperties properties, IDataPartitioner partitioner, IConsumer consumer){
        DataCarrier<?> dataCarrier = new DataCarrier <Object>( properties.getChannelSize(),properties.getBufferSize() );
        dataCarrier.setBufferStrategy( properties.getBufferStrategy() );
        dataCarrier.setPartitioner( partitioner );
        dataCarrier.consume( consumer,properties.getConsumerSize() );
        return dataCarrier;
    }
}
