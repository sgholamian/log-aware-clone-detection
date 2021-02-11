//,temp,sample_5613.java,2,12,temp,sample_1282.java,2,10
//,3
public class xxx {
public RegionCoprocessor checkAndGetInstance(Class<?> implClass) throws InstantiationException, IllegalAccessException {
if (RegionCoprocessor.class.isAssignableFrom(implClass)) {
return (RegionCoprocessor)implClass.newInstance();
} else if (CoprocessorService.class.isAssignableFrom(implClass)) {
return new CoprocessorServiceBackwardCompatiblity.RegionCoprocessorService( (CoprocessorService)implClass.newInstance());
} else {


log.info("is not of type regioncoprocessor check the configuration");
}
}

};