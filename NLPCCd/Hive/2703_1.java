//,temp,sample_192.java,2,12,temp,sample_193.java,2,12
//,3
public class xxx {
public void testUpdateSomeColumnsUsed() throws HiveAuthzPluginException, HiveAccessControlException, CommandNeedRetryException {
reset(mockedAuthorizer);
int status = driver.compile("update " + acidTableName + " set i = 5 where j = 3");
assertEquals(0, status);
Pair<List<HivePrivilegeObject>, List<HivePrivilegeObject>> io = getHivePrivilegeObjectInputs();
List<HivePrivilegeObject> outputs = io.getRight();
HivePrivilegeObject tableObj = outputs.get(0);


log.info("got privilege object");
}

};