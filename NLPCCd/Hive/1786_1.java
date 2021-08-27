//,temp,sample_5346.java,2,17,temp,sample_5345.java,2,18
//,3
public class xxx {
public void dummy_method(){
Collections.sort(mapWorks, new Comparator<MapWork>() {
public int compare(MapWork o1, MapWork o2) {
return o1.getName().compareTo(o2.getName());
}
});
for (MapWork mapWork : mapWorks) {
Collection<Operator<? extends OperatorDesc>> topOperators = mapWork.getAliasToWork().values();
if (topOperators.size() == 0) {
return null;
}


log.info("looking for table scans where optimization is applicable");
}
}

};