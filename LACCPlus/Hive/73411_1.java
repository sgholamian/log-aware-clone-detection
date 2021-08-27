//,temp,TestHiveAuthorizerCheckInvocation.java,527,546,temp,TestHiveAuthorizerCheckInvocation.java,508,525
//,3
public class xxx {
  @Test
  public void testUpdateSomeColumnsUsedExprInSet() throws Exception {
    reset(mockedAuthorizer);
    int status = driver.compile("update " + acidTableName + " set i = 5, j = k where j = 3", true);
    assertEquals(0, status);

    Pair<List<HivePrivilegeObject>, List<HivePrivilegeObject>> io = getHivePrivilegeObjectInputs();
    List<HivePrivilegeObject> outputs = io.getRight();
    HivePrivilegeObject tableObj = outputs.get(0);
    LOG.debug("Got privilege object " + tableObj);
    assertEquals("no of columns used", 2, tableObj.getColumns().size());
    assertEquals("Columns used", Arrays.asList("i", "j"),
        getSortedList(tableObj.getColumns()));
    List<HivePrivilegeObject> inputs = io.getLeft();
    assertEquals(1, inputs.size());
    tableObj = inputs.get(0);
    assertEquals(2, tableObj.getColumns().size());
    assertEquals("Columns used", Arrays.asList("j", "k"),
        getSortedList(tableObj.getColumns()));
  }

};