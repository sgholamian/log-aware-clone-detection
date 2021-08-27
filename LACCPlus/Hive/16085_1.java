//,temp,TestCommands.java,212,302,temp,TestCommands.java,132,210
//,3
public class xxx {
  @Test
  public void testDropPartitionCommand() throws HCatException, MetaException, CommandProcessorException {
    String dbName = "cmd_testdb";
    String tableName = "cmd_testtable";
    int evid = 789;

    List<HCatFieldSchema> pcols = HCatSchemaUtils.getHCatSchema("b:string").getFields();
    List<HCatFieldSchema> cols = HCatSchemaUtils.getHCatSchema("a:int").getFields();
    Map<String, String> ptnDesc = new HashMap<String,String>();
    ptnDesc.put("b","test");

    Command testReplicatedDropPtnCmd = new DropPartitionCommand(dbName, tableName, ptnDesc, true, evid);

    assertEquals(evid,testReplicatedDropPtnCmd.getEventId());
    assertEquals(1, testReplicatedDropPtnCmd.get().size());
    assertEquals(true, testReplicatedDropPtnCmd.isRetriable());
    assertEquals(false, testReplicatedDropPtnCmd.isUndoable());

    CommandTestUtils.testCommandSerialization(testReplicatedDropPtnCmd);

    Command testNormalDropPtnCmd = new DropPartitionCommand(dbName,tableName, ptnDesc, false, evid);

    assertEquals(evid,testNormalDropPtnCmd.getEventId());
    assertEquals(1, testNormalDropPtnCmd.get().size());
    assertEquals(true,testNormalDropPtnCmd.isRetriable());
    assertEquals(false,testNormalDropPtnCmd.isUndoable());

    CommandTestUtils.testCommandSerialization(testNormalDropPtnCmd);

    client.dropDatabase(dbName, true, HCatClient.DropDBMode.CASCADE);
    client.createDatabase(HCatCreateDBDesc.create(dbName).ifNotExists(false).build());

    Map<String, String> props = new HashMap<String,String>();
    props.put(ReplicationUtils.REPL_STATE_ID,String.valueOf(evid + 5));
    HCatTable table = (new HCatTable(dbName, tableName)).tblProps(props).cols(cols).partCols(pcols);

    client.createTable(HCatCreateTableDesc.create(table).build());
    HCatTable tableCreated = client.getTable(dbName, tableName);
    assertNotNull(tableCreated);

    HCatPartition ptnToAdd = (new HCatPartition(tableCreated, ptnDesc,
        TestHCatClient.makePartLocation(tableCreated,ptnDesc))).parameters(props);
    client.addPartition(HCatAddPartitionDesc.create(ptnToAdd).build());

    HCatPartition p1 = client.getPartition(dbName,tableName,ptnDesc);
    assertNotNull(p1);

    // Test replicated drop, should not drop, because evid < repl.state.id
    LOG.info("About to run :"+testReplicatedDropPtnCmd.get().get(0));
    driver.run(testReplicatedDropPtnCmd.get().get(0));
    HCatPartition p2 = client.getPartition(dbName,tableName,ptnDesc);
    assertNotNull(p2);

    // Test normal drop, should drop unconditionally.
    LOG.info("About to run :"+testNormalDropPtnCmd.get().get(0));
    driver.run(testNormalDropPtnCmd.get().get(0));

    Exception onfe = null;
    try {
      HCatPartition p_del = client.getPartition(dbName,tableName,ptnDesc);
    } catch (Exception e) {
      onfe = e;
    }

    assertNotNull(onfe);
    assertTrue(onfe instanceof ObjectNotFoundException);

    Map<String, String> props2 = new HashMap<String,String>();
    props2.put(ReplicationUtils.REPL_STATE_ID,String.valueOf(evid - 5));

    HCatPartition ptnToAdd2 = (new HCatPartition(tableCreated, ptnDesc,
        TestHCatClient.makePartLocation(tableCreated,ptnDesc))).parameters(props2);
    client.addPartition(HCatAddPartitionDesc.create(ptnToAdd2).build());

    HCatPartition p3 = client.getPartition(dbName,tableName,ptnDesc);
    assertNotNull(p3);

    // Test replicated drop, should drop this time, since repl.state.id < evid.
    LOG.info("About to run :"+testReplicatedDropPtnCmd.get().get(0));
    driver.run(testReplicatedDropPtnCmd.get().get(0));

    Exception onfe2 = null;
    try {
      HCatPartition p_del = client.getPartition(dbName,tableName,ptnDesc);
    } catch (Exception e) {
      onfe2 = e;
    }

    assertNotNull(onfe2);
    assertTrue(onfe2 instanceof ObjectNotFoundException);
  }

};