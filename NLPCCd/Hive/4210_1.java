//,temp,sample_2375.java,2,16,temp,sample_5407.java,2,16
//,3
public class xxx {
private void connect(Operator<?> o, AtomicInteger index, Stack<Operator<?>> nodes, Map<Operator<?>, Integer> indexes, Map<Operator<?>, Integer> lowLinks, Set<Set<Operator<?>>> components) {
indexes.put(o, index.get());
lowLinks.put(o, index.get());
index.incrementAndGet();
nodes.push(o);
List<Operator<?>> children;
if (o instanceof SparkPartitionPruningSinkOperator) {
children = new ArrayList<>();
children.addAll(o.getChildOperators());
TableScanOperator ts = ((SparkPartitionPruningSinkDesc) o.getConf()).getTableScan();


log.info("adding special edge");
}
}

};