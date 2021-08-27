//,temp,sample_3531.java,2,17,temp,sample_3526.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (endpoint.getRestletMethods() != null) {
Collections.addAll(methods, endpoint.getRestletMethods());
} else {
Collections.addAll(methods, endpoint.getRestletMethod());
}
for (Method method : methods) {
router.addRoute(method, target);
}
if (!router.hasBeenAttached()) {
component.getDefaultHost().attach( offsetPath == null ? uriPattern : offsetPath + uriPattern, router);


log.info("attached methodrouter uripattern");
}
}

};