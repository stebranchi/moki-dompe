package eu.fbk.ict.pdi.moki.sopmanager;

import org.apache.solr.common.SolrDocument;
import eu.fbk.ict.pdi.moki.sopmanager.LuceneConstants;

public class SOPDocument {

	private String id;
	private String title;
	private String isbn;
	private String numrel;
	private String author;
	private String fullText;
	private String urls;
	private String type;
	private String abstrac;
	private String date;
	private String material;
	private String pages;
	private String doi;
	private String requester;
	private String pmcid;
	private String notes;
	private String author_address;
	private String keywords;
	private String language;
	private String cro;
	private String documentno;
	private String project;
	private String glp;
	private String saggio;
	private String administration;
	private String location;
	private String date_upload;
	private String desc_material;
	private String data_arch;
	private String num_lotto;
	private String prodotto;
	private String container;
	private String formula;
	private String tipo_studio;
  


	public String getDesc_material() {
		return desc_material;
	}



	public void setDesc_material(String desc_material) {
		this.desc_material = desc_material;
	}



	public String getData_arch() {
		return data_arch;
	}



	public void setData_arch(String data_arch) {
		this.data_arch = data_arch;
	}



	public String getNum_lotto() {
		return num_lotto;
	}



	public void setNum_lotto(String num_lotto) {
		this.num_lotto = num_lotto;
	}



	public String getProdotto() {
		return prodotto;
	}



	public void setProdotto(String prodotto) {
		this.prodotto = prodotto;
	}



	public String getContainer() {
		return container;
	}



	public void setContainer(String container) {
		this.container = container;
	}



	public String getFormula() {
		return formula;
	}



	public void setFormula(String formula) {
		this.formula = formula;
	}



	public String getTipo_studio() {
		return tipo_studio;
	}



	public void setTipo_studio(String tipo_studio) {
		this.tipo_studio = tipo_studio;
	}



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



	public String getIsbn() {
		return isbn;
	}



	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}



	public String getNumrel() {
		return numrel;
	}



	public void setNumrel(String numrel) {
		this.numrel = numrel;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
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



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getMaterial() {
		return material;
	}



	public void setMaterial(String material) {
		this.material = material;
	}



	public String getPages() {
		return pages;
	}



	public void setPages(String pages) {
		this.pages = pages;
	}



	public String getDoi() {
		return doi;
	}



	public void setDoi(String doi) {
		this.doi = doi;
	}



	public String getRequester() {
		return requester;
	}



	public void setRequester(String requester) {
		this.requester = requester;
	}



	public String getPmcid() {
		return pmcid;
	}



	public void setPmcid(String pmcid) {
		this.pmcid = pmcid;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}



	public String getAuthor_address() {
		return author_address;
	}



	public void setAuthor_address(String author_address) {
		this.author_address = author_address;
	}



	public String getKeywords() {
		return keywords;
	}



	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public String getCro() {
		return cro;
	}



	public void setCro(String cro) {
		this.cro = cro;
	}



	public String getDocumentno() {
		return documentno;
	}



	public void setDocumentno(String documentno) {
		this.documentno = documentno;
	}



	public String getProject() {
		return project;
	}



	public void setProject(String project) {
		this.project = project;
	}



	public String getGlp() {
		return glp;
	}



	public void setGlp(String glp) {
		this.glp = glp;
	}



	public String getSaggio() {
		return saggio;
	}



	public void setSaggio(String saggio) {
		this.saggio = saggio;
	}



	public String getAdministration() {
		return administration;
	}



	public void setAdministration(String administration) {
		this.administration = administration;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getDate_upload() {
		return date_upload;
	}



	public void setDate_upload(String date_upload) {
		this.date_upload = date_upload;
	}



public SOPDocument toSOPDocument(SolrDocument doc) {
	    this.title = (doc.getFieldValue(LuceneConstants.title) != null) ? doc.getFieldValue(LuceneConstants.title).toString().substring(1, (doc.getFieldValue(LuceneConstants.title).toString().length()-1)) : "";
	    this.isbn = (doc.getFieldValue(LuceneConstants.isbn) != null) ? doc.getFieldValue(LuceneConstants.isbn).toString().substring(1, (doc.getFieldValue(LuceneConstants.isbn).toString().length()-1)) : "";
	    this.numrel = (doc.getFieldValue(LuceneConstants.numrel) != null) ? doc.getFieldValue(LuceneConstants.numrel).toString().substring(1, (doc.getFieldValue(LuceneConstants.numrel).toString().length()-1)) : "";
	    this.author = (doc.getFieldValue(LuceneConstants.author) != null) ? doc.getFieldValue(LuceneConstants.author).toString().substring(1, (doc.getFieldValue(LuceneConstants.author).toString().length()-1)) : "";
	    this.fullText = (doc.getFieldValue(LuceneConstants.fullText) != null) ? doc.getFieldValue(LuceneConstants.fullText).toString().substring(1, (doc.getFieldValue(LuceneConstants.fullText).toString().length()-1)) : "";
	    this.urls = (doc.getFieldValue(LuceneConstants.urls) != null) ? doc.getFieldValue(LuceneConstants.urls).toString().substring(1, (doc.getFieldValue(LuceneConstants.urls).toString().length()-1)) : "";
	    this.type = (doc.getFieldValue(LuceneConstants.type) != null) ? doc.getFieldValue(LuceneConstants.type).toString().substring(1, (doc.getFieldValue(LuceneConstants.type).toString().length()-1)) : "";
	    this.abstrac = (doc.getFieldValue(LuceneConstants.abstrac) != null) ? doc.getFieldValue(LuceneConstants.abstrac).toString().substring(1, (doc.getFieldValue(LuceneConstants.abstrac).toString().length()-1)) : "";
	    this.date = (doc.getFieldValue(LuceneConstants.date) != null) ? doc.getFieldValue(LuceneConstants.date).toString().substring(1, (doc.getFieldValue(LuceneConstants.date).toString().length()-1)) : "";
	    this.material = (doc.getFieldValue(LuceneConstants.material) != null) ? doc.getFieldValue(LuceneConstants.material).toString().substring(1, (doc.getFieldValue(LuceneConstants.material).toString().length()-1)) : "";
	    this.pages = (doc.getFieldValue(LuceneConstants.pages) != null) ? doc.getFieldValue(LuceneConstants.pages).toString().substring(1, (doc.getFieldValue(LuceneConstants.pages).toString().length()-1)) : "";
	    this.doi = (doc.getFieldValue(LuceneConstants.doi) != null) ? doc.getFieldValue(LuceneConstants.doi).toString().substring(1, (doc.getFieldValue(LuceneConstants.doi).toString().length()-1)) : "";
	    this.requester = (doc.getFieldValue(LuceneConstants.requester) != null) ? doc.getFieldValue(LuceneConstants.requester).toString().substring(1, (doc.getFieldValue(LuceneConstants.requester).toString().length()-1)) : "";
	    this.pmcid = (doc.getFieldValue(LuceneConstants.pmcid) != null) ? doc.getFieldValue(LuceneConstants.pmcid).toString().substring(1, (doc.getFieldValue(LuceneConstants.pmcid).toString().length()-1)) : "";
	    this.notes = (doc.getFieldValue(LuceneConstants.notes) != null) ? doc.getFieldValue(LuceneConstants.notes).toString().substring(1, (doc.getFieldValue(LuceneConstants.notes).toString().length()-1)) : "";
	    this.author_address = (doc.getFieldValue(LuceneConstants.author_address) != null) ? doc.getFieldValue(LuceneConstants.author_address).toString().substring(1, (doc.getFieldValue(LuceneConstants.author_address).toString().length()-1)) : "";
	    this.keywords = (doc.getFieldValue(LuceneConstants.keywords) != null) ? doc.getFieldValue(LuceneConstants.keywords).toString().substring(1, (doc.getFieldValue(LuceneConstants.keywords).toString().length()-1)) : "";
	    this.language = (doc.getFieldValue(LuceneConstants.language) != null) ? doc.getFieldValue(LuceneConstants.language).toString().substring(1, (doc.getFieldValue(LuceneConstants.language).toString().length()-1)) : "";
	    this.cro = (doc.getFieldValue(LuceneConstants.cro) != null) ? doc.getFieldValue(LuceneConstants.cro).toString().substring(1, (doc.getFieldValue(LuceneConstants.cro).toString().length()-1)) : "";
	    this.documentno = (doc.getFieldValue(LuceneConstants.documentno) != null) ? doc.getFieldValue(LuceneConstants.documentno).toString().substring(1, (doc.getFieldValue(LuceneConstants.documentno).toString().length()-1)) : "";
	    this.project = (doc.getFieldValue(LuceneConstants.project) != null) ? doc.getFieldValue(LuceneConstants.project).toString().substring(1, (doc.getFieldValue(LuceneConstants.project).toString().length()-1)) : "";
	    this.glp = (doc.getFieldValue(LuceneConstants.glp) != null) ? doc.getFieldValue(LuceneConstants.glp).toString().substring(1, (doc.getFieldValue(LuceneConstants.glp).toString().length()-1)) : "";
	    this.saggio = (doc.getFieldValue(LuceneConstants.saggio) != null) ? doc.getFieldValue(LuceneConstants.saggio).toString().substring(1, (doc.getFieldValue(LuceneConstants.saggio).toString().length()-1)) : "";
	    this.administration = (doc.getFieldValue(LuceneConstants.administration) != null) ? doc.getFieldValue(LuceneConstants.administration).toString().substring(1, (doc.getFieldValue(LuceneConstants.administration).toString().length()-1)) : "";
	    this.location = (doc.getFieldValue(LuceneConstants.location) != null) ? doc.getFieldValue(LuceneConstants.location).toString().substring(1, (doc.getFieldValue(LuceneConstants.location).toString().length()-1)) : "";
	    this.date_upload = (doc.getFieldValue(LuceneConstants.date_upload) != null) ? doc.getFieldValue(LuceneConstants.date_upload).toString().substring(1, (doc.getFieldValue(LuceneConstants.date_upload).toString().length()-1)) : "";
	    this.desc_material = (doc.getFieldValue(LuceneConstants.desc_material) != null) ? doc.getFieldValue(LuceneConstants.desc_material).toString().substring(1, (doc.getFieldValue(LuceneConstants.desc_material).toString().length()-1)) : "";
	    this.data_arch = (doc.getFieldValue(LuceneConstants.data_arch) != null) ? doc.getFieldValue(LuceneConstants.data_arch).toString().substring(1, (doc.getFieldValue(LuceneConstants.data_arch).toString().length()-1)) : "";
	    this.num_lotto = (doc.getFieldValue(LuceneConstants.num_lotto) != null) ? doc.getFieldValue(LuceneConstants.num_lotto).toString().substring(1, (doc.getFieldValue(LuceneConstants.num_lotto).toString().length()-1)) : "";
	    this.prodotto = (doc.getFieldValue(LuceneConstants.prodotto) != null) ? doc.getFieldValue(LuceneConstants.prodotto).toString().substring(1, (doc.getFieldValue(LuceneConstants.prodotto).toString().length()-1)) : "";
	    this.container = (doc.getFieldValue(LuceneConstants.container) != null) ? doc.getFieldValue(LuceneConstants.container).toString().substring(1, (doc.getFieldValue(LuceneConstants.container).toString().length()-1)) : "";
	    this.formula = (doc.getFieldValue(LuceneConstants.formula) != null) ? doc.getFieldValue(LuceneConstants.formula).toString().substring(1, (doc.getFieldValue(LuceneConstants.formula).toString().length()-1)) : "";
	    this.tipo_studio = (doc.getFieldValue(LuceneConstants.tipo_studio) != null) ? doc.getFieldValue(LuceneConstants.tipo_studio).toString().substring(1, (doc.getFieldValue(LuceneConstants.tipo_studio).toString().length()-1)) : "";

	    return this;
  }
  

}
