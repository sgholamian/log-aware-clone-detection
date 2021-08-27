//,temp,sample_5036.java,2,13,temp,sample_5037.java,2,13
//,3
public class xxx {
public HCatTable deserializeTable(String hcatTableStringRep) throws HCatException {
try {
Table table = new Table();
new TDeserializer(new TJSONProtocol.Factory()).deserialize(table, hcatTableStringRep, "UTF-8");
return new HCatTable(table);
}
catch(TException exception) {


log.info("could not de serialize from");
}
}

};