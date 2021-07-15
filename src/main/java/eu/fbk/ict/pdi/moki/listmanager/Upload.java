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

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
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
	    final String solrUrl = this.properties.getProperty("solrUrl") + "/solr/" + this.properties.getProperty("solrSchema");
	    this.client = new HttpSolrClient.Builder(solrUrl)
			    .withConnectionTimeout(100000)
			    .withSocketTimeout(600000)
			    .build();
	    res = new ReturnMessage();
	    res.setName_service("Upload");
	    res.setType_service("upload");
	  }
	  
	  public ReturnMessage uploadFile() throws ParserConfigurationException, SAXException, IOException, SolrServerException {
		  SolrInputDocument input = new SolrInputDocument();
		  String title = "";
		  String everything = "";
		  if (list_req.getXml() != null) {
			  String xml_tmp = list_req.getXml();
			  String[] xml_sub = xml_tmp.split("base64,");
			  String xml = xml_sub[1];
			  Decoder decoder = Base64.getDecoder();
			  byte[] bytes = decoder.decode(xml);
			  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      factory.setNamespaceAware(true);
		      DocumentBuilder builder = factory.newDocumentBuilder();
		      Document doc = builder.parse(new ByteArrayInputStream(bytes));
		      input = parseXml(doc, list_req.getType());
		      title = (String)input.getField(LuceneConstants.title).getValue();
		  } else {
			  if (list_req.getTitle() != null) {
				  	title = list_req.getTitle();
					input.addField(LuceneConstants.title, title);
					everything += title;
				}
				if (list_req.getType() != null) {
					input.addField(LuceneConstants.type, list_req.getType());
					everything += " " + list_req.getType();
				}
				if (list_req.getAuthor() != null) {
					input.addField(LuceneConstants.author, list_req.getAuthor());
					everything += " " + list_req.getAuthor();
				}
				if (list_req.getAbstrac() != null) {
					input.addField(LuceneConstants.author, list_req.getAbstrac());
					everything += " " + list_req.getAbstrac();
				}
				if (list_req.getDate() != null) {
					input.addField(LuceneConstants.date, list_req.getDate());
					everything += " " + list_req.getDate();
				}
				if (list_req.getJournal() != null) {
					input.addField(LuceneConstants.journal, list_req.getJournal());
					everything += " " + list_req.getJournal();
				}
				if (list_req.getPages() != null) {
					input.addField(LuceneConstants.pages, list_req.getPages());
					everything += " " + list_req.getPages();
				}
				if (list_req.getIsbn() != null) {
					input.addField(LuceneConstants.isbn, list_req.getIsbn());
					everything += " " + list_req.getIsbn();
				}
				if (list_req.getDoi() != null) {
					input.addField(LuceneConstants.doi, list_req.getDoi());
					everything += " " + list_req.getDoi();
				}
				if (list_req.getRequester() != null) {
					input.addField(LuceneConstants.requester, list_req.getRequester());
					everything += " " + list_req.getRequester();
				}
				if (list_req.getPmcid() != null) {
					input.addField(LuceneConstants.pmcid, list_req.getPmcid());
					everything += " " + list_req.getPmcid();
				}
				if (list_req.getNotes() != null) {
					input.addField(LuceneConstants.notes, list_req.getNotes());
					everything += " " + list_req.getNotes();
				}
				if (list_req.getAuthorAddress() != null) {
					input.addField(LuceneConstants.author_address, list_req.getAuthorAddress());
					everything += " " + list_req.getAuthorAddress();
				}
				if (list_req.getKeywords() != null) {
					input.addField(LuceneConstants.keywords, list_req.getKeywords());
					everything += " " + list_req.getKeywords();
				}
				if (list_req.getLanguage() != null) {
					input.addField(LuceneConstants.language, list_req.getLanguage());
					everything += " " + list_req.getLanguage();
				}
				if (list_req.getCro() != null) {
					input.addField(LuceneConstants.cro, list_req.getCro());
					everything += " " + list_req.getCro();
				}
				if (list_req.getMaterial() != null) {
					input.addField(LuceneConstants.material, list_req.getMaterial());
					everything += " " + list_req.getMaterial();
				}
				if (list_req.getDocumentno() != null) {
					input.addField(LuceneConstants.documentno, list_req.getDocumentno());
					everything += " " + list_req.getDocumentno();
				}
				if (list_req.getProject() != null) {
					input.addField(LuceneConstants.project, list_req.getProject());
					everything += " " + list_req.getProject();
				}
				if (list_req.getGlp() != null) {
					input.addField(LuceneConstants.glp, list_req.getGlp());
					everything += " " + list_req.getGlp();
				}
				if (list_req.getSaggio() != null) {
					input.addField(LuceneConstants.saggio, list_req.getSaggio());
					everything += " " + list_req.getSaggio();
				}
				if (list_req.getAdministration() != null) {
					input.addField(LuceneConstants.administration, list_req.getAdministration());
					everything += " " + list_req.getAdministration();
				}
				if (list_req.getDesc_material() != null) {
					input.addField(LuceneConstants.desc_material, list_req.getDesc_material());
					everything += " " + list_req.getDesc_material();
				}
				if (list_req.getData_arch() != null) {
					input.addField(LuceneConstants.data_arch, list_req.getData_arch());
					everything += " " + list_req.getData_arch();
				}
				if (list_req.getNum_lotto() != null) {
					input.addField(LuceneConstants.num_lotto, list_req.getNum_lotto());
					everything += " " + list_req.getNum_lotto();
				}
				if (list_req.getProdotto() != null) {
					input.addField(LuceneConstants.prodotto, list_req.getProdotto());
					everything += " " + list_req.getProdotto();
				}
				if (list_req.getContainer() != null) {
					input.addField(LuceneConstants.container, list_req.getContainer());
					everything += " " + list_req.getContainer();
				}
				if (list_req.getFormula() != null) {
					input.addField(LuceneConstants.formula, list_req.getFormula());
					everything += " " + list_req.getFormula();
				}
				if (list_req.getTipo_studio() != null) {
					input.addField(LuceneConstants.tipo_studio, list_req.getTipo_studio());
					everything += " " + list_req.getTipo_studio();
				}
				if (list_req.getLocation() != null) {
					input.addField(LuceneConstants.location, list_req.getLocation());
					everything += " " + list_req.getLocation();
				}
		  }
		  String pdf = null;
		  if (list_req.getPdf() != null) {
			  String pdf_tmp = list_req.getPdf();
		  	  String[] pdf_sub = pdf_tmp.split("base64,");
		  	  pdf = pdf_sub[1];
		  	  input = parsePdf(pdf, list_req.getTitle(), input, everything);
		  }
		  
		  java.util.Date dt = new java.util.Date();
		  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String currentTime = sdf.format(dt);
		  input.addField(LuceneConstants.date_upload, currentTime);
		  String user_email = (String)this.session.getAttribute("email");
		  
		  this.client.add(input);
	      this.client.commit();
	      System.out.println("Committed");
		  
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
	  
	  private SolrInputDocument parseXml(Document doc, String type) {
		  NodeList nList = doc.getElementsByTagName("record");
    	  SolrInputDocument document = new SolrInputDocument();
    	  Node nNode = nList.item(0); 
    	  String everything = "";
    	  if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    		   Element element = (Element) nNode;
    		   NodeList tmp_node = element.getElementsByTagName("title");
    		   String title = "";
    		   if(tmp_node.getLength()>0) {
    			   title = tmp_node.item(0).getFirstChild().getTextContent();
    			   everything += title;
    		   }
    		   tmp_node = element.getElementsByTagName("abstract");
    		   String abstrac = "";
    		   if(tmp_node.getLength()>0) {
    			   abstrac = tmp_node.item(0).getFirstChild().getTextContent();
    			   everything += abstrac;
    		   }
    		   String autori = "";
    		   tmp_node = element.getElementsByTagName("author"); 
    		   for(int i = 0; i < tmp_node.getLength(); i++) {
    			   autori = autori + tmp_node.item(i).getFirstChild().getTextContent();
    			   everything += autori;
    		   }
    		   String pag = "";
    		   tmp_node = element.getElementsByTagName("pages"); 
    		   if(tmp_node.getLength()>0) {
    			   pag = tmp_node.item(0).getFirstChild().getTextContent();
    			   everything += pag;
    		   }
    		   String data = "";
    		   String isbn = "";
    		   tmp_node = element.getElementsByTagName("dates"); 
    		   if(tmp_node.getLength()>0) {
    			   data = tmp_node.item(0).getTextContent();
    			   String[] tmp_list = data.split(" - ");
    			   data = tmp_list[0];
    			   everything += data;
    			   if(tmp_list.length > 1) {
	    			   isbn = tmp_list[1];
	    			   everything += isbn;
    			   } else {
    				   tmp_node = element.getElementsByTagName("isbn"); 
    	    		   if(tmp_node.getLength()>0) {
    	    			   isbn = tmp_node.item(0).getFirstChild().getTextContent();
    	    		   }
    			   }
    		   }
    		   String notes = "";
    		   tmp_node = element.getElementsByTagName("notes"); 
    		   if(tmp_node.getLength()>0) {
    			   notes = tmp_node.item(0).getFirstChild().getTextContent();
    			   everything += notes;
    		   }
    		   String auth_add = "";
    		   tmp_node = element.getElementsByTagName("author-address"); 
    		   if(tmp_node.getLength()>0) {
    			   auth_add = tmp_node.item(0).getFirstChild().getTextContent();
    			   everything += auth_add;
    		   }
    		
    		   java.util.Date dt = new java.util.Date();
			   java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   String currentTime = sdf.format(dt);
	    		   document.addField(LuceneConstants.date_upload, currentTime);
	    		   document.addField(LuceneConstants.title, title);
    		       document.addField(LuceneConstants.author, autori);
	    		   document.addField(LuceneConstants.notes, notes);
    		       document.addField(LuceneConstants.author_address, auth_add);
    		       document.addField(LuceneConstants.isbn, isbn);
    		       document.addField(LuceneConstants.pages, pag);
    		       document.addField(LuceneConstants.date, data);
    		       document.addField(LuceneConstants.type, type);
    		       document.addField(LuceneConstants.abstrac, abstrac);
    		       document.addField(LuceneConstants.language, "ITA");
    		       document.addField(LuceneConstants.everything, everything);
    	  }
    	return document;  
	  }
	  
	  private SolrInputDocument parsePdf(String pdf, String title, SolrInputDocument doc, String everything) throws IOException, SolrServerException {
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
		  everything += " " + text;
		  doc.addField(LuceneConstants.urls, url_pdf);
	      doc.addField(LuceneConstants.fullText, text);
	      doc.addField(LuceneConstants.everything, everything);
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
