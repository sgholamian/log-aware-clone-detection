//,temp,sample_3720.java,2,15,temp,sample_3721.java,2,19
//,3
public class xxx {
public void commitDropTable(Table table, boolean deleteData) throws MetaException {
if (MetaStoreUtils.isExternalTable(table)) {
return;
}
String dataSourceName = Preconditions .checkNotNull(table.getParameters().get(Constants.DRUID_DATA_SOURCE), "DataSource name is null !" );
if (deleteData == true) {
List<DataSegment> dataSegmentList = DruidStorageHandlerUtils .getDataSegmentList(getConnector(), getDruidMetadataStorageTablesConfig(), dataSourceName);
if (dataSegmentList.isEmpty()) {


log.info("nothing to delete for data source");
}
}
}

};