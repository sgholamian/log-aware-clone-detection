//,temp,sample_3530.java,2,18,temp,sample_3531.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (sargApp != null) {
OrcStripeMetadata stripeMetadata = metadata.get(stripeIxMod);
rgsToRead = sargApp.pickRowGroups(stripe, stripeMetadata.getRowIndexes(), stripeMetadata.getBloomFilterKinds(), stripeMetadata.getEncodings(), stripeMetadata.getBloomFilterIndexes(), true);
}
boolean isNone = rgsToRead == RecordReaderImpl.SargApplier.READ_NO_RGS, isAll = rgsToRead == RecordReaderImpl.SargApplier.READ_ALL_RGS;
hasAnyData = hasAnyData || !isNone;
if (LlapIoImpl.ORC_LOGGER.isTraceEnabled()) {
if (isNone) {
trace.logSargResult(stripeIx, 0);
} else if (!isAll) {


log.info("sarg picked rgs for stripe");
}
}
}

};