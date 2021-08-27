//,temp,sample_5391.java,2,17,temp,sample_5390.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (transport != null && transport.isOpen()) {
transport.close();
}
String host = conf.getVar(HiveConf.ConfVars.HIVE_SERVER2_THRIFT_BIND_HOST);
int port = conf.getIntVar(HiveConf.ConfVars.HIVE_SERVER2_THRIFT_PORT);
transport = new TSocket(host, port);
((TSocket) transport).setTimeout((int) conf.getTimeVar(HiveConf.ConfVars.SERVER_READ_SOCKET_TIMEOUT, TimeUnit.SECONDS) * 1000);
try {
((TSocket) transport).getSocket().setKeepAlive(conf.getBoolVar(HiveConf.ConfVars.SERVER_TCP_KEEP_ALIVE));
} catch (SocketException e) {


log.info("error setting keep alive to");
}
}

};