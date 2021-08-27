//,temp,TestBeelineArgParsing.java,338,352,temp,TestBeelineArgParsing.java,326,336
//,3
public class xxx {
  @Test
  public void testAddLocalJarWithoutAddDriverClazz() throws Exception {
    TestBeeline bl = new TestBeeline();

    LOG.info("Add " + driverJarFileName + " for the driver class " + driverClazzName);
    assertTrue("expected to exists: "+driverJarFileName,new File(driverJarFileName).exists());
    bl.addLocalJar(driverJarFileName);
    if (!defaultSupported) {
      Assert.assertNull(bl.findLocalDriver(connectionString));
    } else {
      // no need to add for the default supported local jar driver
      Assert.assertNotNull(bl.findLocalDriver(connectionString));
      Assert.assertEquals(bl.findLocalDriver(connectionString).getClass().getName(), driverClazzName);
    }
  }

};