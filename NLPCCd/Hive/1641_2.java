//,temp,sample_3720.java,2,15,temp,sample_3721.java,2,19
//,3
public class xxx {
public void dummy_method(){
String dataSourceName = Preconditions .checkNotNull(table.getParameters().get(Constants.DRUID_DATA_SOURCE), "DataSource name is null !" );
if (deleteData == true) {
List<DataSegment> dataSegmentList = DruidStorageHandlerUtils .getDataSegmentList(getConnector(), getDruidMetadataStorageTablesConfig(), dataSourceName);
if (dataSegmentList.isEmpty()) {
return;
}
for (DataSegment dataSegment : dataSegmentList) {
try {
deleteSegment(dataSegment);
} catch (SegmentLoadingException e) {


log.info("error while deleting segment s");
}
}
}
}

};