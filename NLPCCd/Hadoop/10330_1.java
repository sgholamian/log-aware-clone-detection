//,temp,sample_7932.java,2,16,temp,sample_1584.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertEquals(NUM, array.length);
for (int i = 0; i < array.length; i++) {
assertTrue(list.contains(array[i]));
}
assertEquals(NUM, set.size());
Object[] array2 = set.toArray();
assertEquals(NUM, array2.length);
for (int i = 0; i < array2.length; i++) {
assertTrue(list.contains(array2[i]));
}


log.info("test capacity done");
}

};