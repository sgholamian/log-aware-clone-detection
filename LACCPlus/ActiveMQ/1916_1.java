//,temp,JMSEndpointStatsImpl.java,175,182,temp,JMSEndpointStatsImpl.java,166,173
//,2
public class xxx {
    protected void setParent(TimeStatisticImpl child, TimeStatisticImpl parent) {
        if (child instanceof TimeStatisticImpl && parent instanceof TimeStatisticImpl) {
            TimeStatisticImpl c = (TimeStatisticImpl)child;
            c.setParent((TimeStatisticImpl)parent);
        } else {
            LOG.warn("Cannot associate endpoint counters with session level counters as they are not both TimeStatisticImpl clases. Endpoint: " + child + " session: " + parent);
        }
    }

};