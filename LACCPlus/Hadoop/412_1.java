//,temp,SysInfoLinux.java,444,503,temp,SysInfoLinux.java,389,438
//,3
public class xxx {
  private void readProcDisksInfoFile() {

    numDisksBytesRead = 0L;
    numDisksBytesWritten = 0L;

    // Read "/proc/diskstats" file
    BufferedReader in;
    try {
      in = new BufferedReader(new InputStreamReader(
            new FileInputStream(procfsDisksFile), Charset.forName("UTF-8")));
    } catch (FileNotFoundException f) {
      return;
    }

    Matcher mat;
    try {
      String str = in.readLine();
      while (str != null) {
        mat = PROCFS_DISKSFILE_FORMAT.matcher(str);
        if (mat.find()) {
          String diskName = mat.group(4);
          assert diskName != null;
          // ignore loop or ram partitions
          if (diskName.contains("loop") || diskName.contains("ram")) {
            str = in.readLine();
            continue;
          }

          Integer sectorSize;
          synchronized (perDiskSectorSize) {
            sectorSize = perDiskSectorSize.get(diskName);
            if (null == sectorSize) {
              // retrieve sectorSize
              // if unavailable or error, assume 512
              sectorSize = readDiskBlockInformation(diskName, 512);
              perDiskSectorSize.put(diskName, sectorSize);
            }
          }

          String sectorsRead = mat.group(7);
          String sectorsWritten = mat.group(11);
          if (null == sectorsRead || null == sectorsWritten) {
            return;
          }
          numDisksBytesRead += Long.parseLong(sectorsRead) * sectorSize;
          numDisksBytesWritten += Long.parseLong(sectorsWritten) * sectorSize;
        }
        str = in.readLine();
      }
    } catch (IOException e) {
      LOG.warn("Error reading the stream " + procfsDisksFile, e);
    } finally {
      // Close the streams
      try {
        in.close();
      } catch (IOException e) {
        LOG.warn("Error closing the stream " + procfsDisksFile, e);
      }
    }
  }

};