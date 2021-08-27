//,temp,CamelInfluxDbConverterTest.java,71,87,temp,CamelInfluxDbConverterTest.java,35,51
//,3
public class xxx {
    @Test
    public void doesNotAddCamelHeaders() {
        Map<String, Object> pointInMapFormat = new HashMap<>();
        pointInMapFormat.put(InfluxDbConstants.MEASUREMENT_NAME, "testCPU");
        double value = 99.999999d;
        pointInMapFormat.put("busy", value);

        Point p = CamelInfluxDbConverters.fromMapToPoint(pointInMapFormat);
        assertNotNull(p);

        String line = p.lineProtocol();

        assertNotNull(line);
        LOG.debug("doesNotAddCamelHeaders generated: \"{}\"", line);
        assertTrue(!line.contains(InfluxDbConstants.MEASUREMENT_NAME));

    }

};