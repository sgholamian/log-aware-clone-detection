//,temp,Resources.java,327,339,temp,Resources.java,253,266
//,3
public class xxx {
  public static Resource multiplyTo(Resource lhs, double by) {
    int maxLength = ResourceUtils.getNumberOfKnownResourceTypes();
    for (int i = 0; i < maxLength; i++) {
      try {
        ResourceInformation lhsValue = lhs.getResourceInformation(i);
        lhs.setResourceValue(i, (long) (lhsValue.getValue() * by));
      } catch (ResourceNotFoundException ye) {
        LOG.warn("Resource is missing:" + ye.getMessage());
        continue;
      }
    }
    return lhs;
  }

};