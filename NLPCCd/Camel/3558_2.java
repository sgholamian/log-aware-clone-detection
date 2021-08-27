//,temp,sample_7046.java,2,14,temp,sample_7047.java,2,16
//,3
public class xxx {
public SortedIndex<Buffer, Buffer> getRepositoryIndex(Transaction tx, String name, boolean create) {
SortedIndex<Buffer, Buffer> answer = null;
SortedIndex<String, Integer> indexes = ROOT_INDEXES_FACTORY.open(tx);
Integer location = indexes.get(name);
if (create && location == null) {
SortedIndex<Buffer, Buffer> created = INDEX_FACTORY.create(tx);
int page = created.getIndexLocation();
indexes.put(name, page);
answer = created;
} else if (location != null) {


log.info("repository index with name at location");
}
}

};