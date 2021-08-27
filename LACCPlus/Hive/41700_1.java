//,temp,ReloadProcessor.java,33,43,temp,DbTxnManager.java,189,198
//,3
public class xxx {
  @Override
  public CommandProcessorResponse run(String command) throws CommandProcessorException {
    SessionState ss = SessionState.get();
    try {
      ss.loadReloadableAuxJars();
    } catch (IOException e) {
      LOG.error("fail to reload auxiliary jar files", e);
      throw new CommandProcessorException(e.getMessage(), e);
    }
    return new CommandProcessorResponse();
  }

};