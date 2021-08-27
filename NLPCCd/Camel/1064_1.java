//,temp,sample_3937.java,2,18,temp,sample_3938.java,2,18
//,3
public class xxx {
public void dummy_method(){
serverSocket.setSoTimeout(getConfiguration().getAcceptTimeout());
InetSocketAddress socketAddress;
if (null == getEndpoint().getHostname()) {
socketAddress = new InetSocketAddress(getEndpoint().getPort());
} else {
socketAddress = new InetSocketAddress(getEndpoint().getHostname(), getEndpoint().getPort());
}
long startTicks = System.currentTimeMillis();
if (getConfiguration().hasMaxReceiveTimeouts()) {
if (getConfiguration().hasIdleTimeout()) {


log.info("both maxreceivedtimeouts and idletimeout uri options are specified idletimeout will be used");
}
}
}

};