//,temp,ShowLocksOperation.java,142,156,temp,ShowLocksOperation.java,71,88
//,3
public class xxx {
  private int showLocksOldFormat(HiveLockManager lockMgr) throws HiveException {
    if (lockMgr == null) {
      throw new HiveException("show Locks LockManager not specified");
    }

    // write the results in the file
    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      List<HiveLock> locks = getLocksForOldFormat(lockMgr);
      writeLocksInOldFormat(os, locks);
    } catch (IOException e) {
      LOG.warn("show function: ", e);
      return 1;
    } catch (Exception e) {
      throw new HiveException(e.toString(), e);
    }

    return 0;
  }

};