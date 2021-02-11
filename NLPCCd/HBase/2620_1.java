//,temp,sample_3780.java,2,14,temp,sample_3781.java,2,17
//,3
public class xxx {
public List<byte[]> getMetaTableRows(TableName tableName) throws IOException {
Table t = getConnection().getTable(TableName.META_TABLE_NAME);
List<byte[]> rows = new ArrayList<>();
ResultScanner s = t.getScanner(new Scan());
for (Result result : s) {
RegionInfo info = MetaTableAccessor.getRegionInfo(result);
if (info == null) {


log.info("no region info for row");
}
}
}

};