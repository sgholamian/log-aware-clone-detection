//,temp,BoxEventLogsManagerIT.java,47,69,temp,DriveFilesIT.java,220,251
//,3
public class xxx {
    @Disabled // Requires enterprise admin account to test
    @Test
    public void testGetEnterpriseEvents() throws Exception {
        Date before = new Date();
        Date after = new Date();
        after.setTime(before.getTime() - ONE_MINUTE_OF_MILLISECONDS);

        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.position", null);
        // parameter type is java.util.Date
        headers.put("CamelBox.after", after);
        // parameter type is java.util.Date
        headers.put("CamelBox.before", before);
        // parameter type is com.box.sdk.BoxEvent.Type[]
        headers.put("CamelBox.types", null);

        @SuppressWarnings("rawtypes")
        final java.util.List result = requestBodyAndHeaders("direct://GETENTERPRISEEVENTS", null, headers);

        assertNotNull(result, "getEnterpriseEvents result");
        LOG.debug("getEnterpriseEvents: " + result);
    }

};