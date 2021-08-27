//,temp,sample_7528.java,2,15,temp,sample_1154.java,2,15
//,3
public class xxx {
public static void assertInMessageBodyEquals(Exchange exchange, Object expected) throws InvalidPayloadException {
assertNotNull(exchange, "Should have a response exchange!");
Object actual;
if (expected == null) {
actual = exchange.getIn().getMandatoryBody();
assertEquals(actual, expected, "in body of: " + exchange);
} else {
actual = exchange.getIn().getMandatoryBody(expected.getClass());
}
assertEquals(actual, expected, "in body of: " + exchange);


log.info("received response with in");
}

};