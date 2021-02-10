//,temp,TestOzoneShell.java,470,506,temp,TestOzoneShell.java,447,468
//,3
public class xxx {
  @Test
  public void testDeleteBucket() throws Exception {
    LOG.info("Running testDeleteBucket");
    OzoneVolume vol = creatVolume();
    String bucketName = "bucket" + RandomStringUtils.randomNumeric(5);
    vol.createBucket(bucketName);
    OzoneBucket bucketInfo = vol.getBucket(bucketName);
    assertNotNull(bucketInfo);

    String[] args = new String[] {"-deleteBucket",
        url + "/" + vol.getName() + "/" + bucketName};
    assertEquals(0, ToolRunner.run(shell, args));

    // verify if bucket has been deleted in volume
    try {
      vol.getBucket(bucketName);
      fail("Get bucket should have thrown.");
    } catch (IOException e) {
      GenericTestUtils.assertExceptionContains(
          "Info Bucket failed, error: BUCKET_NOT_FOUND", e);
    }

    // test delete bucket in a non-exist volume
    args = new String[] {"-deleteBucket",
        url + "/invalid-volume" + "/" + bucketName};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "Info Volume failed, error:VOLUME_NOT_FOUND"));

    err.reset();
    // test delete non-exist bucket
    args = new String[] {"-deleteBucket",
        url + "/" + vol.getName() + "/invalid-bucket"};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "Delete Bucket failed, error:BUCKET_NOT_FOUND"));
  }

};