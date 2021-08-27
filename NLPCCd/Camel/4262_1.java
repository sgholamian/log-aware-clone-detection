//,temp,sample_7501.java,2,17,temp,sample_7502.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<RouteStartupOrder> routesOrdered = new ArrayList<RouteStartupOrder>(routes);
routesOrdered.sort(new Comparator<RouteStartupOrder>() {
public int compare(RouteStartupOrder o1, RouteStartupOrder o2) {
return o1.getStartupOrder() - o2.getStartupOrder();
}
});
if (shutdownRoutesInReverseOrder) {
Collections.reverse(routesOrdered);
}
if (suspendOnly) {


log.info("starting to graceful suspend routes timeout");
}
}

};