异步批量处理任务、非定时任务

实现原理基于DataCarrier轻量级的生产者-消费者模式的实现库

Buffer 提供了读写底层 Objec[] 数组的相关方法

    每个Buffer内部维护了一个环形指针（AtomicRangeInteger 类型)
        指定其中的 value 字段（AtomicInteger 类型）从 start 值开始递增，当 value 递增到 end 值（int 类型）时，value 字段会被重置为 start 值，实现环形指针的效果
            //代码实现
            public final int getAndIncrement() { //  典型的基于乐观锁的环形指针实现
                int next;
                do {
                    next = this.value.incrementAndGet(); // 
                    if (next > endValue && 
                          this.value.compareAndSet(next, startValue)) { // CAS操作
                        return endValue;
                    }
                } while (next > endValue);
                return next - 1;
            }
            
### 相关概念及参数 
    Channels：底层管理了多个 Buffer 对象，提供了 IDataPartitioner 选择器用于确定一个数据元素写入到底层的哪个 Buffer 对象中
    
Buffer 缓冲池策略，根据缓冲池循环队列模型，数据时持续循环写入的，DataCarrier针对写满而没有来得及消费时，提供了三种默认策略

    BLOCKING 策略（默认）：写入线程阻塞等待，直到 Buffer 有空闲空间为止。如果选择了 BLOCKING 策略，我们可以向 Buffer 中注册 Callback 回调，当发生阻塞时 Callback 会收到相应的事件。
    OVERRIDE 策略：覆盖旧数据，会导致缓存在 Buffer 中的旧数据丢失。
    IF_POSSIBLE 策略：如果无法写入则直接返回 false，由上层应用判断如何处理。
    
    缓冲池策略，对于针对不同场景，数据的高效处理，有显著意义。
        
![IDataPartitioner关系图](java-async-batch/image/WX20210101-222601@2x.png "IDataPartitioner关系图")


    ProducerThreadPartitioner 会根据写入的 Thread ID 进行分发，这样可以保证相同线程写入的数据都在一个 Buffer 中。
    SimpleRollingPartitioner 简单循环自增选择器，使用无锁整型（volatile 修饰）的自增并取模，选择要写入的 Buffer 。当然，在高负载时会产生批量连续写入一个 Buffer 的情况，但在中低负载情况下，可以很好的避免不同线程写入数据量不均衡的问题，从而提供较好性能。

参数
    
    channelSize的大小，控制的通道数量，在合理的分区选择函数的基础上，不会出现竞争。
    bufferSize为每一个channelSize的大小，可以控制循环写入和批量消费。

