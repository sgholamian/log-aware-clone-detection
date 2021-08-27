//,temp,CamelCatalogTest.java,1052,1066,temp,CamelCatalogTest.java,1015,1035
//,3
public class xxx {
    @Test
    public void testSimpleExpression() {
        LanguageValidationResult result = catalog.validateLanguageExpression(null, "simple", "${body}");
        assertTrue(result.isSuccess());
        assertEquals("${body}", result.getText());

        result = catalog.validateLanguageExpression(null, "simple", "${body");
        assertFalse(result.isSuccess());
        assertEquals("${body", result.getText());
        LOG.info(result.getError());
        assertTrue(result.getError().startsWith("expected symbol functionEnd but was eol at location 5"));
        assertEquals("expected symbol functionEnd but was eol", result.getShortError());
        assertEquals(5, result.getIndex());

        result = catalog.validateLanguageExpression(null, "simple", "${bodyxxx}");
        assertFalse(result.isSuccess());
        assertEquals("${bodyxxx}", result.getText());
        LOG.info(result.getError());
        assertEquals("Valid syntax: ${body.OGNL} was: bodyxxx", result.getShortError());
        assertEquals(0, result.getIndex());
    }

};