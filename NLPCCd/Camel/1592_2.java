//,temp,sample_7528.java,2,15,temp,sample_1154.java,2,15
//,3
public class xxx {
public static void assertInMessageBodyEquals(Exchange exchange, Object expected) throws InvalidPayloadException {
assertNotNull("Should have a response exchange!", exchange);
Object actual;
if (expected == null) {
actual = ExchangeHelper.getMandatoryInBody(exchange);
assertEquals("in body of: " + exchange, expected, actual);
} else {
actual = ExchangeHelper.getMandatoryInBody(exchange, expected.getClass());
}
assertEquals("in body of: " + exchange, expected, actual);


log.info("received response with in");
}

};