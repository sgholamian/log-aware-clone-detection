//,temp,sample_7529.java,2,15,temp,sample_3137.java,2,15
//,2
public class xxx {
public static void assertInMessageBodyEquals(Exchange exchange, Object expected) throws InvalidPayloadException {
assertNotNull("Should have a response exchange!", exchange);
Object actual;
if (expected == null) {
actual = exchange.getIn().getMandatoryBody();
assertEquals("in body of: " + exchange, expected, actual);
} else {
actual = exchange.getIn().getMandatoryBody(expected.getClass());
}
assertEquals("in body of: " + exchange, expected, actual);


log.info("received response with in");
}

};