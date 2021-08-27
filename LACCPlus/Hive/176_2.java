//,temp,TestBeelineArgParsing.java,338,352,temp,TestBeelineArgParsing.java,326,336
//,3
public class xxx {
  @Test
  public void testAddLocalJar() throws Exception {
    TestBeeline bl = new TestBeeline();
    Assert.assertNull(bl.findLocalDriver(connectionString));

    LOG.info("Add " + driverJarFileName + " for the driver class " + driverClazzName);

    bl.addLocalJar(driverJarFileName);
    bl.addlocaldrivername(driverClazzName);
    Assert.assertEquals(bl.findLocalDriver(connectionString).getClass().getName(), driverClazzName);
  }

};