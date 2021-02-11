//,temp,sample_6736.java,2,8,temp,sample_4105.java,2,7
//,3
public class xxx {
public static ConfigurationMutationACLPolicy getPolicy(Configuration conf) {
Class<? extends ConfigurationMutationACLPolicy> policyClass = conf.getClass(YarnConfiguration.RM_SCHEDULER_MUTATION_ACL_POLICY_CLASS, DefaultConfigurationMutationACLPolicy.class, ConfigurationMutationACLPolicy.class);


log.info("using configurationmutationaclpolicy implementation");
}

};