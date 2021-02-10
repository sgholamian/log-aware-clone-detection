//,temp,TestOzoneShell.java,508,530,temp,TestOzoneShell.java,265,289
//,3
public class xxx {
  @Test
  public void testInfoVolume() throws Exception {
    LOG.info("Running testInfoVolume");
    String volumeName = "volume" + RandomStringUtils.randomNumeric(5);
    VolumeArgs volumeArgs = VolumeArgs.newBuilder()
        .setOwner("bilbo")
        .setQuota("100TB")
        .build();
    client.createVolume(volumeName, volumeArgs);

    String[] args = new String[] {"-infoVolume", url + "/" + volumeName,
        "-root"};
    assertEquals(0, ToolRunner.run(shell, args));

    String output = out.toString();
    assertTrue(output.contains(volumeName));
    assertTrue(output.contains("createdOn")
        && output.contains(OzoneConsts.OZONE_TIME_ZONE));

    // get info for non-exist volume
    args = new String[] {"-infoVolume", url + "/invalid-volume", "-root"};
    assertEquals(1, ToolRunner.run(shell, args));
    assertTrue(err.toString().contains(
        "Info Volume failed, error:VOLUME_NOT_FOUND"));
  }

};