//,temp,SecureTestUtil.java,696,710,temp,SecureTestUtil.java,511,525
//,3
public class xxx {
  public static void revokeFromNamespaceUsingAccessControlClient(final HBaseTestingUtility util,
      final Connection connection, final String user, final String namespace,
      final Permission.Action... actions) throws Exception {
    SecureTestUtil.updateACLs(util, new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        try {
          AccessControlClient.revoke(connection, namespace, user, actions);
        } catch (Throwable t) {
          LOG.error("revoke failed: ", t);
        }
        return null;
      }
    });
  }

};