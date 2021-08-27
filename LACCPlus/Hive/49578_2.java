//,temp,TempletonControllerJob.java,208,239,temp,TempletonControllerJob.java,187,206
//,3
public class xxx {
  private String buildHcatDelegationToken(String user) throws IOException, InterruptedException,
          TException {
    final HiveConf c = new HiveConf();
    LOG.debug("Creating hive metastore delegation token for user " + user);
    final UserGroupInformation ugi = UgiFactory.getUgi(user);
    UserGroupInformation real = ugi.getRealUser();
    return real.doAs(new PrivilegedExceptionAction<String>() {
      @Override
      public String run() throws IOException, TException, InterruptedException  {
        final IMetaStoreClient client = HCatUtil.getHiveMetastoreClient(c);
        return ugi.doAs(new PrivilegedExceptionAction<String>() {
          @Override
          public String run() throws IOException, TException, InterruptedException {
            String u = ugi.getUserName();
            return client.getDelegationToken(c.getUser(),u);
          }
        });
      }
    });
  }

};