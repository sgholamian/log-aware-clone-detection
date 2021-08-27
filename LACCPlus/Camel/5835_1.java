//,temp,CamelCatalogTest.java,1052,1066,temp,CamelCatalogTest.java,1015,1035
//,3
public class xxx {
    @Test
    public void testPredicatePlaceholder() {
        LanguageValidationResult result = catalog.validateLanguagePredicate(null, "simple", "${body} contains '{{danger}}'");
        assertTrue(result.isSuccess());
        assertEquals("${body} contains '{{danger}}'", result.getText());

        result = catalog.validateLanguagePredicate(null, "simple", "${bdy} contains '{{danger}}'");
        assertFalse(result.isSuccess());
        assertEquals("${bdy} contains '{{danger}}'", result.getText());
        LOG.info(result.getError());
        assertTrue(result.getError().startsWith("Unknown function: bdy at location 0"));
        assertTrue(result.getError().contains("'{{danger}}'"));
        assertEquals("Unknown function: bdy", result.getShortError());
        assertEquals(0, result.getIndex());
    }

};