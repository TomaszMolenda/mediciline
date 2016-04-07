package pl.tomo.medicament.response;

import java.util.List;

import pl.tomo.medicament.entity.ATC;

public class ResponseATC extends ResponseJson {

	private List<ATC> rows;

	public List<ATC> getRows() {
		return rows;
	}

	public void setRows(List<ATC> rows) {
		this.rows = rows;
	}


	
	
	
	
}
