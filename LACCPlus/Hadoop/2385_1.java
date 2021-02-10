//,temp,TestOzoneShell.java,777,816,temp,TestOzoneShell.java,508,530
//,3
public class xxx {
  @Test
  public void testInfoKey() throws Exception {
    LOG.info("Running testInfoKey");
    String keyName = "key" + RandomStringUtils.randomNumeric(5);
    OzoneBucket bucket = creatBucket();
    String volumeName = bucket.getVolumeName();
    String bucketName = bucket.getName();
    String dataStr = "test-data";
    OzoneOutputStream keyOutputStream =
        bucket.createKey(keyName, dataStr.length());
    keyOutputStream.write(dataStr.getBytes());
    keyOutputStream.close();

    String[] args = new String[] {"-infoKey",
        url + "/" + volumeName + "/" + bucketName + "/" + keyName};

    // verify the response output
    int a = ToolRunner.run(shell, args);
    String output = out.toString();
    assertEquals(0, a);

    assertTrue(output.contains(keyName));
    assertTrue(
        output.contains("createdOn") && output.contains("modifiedOn") && output
            .contains(OzoneConsts.OZONE_TIME_ZONE));

    // reset stream
    out.reset();
    err.reset();

    // get the info of a non-exist key
    args = new String[] {"-infoKey",
        url + "/" + volumeName + "/" + bucketName + "/invalid-key"};

    // verify the response output
    // get the non-exist key info should be failed
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "Lookup key failed, error:KEY_NOT_FOUND"));
  }

};