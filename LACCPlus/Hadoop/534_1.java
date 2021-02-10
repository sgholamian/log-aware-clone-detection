//,temp,TestWinUtils.java,432,446,temp,TestWinUtils.java,416,430
//,2
public class xxx {
  @Test (timeout = 30000)
  public void testSymlinkRejectsForwardSlashesInTarget() throws IOException {
    File newFile = new File(TEST_DIR, "file");
    assertTrue(newFile.createNewFile());
    String target = newFile.getPath().replaceAll("\\\\", "/");
    String link = new File(TEST_DIR, "link").getPath();
    try {
      Shell.execCommand(Shell.WINUTILS, "symlink", link, target);
      fail(String.format("did not receive expected failure creating symlink "
        + "with forward slashes in target: link = %s, target = %s", link, target));
    } catch (IOException e) {
      LOG.info(
        "Expected: Failed to create symlink with forward slashes in target");
    }
  }

};