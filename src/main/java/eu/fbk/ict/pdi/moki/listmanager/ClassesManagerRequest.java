package eu.fbk.ict.pdi.moki.listmanager;

public class ClassesManagerRequest {
	private String mode;
	private Object [] list;
	private String id;
	private String name;
	private String title;
	private String type;
	private String author;
	private String date;
	private String pages;
	private String isbn;
	private String doi;
	private String requester;
	private String pmcid;
	private String notes;
	private String author_address;
	private String keywords;
	private String language;
	private String journal;
	private String cro;
	private String material;
	private String documentno;
	private String project;
	private String glp;
	private String saggio;
	private String administration;
	private String location;
	
	ClassesManagerRequest () {	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

	public String getAuthorAddress() {
		return author_address;
	}

	public void setAuthorAddress(String author_address) {
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
	
	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getCro() {
		return cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
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

	public Object [] getList() {
		return list;
	}

	public void setList(Object [] list) {
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
