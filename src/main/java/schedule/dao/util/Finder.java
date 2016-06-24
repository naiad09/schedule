package schedule.dao.util;

abstract public class Finder {
	
	private int perPage = 10;
	private int page = 1;
	
	public Finder() {
		super();
	}
	
	public int getPerPage() {
		return perPage;
	}
	
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
}