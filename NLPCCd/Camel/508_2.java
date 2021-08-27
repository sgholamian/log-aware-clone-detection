//,temp,sample_7343.java,2,9,temp,sample_7500.java,2,10
//,3
public class xxx {
public boolean unregister(String messageName) {
if (!StringUtils.isEmpty(messageName)) {
if (consumerRegistry.remove(messageName) == null) {


log.info("consumer with message name was already unregistered");
}
}
}

};