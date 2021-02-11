//,temp,TestAccessController.java,2574,2617,temp,TestAccessController.java,2366,2403
//,3
public class xxx {
  @Test
  public void testAccessControlClientGrantRevoke() throws Exception {
    // Create user for testing, who has no READ privileges by default.
    User testGrantRevoke = User.createUserForTesting(conf, "testGrantRevoke", new String[0]);
    AccessTestAction getAction = new AccessTestAction() {
      @Override
      public Object run() throws Exception {
        try(Connection conn = ConnectionFactory.createConnection(conf);
            Table t = conn.getTable(TEST_TABLE)) {
          return t.get(new Get(TEST_ROW));
        }
      }
    };

    verifyDenied(getAction, testGrantRevoke);

    // Grant table READ permissions to testGrantRevoke.
    try {
      grantOnTableUsingAccessControlClient(TEST_UTIL, systemUserConnection,
        testGrantRevoke.getShortName(), TEST_TABLE, null, null, Permission.Action.READ);
    } catch (Throwable e) {
      LOG.error("error during call of AccessControlClient.grant. ", e);
    }

    // Now testGrantRevoke should be able to read also
    verifyAllowed(getAction, testGrantRevoke);

    // Revoke table READ permission to testGrantRevoke.
    try {
      revokeFromTableUsingAccessControlClient(TEST_UTIL, systemUserConnection,
        testGrantRevoke.getShortName(), TEST_TABLE, null, null, Permission.Action.READ);
    } catch (Throwable e) {
      LOG.error("error during call of AccessControlClient.revoke ", e);
    }

    // Now testGrantRevoke shouldn't be able read
    verifyDenied(getAction, testGrantRevoke);
  }

};