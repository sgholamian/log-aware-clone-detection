//,temp,sample_1087.java,2,17,temp,sample_3529.java,2,17
//,2
public class xxx {
public void returnData(OrcEncodedColumnBatch ecb) {
for (int colIx = 0; colIx < ecb.getTotalColCount(); ++colIx) {
if (!ecb.hasData(colIx)) continue;
ColumnStreamData[] datas = ecb.getColumnData(colIx);
for (ColumnStreamData data : datas) {
if (data == null || data.decRef() != 0) continue;
if (LlapIoImpl.LOCKING_LOGGER.isTraceEnabled()) {
for (MemoryBuffer buf : data.getCacheBuffers()) {


log.info("unlocking at the end of processing");
}
}
}
}
}

};