//,temp,sample_2730.java,2,16,temp,sample_2733.java,2,16
//,3
public class xxx {
public void dummy_method(){
response = r.path("ws").path("v1").path("cluster") .path("replace-node-to-labels") .queryParam("user.name", userName) .accept(MediaType.APPLICATION_JSON) .entity(toJson(ntli, NodeToLabelsEntryList.class), MediaType.APPLICATION_JSON) .post(ClientResponse.class);
response = r.path("ws").path("v1").path("cluster") .path("get-node-to-labels").queryParam("user.name", userName) .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getType());
NodeToLabelsInfo ntlinfo = response.getEntity(NodeToLabelsInfo.class);
NodeLabelsInfo nlinfo = ntlinfo.getNodeToLabels().get("nid:0");
assertEquals(1, nlinfo.getNodeLabels().size());
assertTrue(nlinfo.getNodeLabelsInfo().contains(new NodeLabelInfo("a")));
params = new MultivaluedMapImpl();
params.add("labels", "");
response = r.path("ws").path("v1").path("cluster") .path("nodes").path("nid:0") .path("replace-labels") .queryParam("user.name", userName) .queryParams(params) .accept(MediaType.APPLICATION_JSON) .post(ClientResponse.class);


log.info("posted node nodelabel");
}

};