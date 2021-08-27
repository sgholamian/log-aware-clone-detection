//,temp,sample_7529.java,2,15,temp,sample_3137.java,2,15
//,2
public class xxx {
public static void assertOutMessageBodyEquals(Exchange exchange, Object expected) throws InvalidPayloadException {
assertNotNull(exchange, "Should have a response exchange!");
Object actual;
if (expected == null) {
actual = exchange.getOut().getMandatoryBody();
assertEquals(actual, expected, "output body of: " + exchange);
} else {
actual = exchange.getOut().getMandatoryBody(expected.getClass());
}
assertEquals(actual, expected, "output body of: " + exchange);


log.info("received response with out");
}

};