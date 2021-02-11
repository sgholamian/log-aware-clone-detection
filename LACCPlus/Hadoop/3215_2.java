//,temp,TestOzoneShell.java,818,909,temp,TestOzoneShell.java,334,445
//,3
public class xxx {
  @Test
  public void testListVolume() throws Exception {
    LOG.info("Running testListVolume");
    String protocol = clientProtocol.getName().toLowerCase();
    String commandOutput, commandError;
    List<VolumeInfo> volumes;
    final int volCount = 20;
    final String user1 = "test-user-a-" + protocol;
    final String user2 = "test-user-b-" + protocol;

    // Create 20 volumes, 10 for user1 and another 10 for user2.
    for (int x = 0; x < volCount; x++) {
      String volumeName;
      String userName;

      if (x % 2 == 0) {
        // create volume [test-vol0, test-vol2, ..., test-vol18] for user1
        userName = user1;
        volumeName = "test-vol-" + protocol + x;
      } else {
        // create volume [test-vol1, test-vol3, ..., test-vol19] for user2
        userName = user2;
        volumeName = "test-vol-" + protocol + x;
      }
      VolumeArgs volumeArgs = VolumeArgs.newBuilder()
          .setOwner(userName)
          .setQuota("100TB")
          .build();
      client.createVolume(volumeName, volumeArgs);
      OzoneVolume vol = client.getVolumeDetails(volumeName);
      assertNotNull(vol);
    }

    String[] args = new String[] {"-listVolume", url + "/abcde", "-user",
        user1, "-length", "100"};
    assertEquals(1, ToolRunner.run(shell, args));
    commandError = err.toString();
    Assert.assertTrue(commandError.contains("Invalid URI:"));

    err.reset();
    // test -length option
    args = new String[] {"-listVolume", url + "/", "-user",
        user1, "-length", "100"};
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    volumes = (List<VolumeInfo>) JsonUtils
        .toJsonList(commandOutput, VolumeInfo.class);

    assertEquals(10, volumes.size());
    for (VolumeInfo volume : volumes) {
      assertEquals(volume.getOwner().getName(), user1);
      assertTrue(volume.getCreatedOn().contains(OzoneConsts.OZONE_TIME_ZONE));
    }

    out.reset();
    args = new String[] {"-listVolume", url + "/", "-user",
        user1, "-length", "2"};
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    volumes = (List<VolumeInfo>) JsonUtils
        .toJsonList(commandOutput, VolumeInfo.class);

    assertEquals(2, volumes.size());

    // test -prefix option
    out.reset();
    args = new String[] { "-listVolume", url + "/", "-user", user1, "-length",
        "100", "-prefix", "test-vol-" + protocol + "1" };
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    volumes = (List<VolumeInfo>) JsonUtils
        .toJsonList(commandOutput, VolumeInfo.class);

    assertEquals(5, volumes.size());
    // return volume names should be [test-vol10, test-vol12, ..., test-vol18]
    for (int i = 0; i < volumes.size(); i++) {
      assertEquals(volumes.get(i).getVolumeName(),
          "test-vol-" + protocol + ((i + 5) * 2));
      assertEquals(volumes.get(i).getOwner().getName(), user1);
    }

    // test -start option
    out.reset();
    args = new String[] { "-listVolume", url + "/", "-user", user2, "-length",
        "100", "-start", "test-vol-" + protocol + "15" };
    assertEquals(0, ToolRunner.run(shell, args));
    commandOutput = out.toString();
    volumes = (List<VolumeInfo>) JsonUtils
        .toJsonList(commandOutput, VolumeInfo.class);

    assertEquals(2, volumes.size());

    assertEquals(volumes.get(0).getVolumeName(), "test-vol-" + protocol + "17");
    assertEquals(volumes.get(1).getVolumeName(), "test-vol-" + protocol + "19");
    assertEquals(volumes.get(0).getOwner().getName(), user2);
    assertEquals(volumes.get(1).getOwner().getName(), user2);

    // test error conditions
    err.reset();
    args  = new String[] {"-listVolume", url + "/", "-user",
        user2, "-length", "-1"};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "the vaule should be a positive number"));

    err.reset();
    args  = new String[] {"-listVolume", url + "/", "-user",
        user2, "-length", "invalid-length"};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "the vaule should be digital"));
  }

};