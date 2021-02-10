//,temp,Resources.java,514,531,temp,Resources.java,382,395
//,3
public class xxx {
  public static Resource multiplyAndRoundDown(Resource lhs, double by) {
    Resource out = clone(lhs);
    int maxLength = ResourceUtils.getNumberOfKnownResourceTypes();
    for (int i = 0; i < maxLength; i++) {
      try {
        ResourceInformation lhsValue = lhs.getResourceInformation(i);
        out.setResourceValue(i, (long) (lhsValue.getValue() * by));
      } catch (ResourceNotFoundException ye) {
        LOG.warn("Resource is missing:" + ye.getMessage());
        continue;
      }
    }
    return out;
  }

};