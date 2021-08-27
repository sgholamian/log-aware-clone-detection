//,temp,sample_1401.java,2,16,temp,sample_2927.java,2,16
//,3
public class xxx {
public void dummy_method(){
RouteBuilder routes = new RouteBuilder() {
public void configure() throws Exception {
from("quickfix:examples/inprocess.cfg"). filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.SessionLogon)). bean(new CountDownLatchDecrementer("logon", logonLatch));
from("quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:MARKET->TRADER"). filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived)). to("trade-executor:market");
from("trade-executor:market").to("quickfix:examples/inprocess.cfg");
from("quickfix:examples/inprocess.cfg"). filter(PredicateBuilder.or( header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived), header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageSent))). bean(new QuickfixjMessageJsonPrinter());
from("quickfix:examples/inprocess.cfg?sessionID=FIX.4.2:TRADER->MARKET"). filter(PredicateBuilder.and( header(QuickfixjEndpoint.EVENT_CATEGORY_KEY).isEqualTo(QuickfixjEventCategory.AppMessageReceived), header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EXECUTION_REPORT))). bean(new CountDownLatchDecrementer("execution report", executionReportLatch));
}
};
context.addRoutes(routes);


log.info("starting camel context");
}

};