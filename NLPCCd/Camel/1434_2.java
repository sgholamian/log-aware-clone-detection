//,temp,sample_5946.java,2,16,temp,sample_5945.java,2,12
//,3
public class xxx {
protected void doServiceAsync(AsyncContext context) {
final HttpServletRequest request = (HttpServletRequest) context.getRequest();
final HttpServletResponse response = (HttpServletResponse) context.getResponse();
try {
doService(request, response);
} catch (Exception e) {


log.info("error processing request");
}
}

};