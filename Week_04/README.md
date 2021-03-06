### 第八课作业

#### 第2题

##### 什么是并发

> 对于并发的定义是，同一时间间隔内，多个任务同时执行。所以，单核心CPU也可以做到并发，但是会带来上下文切换的开销。

##### 什么是高并发

> 高并发就是同一时间间隔内，有很多的任务同时执行。比如还是单核心CPU，1秒内有1000个任务同时执行，那么每个任务理论上的执行时间就是1毫秒（不计算上下文切换的开销）

##### 实现高并发高可用系统需要考虑哪些因素，对于这些你是怎么理解的?

> 实现高并发高可用的系统，首先要根据并发量来调整一些参数
>
> - ulimit 设置文件的最大打开数
> - TCP的相关参数，比如全连接队列大小，禁用Nagle算法，开启SO_REUSEADDR，如果多个进程同时监听一个端口号，可以开启SO_REUSEPORT

> 然后通过基准压测得到当前系统可承受的并发量，并依此设置限流参数。

> 如果系统涉及到内部多个子系统的调用，需要根据业务情况设置请求的超时时间，启用熔断器并做好降级策略来保证避免由于某个子系统不可用导致整个系统崩溃的情况

> 业务设计上，能用缓存就不要直接查数据库，通过cache-aside或者借助mysql的binlog维护缓存与数据库的一致性。如果操作数据库，要考虑到主从延迟问题。如果有用到锁的操作，尽量让锁的粒度最小化。尽量不使用分布式事务等强一致性的操作，根据业务场景折中，选择合适的最终一致性方案，并做好补偿和兜底的策略。

#### 第3题

##### 还有哪些跟并发类似/有关的场景和问题，有哪些可以借鉴的解决办法。

> 只要有共享数据的地方，都会存在并发问题，从小到大来讲
>
> - CPU的 L3Cache是多个核心共享的，所以为了解决这里的并发问题，有总线锁和MESI两种方式
> - Redis内存数据库，简单粗暴的使用单线程来避免并发问题。但是在Redis对内存中的数据做持久化操作，比如备份RDB文件，通过Fork子进程来做，此时会共享父进程的内存空间，当有了写操作的时候，不能阻塞写，也不能暂停子进程，这时候就使用了CopyOnWrite的方式解决了问题
> - 关系型数据库解决并发问题，因为有了隔离性这个条件，可以根据隔离级别的不同来调整并发度。本质上就是`读-读`，`读-写`，`写-读`，`写-写`能不能同时进行。在MySQL中，通过MVCC的方式实现了除`写-写`之外的操作都能同时执行（这里的读操作是一致性读而不是当前读）