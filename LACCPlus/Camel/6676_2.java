//,temp,SheetsSpreadsheetsValuesIT.java,162,199,temp,SheetsSpreadsheetsValuesIT.java,83,123
//,3
public class xxx {
    @Test
    public void testUpdate() throws Exception {
        assertThatGoogleApi(getGoogleApiTestServer())
                .createSpreadsheetRequest()
                .hasSheetTitle("TestData")
                .andReturnRandomSpreadsheet();

        Spreadsheet testSheet = getSpreadsheet();

        List<List<Object>> data = Arrays.asList(
                Arrays.asList("A1", "B1"),
                Arrays.asList("A2", "B2"));

        assertThatGoogleApi(getGoogleApiTestServer())
                .updateValuesRequest(testSheet.getSpreadsheetId(), TEST_SHEET + "!A1:B2", data)
                .andReturnUpdateResponse();

        ValueRange values = new ValueRange();
        values.setValues(data);

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "spreadsheetId", testSheet.getSpreadsheetId());
        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "range", TEST_SHEET + "!A1:B2");
        // parameter type is com.google.api.services.sheets.v4.model.ValueRange
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "values", values);

        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "valueInputOption", "USER_ENTERED");

        final UpdateValuesResponse result = requestBodyAndHeaders("direct://UPDATE", null, headers);

        assertNotNull(result, "update result is null");
        assertEquals(testSheet.getSpreadsheetId(), result.getSpreadsheetId());
        assertEquals(TEST_SHEET + "!A1:B2", result.getUpdatedRange());
        assertEquals(Integer.valueOf(2), result.getUpdatedRows());
        assertEquals(Integer.valueOf(4), result.getUpdatedCells());

        LOG.debug("update: " + result);
    }

};