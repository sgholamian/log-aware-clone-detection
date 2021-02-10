//,temp,Resources.java,533,550,temp,Resources.java,349,365
//,3
public class xxx {
  public static Resource multiplyAndAddTo(
      Resource lhs, Resource rhs, double by) {
    int maxLength = ResourceUtils.getNumberOfKnownResourceTypes();
    for (int i = 0; i < maxLength; i++) {
      try {
        ResourceInformation rhsValue = rhs.getResourceInformation(i);
        ResourceInformation lhsValue = lhs.getResourceInformation(i);

        long convertedRhs = (long) (rhsValue.getValue() * by);
        lhs.setResourceValue(i, lhsValue.getValue() + convertedRhs);
      } catch (ResourceNotFoundException ye) {
        LOG.warn("Resource is missing:" + ye.getMessage());
        continue;
      }
    }
    return lhs;
  }

};