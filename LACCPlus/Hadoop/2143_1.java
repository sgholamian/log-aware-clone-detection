//,temp,JniBasedUnixGroupsNetgroupMappingWithFallback.java,36,46,temp,JniBasedUnixGroupsMappingWithFallback.java,37,47
//,2
public class xxx {
  public JniBasedUnixGroupsNetgroupMappingWithFallback() {
    if (NativeCodeLoader.isNativeCodeLoaded()) {
      this.impl = new JniBasedUnixGroupsNetgroupMapping();
    } else {
      LOG.info("Falling back to shell based");
      this.impl = new ShellBasedUnixGroupsNetgroupMapping();
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Group mapping impl=" + impl.getClass().getName());
    }
  }

};