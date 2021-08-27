//,temp,sample_1401.java,2,16,temp,sample_2927.java,2,16
//,3
public class xxx {
public void dummy_method(){
final CountDownLatch receivedMessageLatch = new CountDownLatch(1);
RouteBuilder routes = new RouteBuilder() {
public void configure() throws Exception {
from("quickfix:examples/gateway.cfg"). filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.SessionLogon)). bean(new CountDownLatchDecrementer("logon", logonLatch));
from("quickfix:examples/gateway.cfg"). filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived)). recipientList(method(new FixMessageRouter("quickfix:examples/gateway.cfg")));
from("quickfix:examples/gateway.cfg"). filter(PredicateBuilder.or( header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived), header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageSent))). bean(new QuickfixjMessageJsonPrinter());
from("quickfix:examples/gateway.cfg?sessionID=FIX.4.2:TRADER@2->GATEWAY"). filter(PredicateBuilder.and( header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived), header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL))). bean(new CountDownLatchDecrementer("message", receivedMessageLatch));
}
};
context.addRoutes(routes);


log.info("starting camel context");
}

};