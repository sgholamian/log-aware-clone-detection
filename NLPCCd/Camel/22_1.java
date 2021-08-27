//,temp,sample_1519.java,2,9,temp,sample_1520.java,2,11
//,3
public class xxx {
public void configureListenerContainer(AbstractMessageListenerContainer listenerContainer, JmsConsumer consumer) {
if (destinationName != null) {
listenerContainer.setDestinationName(destinationName);


log.info("using destinationname on listenercontainer");
}
}

};