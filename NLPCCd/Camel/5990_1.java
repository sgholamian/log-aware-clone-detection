//,temp,sample_1224.java,2,13,temp,sample_1223.java,2,11
//,3
public class xxx {
protected Object assertReceivedValidExchange() throws Exception {
assertTrue(latch.await(5, TimeUnit.SECONDS));
assertNotNull(receivedExchange);
XmppMessage receivedMessage = (XmppMessage)receivedExchange.getIn();
assertEquals("cheese header", 123, receivedMessage.getHeader("cheese"));
Object body = receivedMessage.getBody();
Message xmppMessage = receivedMessage.getXmppMessage();
assertNotNull(xmppMessage);


log.info("received xmpp message");
}

};