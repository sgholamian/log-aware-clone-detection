//,temp,XmlParseTest.java,383,388,temp,XmlParseTest.java,375,381
//,3
public class xxx {
    protected void assertTo(String message, ProcessorDefinition<?> processor, String uri) {
        ToDefinition value = assertIsInstanceOf(ToDefinition.class, processor);
        String text = message + "To URI";
        log.info("Testing: {} is equal to: {} for processor: {}", text, uri, processor);
        assertEquals(uri, value.getUri(), text);
    }

};