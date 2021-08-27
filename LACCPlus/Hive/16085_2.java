//,temp,TestCommands.java,212,302,temp,TestCommands.java,132,210
//,3
public class xxx {
  @Test
  public void testDropTableCommand() throws HCatException, CommandProcessorException {
    String dbName = "cmd_testdb";
    String tableName = "cmd_testtable";
    int evid = 789;
    List<HCatFieldSchema> cols = HCatSchemaUtils.getHCatSchema("a:int,b:string").getFields();

    Command testReplicatedDropCmd = new DropTableCommand(dbName,tableName,true,evid);

    assertEquals(evid,testReplicatedDropCmd.getEventId());
    assertEquals(1, testReplicatedDropCmd.get().size());
    assertEquals(true, testReplicatedDropCmd.isRetriable());
    assertEquals(false, testReplicatedDropCmd.isUndoable());

    CommandTestUtils.testCommandSerialization(testReplicatedDropCmd);

    Command testNormalDropCmd = new DropTableCommand(dbName,tableName,false,evid);

    assertEquals(evid,testNormalDropCmd.getEventId());
    assertEquals(1, testNormalDropCmd.get().size());
    assertEquals(true,testNormalDropCmd.isRetriable());
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

    // Test replicated drop, should not drop, because evid < repl.state.id
    LOG.info("About to run :"+testReplicatedDropCmd.get().get(0));
    driver.run(testReplicatedDropCmd.get().get(0));
    HCatTable t2 = client.getTable(dbName,tableName);
    assertNotNull(t2);

    // Test normal drop, should drop unconditionally.
    LOG.info("About to run :"+testNormalDropCmd.get().get(0));
    driver.run(testNormalDropCmd.get().get(0));

    Exception onfe = null;
    try {
      HCatTable t_del = client.getTable(dbName, tableName);
    } catch (Exception e) {
      onfe = e;
    }

    assertNotNull(onfe);
    assertTrue(onfe instanceof ObjectNotFoundException);

    Map<String, String> tprops2 = new HashMap<String,String>();
    tprops2.put(ReplicationUtils.REPL_STATE_ID,String.valueOf(evid - 5));
    HCatTable tableToCreate2 = (new HCatTable(dbName, tableName)).tblProps(tprops2).cols(cols);

    client.createTable(HCatCreateTableDesc.create(tableToCreate2).build());
    HCatTable t3 = client.getTable(dbName, tableName);
    assertNotNull(t3);

    // Test replicated drop, should drop this time, since repl.state.id < evid.
    LOG.info("About to run :"+testReplicatedDropCmd.get().get(0));
    driver.run(testReplicatedDropCmd.get().get(0));

    Exception onfe2 = null;
    try {
      HCatTable t_del = client.getTable(dbName, tableName);
    } catch (Exception e) {
      onfe2 = e;
    }

    assertNotNull(onfe2);
    assertTrue(onfe2 instanceof ObjectNotFoundException);

  }

};