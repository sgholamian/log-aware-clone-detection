//,temp,SecureTestUtil.java,634,648,temp,SecureTestUtil.java,490,504
//,3
public class xxx {
  public static void grantOnNamespaceUsingAccessControlClient(final HBaseTestingUtility util,
      final Connection connection, final String user, final String namespace,
      final Permission.Action... actions) throws Exception {
    SecureTestUtil.updateACLs(util, new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        try {
          AccessControlClient.grant(connection, namespace, user, actions);
        } catch (Throwable t) {
          LOG.error("grant failed: ", t);
        }
        return null;
      }
    });
  }

};