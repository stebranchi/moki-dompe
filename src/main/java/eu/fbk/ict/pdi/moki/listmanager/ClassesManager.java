package eu.fbk.ict.pdi.moki.listmanager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import eu.fbk.ict.pdi.moki.sopmanager.LuceneConstants;
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
    final String solrUrl = this.properties.getProperty("solrUrl") + "/solr/" + this.properties.getProperty("solrSchema");
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
/*  public ReturnMessage searchById() throws IOException, SolrServerException {
	  SolrQuery query = new SolrQuery();
	  query.set("q", "numrel:\"" + list_req.getId() +"\"");
	  QueryResponse response = client.query(query);
	  SolrDocumentList list = response.getResults();
	  res = parseResults(list);
	  return res;
  }  */
  
//This function is used to free text search in the fulltext fo the SOP or in the ID
  public ReturnMessage searchName() throws IOException, SolrServerException, SQLException {
	SolrQuery query = new SolrQuery();
	String name = list_req.getName();
	String type = list_req.getType();
	DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
	DBLayer.SQL("INSERT INTO searchterms (text) VALUES", name);
	name = name.trim();
	name = name + '~';
	name = name.replace(" ", "~ AND everything:");
	System.out.println(name);
    query.set("q", "everything: " + name);
    query.setStart(0);
    query.setRows(LuceneConstants.MAX_SEARCH);
	QueryResponse response = client.query(query);
	SolrDocumentList list = response.getResults();
	res = parseResults(list, type);
    return res;
  }
  
//  This Function is used to search for a sentence or a word in 1 or more specific section
  public ReturnMessage advancedSearch() throws IOException, SolrServerException {
	String type = list_req.getType();
	ArrayList<String> qlist = new ArrayList<String>();
	if (list_req.getTitle() != null && list_req.getTitle() != "") {
		String tmp = list_req.getTitle();
		String[] parts = tmp.split(" ");
		for(String x: parts) {
			qlist.add("title: " + x + "~");
		}
	}
	if (list_req.getJournal() != null && list_req.getJournal() != "") {
		String tmp = list_req.getJournal();
		qlist.add("journal: " + tmp);
	}
	if (list_req.getAuthor() != null && list_req.getAuthor() != "") {
		String tmp = list_req.getAuthor();
		String[] parts = tmp.split(" ");
		for(String x: parts) {
			qlist.add("author: " + x + "~");
		}
	}
	if (list_req.getDate() != null && list_req.getDate() != "") {
		String tmp = list_req.getDate();
		qlist.add("date: " + tmp);
	}
	if (list_req.getPages() != null && list_req.getPages() != "") {
		String tmp = list_req.getPages();
		qlist.add("pages: " + tmp);
	}
	if (list_req.getIsbn() != null && list_req.getIsbn() != "") {
		String tmp = list_req.getIsbn();
		qlist.add("isbn: " + tmp);
	}
	if (list_req.getNumrel() != null && list_req.getNumrel() != "") {
		String tmp = list_req.getNumrel();
		qlist.add("numrel: " + tmp);
	}
	if (list_req.getDoi() != null && list_req.getDoi() != "") {
		String tmp = list_req.getDoi();
		qlist.add("doi: " + tmp);
	}
	if (list_req.getRequester() != null && list_req.getRequester() != "") {
		String tmp = list_req.getRequester();
		qlist.add("requester: " + tmp);
	}
	if (list_req.getPmcid() != null && list_req.getPmcid() != "") {
		String tmp = list_req.getPmcid();
		qlist.add("pmcid: " + tmp);
	}
	if (list_req.getNotes() != null && list_req.getNotes() != "") {
		String tmp = list_req.getNotes();
		qlist.add("notes: " + tmp);
	}
	if (list_req.getAuthorAddress() != null && list_req.getAuthorAddress() != "") {
		String tmp = list_req.getAuthorAddress();
		qlist.add("author_address: " + tmp);
	}
	if (list_req.getKeywords() != null && list_req.getKeywords() != "") {
		String tmp = list_req.getKeywords();
		String[] parts = tmp.split(" ");
		for(String x: parts) {
			qlist.add("keywords: " + x + "~");
		}
	}
	if (list_req.getLanguage() != null && list_req.getLanguage() != "") {
		String tmp = list_req.getLanguage();
		qlist.add("language: " + tmp);
	}
	if (list_req.getCro() != null && list_req.getCro() != "") {
		String tmp = list_req.getCro();
		qlist.add("cro: " + tmp);
	}
	if (list_req.getAbstrac() != null && list_req.getAbstrac() != "") {
		String tmp = list_req.getAbstrac();
		String[] parts = tmp.split(" ");
		for(String x: parts) {
			qlist.add("abstract: " + x + "~");
		}
	}
	if (list_req.getMaterial() != null && list_req.getMaterial() != "") {
		String tmp = list_req.getMaterial();
		qlist.add("material: " + tmp);
	}
	if (list_req.getDocumentno() != null && list_req.getDocumentno() != "") {
		String tmp = list_req.getDocumentno();
		qlist.add("documentno: " + tmp);
	}
	if (list_req.getProject() != null && list_req.getProject() != "") {
		String tmp = list_req.getProject();
		qlist.add("project: " + tmp);
	}
	if (list_req.getGlp() != null && list_req.getGlp() != "") {
		String tmp = list_req.getGlp();
		qlist.add("glp: " + tmp);
	}
	if (list_req.getSaggio() != null && list_req.getSaggio() != "") {
		String tmp = list_req.getSaggio();
		qlist.add("saggio: " + tmp);
	}
	if (list_req.getAdministration() != null && list_req.getAdministration() != "") {
		String tmp = list_req.getAdministration();
		qlist.add("administration: " + tmp);
	}
	if (list_req.getLocation() != null && list_req.getLocation() != "") {
		String tmp = list_req.getLocation();
		qlist.add("location: " + tmp);
	}
	String qstring = String.join(" AND ", qlist);
	System.out.println(qstring);
	SolrQuery query = new SolrQuery();
	query.set("deftype", "synonym_edismax");
    query.set("synonyms", "true");
    query.set("sow","false");
    query.setStart(0);
    query.setRows(LuceneConstants.MAX_SEARCH);
	query.set("q", qstring);
    QueryResponse response = client.query(query);
    SolrDocumentList list = response.getResults();
    res = parseResults(list, type);
	return res;
  }

//Given the list of SOPS retrieved from SOLR, each one is taken and converted in SopDocument object
  private ReturnMessage parseResults(SolrDocumentList list, String type) {
    try {
      ClassesManagerRequest getlist = new ClassesManagerRequest();
      ArrayList<SOPDocument> resultsDoc = new ArrayList<SOPDocument>();
      for (SolrDocument doc : list) {
          SOPDocument sop = new SOPDocument();
          sop.toSOPDocument(doc);
          
          if(type.equals("All")) {
        	  resultsDoc.add(sop);
          }
          else if(type.equals("Paper")) {
        	  if (sop.getType().equals(type)) {
        		  resultsDoc.add(sop);
        	  }
          }else {
        	  if(!sop.getType().equals("Paper")) {
        		  resultsDoc.add(sop);
        	  }
          }
      }
      getlist.setList(resultsDoc.toArray());
      res.setMessage_text("ok");
      System.out.println("SOP parsed");
      res.setData(getlist);
    } catch (Exception exc) {
      exc.printStackTrace();
      res.setMessage_text("Database error: can't get data");
      return res;
    }
    return res;
  }

}