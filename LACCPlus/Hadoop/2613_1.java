//,temp,RuleBasedLdapGroupsMapping.java,55,65,temp,DfsClientConf.java,308,321
//,3
public class xxx {
  @Override
  public synchronized void setConf(Configuration conf) {
    super.setConf(conf);
    String value = conf.get(CONVERSION_RULE_KEY, CONVERSION_RULE_DEFAULT);
    try {
      rule = Rule.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException iae) {
      LOG.warn("Invalid {} configured: '{}'. Using default value: '{}'",
          CONVERSION_RULE_KEY, value, CONVERSION_RULE_DEFAULT);
    }
  }

};