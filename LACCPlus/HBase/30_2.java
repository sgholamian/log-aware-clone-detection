//,temp,SecureTestUtil.java,721,729,temp,SecureTestUtil.java,700,708
//,3
public class xxx {
      @Override
      public Void call() throws Exception {
        try {
          AccessControlClient.revoke(connection, table, user, family, qualifier, actions);
        } catch (Throwable t) {
          LOG.error("revoke failed: ", t);
        }
        return null;
      }

};