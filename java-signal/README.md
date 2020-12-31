参考 skywalking 源码

应用场景

    信号量、时间窗、取样

SamplingService 

    守护线程 每秒刷新一次清零 AtomicInteger
    while 循环执行三次后 false 不在进行后续代码
    
注意启动后 守护线程不是立刻执行。第一次执行可能会放过多个