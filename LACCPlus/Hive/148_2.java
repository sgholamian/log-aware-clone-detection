//,temp,HMSHandler.java,10613,10625,temp,HMSHandler.java,10585,10597
//,3
public class xxx {
  public void add_package(AddPackageRequest request) throws MetaException, NoSuchObjectException {
    startFunction("add_package");
    Exception ex = null;
    try {
      getMS().addPackage(request);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("add_package", ex == null, ex);
    }
  }

};