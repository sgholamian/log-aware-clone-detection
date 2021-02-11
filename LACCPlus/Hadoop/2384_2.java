//,temp,TestOzoneShell.java,667,690,temp,TestOzoneShell.java,447,468
//,3
public class xxx {
  @Test
  public void testCreateBucket() throws Exception {
    LOG.info("Running testCreateBucket");
    OzoneVolume vol = creatVolume();
    String bucketName = "bucket" + RandomStringUtils.randomNumeric(5);
    String[] args = new String[] {"-createBucket",
        url + "/" + vol.getName() + "/" + bucketName};

    assertEquals(0, ToolRunner.run(shell, args));
    OzoneBucket bucketInfo = vol.getBucket(bucketName);
    assertEquals(vol.getName(),
        bucketInfo.getVolumeName());
    assertEquals(bucketName, bucketInfo.getName());

    // test create a bucket in a non-exist volume
    args = new String[] {"-createBucket",
        url + "/invalid-volume/" + bucketName};

    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "Info Volume failed, error:VOLUME_NOT_FOUND"));
  }

};