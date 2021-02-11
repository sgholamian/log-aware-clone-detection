//,temp,sample_1190.java,2,13,temp,sample_4112.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
NamespaceDescriptor nsDescriptor = UTIL.getAdmin().getNamespaceDescriptor(namespaceName);
assertNull(nsDescriptor);
} catch (NamespaceNotFoundException nsnfe) {
}
final NamespaceDescriptor nsd = NamespaceDescriptor.create(namespaceName).build();
long procId = procExec.submitProcedure( new ModifyNamespaceProcedure(procExec.getEnvironment(), nsd));
ProcedureTestingUtility.waitProcedure(procExec, procId);
Procedure<?> result = procExec.getResult(procId);
assertTrue(result.isFailed());


log.info("modify namespace failed with exception");
}

};