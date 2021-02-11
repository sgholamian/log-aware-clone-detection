//,temp,sample_2217.java,2,12,temp,sample_5630.java,2,11
//,3
public class xxx {
public static Cell filterKv(Filter filter, Cell c) throws IOException {
if (filter != null) {
Filter.ReturnCode code = filter.filterCell(c);
if (LOG.isTraceEnabled()) {


log.info("filter returned for the cell");
}
}
}

};