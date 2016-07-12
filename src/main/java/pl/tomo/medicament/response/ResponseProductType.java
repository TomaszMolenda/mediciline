package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.medicament.entity.ProductType;

@Getter
@Setter
@ToString
public class ResponseProductType extends ResponseJson{
	
	private List<ProductType> rows;

}
