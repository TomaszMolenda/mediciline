package pl.tomo.medicament.response;

import java.util.List;

import pl.tomo.medicament.entity.Medicament;

public class ResponseMedicament extends ResponseJson {

	private List<Medicament> rows;

	public List<Medicament> getRows() {
		return rows;
	}

	public void setRows(List<Medicament> rows) {
		this.rows = rows;
	}
	
	
	
	
	
}
