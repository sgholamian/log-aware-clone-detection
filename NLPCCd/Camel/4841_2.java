//,temp,sample_3515.java,2,16,temp,sample_3514.java,2,16
//,2
public class xxx {
public void dummy_method(){
if (body instanceof String[]) {
messageId = this.ironQueue.pushMessages((String[])body, configuration.getVisibilityDelay());
} else if (body instanceof String) {
if (configuration.isPreserveHeaders()) {
body = GsonUtil.getBodyFromMessage(exchange.getIn());
}
messageId = this.ironQueue.push((String)body, configuration.getVisibilityDelay());
} else {
throw new InvalidPayloadException(exchange, String.class);
}


log.info("send request from exchange");
}

};