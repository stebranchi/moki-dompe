package eu.fbk.ict.pdi.moki.listmanager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import eu.fbk.ict.pdi.moki.utils.Config;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;
import eu.fbk.ict.pdi.moki.sopmanager.SOPDocument;
import eu.fbk.ict.pdi.moki.utils.DBLayer;

public class ClassesManager implements Service {
  // to be initialized
  ClassesManagerRequest list_req = null;
  HttpSession session;
  Gson gson = new Gson();
  ReturnMessage res;
  HttpSolrClient client;
  Properties properties = new Properties();

  @Override
  public ReturnMessage run() {

    if (list_req == null) {
      res.setMessage_text("Error: data is empty");
      return res;
    }

    // invoke the method requested by the user
    try {
      Method method = this.getClass().getMethod(list_req.getMode());
      method.invoke(this);
    } catch (Exception e) {
      res.setMessage_text("mode " + list_req.getMode() + " don't found");
    }

    /*
     * if(list_req.getMode().equals("getList")) res = getList(); else
     * if(list_req.getMode().equals("getClassData")) res = getClassData(); else
     * res.setMessage_text("mode " + list_req.getMode() + "don't found");
     */

    return res;
  }

  @Override
  public void init(String data, HttpSession session) {

    list_req = gson.fromJson(data, ClassesManagerRequest.class);
    this.session = session;
    try {
    this.properties.load(getClass().getResourceAsStream("/config.properties"));
    } catch( Exception e) {
    	
    }
    final String solrUrl = this.properties.getProperty("solrUrl") + "/solr/Prova";
    this.client = new HttpSolrClient.Builder(solrUrl)
		    .withConnectionTimeout(100000)
		    .withSocketTimeout(600000)
		    .build();
    res = new ReturnMessage();
    res.setName_service("ListManager");
    res.setType_service("listmanager");
  }
  
  public ReturnMessage saveLog() throws IOException {
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		//insert json log in the database
		try {
			   DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
			   DBLayer.SQL("INSERT INTO searchlog VALUES (DEFAULT, ?, ?, ? ,?)", currentTime, session.getAttribute("name").toString(), list_req.getName(), list_req.getId());
			   
		} catch(Exception e) {
			   System.err.println("errore nell'inserimento del log");
		}
	    res.setMessage_text("ok");
	    return res;
	  }
  
// This function is called when the page first load to retrieve a sample of SOPS to show
  public ReturnMessage getList() throws IOException, SolrServerException {

    ArrayList<SOPDocument> resultsDoc = new ArrayList<SOPDocument>();
    ClassesManagerRequest getlist = new ClassesManagerRequest();
    SolrQuery query = new SolrQuery();
    query.setStart(0);
    query.setRows(60);
    query.set("q", "*:*");
	QueryResponse response = client.query(query);
	SolrDocumentList list = response.getResults();
    for (SolrDocument doc : list) {
      SOPDocument sop = new SOPDocument();
      sop.toSOPDocument(doc);
      resultsDoc.add(sop);
    }
    getlist.setList(resultsDoc.toArray());
    res.setMessage_text("ok");
    res.setData(getlist);
    return res;
  }

//This function is used to retrieve a single SOP from Solr with an ID (Ex. SOP-0141) given
  public ReturnMessage getById() throws IOException {
	  SolrDocumentList list = new SolrDocumentList();
		SolrDocument doc = new SolrDocument ();
		try {
			doc = this.client.getById(list_req.getId());
		} catch (Exception e){
			res.setMessage_text(e.getMessage());
		}
		list.add(doc);
		res = parseResults(list);
	    return res;
  }
  
//This function is used to retrieve a single SOP from Solr with an ID (Ex. SOP-0141) given
  public ReturnMessage searchById() throws IOException, SolrServerException {
	  SolrQuery query = new SolrQuery();
	  query.set("q", "numrel:\"" + list_req.getId() +"\"");
	  QueryResponse response = client.query(query);
	  SolrDocumentList list = response.getResults();
	  res = parseResults(list);
	  return res;
  }  
  
//This function is used to free text search in the fulltext fo the SOP or in the ID
  public ReturnMessage search() throws IOException, SolrServerException {
	SolrQuery query = new SolrQuery();
	String name = list_req.getName();
	DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
	DBLayer.SQL("INSERT INTO searchterms (text) VALUES", name);
	name = name + '~';
	name.replace(" ", "~ ");
	System.out.println(name);
	String id = name;
	String abs = name;
    query.set("q", "title:" + id + " OR fulltext:" + name + " OR abstract:" + abs + "");
    query.setStart(0);
    query.setRows(2000);
	QueryResponse response = client.query(query);
	SolrDocumentList list = response.getResults();
	res = parseResults(list);
    return res;
  }
  
  public ReturnMessage searchRole() throws IOException, SolrServerException {
	SolrQuery query = new SolrQuery();
	String name = list_req.getName();
	String id = name;
	DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
	DBLayer.SQL("INSERT INTO searchterms (text) VALUES", name);
	query.set("deftype", "synonym_edismax");
    query.set("synonyms", "true");
    query.set("sow","false");
    query.set("q", "isbn:" + id);
    query.setStart(0);
    query.setRows(2000);
	QueryResponse response = client.query(query);
	SolrDocumentList list = response.getResults();
	res = parseResults(list);
    return res;
  }
  
//  This Function is used to search for a sentence or a word in 1 or more specific section
  public ReturnMessage searchSection() throws IOException, SolrServerException {
	String name = list_req.getName();
	String[] sections = list_req.getSections();
	String qstring = sections[0] + ":" + name;
	for (int i = 1; i < sections.length; i++)
	{
	  	qstring = qstring + " OR " + sections[i] + ":" + name;
	}
	SolrQuery query = new SolrQuery();
	query.set("deftype", "synonym_edismax");
    query.set("synonyms", "true");
    query.set("sow","false");
	query.set("q", qstring);
    QueryResponse response = client.query(query);
    SolrDocumentList list = response.getResults();
    res = parseResults(list);
	return res;
  }

//Given the list of SOPS retrieved from SOLR, each one is taken and converted in SopDocument object
  private ReturnMessage parseResults(SolrDocumentList list) {
    try {
      ClassesManagerRequest getlist = new ClassesManagerRequest();
      ArrayList<SOPDocument> resultsDoc = new ArrayList<SOPDocument>();
      for (SolrDocument doc : list) {
          SOPDocument sop = new SOPDocument();
          sop.toSOPDocument(doc);
          resultsDoc.add(sop);
        }
      getlist.setList(resultsDoc.toArray());
      res.setMessage_text("ok");
      res.setData(getlist);
    } catch (Exception exc) {
      exc.printStackTrace();
      res.setMessage_text("Database error: can't get data");
      return res;
    }
    return res;
  }
  

}