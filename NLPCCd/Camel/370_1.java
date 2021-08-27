//,temp,sample_2475.java,2,11,temp,sample_852.java,2,9
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Object body = exchange.getIn().getBody();
if (body != null) {
eventBus.post(body);
} else {


log.info("body is null cannot post to eventbus");
}
}

};