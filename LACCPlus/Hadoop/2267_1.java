//,temp,Resources.java,514,531,temp,Resources.java,272,285
//,3
public class xxx {
  public static Resource componentwiseMin(Resource lhs, Resource rhs) {
    Resource ret = createResource(0);
    int maxLength = ResourceUtils.getNumberOfKnownResourceTypes();
    for (int i = 0; i < maxLength; i++) {
      try {
        ResourceInformation rhsValue = rhs.getResourceInformation(i);
        ResourceInformation lhsValue = lhs.getResourceInformation(i);
        ResourceInformation outInfo = lhsValue.getValue() < rhsValue.getValue()
            ? lhsValue
            : rhsValue;
        ret.setResourceInformation(i, outInfo);
      } catch (ResourceNotFoundException ye) {
        LOG.warn("Resource is missing:" + ye.getMessage());
        continue;
      }
    }
    return ret;
  }

};