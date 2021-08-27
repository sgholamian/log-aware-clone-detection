//,temp,sample_5816.java,2,14,temp,sample_5815.java,2,11
//,3
public class xxx {
public boolean validateObject(ChannelFuture channelFuture) {
if (!channelFuture.isDone()) {
return true;
}
if (!channelFuture.isSuccess()) {
return false;
}
Channel channel = channelFuture.channel();
boolean answer = channel.isActive();


log.info("validating channel");
}

};