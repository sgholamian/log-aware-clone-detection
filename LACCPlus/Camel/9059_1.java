//,temp,ServiceNowIT.java,221,238,temp,ServiceNowIT.java,204,219
//,3
public class xxx {
    @Test
    public void testDateTimeWithCustomFormats() throws Exception {
        final ServiceNowConfiguration configuration = new ServiceNowConfiguration();
        configuration.setDateFormat("yyyyMMdd");
        configuration.setTimeFormat("HHmmss");

        ObjectMapper mapper = configuration.getOrCreateMapper();
        DateTimeBean bean = new DateTimeBean();
        String serialized = mapper.writeValueAsString(bean);

        LOGGER.debug(serialized);

        DateTimeBean deserialized = mapper.readValue(serialized, DateTimeBean.class);

        assertEquals(bean.dateTime, deserialized.dateTime);
        assertEquals(bean.date, deserialized.date);
        assertEquals(bean.time, deserialized.time);
    }

};