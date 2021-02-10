//,temp,ProcfsBasedProcessTree.java,510,561,temp,SysInfoLinux.java,339,383
//,3
public class xxx {
  private static ProcessInfo constructProcessInfo(ProcessInfo pinfo,
                                                    String procfsDir) {
    ProcessInfo ret = null;
    // Read "procfsDir/<pid>/stat" file - typically /proc/<pid>/stat
    BufferedReader in = null;
    InputStreamReader fReader = null;
    try {
      File pidDir = new File(procfsDir, pinfo.getPid());
      fReader = new InputStreamReader(
          new FileInputStream(
              new File(pidDir, PROCFS_STAT_FILE)), Charset.forName("UTF-8"));
      in = new BufferedReader(fReader);
    } catch (FileNotFoundException f) {
      // The process vanished in the interim!
      return ret;
    }

    ret = pinfo;
    try {
      String str = in.readLine(); // only one line
      Matcher m = PROCFS_STAT_FILE_FORMAT.matcher(str);
      boolean mat = m.find();
      if (mat) {
        // Set (name) (ppid) (pgrpId) (session) (utime) (stime) (vsize) (rss)
        pinfo.updateProcessInfo(m.group(2), m.group(3),
                Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)),
                Long.parseLong(m.group(7)), new BigInteger(m.group(8)),
                Long.parseLong(m.group(10)), Long.parseLong(m.group(11)));
      } else {
        LOG.warn("Unexpected: procfs stat file is not in the expected format"
            + " for process with pid " + pinfo.getPid());
        ret = null;
      }
    } catch (IOException io) {
      LOG.warn("Error reading the stream " + io);
      ret = null;
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

    return ret;
  }

};