//,temp,sample_2897.java,2,10,temp,sample_2115.java,2,10
//,3
public class xxx {
public void ensureIndex(MongoCollection<BasicDBObject> collection, List<BasicDBObject> dynamicIndex) {
if (dynamicIndex != null && !dynamicIndex.isEmpty()) {
for (BasicDBObject index : dynamicIndex) {


log.info("create bdobject index");
}
}
}

};