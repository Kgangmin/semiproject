package myshop.domain;

public class CategoryStatsVO {

	
	private String cname;
    private int    cnt;
    private long   sumpay;
    private double sumpayPct;
    
	public String getCname() {
		return cname;
	}
	
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public int getCnt() {
		return cnt;
	}
	
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public long getSumpay() {
		return sumpay;
	}
	
	public void setSumpay(long sumpay) {
		this.sumpay = sumpay;
	}
	
	public double getSumpayPct() {
		return sumpayPct;
	}
	
	public void setSumpayPct(double sumpayPct) {
		this.sumpayPct = sumpayPct;
	}
    
    
    
}
