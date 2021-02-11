//,temp,SecureTestUtil.java,696,710,temp,SecureTestUtil.java,613,627
//,2
public class xxx {
  public static void revokeFromTableUsingAccessControlClient(final HBaseTestingUtility util,
      final Connection connection, final String user, final TableName table, final byte[] family,
      final byte[] qualifier, final Permission.Action... actions) throws Exception {
    SecureTestUtil.updateACLs(util, new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        try {
          AccessControlClient.revoke(connection, table, user, family, qualifier, actions);
        } catch (Throwable t) {
          LOG.error("revoke failed: ", t);
        }
        return null;
      }
    });
  }

};