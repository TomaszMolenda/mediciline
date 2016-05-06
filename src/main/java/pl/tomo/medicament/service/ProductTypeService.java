package pl.tomo.medicament.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.ProductType;
import pl.tomo.medicament.repository.ProductTypeRepository;

@Service
public class ProductTypeService {

	private Logger logger = Logger.getLogger(ProductTypeService.class);

	@Autowired
	private ProductTypeRepository productTypeRepository;

	public void save(ProductType productType) {
		productTypeRepository.save(productType);
		logger.info("save productType, id " + productType.getProductTypeID());
	}

	public List<Integer> getAllId() {
		logger.info("get list productTypes");
		return productTypeRepository.getAllId();
	}

	public ProductType getProductLine(int productTypeID) {
		logger.info("get productType, id: " + productTypeID);
		return productTypeRepository.findOne(productTypeID);
	}

}
