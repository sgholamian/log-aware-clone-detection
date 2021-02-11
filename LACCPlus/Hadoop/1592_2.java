//,temp,TestNativeIO.java,214,254,temp,TestNativeIO.java,167,212
//,3
public class xxx {
  @Test (timeout = 30000)
  public void testSetFilePointer() throws Exception {
    if (!Path.WINDOWS) {
      return;
    }

    LOG.info("Set a file pointer on Windows");
    try {
      File testfile = new File(TEST_DIR, "testSetFilePointer");
      assertTrue("Create test subject",
          testfile.exists() || testfile.createNewFile());
      FileWriter writer = new FileWriter(testfile);
      try {
        for (int i = 0; i < 200; i++)
          if (i < 100)
            writer.write('a');
          else
            writer.write('b');
        writer.flush();
      } catch (Exception writerException) {
        fail("Got unexpected exception: " + writerException.getMessage());
      } finally {
        writer.close();
      }

      FileDescriptor fd = NativeIO.Windows.createFile(
          testfile.getCanonicalPath(),
          NativeIO.Windows.GENERIC_READ,
          NativeIO.Windows.FILE_SHARE_READ |
          NativeIO.Windows.FILE_SHARE_WRITE |
          NativeIO.Windows.FILE_SHARE_DELETE,
          NativeIO.Windows.OPEN_EXISTING);
      NativeIO.Windows.setFilePointer(fd, 120, NativeIO.Windows.FILE_BEGIN);
      FileReader reader = new FileReader(fd);
      try {
        int c = reader.read();
        assertTrue("Unexpected character: " + c, c == 'b');
      } catch (Exception readerException) {
        fail("Got unexpected exception: " + readerException.getMessage());
      } finally {
        reader.close();
      }
    } catch (Exception e) {
      fail("Got unexpected exception: " + e.getMessage());
    }
  }

};