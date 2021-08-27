//,temp,sample_2375.java,2,16,temp,sample_5407.java,2,16
//,3
public class xxx {
private void connect(Operator<?> o, AtomicInteger index, Stack<Operator<?>> nodes, Map<Operator<?>, Integer> indexes, Map<Operator<?>, Integer> lowLinks, Set<Set<Operator<?>>> components, ParseContext parseContext) {
indexes.put(o, index.get());
lowLinks.put(o, index.get());
index.incrementAndGet();
nodes.push(o);
List<Operator<?>> children;
if (o instanceof AppMasterEventOperator) {
children = new ArrayList<Operator<?>>();
children.addAll(o.getChildOperators());
TableScanOperator ts = ((DynamicPruningEventDesc) o.getConf()).getTableScan();


log.info("adding special edge");
}
}

};