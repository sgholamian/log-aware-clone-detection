//,temp,TestTokenAuthentication.java,247,253,temp,MockServer.java,72,77
//,3
public class xxx {
  @Override
  public void abort(String why, Throwable e) {
    LOG.error(HBaseMarkers.FATAL, "Abort why=" + why, e);
    stop(why);
    this.aborted = true;
  }

};