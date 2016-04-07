package pl.tomo.medicament.response;

import java.util.List;

import pl.tomo.medicament.entity.Disease;

public class ResponseDisease extends ResponseJson {

	private List<Disease> rows;

	public List<Disease> getRows() {
		return rows;
	}

	public void setRows(List<Disease> rows) {
		this.rows = rows;
	}
	
	
	
	
	
}
