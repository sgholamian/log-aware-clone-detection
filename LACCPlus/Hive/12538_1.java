//,temp,CachedStore.java,3024,3036,temp,CachedStore.java,3010,3022
//,2
public class xxx {
  private static boolean isInWhitelist(String catName, String dbName, String tblName) {
    String str = TableName.getQualified(catName, dbName, tblName);
    for (Pattern pattern : whitelistPatterns) {
      LOG.debug("Trying to match: {} against whitelist pattern: {}", str, pattern);
      Matcher matcher = pattern.matcher(str);
      if (matcher.matches()) {
        LOG.debug("Found matcher group: {} at start index: {} and end index: {}", matcher.group(), matcher.start(),
            matcher.end());
        return true;
      }
    }
    return false;
  }

};