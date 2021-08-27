//,temp,sample_8292.java,2,13,temp,sample_8291.java,2,12
//,3
public class xxx {
public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
this.applicationContext = applicationContext;
ClassLoader cl;
if (applicationContext != null && applicationContext.getClassLoader() != null) {
cl = applicationContext.getClassLoader();
} else {
cl = Thread.currentThread().getContextClassLoader();
}


log.info("set the application context classloader to");
}

};