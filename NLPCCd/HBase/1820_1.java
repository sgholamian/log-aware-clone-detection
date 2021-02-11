//,temp,sample_4940.java,2,16,temp,sample_4942.java,2,16
//,3
public class xxx {
public void dummy_method(){
HMaster m = cluster.getMaster();
try (Table ht = TEST_UTIL.createTable(TABLENAME, FAMILYNAME)) {
assertTrue(m.getTableStateManager().isTableState(TABLENAME, TableState.State.ENABLED));
TEST_UTIL.loadTable(ht, FAMILYNAME, false);
}
List<Pair<RegionInfo, ServerName>> tableRegions = MetaTableAccessor.getTableRegionsAndLocations( m.getConnection(), TABLENAME);
assertEquals(1, tableRegions.size());
assertArrayEquals(HConstants.EMPTY_START_ROW, tableRegions.get(0).getFirst().getStartKey());
assertArrayEquals(HConstants.EMPTY_END_ROW, tableRegions.get(0).getFirst().getEndKey());
TEST_UTIL.getAdmin().split(TABLENAME);


log.info("making sure we can call gettableregions while opening");
}

};