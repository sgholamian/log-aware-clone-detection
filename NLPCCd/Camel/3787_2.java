//,temp,sample_3531.java,2,17,temp,sample_3526.java,2,17
//,3
public class xxx {
public void dummy_method(){
Collections.addAll(methods, Method.OPTIONS);
if (endpoint.getRestletMethods() != null) {
Collections.addAll(methods, endpoint.getRestletMethods());
} else {
Collections.addAll(methods, endpoint.getRestletMethod());
}
for (Method method : methods) {
router.removeRoute(method);
}
if (LOG.isDebugEnabled()) {


log.info("detached restlet uripattern method");
}
}

};