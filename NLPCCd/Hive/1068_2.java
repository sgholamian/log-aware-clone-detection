//,temp,sample_4108.java,2,16,temp,sample_4109.java,2,16
//,3
public class xxx {
public void dummy_method(){
client.createTable(HCatCreateTableDesc.create(table).build());
HCatTable tableCreated = client.getTable(dbName, tableName);
assertNotNull(tableCreated);
HCatPartition ptnToAdd = (new HCatPartition(tableCreated, ptnDesc, TestHCatClient.makePartLocation(tableCreated,ptnDesc))).parameters(props);
client.addPartition(HCatAddPartitionDesc.create(ptnToAdd).build());
HCatPartition p1 = client.getPartition(dbName,tableName,ptnDesc);
assertNotNull(p1);
driver.run(testReplicatedDropPtnCmd.get().get(0));
HCatPartition p2 = client.getPartition(dbName,tableName,ptnDesc);
assertNotNull(p2);


log.info("about to run");
}

};