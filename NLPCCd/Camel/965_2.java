//,temp,sample_3868.java,2,14,temp,sample_1177.java,2,13
//,3
public class xxx {
public void filter(Direction direction, List<Header> headers) {
if (headers == null) {
return;
}
Iterator<Header> iterator = headers.iterator();
while (iterator.hasNext()) {
Header header = iterator.next();


log.info("processing header");
}
}

};