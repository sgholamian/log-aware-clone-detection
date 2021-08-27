//,temp,TestHCatClient.java,661,690,temp,TestHCatClient.java,638,659
//,3
public class xxx {
  @Test
  public void testGetMessageBusTopicName() throws Exception {
    try {
      HCatClient client = HCatClient.create(new Configuration(hcatConf));
      String dbName = "testGetMessageBusTopicName_DBName";
      String tableName = "testGetMessageBusTopicName_TableName";
      client.dropDatabase(dbName, true, HCatClient.DropDBMode.CASCADE);
      client.createDatabase(HCatCreateDBDesc.create(dbName).build());
      String messageBusTopicName = "MY.topic.name";
      Map<String, String> tableProperties = new HashMap<String, String>(1);
      tableProperties.put(HCatConstants.HCAT_MSGBUS_TOPIC_NAME, messageBusTopicName);
      client.createTable(HCatCreateTableDesc.create(dbName, tableName, Arrays.asList(new HCatFieldSchema("foo", Type.STRING, ""))).tblProps(tableProperties).build());

      assertEquals("MessageBus topic-name doesn't match!", messageBusTopicName, client.getMessageBusTopicName(dbName, tableName));
      client.dropDatabase(dbName, true, HCatClient.DropDBMode.CASCADE);
      client.close();
    }
    catch (Exception exception) {
      LOG.error("Unexpected exception.", exception);
      assertTrue("Unexpected exception:" + exception.getMessage(), false);
    }
  }

};