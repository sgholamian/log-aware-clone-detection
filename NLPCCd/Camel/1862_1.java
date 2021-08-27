//,temp,sample_5056.java,2,12,temp,sample_1219.java,2,12
//,2
public class xxx {
public void messageReceived(IoSession session, Object object) throws Exception {
if (LOG.isDebugEnabled()) {
Object in = object;
if (in instanceof byte[]) {
in = getEndpoint().getCamelContext().getTypeConverter().convertTo(String.class, in);
}


log.info("received body");
}
}

};