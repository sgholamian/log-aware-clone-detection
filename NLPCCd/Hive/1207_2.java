//,temp,sample_3726.java,2,8,temp,sample_5389.java,2,11
//,3
public class xxx {
protected synchronized TTransport connect(HiveConf conf) throws HiveSQLException, TTransportException {
if (transport != null && transport.isOpen()) {
transport.close();
}
String host = conf.getVar(HiveConf.ConfVars.HIVE_SERVER2_THRIFT_BIND_HOST);
int port = conf.getIntVar(HiveConf.ConfVars.HIVE_SERVER2_THRIFT_PORT);


log.info("connecting to");
}

};