//,temp,SheetsSpreadsheetsValuesIT.java,162,199,temp,SheetsSpreadsheetsValuesIT.java,83,123
//,3
public class xxx {
    @Test
    public void testClear() throws Exception {
        String spreadsheetId = UUID.randomUUID().toString();

        assertThatGoogleApi(getGoogleApiTestServer())
                .createSpreadsheetRequest()
                .hasSheetTitle("TestData")
                .andReturnSpreadsheet(spreadsheetId);

        Spreadsheet testSheet = getSpreadsheet();

        assertThatGoogleApi(getGoogleApiTestServer())
                .updateValuesRequest(spreadsheetId, TEST_SHEET + "!A1:B2",
                        Arrays.asList(Arrays.asList("a1", "b1"), Arrays.asList("a2", "b2")))
                .andReturnUpdateResponse();

        applyTestData(testSheet);

        assertThatGoogleApi(getGoogleApiTestServer())
                .clearValuesRequest(testSheet.getSpreadsheetId(), TEST_SHEET + "!A1:B2")
                .andReturnClearResponse(TEST_SHEET + "!A1:B2");

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "spreadsheetId", testSheet.getSpreadsheetId());
        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "range", TEST_SHEET + "!A1:B2");
        // parameter type is com.google.api.services.sheets.v4.model.ClearValuesRequest
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "clearValuesRequest", new ClearValuesRequest());

        final ClearValuesResponse result = requestBodyAndHeaders("direct://CLEAR", null, headers);

        assertNotNull(result, "clear result is null");
        assertEquals(testSheet.getSpreadsheetId(), result.getSpreadsheetId());
        assertEquals(TEST_SHEET + "!A1:B2", result.getClearedRange());

        LOG.debug("clear: " + result);
    }

};