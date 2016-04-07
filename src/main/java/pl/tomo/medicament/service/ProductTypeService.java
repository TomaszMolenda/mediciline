package pl.tomo.medicament.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.ProductType;
import pl.tomo.medicament.repository.ProductTypeRepository;

@Service
public class ProductTypeService {
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	public void save(ProductType productType) {
		productTypeRepository.save(productType);
	}
	
	public void save(List<ProductType> productTypes) {
		for (ProductType productType : productTypes) {
			productTypeRepository.save(productType);
		}
	}

	public List<Integer> getAllId() {
		return productTypeRepository.getAllId();
	}

	public ProductType getActiveById(Integer integer) {
		return productTypeRepository.getActiveById(integer);
	}

}
