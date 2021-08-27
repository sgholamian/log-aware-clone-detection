//,temp,sample_5802.java,2,17,temp,sample_2780.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (ExchangeHelper.isOutCapable(exchange)) {
close = exchange.getOut().getHeader(Mina2Constants.MINA_CLOSE_SESSION_WHEN_COMPLETE, Boolean.class);
} else {
close = exchange.getIn().getHeader(Mina2Constants.MINA_CLOSE_SESSION_WHEN_COMPLETE, Boolean.class);
}
boolean disconnect = getEndpoint().getConfiguration().isDisconnect();
if (close != null) {
disconnect = close;
}
if (disconnect) {


log.info("closing session when complete at address");
}
}

};