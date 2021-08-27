//,temp,HazelcastRoutePolicy.java,150,166,temp,EtcdRoutePolicy.java,157,166
//,3
public class xxx {
    protected void setLeader(boolean isLeader) {
        if (isLeader && leader.compareAndSet(false, isLeader)) {
            LOGGER.info("Leadership taken (path={}, name={})", servicePath, serviceName);
            startAllStoppedConsumers();
        } else {
            if (!leader.getAndSet(isLeader) && isLeader) {
                LOGGER.info("Leadership lost (path={}, name={})", servicePath, serviceName);
            }
        }
    }

};