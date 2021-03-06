//,temp,sample_3814.java,2,16,temp,sample_3815.java,2,16
//,2
public class xxx {
public void testSymlinkRejectsForwardSlashesInLink() throws IOException {
requireWinutils();
File newFile = new File(TEST_DIR, "file");
assertTrue(newFile.createNewFile());
String target = newFile.getPath();
String link = new File(TEST_DIR, "link").getPath().replaceAll("\\\\", "/");
try {
Shell.execCommand(winutils, "symlink", link, target);
fail(String.format("did not receive expected failure creating symlink " + "with forward slashes in link: link = %s, target = %s", link, target));
} catch (IOException e) {


log.info("expected failed to create symlink with forward slashes in target");
}
}

};