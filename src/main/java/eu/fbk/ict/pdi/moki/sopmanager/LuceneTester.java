package eu.fbk.ict.pdi.moki.sopmanager;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;

public class LuceneTester implements Service {
  HttpSession session;
  Gson gson = new Gson();
  ReturnMessage res;
  SopManagerRequest list_req = null;
  HttpSolrClient client;

  @Override
  public ReturnMessage run() {
    if (list_req == null) {
      res.setMessage_text("Error: data is empty");
      return res;
    }

    try {
      Method method = this.getClass().getMethod(list_req.getMode());
      method.invoke(this);
    } catch (Exception e) {
      res.setMessage_text("mode " + list_req.getMode() + " don't found");
    }
    return res;
  }

  @Override
  public void init(String data, HttpSession session) {
    list_req = gson.fromJson(data, SopManagerRequest.class);
    this.session = session;

    res = new ReturnMessage();
    res.setName_service("LuceneTester");
    res.setType_service("sopmanager");

  }
  
//  This function is used to retrieve a single SOP from Solr with an ID (Ex. SOP-0141) given
  /*  public ReturnMessage searchById() throws IOException {
	SolrDocumentList list = new SolrDocumentList();
	SolrDocument doc = new SolrDocument();
	try {
		doc = this.client.getById(list_req.getId());
		System.out.println("Trovato");
	} catch (Exception e){
		res.setMessage_text(e.getMessage());
		return res;
	}
	list.add(doc);
	res = parseResults(list);
	System.out.println(list.getNumFound());
    return res;
  }
  
// This function is used to free text search in the fulltext fo the SOP or in the IDz
  public ReturnMessage search() throws IOException, SolrServerException {
	SolrQuery query = new SolrQuery();
    String name = list_req.getName();
    query.set("deftype", "synonym_edismax");
    query.set("synonyms", "true");
    query.set("sow","false");
    query.set("q", "id:\"" + name + "\" OR text:\"" + name + "\"");
    QueryResponse response = client.query(query);
    SolrDocumentList list = response.getResults();
    res = parseResults(list);

    return res;
  }
  
  public ReturnMessage searchRole() throws IOException, SolrServerException {
	SolrQuery query = new SolrQuery();
    String name = list_req.getName();
    query.set("q", "id:\"" + name + "\" OR text:\"" + name + "\"");
    QueryResponse response = client.query(query);
    SolrDocumentList list = response.getResults();
    res = parseResults(list);

    return res;
  }

//  Given the list of SOPS retrieved from SOLR, each one is taken and converted in SopDocument object
//  Also the json for the graph is returned
  private ReturnMessage parseResults(SolrDocumentList list) {
    try {
      ArrayList<SOPDocument> resultsDoc = new ArrayList<SOPDocument>();
      for (SolrDocument doc : list) {
        SOPDocument sop = new SOPDocument();
        sop.toSOPDocument(doc);
        resultsDoc.add(sop);
      }
      String results = gson.toJson(resultsDoc);
      JsonObject json = this.getJsonGraph(resultsDoc);
      res.setData(results);
      res.setJson(json);
      res.setMessage_text("ok");
    } catch (Exception exc) {
      exc.printStackTrace();
      res.setMessage_text("Database error: can't get data");
      return res;
    }
    return res;
  }

//  This function creates the json used for the graph
  private JsonObject getJsonGraph(ArrayList<SOPDocument> sops) {
    JsonObject json = new JsonObject();
    JsonArray nodesJson = new JsonArray();
    JsonArray linksJson = new JsonArray();
    Set<String> nodes = new HashSet<String>();
    Set<String> searched = new HashSet<String>();
    for (SOPDocument item : sops) {
      Set<String> links_in = new HashSet<String>();
      Set<String> links_out = new HashSet<String>();
      searched.add(item.getId());
      for (String insop : item.getSopInLinks().split(" ")) {
        if (insop.length() > 0) {
          nodes.add(insop);
          links_in.add(insop);
        }
      }
      for (String outsop : item.getSopOutLinks().split(" ")) {
        if (outsop.length() > 0) {
          nodes.add(outsop);
          links_out.add(outsop);
        }
      }
      for (String link_in : links_in) {
        JsonObject link = new JsonObject();
        link.addProperty("source", link_in);
        link.addProperty("target", item.getSopId());
        linksJson.add(link);
      }
      for (String link_out : links_out) {
        JsonObject link = new JsonObject();
        link.addProperty("source", item.getSopId());
        link.addProperty("target", link_out);
        linksJson.add(link);
      }
    }
    for (String item : searched) {
      JsonObject node = new JsonObject();
      node.addProperty("id", item);
      node.addProperty("group", "1");
      nodesJson.add(node);
    }
    for (String item : nodes) {
      JsonObject node = new JsonObject();
      node.addProperty("id", item);
      node.addProperty("group", "2");
      nodesJson.add(node);
    }
    json.add("nodes", nodesJson);
    json.add("links", linksJson);
    return json;
  }*/
}
