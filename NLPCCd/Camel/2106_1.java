//,temp,sample_6926.java,2,14,temp,sample_6924.java,2,14
//,2
public class xxx {
public void canAddInt() {
Map<String, Object> pointInMapFormat = new HashMap<>();
pointInMapFormat.put(InfluxDbConstants.MEASUREMENT_NAME, "testCPU");
int value = 99999999;
pointInMapFormat.put("busy", value);
Point p = CamelInfluxDbConverters.fromMapToPoint(pointInMapFormat);
assertNotNull(p);
String line = p.lineProtocol();
assertNotNull(line);


log.info("int command generated");
}

};