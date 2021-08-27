//,temp,sample_4108.java,2,16,temp,sample_4109.java,2,16
//,3
public class xxx {
public void dummy_method(){
Map<String, String> props = new HashMap<String,String>();
props.put(ReplicationUtils.REPL_STATE_ID,String.valueOf(evid + 5));
HCatTable table = (new HCatTable(dbName, tableName)).tblProps(props).cols(cols).partCols(pcols);
client.createTable(HCatCreateTableDesc.create(table).build());
HCatTable tableCreated = client.getTable(dbName, tableName);
assertNotNull(tableCreated);
HCatPartition ptnToAdd = (new HCatPartition(tableCreated, ptnDesc, TestHCatClient.makePartLocation(tableCreated,ptnDesc))).parameters(props);
client.addPartition(HCatAddPartitionDesc.create(ptnToAdd).build());
HCatPartition p1 = client.getPartition(dbName,tableName,ptnDesc);
assertNotNull(p1);


log.info("about to run");
}

};