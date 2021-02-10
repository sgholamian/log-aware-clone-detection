//,temp,Resources.java,272,285,temp,Resources.java,253,266
//,3
public class xxx {
  public static Resource subtractFrom(Resource lhs, Resource rhs) {
    int maxLength = ResourceUtils.getNumberOfKnownResourceTypes();
    for (int i = 0; i < maxLength; i++) {
      try {
        ResourceInformation rhsValue = rhs.getResourceInformation(i);
        ResourceInformation lhsValue = lhs.getResourceInformation(i);
        lhs.setResourceValue(i, lhsValue.getValue() - rhsValue.getValue());
      } catch (ResourceNotFoundException ye) {
        LOG.warn("Resource is missing:" + ye.getMessage());
        continue;
      }
    }
    return lhs;
  }

};