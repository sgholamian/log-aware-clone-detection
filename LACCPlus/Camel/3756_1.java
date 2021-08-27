//,temp,CamelInfluxDbConverterTest.java,89,105,temp,CamelInfluxDbConverterTest.java,53,69
//,3
public class xxx {
    @Test
    public void canAddByte() {
        Map<String, Object> pointInMapFormat = new HashMap<>();
        pointInMapFormat.put(InfluxDbConstants.MEASUREMENT_NAME, "testCPU");
        byte value = Byte.MAX_VALUE;
        pointInMapFormat.put("busy", value);

        Point p = CamelInfluxDbConverters.fromMapToPoint(pointInMapFormat);
        assertNotNull(p);

        String line = p.lineProtocol();

        assertNotNull(line);
        LOG.debug("Byte command generated: \"{}\"", line);
        assertTrue(line.contains("busy=127"));

    }

};