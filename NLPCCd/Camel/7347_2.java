//,temp,sample_898.java,2,12,temp,sample_899.java,2,15
//,3
public class xxx {
private void deserializeBody(final Exchange camelExchange, final Message message, final byte[] body) {
Object messageBody = null;
try (InputStream b = new ByteArrayInputStream(body);
ObjectInputStream o = new ObjectInputStream(b)) {
messageBody = o.readObject();
} catch (IOException | ClassNotFoundException e) {
camelExchange.setException(e);
}
if (messageBody instanceof Throwable) {


log.info("reply was an exception setting the exception on the exchange");
}
}

};