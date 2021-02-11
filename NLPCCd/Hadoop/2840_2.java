//,temp,sample_3814.java,2,16,temp,sample_3815.java,2,16
//,2
public class xxx {
public void testSymlinkRejectsForwardSlashesInTarget() throws IOException {
requireWinutils();
File newFile = new File(TEST_DIR, "file");
assertTrue(newFile.createNewFile());
String target = newFile.getPath().replaceAll("\\\\", "/");
String link = new File(TEST_DIR, "link").getPath();
try {
Shell.execCommand(winutils, "symlink", link, target);
fail(String.format("did not receive expected failure creating symlink " + "with forward slashes in target: link = %s, target = %s", link, target));
} catch (IOException e) {


log.info("expected failed to create symlink with forward slashes in target");
}
}

};