//,temp,sample_1359.java,2,10,temp,sample_5231.java,2,10
//,2
public class xxx {
protected String connect(final String vmName, final String ipAddress, final int port) {
final long startTick = System.currentTimeMillis();
int retry = _retry;
while (System.currentTimeMillis() - startTick <= _opsTimeout || --retry > 0) {


log.info("trying to connect to");
}
}

};