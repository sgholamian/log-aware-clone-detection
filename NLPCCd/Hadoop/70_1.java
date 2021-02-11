//,temp,sample_3799.java,2,13,temp,sample_1140.java,2,7
//,3
public class xxx {
public static ResourceCalculatorPlugin getResourceCalculatorPlugin( Class<? extends ResourceCalculatorPlugin> clazz, Configuration conf) {
if (clazz != null) {
return ReflectionUtils.newInstance(clazz, conf);
}
try {
return new ResourceCalculatorPlugin();
} catch (UnsupportedOperationException ue) {


log.info("failed to instantiate default resource calculator");
}
}

};