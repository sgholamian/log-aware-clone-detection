//,temp,sample_2217.java,2,12,temp,sample_5630.java,2,11
//,3
public class xxx {
public void append(TableName tableName, byte[] encodedRegionName, byte[] row, List<Entry> entries) throws IOException {
if (disabledAndDroppedTables.getIfPresent(tableName) != null) {
if (LOG.isTraceEnabled()) {
for (Entry entry : entries) {


log.info("skipping");
}
}
}
}

};