//,temp,sample_8544.java,2,13,temp,sample_8543.java,2,11
//,3
public class xxx {
public void setConf(Configuration conf) {
balancedSpaceThreshold = conf.getLong( DFS_DATANODE_AVAILABLE_SPACE_VOLUME_CHOOSING_POLICY_BALANCED_SPACE_THRESHOLD_KEY, DFS_DATANODE_AVAILABLE_SPACE_VOLUME_CHOOSING_POLICY_BALANCED_SPACE_THRESHOLD_DEFAULT);
balancedPreferencePercent = conf.getFloat( DFS_DATANODE_AVAILABLE_SPACE_VOLUME_CHOOSING_POLICY_BALANCED_SPACE_PREFERENCE_FRACTION_KEY, DFS_DATANODE_AVAILABLE_SPACE_VOLUME_CHOOSING_POLICY_BALANCED_SPACE_PREFERENCE_FRACTION_DEFAULT);
LOG.info("Available space volume choosing policy initialized: " + DFS_DATANODE_AVAILABLE_SPACE_VOLUME_CHOOSING_POLICY_BALANCED_SPACE_THRESHOLD_KEY + " = " + balancedSpaceThreshold + ", " + DFS_DATANODE_AVAILABLE_SPACE_VOLUME_CHOOSING_POLICY_BALANCED_SPACE_PREFERENCE_FRACTION_KEY + " = " + balancedPreferencePercent);
if (balancedPreferencePercent > 1.0) {


log.info("the value of is greater than but should be in the range");
}
}

};