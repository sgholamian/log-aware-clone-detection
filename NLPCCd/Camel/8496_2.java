//,temp,sample_289.java,2,15,temp,sample_3138.java,2,15
//,2
public class xxx {
public static void assertOutMessageBodyEquals(Exchange exchange, Object expected) throws InvalidPayloadException {
assertNotNull("Should have a response exchange!", exchange);
Object actual;
if (expected == null) {
actual = exchange.getOut().getMandatoryBody();
assertEquals("output body of: " + exchange, expected, actual);
} else {
actual = exchange.getOut().getMandatoryBody(expected.getClass());
}
assertEquals("output body of: " + exchange, expected, actual);


log.info("received response with out");
}

};