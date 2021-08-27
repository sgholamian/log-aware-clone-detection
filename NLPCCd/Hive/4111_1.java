//,temp,sample_5391.java,2,17,temp,sample_5390.java,2,17
//,3
public class xxx {
public void dummy_method(){
((TSocket) transport).setTimeout((int) conf.getTimeVar(HiveConf.ConfVars.SERVER_READ_SOCKET_TIMEOUT, TimeUnit.SECONDS) * 1000);
try {
((TSocket) transport).getSocket().setKeepAlive(conf.getBoolVar(HiveConf.ConfVars.SERVER_TCP_KEEP_ALIVE));
} catch (SocketException e) {
}
String userName = conf.getVar(HiveConf.ConfVars.HIVE_SERVER2_THRIFT_CLIENT_USER);
String passwd = conf.getVar(HiveConf.ConfVars.HIVE_SERVER2_THRIFT_CLIENT_PASSWORD);
try {
transport = PlainSaslHelper.getPlainTransport(userName, passwd, transport);
} catch (SaslException e) {


log.info("error creating plain sasl transport");
}
}

};