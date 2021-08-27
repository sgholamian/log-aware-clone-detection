//,temp,sample_256.java,2,17,temp,sample_255.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (useBloomFilter) {
if (newKeyCount <= BLOOM_FILTER_MAX_SIZE) {
this.bloom1 = new BloomFilter(newKeyCount);
} else {
double fpp = calcFPP(newKeyCount);
assert fpp < 1 : "Too many keys! BloomFilter False Positive Probability is 1!";
if (fpp >= 0.5) {
}
this.bloom1 = new BloomFilter(newKeyCount, fpp);
}


log.info("using a bloom filter d keys of size d bytes");
}
}

};