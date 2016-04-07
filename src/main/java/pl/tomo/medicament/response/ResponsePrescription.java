package pl.tomo.medicament.response;

import java.util.List;

import pl.tomo.medicament.entity.Prescription;

public class ResponsePrescription extends ResponseJson {

	private List<Prescription> rows;

	public List<Prescription> getRows() {
		return rows;
	}

	public void setRows(List<Prescription> rows) {
		this.rows = rows;
	}
	
	
	
	
	
}
