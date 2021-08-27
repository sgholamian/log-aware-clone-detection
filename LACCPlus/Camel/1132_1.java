//,temp,CamelInfluxDbConverterTest.java,71,87,temp,CamelInfluxDbConverterTest.java,35,51
//,3
public class xxx {
    @Test
    public void canAddInt() {
        Map<String, Object> pointInMapFormat = new HashMap<>();
        pointInMapFormat.put(InfluxDbConstants.MEASUREMENT_NAME, "testCPU");
        int value = 99999999;
        pointInMapFormat.put("busy", value);

        Point p = CamelInfluxDbConverters.fromMapToPoint(pointInMapFormat);
        assertNotNull(p);

        String line = p.lineProtocol();

        assertNotNull(line);
        LOG.debug("Int command generated: \"{}\"", line);
        assertTrue(line.contains("busy=99999999"));

    }

};