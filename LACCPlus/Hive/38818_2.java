//,temp,HiveMetaStoreClient.java,3909,3934,temp,HiveMetaStoreClientPreCatalog.java,2500,2525
//,2
public class xxx {
  @Override
  public void replTableWriteIdState(String validWriteIdList, String dbName, String tableName, List<String> partNames)
          throws TException {
    String user;
    try {
      user = UserGroupInformation.getCurrentUser().getUserName();
    } catch (IOException e) {
      LOG.error("Unable to resolve current user name " + e.getMessage());
      throw new RuntimeException(e);
    }

    String hostName;
    try {
      hostName = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      LOG.error("Unable to resolve my host name " + e.getMessage());
      throw new RuntimeException(e);
    }

    ReplTblWriteIdStateRequest rqst
            = new ReplTblWriteIdStateRequest(validWriteIdList, user, hostName, dbName, tableName);
    if (partNames != null) {
      rqst.setPartNames(partNames);
    }
    client.repl_tbl_writeid_state(rqst);
  }

};