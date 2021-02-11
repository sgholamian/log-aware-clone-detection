//,temp,SwiftRestClient.java,261,273,temp,PipeMapRed.java,285,290
//,3
public class xxx {
  void envPut(Properties env, String name, String value) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Add  env entry:" + name + "=" + value);
    }
    env.put(name, value);
  }

};