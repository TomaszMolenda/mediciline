package pl.tomo.entity;

import java.util.List;

public class MedicamentForm {
	
	private List<Medicament> medicaments;
	
	private List<Integer> ids;

	public List<Medicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}


	
	

}
