//,temp,TestOzoneShell.java,667,690,temp,TestOzoneShell.java,447,468
//,3
public class xxx {
  @Test
  public void testPutKey() throws Exception {
    LOG.info("Running testPutKey");
    OzoneBucket bucket = creatBucket();
    String volumeName = bucket.getVolumeName();
    String bucketName = bucket.getName();
    String keyName = "key" + RandomStringUtils.randomNumeric(5);

    String[] args = new String[] {"-putKey",
        url + "/" + volumeName + "/" + bucketName + "/" + keyName, "-file",
        createTmpFile()};
    assertEquals(0, ToolRunner.run(shell, args));

    OzoneKey keyInfo = bucket.getKey(keyName);
    assertEquals(keyName, keyInfo.getName());

    // test put key in a non-exist bucket
    args = new String[] {"-putKey",
        url + "/" + volumeName + "/invalid-bucket/" + keyName, "-file",
        createTmpFile()};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "Info Bucket failed, error: BUCKET_NOT_FOUND"));
  }

};