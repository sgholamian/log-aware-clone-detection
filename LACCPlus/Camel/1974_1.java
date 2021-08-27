//,temp,SheetsSpreadsheetsIT.java,95,127,temp,SheetsSpreadsheetsValuesIT.java,55,81
//,3
public class xxx {
    @Test
    public void testBatchUpdate() throws Exception {
        assertThatGoogleApi(getGoogleApiTestServer())
                .createSpreadsheetRequest()
                .hasSheetTitle("TestData")
                .andReturnRandomSpreadsheet();

        Spreadsheet testSheet = getSpreadsheet();
        String updateTitle = "updated-" + testSheet.getProperties().getTitle();

        assertThatGoogleApi(getGoogleApiTestServer())
                .batchUpdateSpreadsheetRequest(testSheet.getSpreadsheetId())
                .updateTitle(updateTitle)
                .andReturnUpdated();

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "spreadsheetId", testSheet.getSpreadsheetId());
        // parameter type is com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
        headers.put(GoogleSheetsConstants.PROPERTY_PREFIX + "batchUpdateSpreadsheetRequest", new BatchUpdateSpreadsheetRequest()
                .setIncludeSpreadsheetInResponse(true)
                .setRequests(Collections
                        .singletonList(new Request().setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
                                .setProperties(new SpreadsheetProperties().setTitle(updateTitle))
                                .setFields("title")))));

        final BatchUpdateSpreadsheetResponse result = requestBodyAndHeaders("direct://BATCHUPDATE", null, headers);

        assertNotNull(result, "batchUpdate result is null");
        assertEquals(updateTitle, result.getUpdatedSpreadsheet().getProperties().getTitle());

        LOG.debug("batchUpdate: " + result);
    }

};