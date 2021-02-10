//,temp,AllocationTagsManager.java,147,160,temp,InputStriper.java,55,65
//,3
public class xxx {
    private void removeTag(T type, String tag) {
      Map<String, Long> innerMap = typeToTagsWithCount.get(type);
      if (innerMap == null) {
        LOG.warn("Failed to find node/rack=" + type
            + " while trying to remove tags, please double check.");
        return;
      }

      removeTagFromInnerMap(innerMap, tag);

      if (innerMap.isEmpty()) {
        typeToTagsWithCount.remove(type);
      }
    }

};