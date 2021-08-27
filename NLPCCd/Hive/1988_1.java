//,temp,sample_2639.java,2,12,temp,sample_2637.java,2,12
//,2
public class xxx {
public void setStatistics(Statistics stats) {
if (LOG.isInfoEnabled()) {
}
if (conf != null) {
conf.setStatistics(stats);
} else {


log.info("cannot set stats when there s no descriptor");
}
}

};