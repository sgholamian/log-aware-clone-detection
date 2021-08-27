//,temp,sample_4105.java,2,16,temp,sample_4106.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertEquals(false,testNormalDropCmd.isUndoable());
CommandTestUtils.testCommandSerialization(testNormalDropCmd);
client.dropDatabase(dbName, true, HCatClient.DropDBMode.CASCADE);
client.createDatabase(HCatCreateDBDesc.create(dbName).ifNotExists(false).build());
Map<String, String> tprops = new HashMap<String,String>();
tprops.put(ReplicationUtils.REPL_STATE_ID,String.valueOf(evid + 5));
HCatTable tableToCreate = (new HCatTable(dbName, tableName)).tblProps(tprops).cols(cols);
client.createTable(HCatCreateTableDesc.create(tableToCreate).build());
HCatTable t1 = client.getTable(dbName, tableName);
assertNotNull(t1);


log.info("about to run");
}

};