//,temp,sample_6926.java,2,14,temp,sample_6924.java,2,14
//,2
public class xxx {
public void doesNotAddCamelHeaders() {
Map<String, Object> pointInMapFormat = new HashMap<>();
pointInMapFormat.put(InfluxDbConstants.MEASUREMENT_NAME, "testCPU");
double value = 99.999999d;
pointInMapFormat.put("busy", value);
Point p = CamelInfluxDbConverters.fromMapToPoint(pointInMapFormat);
assertNotNull(p);
String line = p.lineProtocol();
assertNotNull(line);


log.info("doesnotaddcamelheaders generated");
}

};