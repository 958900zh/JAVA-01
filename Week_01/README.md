## 第二次作业

压测命令：`wrk -c 50 -t 10 -d 30 http://localhost:8088/api/hello`

本机内存：8G，逻辑核心：8核

程序启动命令：

`java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC -jar gateway-server-0.0.1-SNAPSHOT.jar`

`java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseParallelGC -jar gateway-server-0.0.1-SNAPSHOT.jar`

`java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseConcMarkSweepGC -jar gateway-server-0.0.1-SNAPSHOT.jar`

`java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -jar gateway-server-0.0.1-SNAPSHOT.jar`

| 收集器   | QPS      | STW时长 | YGC次数 | YGC耗时 |
| -------- | -------- | ------- | ------- | ------- |
| Serial   | 49082.65 | 0.655   | 313     | 0.655   |
| Parallel | 52953.93 | 0.391   | 367     | 0.391   |
| CMS      | 50084.02 | 0.486   | 352     | 0.486   |
| G1       | 46692.92 | 0.233   | 152     | 0.233   |

> **结果分析**
>
> QPS结果取三次压测平均值；SWT时长取三次压测后，执行`jstat -gc`命令的GCT的值。
>
> 从结果来看，本次测试环境中，Serial收集器的停顿时间最长，因为是单线程穿行，结果在意料之中。Parallel是并行收集器，自然QPS是最高的，表现符合预期。意料之外的是CMS的STW时间居然比Parallel长，因为CMS设计的目的就是降低STW时间，可能在本次实验环境中，CMS表现确实不太好。G1可以看做CMS的升级版，STW时间理论上会短，但是这次结果的STW时间这么短也出乎了我的意料，在我的认知中，G1适合大内存的场景，在本次1G堆内存的场景下，我预想的G1的表现不会太好。
>
> **结论**
>
> 本次实验只能说明在我此时此刻，本机的测试结果是这样的。所以从实验结果来看，Parallel吞吐量最高确实毋庸置疑，G1也做到了低STW。但是这次的结果也有出乎我意料的地方。所以，实践出真知，不能一味的被理论或者各种结论带着走。理论+实践才能收获到完整的知识。

