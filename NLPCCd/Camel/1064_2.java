//,temp,sample_3937.java,2,18,temp,sample_3938.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (null == getEndpoint().getHostname()) {
socketAddress = new InetSocketAddress(getEndpoint().getPort());
} else {
socketAddress = new InetSocketAddress(getEndpoint().getHostname(), getEndpoint().getPort());
}
long startTicks = System.currentTimeMillis();
if (getConfiguration().hasMaxReceiveTimeouts()) {
if (getConfiguration().hasIdleTimeout()) {
} else {
getConfiguration().setIdleTimeout(getConfiguration().getMaxReceiveTimeouts() * getConfiguration().getReceiveTimeout());


log.info("deprecated uri option maxreceivedtimeouts specified idletimeout will be used");
}
}
}

};