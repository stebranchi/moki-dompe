package eu.fbk.ict.pdi.moki.sopmanager;

import org.apache.solr.common.SolrDocument;
import eu.fbk.ict.pdi.moki.sopmanager.LuceneConstants;

public class SOPDocument {

	private String id;
	private String title;
	private String code;
	private String authors;
	private String fullText;
	private String urls;
	private String type;
	private String abstrac;
	private String csper;
	private String materiale;
	private String descmater;
	private String conten;
	private String dataarch;
	private String numlot;
	private String prod;
	private String uso;
	private String colcont;
	private String utins;
	private String utmod;
	private String dtins;
	private String testataId;
	private String descmater2;
	private String numstud;
	private String datastud;
	private String prog_prod;
	private String societa;
	private String glp_gcp;
	private String classarch;
	private String formul;
	private String saggio;
	private String sommin;
	private String tipostud;
	private String elkeyric;
	private String Datacheck;
	private String DETTAGLIO;
	private String TESTATA;
	private String dtmod;
  

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	
    public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getAuthors() {
		return authors;
	}


	public void setAuthors(String authors) {
		this.authors = authors;
	}


	public String getFullText() {
		return fullText;
	}


	public void setFullText(String fullText) {
		this.fullText = fullText;
	}


	public String getUrls() {
		return urls;
	}


	public void setUrls(String urls) {
		this.urls = urls;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getAbstrac() {
		return abstrac;
	}


	public void setAbstrac(String abstrac) {
		this.abstrac = abstrac;
	}


	public String getCsper() {
		return csper;
	}


	public void setCsper(String csper) {
		this.csper = csper;
	}


	public String getMateriale() {
		return materiale;
	}


	public void setMateriale(String materiale) {
		this.materiale = materiale;
	}


	public String getDescmater() {
		return descmater;
	}


	public void setDescmater(String descmater) {
		this.descmater = descmater;
	}


	public String getConten() {
		return conten;
	}


	public void setConten(String conten) {
		this.conten = conten;
	}


	public String getDataarch() {
		return dataarch;
	}


	public void setDataarch(String dataarch) {
		this.dataarch = dataarch;
	}


	public String getNumlot() {
		return numlot;
	}


	public void setNumlot(String numlot) {
		this.numlot = numlot;
	}


	public String getProd() {
		return prod;
	}


	public void setProd(String prod) {
		this.prod = prod;
	}


	public String getUso() {
		return uso;
	}


	public void setUso(String uso) {
		this.uso = uso;
	}


	public String getColcont() {
		return colcont;
	}


	public void setColcont(String colcont) {
		this.colcont = colcont;
	}


	public String getUtins() {
		return utins;
	}


	public void setUtins(String utins) {
		this.utins = utins;
	}


	public String getUtmod() {
		return utmod;
	}


	public void setUtmod(String utmod) {
		this.utmod = utmod;
	}


	public String getDtins() {
		return dtins;
	}


	public void setDtins(String dtins) {
		this.dtins = dtins;
	}


	public String getTestataId() {
		return testataId;
	}


	public void setTestataId(String testataId) {
		this.testataId = testataId;
	}


	public String getDescmater2() {
		return descmater2;
	}


	public void setDescmater2(String descmater2) {
		this.descmater2 = descmater2;
	}


	public String getNumstud() {
		return numstud;
	}


	public void setNumstud(String numstud) {
		this.numstud = numstud;
	}


	public String getDatastud() {
		return datastud;
	}


	public void setDatastud(String datastud) {
		this.datastud = datastud;
	}


	public String getProg_prod() {
		return prog_prod;
	}


	public void setProg_prod(String prog_prod) {
		this.prog_prod = prog_prod;
	}


	public String getSocieta() {
		return societa;
	}


	public void setSocieta(String societa) {
		this.societa = societa;
	}


	public String getGlp_gcp() {
		return glp_gcp;
	}


	public void setGlp_gcp(String glp_gcp) {
		this.glp_gcp = glp_gcp;
	}


	public String getClassarch() {
		return classarch;
	}


	public void setClassarch(String classarch) {
		this.classarch = classarch;
	}


	public String getFormul() {
		return formul;
	}


	public void setFormul(String formul) {
		this.formul = formul;
	}


	public String getSaggio() {
		return saggio;
	}


	public void setSaggio(String saggio) {
		this.saggio = saggio;
	}


	public String getSommin() {
		return sommin;
	}


	public void setSommin(String sommin) {
		this.sommin = sommin;
	}


	public String getTipostud() {
		return tipostud;
	}


	public void setTipostud(String tipostud) {
		this.tipostud = tipostud;
	}


	public String getElkeyric() {
		return elkeyric;
	}


	public void setElkeyric(String elkeyric) {
		this.elkeyric = elkeyric;
	}


	public String getDatacheck() {
		return Datacheck;
	}


	public void setDatacheck(String datacheck) {
		Datacheck = datacheck;
	}


	public String getDETTAGLIO() {
		return DETTAGLIO;
	}


	public void setDETTAGLIO(String dETTAGLIO) {
		DETTAGLIO = dETTAGLIO;
	}


	public String getTESTATA() {
		return TESTATA;
	}


	public void setTESTATA(String tESTATA) {
		TESTATA = tESTATA;
	}


	public String getDtmod() {
		return dtmod;
	}


	public void setDtmod(String dtmod) {
		this.dtmod = dtmod;
	}


public SOPDocument toSOPDocument(SolrDocument doc) {
	    this.title = (doc.getFieldValue(LuceneConstants.title) != null) ? doc.getFieldValue(LuceneConstants.title).toString().substring(1, (doc.getFieldValue(LuceneConstants.title).toString().length()-1)) : "";
	    this.code = (doc.getFieldValue(LuceneConstants.code) != null) ? doc.getFieldValue(LuceneConstants.code).toString().substring(1, (doc.getFieldValue(LuceneConstants.code).toString().length()-1)) : "";
	    this.authors = (doc.getFieldValue(LuceneConstants.authors) != null) ? doc.getFieldValue(LuceneConstants.authors).toString().substring(1, (doc.getFieldValue(LuceneConstants.authors).toString().length()-1)) : "";
	    this.fullText = (doc.getFieldValue(LuceneConstants.fullText) != null) ? doc.getFieldValue(LuceneConstants.fullText).toString().substring(1, (doc.getFieldValue(LuceneConstants.fullText).toString().length()-1)) : "";
	    this.urls = (doc.getFieldValue(LuceneConstants.urls) != null) ? doc.getFieldValue(LuceneConstants.urls).toString().substring(1, (doc.getFieldValue(LuceneConstants.urls).toString().length()-1)) : "";
	    this.type = (doc.getFieldValue(LuceneConstants.type) != null) ? doc.getFieldValue(LuceneConstants.type).toString().substring(1, (doc.getFieldValue(LuceneConstants.type).toString().length()-1)) : "";
	    this.abstrac = (doc.getFieldValue(LuceneConstants.abstrac) != null) ? doc.getFieldValue(LuceneConstants.abstrac).toString().substring(1, (doc.getFieldValue(LuceneConstants.abstrac).toString().length()-1)) : "";
	    this.csper = (doc.getFieldValue(LuceneConstants.csper) != null) ? doc.getFieldValue(LuceneConstants.csper).toString().substring(1, (doc.getFieldValue(LuceneConstants.csper).toString().length()-1)) : "";
	    this.materiale = (doc.getFieldValue(LuceneConstants.materiale) != null) ? doc.getFieldValue(LuceneConstants.materiale).toString().substring(1, (doc.getFieldValue(LuceneConstants.materiale).toString().length()-1)) : "";
	    this.descmater = (doc.getFieldValue(LuceneConstants.descmater) != null) ? doc.getFieldValue(LuceneConstants.descmater).toString().substring(1, (doc.getFieldValue(LuceneConstants.descmater).toString().length()-1)) : "";
	    this.conten = (doc.getFieldValue(LuceneConstants.conten) != null) ? doc.getFieldValue(LuceneConstants.conten).toString().substring(1, (doc.getFieldValue(LuceneConstants.conten).toString().length()-1)) : "";
	    this.dataarch = (doc.getFieldValue(LuceneConstants.dataarch) != null) ? doc.getFieldValue(LuceneConstants.dataarch).toString().substring(1, (doc.getFieldValue(LuceneConstants.dataarch).toString().length()-1)) : "";
	    this.numlot = (doc.getFieldValue(LuceneConstants.numlot) != null) ? doc.getFieldValue(LuceneConstants.numlot).toString().substring(1, (doc.getFieldValue(LuceneConstants.numlot).toString().length()-1)) : "";
	    this.prod = (doc.getFieldValue(LuceneConstants.prod) != null) ? doc.getFieldValue(LuceneConstants.prod).toString().substring(1, (doc.getFieldValue(LuceneConstants.prod).toString().length()-1)) : "";
	    this.uso = (doc.getFieldValue(LuceneConstants.uso) != null) ? doc.getFieldValue(LuceneConstants.uso).toString().substring(1, (doc.getFieldValue(LuceneConstants.uso).toString().length()-1)) : "";
	    this.colcont = (doc.getFieldValue(LuceneConstants.colcont) != null) ? doc.getFieldValue(LuceneConstants.colcont).toString().substring(1, (doc.getFieldValue(LuceneConstants.colcont).toString().length()-1)) : "";
	    this.utins = (doc.getFieldValue(LuceneConstants.utins) != null) ? doc.getFieldValue(LuceneConstants.utins).toString().substring(1, (doc.getFieldValue(LuceneConstants.utins).toString().length()-1)) : "";
	    this.utmod = (doc.getFieldValue(LuceneConstants.utmod) != null) ? doc.getFieldValue(LuceneConstants.utmod).toString().substring(1, (doc.getFieldValue(LuceneConstants.utmod).toString().length()-1)) : "";
	    this.dtins = (doc.getFieldValue(LuceneConstants.dtins) != null) ? doc.getFieldValue(LuceneConstants.dtins).toString().substring(1, (doc.getFieldValue(LuceneConstants.dtins).toString().length()-1)) : "";
	    this.testataId = (doc.getFieldValue(LuceneConstants.testataId) != null) ? doc.getFieldValue(LuceneConstants.testataId).toString().substring(1, (doc.getFieldValue(LuceneConstants.testataId).toString().length()-1)) : "";
	    this.descmater2 = (doc.getFieldValue(LuceneConstants.descmater2) != null) ? doc.getFieldValue(LuceneConstants.descmater2).toString().substring(1, (doc.getFieldValue(LuceneConstants.descmater2).toString().length()-1)) : "";
	    this.numstud = (doc.getFieldValue(LuceneConstants.numstud) != null) ? doc.getFieldValue(LuceneConstants.numstud).toString().substring(1, (doc.getFieldValue(LuceneConstants.numstud).toString().length()-1)) : "";
	    this.datastud = (doc.getFieldValue(LuceneConstants.datastud) != null) ? doc.getFieldValue(LuceneConstants.datastud).toString().substring(1, (doc.getFieldValue(LuceneConstants.datastud).toString().length()-1)) : "";
	    this.prog_prod = (doc.getFieldValue(LuceneConstants.prog_prod) != null) ? doc.getFieldValue(LuceneConstants.prog_prod).toString().substring(1, (doc.getFieldValue(LuceneConstants.prog_prod).toString().length()-1)) : "";
	    this.societa = (doc.getFieldValue(LuceneConstants.societa) != null) ? doc.getFieldValue(LuceneConstants.societa).toString().substring(1, (doc.getFieldValue(LuceneConstants.societa).toString().length()-1)) : "";
	    this.glp_gcp = (doc.getFieldValue(LuceneConstants.glp_gcp) != null) ? doc.getFieldValue(LuceneConstants.glp_gcp).toString().substring(1, (doc.getFieldValue(LuceneConstants.glp_gcp).toString().length()-1)) : "";
	    this.classarch = (doc.getFieldValue(LuceneConstants.classarch) != null) ? doc.getFieldValue(LuceneConstants.classarch).toString().substring(1, (doc.getFieldValue(LuceneConstants.classarch).toString().length()-1)) : "";
	    this.formul = (doc.getFieldValue(LuceneConstants.formul) != null) ? doc.getFieldValue(LuceneConstants.formul).toString().substring(1, (doc.getFieldValue(LuceneConstants.formul).toString().length()-1)) : "";
	    this.saggio = (doc.getFieldValue(LuceneConstants.saggio) != null) ? doc.getFieldValue(LuceneConstants.saggio).toString().substring(1, (doc.getFieldValue(LuceneConstants.saggio).toString().length()-1)) : "";
	    this.sommin = (doc.getFieldValue(LuceneConstants.sommin) != null) ? doc.getFieldValue(LuceneConstants.sommin).toString().substring(1, (doc.getFieldValue(LuceneConstants.sommin).toString().length()-1)) : "";
	    this.tipostud = (doc.getFieldValue(LuceneConstants.tipostud) != null) ? doc.getFieldValue(LuceneConstants.tipostud).toString().substring(1, (doc.getFieldValue(LuceneConstants.tipostud).toString().length()-1)) : "";
	    this.elkeyric = (doc.getFieldValue(LuceneConstants.elkeyric) != null) ? doc.getFieldValue(LuceneConstants.elkeyric).toString().substring(1, (doc.getFieldValue(LuceneConstants.elkeyric).toString().length()-1)) : "";
	    this.Datacheck = (doc.getFieldValue(LuceneConstants.Datacheck) != null) ? doc.getFieldValue(LuceneConstants.Datacheck).toString().substring(1, (doc.getFieldValue(LuceneConstants.Datacheck).toString().length()-1)) : "";
	    this.DETTAGLIO = (doc.getFieldValue(LuceneConstants.DETTAGLIO) != null) ? doc.getFieldValue(LuceneConstants.DETTAGLIO).toString().substring(1, (doc.getFieldValue(LuceneConstants.DETTAGLIO).toString().length()-1)) : "";
	    this.TESTATA = (doc.getFieldValue(LuceneConstants.TESTATA) != null) ? doc.getFieldValue(LuceneConstants.TESTATA).toString().substring(1, (doc.getFieldValue(LuceneConstants.TESTATA).toString().length()-1)) : "";
	    this.dtmod = (doc.getFieldValue(LuceneConstants.dtmod) != null) ? doc.getFieldValue(LuceneConstants.dtmod).toString().substring(1, (doc.getFieldValue(LuceneConstants.dtmod).toString().length()-1)) : "";

	    return this;
  }
  

}
