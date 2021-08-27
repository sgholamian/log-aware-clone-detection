//,temp,sample_322.java,2,16,temp,sample_7703.java,2,16
//,3
public class xxx {
public void dummy_method(){
final CountDownLatch logonLatch = new CountDownLatch(2);
final CountDownLatch receivedMessageLatch = new CountDownLatch(1);
RouteBuilder routes = new RouteBuilder() {
public void configure() throws Exception {
from("quickfix:examples/inprocess.cfg"). filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.SessionLogon)). bean(new CountDownLatchDecrementer("logon", logonLatch));
from("quickfix:examples/inprocess.cfg"). filter(PredicateBuilder.or( header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AdminMessageReceived), header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived))). bean(new QuickfixjMessageJsonPrinter());
from("quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:MARKET->TRADER"). filter(header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL)). bean(new CountDownLatchDecrementer("message", receivedMessageLatch));
}
};
context.addRoutes(routes);


log.info("starting camel context");
}

};