//,temp,sample_6927.java,2,14,temp,sample_6925.java,2,14
//,3
public class xxx {
public void canAddByte() {
Map<String, Object> pointInMapFormat = new HashMap<>();
pointInMapFormat.put(InfluxDbConstants.MEASUREMENT_NAME, "testCPU");
byte value = Byte.MAX_VALUE;
pointInMapFormat.put("busy", value);
Point p = CamelInfluxDbConverters.fromMapToPoint(pointInMapFormat);
assertNotNull(p);
String line = p.lineProtocol();
assertNotNull(line);


log.info("byte command generated");
}

};