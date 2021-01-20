package com.xian.async.batch.properties;

import com.a.eye.datacarrier.buffer.BufferStrategy;
import com.a.eye.datacarrier.consumer.IConsumer;
import com.a.eye.datacarrier.partition.IDataPartitioner;
import lombok.Data;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 10:51 下午 2021/1/1
 */
@Data
public class DataCarrierProperties {
    /**
     * 控制的通道数量
     */
    private Integer channelSize = 10;
    /**
     * 每个channelSize的大小，及消息数量
     */
    private Integer bufferSize = 100;
    /**
     * 拒绝策略
     */
    private String strategy = "BLOCKING";

    BufferStrategy bufferStrategy;

    private Integer consumerSize = 1;

    public void setStrategy(String strategy) {
        for (BufferStrategy bufferStrategy : BufferStrategy.values()) {
            if(bufferStrategy.name().equals( strategy )){
                this.bufferStrategy = bufferStrategy;
            }
        }
        this.strategy = strategy;
    }
}
