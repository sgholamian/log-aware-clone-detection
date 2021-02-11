//,temp,sample_4940.java,2,16,temp,sample_4942.java,2,16
//,3
public class xxx {
public void dummy_method(){
List<Pair<RegionInfo, ServerName>> tableRegions = MetaTableAccessor.getTableRegionsAndLocations( m.getConnection(), TABLENAME);
assertEquals(1, tableRegions.size());
assertArrayEquals(HConstants.EMPTY_START_ROW, tableRegions.get(0).getFirst().getStartKey());
assertArrayEquals(HConstants.EMPTY_END_ROW, tableRegions.get(0).getFirst().getEndKey());
TEST_UTIL.getAdmin().split(TABLENAME);
while (tableRegions.size() < 3) {
tableRegions = MetaTableAccessor.getTableRegionsAndLocations(m.getConnection(), TABLENAME, false);
Thread.sleep(100);
}
assertEquals(3, tableRegions.size());


log.info("making sure we can call gettableregionclosest while opening");
}

};