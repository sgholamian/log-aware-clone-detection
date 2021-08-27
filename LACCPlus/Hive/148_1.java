//,temp,HMSHandler.java,10613,10625,temp,HMSHandler.java,10585,10597
//,3
public class xxx {
  public void drop_package(DropPackageRequest request) throws MetaException {
    startFunction("drop_package");
    Exception ex = null;
    try {
      getMS().dropPackage(request);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("drop_package", ex == null, ex);
    }
  }

};