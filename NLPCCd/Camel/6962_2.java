//,temp,sample_4570.java,2,15,temp,sample_4571.java,2,17
//,3
public class xxx {
public synchronized void run() {
if (getConfiguration().hasIdleTimeout()) {
if (null != socket && !socket.isClosed() && socket.isConnected()) {
if (lastProcessCallTicks > 0) {
long idleTime = System.currentTimeMillis() - lastProcessCallTicks;
if (log.isDebugEnabled()) {
}
if (idleTime >= getConfiguration().getIdleTimeout()) {


log.info("mllp connection idle time of milliseconds met or exceeded the idle producer timeout of milliseconds resetting conection");
}
}
}
}
}

};