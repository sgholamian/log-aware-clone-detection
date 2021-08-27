//,temp,sample_2639.java,2,12,temp,sample_2637.java,2,12
//,2
public class xxx {
public void setOpTraits(OpTraits metaInfo) {
if (LOG.isInfoEnabled()) {
}
if (conf != null) {
conf.setTraits(metaInfo);
} else {


log.info("cannot set traits when there s no descriptor");
}
}

};