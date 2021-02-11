//,temp,SecureTestUtil.java,634,648,temp,SecureTestUtil.java,613,627
//,3
public class xxx {
  public static void grantOnTableUsingAccessControlClient(final HBaseTestingUtility util,
      final Connection connection, final String user, final TableName table, final byte[] family,
      final byte[] qualifier, final Permission.Action... actions) throws Exception {
    SecureTestUtil.updateACLs(util, new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        try {
          AccessControlClient.grant(connection, table, user, family, qualifier, actions);
        } catch (Throwable t) {
          LOG.error("grant failed: ", t);
        }
        return null;
      }
    });
  }

};