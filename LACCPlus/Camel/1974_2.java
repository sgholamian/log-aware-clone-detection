//,temp,SheetsSpreadsheetsIT.java,95,127,temp,SheetsSpreadsheetsValuesIT.java,55,81
//,3
public class xxx {
    @Test
    public void testGet() throws Exception {
        assertThatGoogleApi(getGoogleApiTestServer())
                .createSpreadsheetRequest()
                .hasSheetTitle("TestData")
                .andReturnRandomSpreadsheet();

        Spreadsheet testSheet = getSpreadsheet();

        assertThatGoogleApi(getGoogleApiTestServer())
                .getValuesRequest(testSheet.getSpreadsheetId(), TEST_SHEET + "!A1:B2")
                .andReturnValues(Collections.emptyList());

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "spreadsheetId", testSheet.getSpreadsheetId());
        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "range", TEST_SHEET + "!A1:B2");

        final ValueRange result = requestBodyAndHeaders("direct://GET", null, headers);

        assertNotNull(result, "get result is null");
        assertEquals(TEST_SHEET + "!A1:B2", result.getRange());
        assertTrue(ObjectHelper.isEmpty(result.getValues()), "expected empty value range but found entries");

        LOG.debug("get: " + result);
    }

};