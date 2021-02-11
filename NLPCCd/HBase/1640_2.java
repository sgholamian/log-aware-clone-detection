//,temp,sample_5711.java,2,12,temp,sample_4239.java,2,17
//,3
public class xxx {
public void dummy_method(){
Map<byte[], String> results = table.coprocessorService(TestRpcServiceProtos.TestProtobufRpcProto.class, ROWS[0], ROWS[ROWS.length - 1], new Batch.Call<TestRpcServiceProtos.TestProtobufRpcProto, String>() {
public String call(TestRpcServiceProtos.TestProtobufRpcProto instance) throws IOException {
CoprocessorRpcUtils.BlockingRpcCallback<TestProtos.EchoResponseProto> callback = new CoprocessorRpcUtils.BlockingRpcCallback<>();
instance.echo(controller, request, callback);
TestProtos.EchoResponseProto response = callback.get();
return null;
}
}
);
for (Map.Entry<byte[], String> e : results.entrySet()) {


log.info("got value for region");
}
}

};