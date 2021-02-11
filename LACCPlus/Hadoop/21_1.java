//,temp,WindowsSecureContainerExecutor.java,618,627,temp,WindowsSecureContainerExecutor.java,591,599
//,3
public class xxx {
  @Override
  protected void setScriptExecutable(Path script, String owner) 
      throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("setScriptExecutable: %s owner:%s", 
          script.toString(), owner));
    }
    super.setScriptExecutable(script, owner);
    Native.Elevated.chown(script, owner, nodeManagerGroup);
  }

};