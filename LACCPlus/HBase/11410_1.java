//,temp,TestAccessController.java,2574,2617,temp,TestAccessController.java,2366,2403
//,3
public class xxx {
  @Test
  public void testAccessControlClientGrantRevokeOnNamespace() throws Exception {
    // Create user for testing, who has no READ privileges by default.
    User testNS = User.createUserForTesting(conf, "testNS", new String[0]);
    AccessTestAction getAction = new AccessTestAction() {
      @Override
      public Object run() throws Exception {
        try(Connection conn = ConnectionFactory.createConnection(conf);
            Table t = conn.getTable(TEST_TABLE)) {
          return t.get(new Get(TEST_ROW));
        }
      }
    };

    verifyDenied(getAction, testNS);

    String userName = testNS.getShortName();
    String namespace = TEST_TABLE.getNamespaceAsString();
    // Grant namespace READ to testNS, this should supersede any table permissions
    try {
      grantOnNamespaceUsingAccessControlClient(TEST_UTIL, systemUserConnection, userName, namespace,
        Permission.Action.READ);
    } catch (Throwable e) {
      LOG.error("error during call of AccessControlClient.grant. ", e);
    }
    try {
      // Now testNS should be able to read also
      verifyAllowed(getAction, testNS);
    } catch (Exception e) {
      revokeFromNamespace(TEST_UTIL, userName, namespace, Permission.Action.READ);
      throw e;
    }

    // Revoke namespace READ to testNS, this should supersede any table permissions
    try {
      revokeFromNamespaceUsingAccessControlClient(TEST_UTIL, systemUserConnection, userName,
        namespace, Permission.Action.READ);
    } catch (Throwable e) {
      LOG.error("error during call of AccessControlClient.revoke ", e);
    }

    // Now testNS shouldn't be able read
    verifyDenied(getAction, testNS);
  }

};