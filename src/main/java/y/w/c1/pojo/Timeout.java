package y.w.c1.pojo;

import lombok.AccessLevel;
import lombok.Setter;

@Setter(value = AccessLevel.NONE)
public class Timeout {
    final private long maxWaitingTime;
    final private long connectionTimeout;
    final private long requestTimeout;

    public Timeout(long maxWaitingTime, long connectionTimeout, long requestTimeout) {
        this.maxWaitingTime = maxWaitingTime;
        this.connectionTimeout = connectionTimeout;
        this.requestTimeout = requestTimeout;
    }
}
