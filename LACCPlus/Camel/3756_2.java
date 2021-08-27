//,temp,CamelInfluxDbConverterTest.java,89,105,temp,CamelInfluxDbConverterTest.java,53,69
//,3
public class xxx {
    @Test
    public void canAddDouble() {
        Map<String, Object> pointInMapFormat = new HashMap<>();
        pointInMapFormat.put(InfluxDbConstants.MEASUREMENT_NAME, "testCPU");
        double value = 99.999999d;
        pointInMapFormat.put("busy", value);

        Point p = CamelInfluxDbConverters.fromMapToPoint(pointInMapFormat);
        assertNotNull(p);

        String line = p.lineProtocol();

        assertNotNull(line);
        LOG.debug("Doublecommand generated: \"{}\"", line);
        assertTrue(line.contains("busy=99.999999"));

    }

};