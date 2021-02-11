//,temp,sample_3706.java,2,12,temp,sample_1282.java,2,10
//,3
public class xxx {
public MasterCoprocessor checkAndGetInstance(Class<?> implClass) throws InstantiationException, IllegalAccessException {
if (MasterCoprocessor.class.isAssignableFrom(implClass)) {
return (MasterCoprocessor)implClass.newInstance();
} else if (CoprocessorService.class.isAssignableFrom(implClass)) {
return new CoprocessorServiceBackwardCompatiblity.MasterCoprocessorService( (CoprocessorService)implClass.newInstance());
} else {


log.info("is not of type mastercoprocessor check the configuration");
}
}

};