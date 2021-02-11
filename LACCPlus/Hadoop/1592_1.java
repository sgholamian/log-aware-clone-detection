//,temp,TestNativeIO.java,214,254,temp,TestNativeIO.java,167,212
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testCreateFile() throws Exception {
    if (!Path.WINDOWS) {
      return;
    }

    LOG.info("Open a file on Windows with SHARE_DELETE shared mode");
    try {
      File testfile = new File(TEST_DIR, "testCreateFile");
      assertTrue("Create test subject",
          testfile.exists() || testfile.createNewFile());

      FileDescriptor fd = NativeIO.Windows.createFile(
          testfile.getCanonicalPath(),
          NativeIO.Windows.GENERIC_READ,
          NativeIO.Windows.FILE_SHARE_READ |
          NativeIO.Windows.FILE_SHARE_WRITE |
          NativeIO.Windows.FILE_SHARE_DELETE,
          NativeIO.Windows.OPEN_EXISTING);

      FileInputStream fin = new FileInputStream(fd);
      try {
        fin.read();

        File newfile = new File(TEST_DIR, "testRenamedFile");

        boolean renamed = testfile.renameTo(newfile);
        assertTrue("Rename failed.", renamed);

        fin.read();
      } catch (Exception e) {
        fail("Got unexpected exception: " + e.getMessage());
      }
      finally {
        fin.close();
      }
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }

  }

};