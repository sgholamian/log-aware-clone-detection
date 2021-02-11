//,temp,TestOzoneShell.java,818,909,temp,TestOzoneShell.java,334,445
//,3
public class xxx {
  @Test
  public void testListKey() throws Exception {
    LOG.info("Running testListKey");
    String commandOutput;
    List<KeyInfo> keys;
    int keyCount = 11;
    OzoneBucket bucket = creatBucket();
    String volumeName = bucket.getVolumeName();
    String bucketName = bucket.getName();

    String keyName;
    List<String> keyNames = new ArrayList<>();
    for (int i = 0; i < keyCount; i++) {
      keyName = "test-key" + i;
      keyNames.add(keyName);
      String dataStr = "test-data";
      OzoneOutputStream keyOutputStream =
          bucket.createKey(keyName, dataStr.length());
      keyOutputStream.write(dataStr.getBytes());
      keyOutputStream.close();
    }

    // test -length option
    String[] args = new String[] {"-listKey",
        url + "/" + volumeName + "/" + bucketName, "-length", "100"};
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    keys = (List<KeyInfo>) JsonUtils.toJsonList(commandOutput,
        KeyInfo.class);

    assertEquals(11, keys.size());
    // sort key names since the return keys isn't in created order
    Collections.sort(keyNames);
    // return key names should be [test-key0, test-key1,
    // test-key10, test-key2, ,..., test-key9]
    for (int i = 0; i < keys.size(); i++) {
      assertEquals(keys.get(i).getKeyName(), keyNames.get(i));
      // verify the creation/modification time of key
      assertTrue(keys.get(i).getCreatedOn()
          .contains(OzoneConsts.OZONE_TIME_ZONE));
      assertTrue(keys.get(i).getModifiedOn()
          .contains(OzoneConsts.OZONE_TIME_ZONE));
    }

    out.reset();
    args = new String[] {"-listKey", url + "/" + volumeName + "/" + bucketName,
        "-length", "3"};
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    keys = (List<KeyInfo>) JsonUtils.toJsonList(commandOutput,
        KeyInfo.class);

    assertEquals(3, keys.size());
    // return key names should be [test-key0, test-key1, test-key10]
    assertEquals(keys.get(0).getKeyName(), "test-key0");
    assertEquals(keys.get(1).getKeyName(), "test-key1");
    assertEquals(keys.get(2).getKeyName(), "test-key10");

    // test -prefix option
    out.reset();
    args = new String[] {"-listKey", url + "/" + volumeName + "/" + bucketName,
        "-length", "100", "-prefix", "test-key1"};
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    keys = (List<KeyInfo>) JsonUtils.toJsonList(commandOutput,
        KeyInfo.class);

    assertEquals(2, keys.size());
    // return key names should be [test-key1, test-key10]
    assertEquals(keys.get(0).getKeyName(), "test-key1");
    assertEquals(keys.get(1).getKeyName(), "test-key10");

    // test -start option
    out.reset();
    args = new String[] {"-listKey", url + "/" + volumeName + "/" + bucketName,
        "-length", "100", "-start", "test-key7"};
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    keys = (List<KeyInfo>) JsonUtils.toJsonList(commandOutput,
        KeyInfo.class);

    assertEquals(keys.get(0).getKeyName(), "test-key8");
    assertEquals(keys.get(1).getKeyName(), "test-key9");

    // test error conditions
    err.reset();
    args = new String[] {"-listKey", url + "/" + volumeName + "/" + bucketName,
        "-length", "-1"};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "the vaule should be a positive number"));
  }

};