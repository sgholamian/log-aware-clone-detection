//,temp,TempletonControllerJob.java,208,239,temp,TempletonControllerJob.java,187,206
//,3
public class xxx {
  private String buildHS2DelegationToken(String user) throws IOException, InterruptedException,
          TException {
    final HiveConf c = new HiveConf();
    LOG.debug("Creating hiveserver2 delegation token for user " + user);
    final UserGroupInformation ugi = UgiFactory.getUgi(user);
    UserGroupInformation real = ugi.getRealUser();
    return real.doAs(new PrivilegedExceptionAction<String>() {
      @Override
      public String run() throws IOException, TException, InterruptedException {
        try {
          Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
          throw new IOException(e);
        }
        String hs2Url = appConf.get(AppConfig.HIVE_SERVER2_URL);
        final HiveConnection con;
        try {
          con = (HiveConnection) DriverManager.getConnection(hs2Url);
        } catch (SQLException e) {
          throw new IOException(e);
        }
        String token = ugi.doAs(new PrivilegedExceptionAction<String>() {
          @Override
          public String run() throws SQLException {
            String u = ugi.getUserName();
            return con.getDelegationToken(u,u);
          }
        });
        return token;
      }
    });
  }

};