//,temp,TestHiveAuthorizerCheckInvocation.java,527,546,temp,TestHiveAuthorizerCheckInvocation.java,508,525
//,3
public class xxx {
  @Test
  public void testUpdateSomeColumnsUsed() throws Exception {
    reset(mockedAuthorizer);
    int status = driver.compile("update " + acidTableName + " set i = 5 where j = 3", true);
    assertEquals(0, status);

    Pair<List<HivePrivilegeObject>, List<HivePrivilegeObject>> io = getHivePrivilegeObjectInputs();
    List<HivePrivilegeObject> outputs = io.getRight();
    HivePrivilegeObject tableObj = outputs.get(0);
    LOG.debug("Got privilege object " + tableObj);
    assertEquals("no of columns used", 1, tableObj.getColumns().size());
    assertEquals("Column used", "i", tableObj.getColumns().get(0));
    List<HivePrivilegeObject> inputs = io.getLeft();
    assertEquals(1, inputs.size());
    tableObj = inputs.get(0);
    assertEquals(2, tableObj.getColumns().size());
    assertEquals("j", tableObj.getColumns().get(0));
  }

};