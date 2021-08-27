//,temp,sample_2897.java,2,10,temp,sample_2115.java,2,10
//,3
public class xxx {
public void ensureIndex(MongoCollection<Document> aCollection, List<Bson> dynamicIndex) {
if (dynamicIndex != null && !dynamicIndex.isEmpty()) {
for (Bson index : dynamicIndex) {


log.info("create document index");
}
}
}

};