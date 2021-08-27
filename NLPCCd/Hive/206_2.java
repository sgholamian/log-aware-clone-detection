//,temp,sample_5460.java,2,16,temp,sample_5471.java,2,16
//,2
public class xxx {
public void dummy_method(){
Operator<?> lastRetainableOp = sr.retainableOps.get(sr.retainableOps.size() - 1);
Operator<?> lastDiscardableOp = sr.discardableOps.get(sr.discardableOps.size() - 1);
if (lastDiscardableOp.getNumChild() != 0) {
List<Operator<? extends OperatorDesc>> allChildren = Lists.newArrayList(lastDiscardableOp.getChildOperators());
for (Operator<? extends OperatorDesc> op : allChildren) {
lastDiscardableOp.getChildOperators().remove(op);
op.replaceParent(lastDiscardableOp, lastRetainableOp);
lastRetainableOp.getChildOperators().add(op);
}
}


log.info("merging subtree starting at into subtree starting at");
}

};