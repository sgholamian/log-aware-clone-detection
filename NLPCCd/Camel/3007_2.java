//,temp,sample_4415.java,2,11,temp,sample_4416.java,2,12
//,3
public class xxx {
private CompletableFuture<UShort> lookupNamespaceIndex(final String namespaceUri) {
{
final UShort result = this.namespaceCache.get(namespaceUri);
if (result != null) {
return CompletableFuture.completedFuture(result);
}
}


log.info("looking up namespace on server");
}

};