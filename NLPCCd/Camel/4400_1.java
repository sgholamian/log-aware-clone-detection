//,temp,sample_322.java,2,16,temp,sample_7703.java,2,16
//,3
public class xxx {
public void dummy_method(){
DefaultCamelContext context = new DefaultCamelContext();
final CountDownLatch logoutLatch = new CountDownLatch(1);
RouteBuilder routes = new RouteBuilder() {
public void configure() throws Exception {
from("quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:TRADER->MARKET"). filter(PredicateBuilder.and( header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AdminMessageSent), header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.LOGON))). bean(new CredentialInjector("PASSWORD"));
from("quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:TRADER->MARKET"). filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.SessionLogoff)). bean(new CountDownLatchDecrementer("logout", logoutLatch));
from("quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:MARKET->TRADER"). filter(PredicateBuilder.and( header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AdminMessageReceived), header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.LOGON))). bean(new LogonAuthenticator());
}
};
context.addRoutes(routes);


log.info("starting camel context");
}

};