//,temp,sample_5232.java,2,18,temp,sample_1360.java,2,18
//,2
public class xxx {
public void dummy_method(){
long startTick = System.currentTimeMillis();
int retry = _retry;
while (System.currentTimeMillis() - startTick <= _opsTimeout || --retry > 0) {
try (SocketChannel sch = SocketChannel.open();) {
sch.configureBlocking(true);
sch.socket().setSoTimeout(5000);
InetSocketAddress addr = new InetSocketAddress(ipAddress, port);
sch.connect(addr);
return null;
} catch (IOException e) {


log.info("could not connect to due to");
}
}
}

};