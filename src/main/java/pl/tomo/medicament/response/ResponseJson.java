package pl.tomo.medicament.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseJson {
	
	private int page;
	private int total;
	private int records;
	@JsonProperty(value = "Msg")
	private String msg;
	@JsonProperty(value = "Err")
	private String err;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	@Override
	public String toString() {
		return "ResponseJson [page=" + page + ", total=" + total + ", records=" + records + ", msg=" + msg + ", err="
				+ err + "]";
	}

	
	
	

	
	

}
