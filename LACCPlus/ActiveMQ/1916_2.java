//,temp,JMSEndpointStatsImpl.java,175,182,temp,JMSEndpointStatsImpl.java,166,173
//,2
public class xxx {
    protected void setParent(CountStatisticImpl child, CountStatisticImpl parent) {
        if (child instanceof CountStatisticImpl && parent instanceof CountStatisticImpl) {
            CountStatisticImpl c = (CountStatisticImpl)child;
            c.setParent((CountStatisticImpl)parent);
        } else {
            LOG.warn("Cannot associate endpoint counters with session level counters as they are not both CountStatisticImpl clases. Endpoint: " + child + " session: " + parent);
        }
    }

};