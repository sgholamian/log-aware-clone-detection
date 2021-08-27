//,temp,sample_3306.java,2,14,temp,sample_3305.java,2,10
//,3
public class xxx {
private <T extends LlapBufferOrBuffers> boolean lockOldVal(Object key, T newVal, T oldVal) {
if (LlapIoImpl.CACHE_LOGGER.isTraceEnabled()) {
}
if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
}
if (lockBuffer(oldVal, true)) {
if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {


log.info("unlocking due to cache collision with");
}
}
}

};