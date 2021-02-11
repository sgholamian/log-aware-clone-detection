//,temp,ProcfsBasedProcessTree.java,510,561,temp,SysInfoLinux.java,339,383
//,3
public class xxx {
  private void readProcStatFile() {
    // Read "/proc/stat" file
    BufferedReader in;
    InputStreamReader fReader;
    try {
      fReader = new InputStreamReader(
          new FileInputStream(procfsStatFile), Charset.forName("UTF-8"));
      in = new BufferedReader(fReader);
    } catch (FileNotFoundException f) {
      // shouldn't happen....
      return;
    }

    Matcher mat;
    try {
      String str = in.readLine();
      while (str != null) {
        mat = CPU_TIME_FORMAT.matcher(str);
        if (mat.find()) {
          long uTime = Long.parseLong(mat.group(1));
          long nTime = Long.parseLong(mat.group(2));
          long sTime = Long.parseLong(mat.group(3));
          cpuTimeTracker.updateElapsedJiffies(
              BigInteger.valueOf(uTime + nTime + sTime),
              getCurrentTime());
          break;
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