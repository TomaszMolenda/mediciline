package pl.tomo.medicament.response;

import java.util.List;

import pl.tomo.medicament.entity.ProductType;

public class ResponseProductType extends ResponseJson{
	
	private List<ProductType> rows;

	public List<ProductType> getRows() {
		return rows;
	}

	public void setRows(List<ProductType> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "ResponseProductType [rows=" + rows + "]";
	}
	
	
	
	
	
	

}
