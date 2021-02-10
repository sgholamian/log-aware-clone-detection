//,temp,MiniDFSClusterManager.java,230,245,temp,TestDefaultStringifier.java,100,117
//,3
public class xxx {
  private void updateConfiguration(Configuration conf2, String[] keyvalues) {
    int num_confs_updated = 0;
    if (keyvalues != null) {
      for (String prop : keyvalues) {
        String[] keyval = prop.split("=", 2);
        if (keyval.length == 2) {
          conf2.set(keyval[0], keyval[1]);
          num_confs_updated++;
        } else {
          LOG.warn("Ignoring -D option {}", prop);
        }
      }
    }
    LOG.info("Updated {} configuration settings from command line.",
        num_confs_updated);
  }

};