package eu.fbk.ict.pdi.moki.listmanager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.Base64.Decoder;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

import eu.fbk.ict.pdi.moki.sopmanager.LuceneConstants;
import eu.fbk.ict.pdi.moki.utils.Config;
import eu.fbk.ict.pdi.moki.utils.DBLayer;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import eu.fbk.ict.pdi.moki.interfaces.Service;
import eu.fbk.ict.pdi.moki.requestmanager.ReturnMessage;

public class Upload implements Service {
	  UploadRequest list_req = null;
	  HttpSession session;
	  Part pdf;
	  Part xml;
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
	      e.printStackTrace();
	      res.setMessage_text("mode " + list_req.getMode() + " don't found");
	    }

	    return res;
	  }

	  @Override
	  public void init(String data, HttpSession session) {

	    list_req = gson.fromJson(data, UploadRequest.class);
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
	    res.setName_service("Upload");
	    res.setType_service("upload");
	  }
	  
	  public ReturnMessage uploadFile() throws ParserConfigurationException, SAXException, IOException, SolrServerException {
		  String xml_tmp = list_req.getXml();
		  String[] xml_sub = xml_tmp.split("base64,");
		  String xml = xml_sub[1];
		  String pdf = null;
		  if (list_req.getPdf() != null) {
			  String pdf_tmp = list_req.getPdf();
		  	  String[] pdf_sub = pdf_tmp.split("base64,");
		  	  pdf = pdf_sub[1];
		  }
		  String title = parseFile(xml, pdf);
		  
		  java.util.Date dt = new java.util.Date();
		  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String currentTime = sdf.format(dt);
		  String user_email = (String)this.session.getAttribute("email");
		  
		  try {
			   DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
			   DBLayer.SQL("INSERT INTO upload VALUES (DEFAULT, ?, ?, ?)", user_email, title, currentTime);
			   
		  } catch(Exception e) {
			   System.err.println("errore nell'inserimento del log");
		  }

		  res.setMessage_text("ok");
		  res.setData("index.jsp?page=uploadcard&module=upload&notification=uploadok");
		  return res;
	  }
	
	  private String parseFile(String xml, String pdf) throws ParserConfigurationException, SAXException, IOException, SolrServerException {
		  Decoder decoder = Base64.getDecoder();
		  byte[] bytes = decoder.decode(xml);
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      factory.setNamespaceAware(true);
	      DocumentBuilder builder = factory.newDocumentBuilder();
	      Document doc = builder.parse(new ByteArrayInputStream(bytes));
	      SolrInputDocument input = parseXml(doc, list_req.getType());
	      String title = (String)input.getField(LuceneConstants.title).getValue();
	      if (pdf != null) {
	    	  System.out.println("Parsing PDF");
	    	  input = parsePdf(pdf, list_req.getTitle(), input);
	      }
	      this.client.add(input);
	      this.client.commit();
	      System.out.println("Committed");
	      
	      return title;
	  }
	  
	  private SolrInputDocument parseXml(Document doc, String type) {
		  NodeList nList = doc.getElementsByTagName("record");
    	  SolrInputDocument document = new SolrInputDocument();
    	  Node nNode = nList.item(0); 
    	  if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    		   Element element = (Element) nNode;
    		   NodeList tmp_title = element.getElementsByTagName("title");
    		   String title = "";
    		   if(tmp_title.getLength()>0) {
    			   title = tmp_title.item(0).getFirstChild().getTextContent();
    		   }
    		   NodeList tmp_abstrac = element.getElementsByTagName("abstract");
    		   String abstrac = "";
    		   if(tmp_abstrac.getLength()>0) {
    			   abstrac = tmp_abstrac.item(0).getFirstChild().getTextContent();
    		   }
    		   String autori = "";
    		   NodeList authors = element.getElementsByTagName("author"); 
    		   for(int i = 0; i < authors.getLength(); i++) {
    			   autori = autori + authors.item(i).getFirstChild().getTextContent();
    		   }
    		   String isbn = "";
    		   NodeList tmp_isbn = element.getElementsByTagName("isbn");
    		   if (tmp_isbn.getLength() > 0) {
    			   isbn = tmp_isbn.item(0).getFirstChild().getTextContent();
    			   String[] isbn_tmp = isbn.split("[(]");
    			   isbn = isbn_tmp[0];
    			   if (isbn_tmp.length > 1) {
    				   isbn = isbn.substring(0, isbn.length() - 1);
    			   }
    		   }
    	document.addField(LuceneConstants.title, title);
	    document.addField(LuceneConstants.authors, autori);
	    document.addField(LuceneConstants.code, isbn);
	    document.addField(LuceneConstants.type, type);
	    document.addField(LuceneConstants.abstrac, abstrac);
	    }
    	return document;  
	  }
	  
	  private SolrInputDocument parsePdf(String pdf, String title, SolrInputDocument doc) throws IOException, SolrServerException {
		  String file_path = "";
		  try {
		  Decoder decoder = Base64.getDecoder();
		  file_path = this.properties.getProperty("datafolder") + title + ".pdf";
		  System.out.println("Creating" + file_path);
		  File file_out = new File(file_path);
		  FileOutputStream fop = new FileOutputStream(file_out);
		  System.out.println("Decoding");
		  byte[] bytes = decoder.decode(pdf);
		  System.out.println("Decoded");
		  fop.write(bytes);
		  fop.close();
		  }
		  catch (Exception e) {
			  res.setMessage_text(e.toString());
		  }
		  System.out.println("PDF Saved");
		  String text = "";
		  Tesseract tesseract = new Tesseract();
	        try {
	        	System.out.println("Starting OCR");
	            tesseract.setDatapath(this.properties.getProperty("tessdata"));
	            text = tesseract.doOCR(new File(file_path));
	        	System.out.println("Finished OCR");
	        }
	        catch (TesseractException e) {
	            e.printStackTrace();
	        }
	      if (text == "") {
	    	  System.out.println("Errore lettura allegato");
	      }
		  String url_pdf = "internal-pdf://" + title + ".pdf";
		  doc.addField(LuceneConstants.urls, url_pdf);
	      doc.addField(LuceneConstants.fullText, text);
	      return doc;
	  }
	  
	  public ReturnMessage getUploadHistory() {
		  ArrayList<UploadHistoryClass> list = new ArrayList<UploadHistoryClass>();
		  try {
				DBLayer.connect(Config.server, Config.dbName, Config.user, Config.pass);
				ResultSet rs = DBLayer.SQL("SELECT * FROM upload ORDER BY STR_TO_DATE(datetime, '%Y-%m-%d %h:%i:%s') DESC");
				ResultSet num_upload = DBLayer.SQL("SELECT COUNT(u.user_email) AS count FROM upload AS u WHERE datetime > STR_TO_DATE(?, '%Y-%m-%d %h:%i:%s')", session.getAttribute("lastlogin").toString());
				String count = "";
				System.out.println(session.getAttribute("lastlogin").toString());
				while(num_upload.next()) {
					count = num_upload.getString("count");
				}
				while(rs.next()) {
					ResultSet user = DBLayer.SQL("SELECT name FROM user WHERE email = ?", rs.getString("user_email"));
					user.next();
					String name = user.getString("name");
					UploadHistoryClass tmp = new UploadHistoryClass();
					tmp.setDate(rs.getString("datetime"));
					tmp.setTitle(rs.getString("title"));
					tmp.setCount(count);
					tmp.setUser(name);
					list.add(tmp);
				}
				res.setMessage_text("ok");
				res.setData(list);
				return res;
		  }catch(Exception exc) {
					exc.printStackTrace();
					System.out.println(exc.toString());
					res.setMessage_text("Database error: can't get data");
					return res;
		  }
	  }
}
