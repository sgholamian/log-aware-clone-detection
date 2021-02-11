//,temp,SysInfoLinux.java,444,503,temp,SysInfoLinux.java,389,438
//,3
public class xxx {
  private void readProcNetInfoFile() {

    numNetBytesRead = 0L;
    numNetBytesWritten = 0L;

    // Read "/proc/net/dev" file
    BufferedReader in;
    InputStreamReader fReader;
    try {
      fReader = new InputStreamReader(
          new FileInputStream(procfsNetFile), Charset.forName("UTF-8"));
      in = new BufferedReader(fReader);
    } catch (FileNotFoundException f) {
      return;
    }

    Matcher mat;
    try {
      String str = in.readLine();
      while (str != null) {
        mat = PROCFS_NETFILE_FORMAT.matcher(str);
        if (mat.find()) {
          assert mat.groupCount() >= 16;

          // ignore loopback interfaces
          if (mat.group(1).equals("lo")) {
            str = in.readLine();
            continue;
          }
          numNetBytesRead += Long.parseLong(mat.group(2));
          numNetBytesWritten += Long.parseLong(mat.group(10));
        }
        str = in.readLine();
      }
    } catch (IOException io) {
      LOG.warn("Error reading the stream " + io);
    } finally {
      // Close the streams
      try {
        fReader.close();
        try {
          in.close();
        } catch (IOException i) {
          LOG.warn("Error closing the stream " + in);
        }
      } catch (IOException i) {
        LOG.warn("Error closing the stream " + fReader);
      }
    }
  }

};