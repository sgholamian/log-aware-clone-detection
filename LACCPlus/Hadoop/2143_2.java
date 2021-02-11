//,temp,JniBasedUnixGroupsNetgroupMappingWithFallback.java,36,46,temp,JniBasedUnixGroupsMappingWithFallback.java,37,47
//,2
public class xxx {
  public JniBasedUnixGroupsMappingWithFallback() {
    if (NativeCodeLoader.isNativeCodeLoaded()) {
      this.impl = new JniBasedUnixGroupsMapping();
    } else {
      PerformanceAdvisory.LOG.debug("Falling back to shell based");
      this.impl = new ShellBasedUnixGroupsMapping();
    }
    if (LOG.isDebugEnabled()){
      LOG.debug("Group mapping impl=" + impl.getClass().getName());
    }
  }

};