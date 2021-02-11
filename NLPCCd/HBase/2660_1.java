//,temp,sample_5479.java,2,16,temp,sample_5481.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertTrue(secondaryRegion.getMemStoreSize() > 0);
verifyData(secondaryRegion, 0, numRows, cq, families);
FlushDescriptor startFlushDescSmallerSeqId = clone(startFlushDesc, startFlushDesc.getFlushSequenceNumber() - 50);
result = secondaryRegion.replayWALFlushStartMarker(startFlushDescSmallerSeqId);
assertNull(result);
assertNotNull(secondaryRegion.getPrepareFlushResult());
assertEquals(secondaryRegion.getPrepareFlushResult().flushOpSeqId, startFlushDesc.getFlushSequenceNumber());
assertTrue(secondaryRegion.getMemStoreSize() > 0);
verifyData(secondaryRegion, 0, numRows, cq, families);
FlushDescriptor startFlushDescLargerSeqId = clone(startFlushDesc, startFlushDesc.getFlushSequenceNumber() + 50);


log.info("replaying same flush start in secondary again");
}

};