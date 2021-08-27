//,temp,sample_776.java,2,17,temp,sample_8484.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (multicastSubnet.getInfo().isInRange(configuration.getHost())) {
ChannelFuture channelFuture = bootstrap.bind(configuration.getPort()).sync();
channel = channelFuture.channel();
DatagramChannel datagramChannel = (DatagramChannel) channel;
String networkInterface = configuration.getNetworkInterface() == null ? LOOPBACK_INTERFACE : configuration.getNetworkInterface();
multicastNetworkInterface = NetworkInterface.getByName(networkInterface);
ObjectHelper.notNull(multicastNetworkInterface, "No network interface found for '" + networkInterface + "'.");
datagramChannel.joinGroup(hostAddress, multicastNetworkInterface).syncUninterruptibly();
allChannels.add(datagramChannel);
} else {


log.info("connectionlessbootstrap binding to");
}
}

};