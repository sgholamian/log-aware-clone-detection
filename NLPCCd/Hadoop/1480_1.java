//,temp,sample_3800.java,2,14,temp,sample_1141.java,2,7
//,3
public class xxx {
public static ResourceCalculatorPlugin getResourceCalculatorPlugin( Class<? extends ResourceCalculatorPlugin> clazz, Configuration conf) {
if (clazz != null) {
return ReflectionUtils.newInstance(clazz, conf);
}
try {
return new ResourceCalculatorPlugin();
} catch (UnsupportedOperationException ue) {
} catch (Throwable t) {


log.info("failed to instantiate default resource calculator");
}
}

};