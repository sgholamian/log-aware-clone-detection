//,temp,ShowLocksOperation.java,142,156,temp,ShowLocksOperation.java,71,88
//,3
public class xxx {
  private int showLocksNewFormat(HiveLockManager lockMgr) throws HiveException {
    ShowLocksResponse response = getLocksForNewFormat(lockMgr);

    // write the results in the file
    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      dumpLockInfo(os, response);
    } catch (IOException e) {
      LOG.warn("show function: ", e);
      return 1;
    } catch (Exception e) {
      throw new HiveException(e.toString());
    }

    return 0;
  }

};