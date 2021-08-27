//,temp,sample_3270.java,2,9,temp,sample_4992.java,2,8
//,3
public class xxx {
public void doSomething(String body, Exchange exchange) {
ObjectHelper.notNull(destination, "destination");
ObjectHelper.notNull(exchange, "exchange");
ObjectHelper.notNull(exchange.getContext(), "exchange.getContext");


log.info("received body");
}

};