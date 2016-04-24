package pl.tomo.medicament.response;

import java.util.List;

import pl.tomo.medicament.entity.DiseaseMini;

public class ResponseDieaseMini extends ResponseJson {

	private List<DiseaseMini> rows;

	public List<DiseaseMini> getRows() {
		return rows;
	}

	public void setRows(List<DiseaseMini> rows) {
		this.rows = rows;
	}


	
	
	
	
}
