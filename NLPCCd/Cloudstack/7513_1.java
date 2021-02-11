//,temp,sample_3317.java,2,11,temp,sample_6961.java,2,11
//,2
public class xxx {
public boolean isPoolReadyForScan(Long pool) {
long dataCenterId = pool.longValue();
if (!isZoneReady(_zoneHostInfoMap, dataCenterId)) {
if (s_logger.isDebugEnabled()) {


log.info("zone is not ready to launch secondary storage vm yet");
}
}
}

};