//,temp,TestOzoneShell.java,508,530,temp,TestOzoneShell.java,265,289
//,3
public class xxx {
  @Test
  public void testInfoBucket() throws Exception {
    LOG.info("Running testInfoBucket");
    OzoneVolume vol = creatVolume();
    String bucketName = "bucket" + RandomStringUtils.randomNumeric(5);
    vol.createBucket(bucketName);

    String[] args = new String[] {"-infoBucket",
        url + "/" + vol.getName() + "/" + bucketName};
    assertEquals(0, ToolRunner.run(shell, args));

    String output = out.toString();
    assertTrue(output.contains(bucketName));
    assertTrue(output.contains("createdOn")
        && output.contains(OzoneConsts.OZONE_TIME_ZONE));

    // test get info from a non-exist bucket
    args = new String[] {"-infoBucket",
        url + "/" + vol.getName() + "/invalid-bucket" + bucketName};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "Info Bucket failed, error: BUCKET_NOT_FOUND"));
  }

};