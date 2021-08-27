//,temp,sample_937.java,2,17,temp,sample_3895.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (sync) {
latch = new CountDownLatch(1);
ResponseHandler handler = (ResponseHandler) session.getHandler();
handler.reset();
}
if (LOG.isDebugEnabled()) {
Object out = body;
if (body instanceof byte[]) {
out = exchange.getContext().getTypeConverter().convertTo(String.class, body);
}


log.info("writing body");
}
}

};