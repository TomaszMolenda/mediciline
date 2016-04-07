package pl.tomo.medicament.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.ATC;
import pl.tomo.medicament.entity.Disease;
import pl.tomo.medicament.entity.Distributor;
import pl.tomo.medicament.entity.Medicament;
import pl.tomo.medicament.entity.MedicamentAdditional;
import pl.tomo.medicament.entity.Prescription;
import pl.tomo.medicament.entity.ProductType;
import pl.tomo.medicament.response.ResponseATC;
import pl.tomo.medicament.response.ResponseDisease;
import pl.tomo.medicament.response.ResponseDistributor;
import pl.tomo.medicament.response.ResponsePrescription;
import pl.tomo.medicament.response.ResponseProductType;
import pl.tomo.medicament.service.ATCService;
import pl.tomo.medicament.service.DiseaseMService;
import pl.tomo.medicament.service.DistributorService;
import pl.tomo.medicament.service.MedicamentAdditionalService;
import pl.tomo.medicament.service.MedicamentMService;
import pl.tomo.medicament.service.PrescriptionService;
import pl.tomo.medicament.service.ProductTypeService;

@Service
class DownloadWWW{

	private List<Medicament> medicaments;
	private List<Integer> medicamentsId = new ArrayList<Integer>();
	private List<ProductType> productTypes;
	private List<Integer> productTypesId = new ArrayList<Integer>();
	private List<Prescription> prescriptions;
	private List<Integer> prescriptionsId = new ArrayList<Integer>();
	private List<Disease> diseases;
	private List<Integer> diseasesId = new ArrayList<Integer>();
	
	@Autowired
	private Crawler crawler;

	public void createLists() throws Exception {
		medicaments = crawler.getMedicaments();
		for (Medicament medicament : medicaments) medicamentsId.add(medicament.getPackageID());
		productTypes = getProductTypes(crawler);
		for (ProductType productType : productTypes) productTypesId.add(productType.getProductTypeID());
		prescriptions = getPrescription(crawler);
		for (Prescription prescription : prescriptions) prescriptionsId.add(prescription.getPrescriptionID());
		diseases = getDisease(crawler, 2);
		diseases.addAll(getDisease(crawler, 3));
		for (Disease disease : diseases) diseasesId.add(disease.getDiseaseID());

	}
	
	public List<Medicament> getMedicaments() {
		return medicaments;
	}

	public List<Integer> getMedicamentsId() {
		return medicamentsId;
	}
	
	public List<ProductType> getProductTypes() {
		return productTypes;
	}

	public List<Integer> getProductTypesId() {
		return productTypesId;
	}

	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public List<Integer> getPrescriptionsId() {
		return prescriptionsId;
	}

	public List<Disease> getDiseases() {
		return diseases;
	}

	public List<Integer> getDiseasesId() {
		return diseasesId;
	}

	private List<ProductType> getProductTypes(Crawler crawler) throws Exception {
		Map<String, String> parametrs = crawler.getParametrs();
		ResponseProductType responseProductType = (ResponseProductType) crawler.getData(ResponseProductType.class,
				parametrs.get("param1"), parametrs.get("param29"), parametrs.get("param9"), parametrs.get("param10"),
				parametrs.get("param11"), parametrs.get("param28"), parametrs.get("param13"), parametrs.get("param22"));
		return responseProductType.getRows();
	}
	
	private List<Prescription> getPrescription(Crawler crawler) throws Exception {
		Map<String, String> parametrs = crawler.getParametrs();
		ResponsePrescription responsePrescription = (ResponsePrescription) crawler.getData(ResponsePrescription.class,
				parametrs.get("param1"), parametrs.get("param27"), parametrs.get("param9"), parametrs.get("param10"),
				parametrs.get("param11"), parametrs.get("param28"), parametrs.get("param13"), parametrs.get("param22"));
		return responsePrescription.getRows();
	}
	
	private List<Disease> getDisease(Crawler crawler, int i) throws Exception {
		Map<String, String> parametrs = crawler.getParametrs();
		ResponseDisease responseDisease = (ResponseDisease) crawler.getData(ResponseDisease.class,
				parametrs.get("param1"), parametrs.get("param23"), parametrs.get("param24"), Integer.toString(i),
				parametrs.get("param26"), "", parametrs.get("param9"), parametrs.get("param10"),
				parametrs.get("param11"), parametrs.get("param12"), parametrs.get("param13"), parametrs.get("param22"));
		return responseDisease.getRows();
	}
	
	private List<ATC> getATCs(Crawler crawler, List<Integer> productLinesID)
			throws Exception {
		Map<String, String> parametrs = crawler.getParametrs();
		List<ATC> atcs = new ArrayList<ATC>();
		for (Integer integer : productLinesID) {
			ResponseATC responseATC = (ResponseATC) crawler.getData(ResponseATC.class, parametrs.get("param1"),
					parametrs.get("param20"), parametrs.get("param9"), parametrs.get("param10"),
					parametrs.get("param11"), parametrs.get("param21"), parametrs.get("param13"),
					parametrs.get("param22"), parametrs.get("param3"), Integer.toString(integer));
			atcs.addAll(responseATC.getRows());
		}
		return atcs;
	}
	private List<MedicamentAdditional> getMedicamentAdditionals(Crawler crawler, List<Integer> productLinesID)
			throws Exception {
		List<MedicamentAdditional> medicamentAdditionals = new ArrayList<MedicamentAdditional>();
		for (Integer integer : productLinesID) {
			medicamentAdditionals.add(crawler.getMedicamentAdditional(integer));
		}
		return medicamentAdditionals;
	}
	private List<Distributor> getDistributors(Crawler crawler, List<Integer> distributorsId) throws Exception {
		Map<String, String> parametrs = crawler.getParametrs();
		List<Distributor> distributors = new ArrayList<Distributor>();
		for (Integer integer : distributorsId) {
			ResponseDistributor responseDistributor = (ResponseDistributor) crawler.getData(ResponseDistributor.class,
					parametrs.get("param1"), parametrs.get("param16"), parametrs.get("param9"),
					parametrs.get("param10"), parametrs.get("param11"), parametrs.get("param17"),
					parametrs.get("param13"), parametrs.get("param18"), parametrs.get("param19"),
					Integer.toString(integer));
			distributors.add(responseDistributor.getResult());
		}
		return distributors;
	}

	List<Distributor> getDistributors(List<Integer> distributorsId) throws Exception {
		return getDistributors(crawler, distributorsId);
	}

	List<ATC> getAtcs(List<Integer> productLinesId) throws Exception {
		return getATCs(crawler, productLinesId);
	}

	public List<MedicamentAdditional> getMedicamentAdditionals(List<Integer> productLinesId) throws Exception {
		return getMedicamentAdditionals(crawler, productLinesId);
	}

	
}

@Service
class DownloadDataBase {
	
	@Autowired
	private DistributorService distributorService; 
	
	@Autowired
	private MedicamentMService medicamentMService;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	@Autowired
	private DiseaseMService diseaseMService;
	
	@Autowired
	private ATCService atcService;
	
	@Autowired
	private MedicamentAdditionalService medicamentAdditionalService;

	List<Medicament> getMedicaments() {
		return medicamentMService.getAllMedicaments();
	}

	List<Integer> getMedicamentsId() {
		return medicamentMService.getActivePackageID();
	}
	
	List<Integer> getProductTypesId() {
		return productTypeService.getAllId();
	}
	
	List<Integer> getPrescriptionId() {
		return prescriptionService.getAllId();
	}
	
	List<Integer> getProductLinesId() {
		Set<Integer> productLinesID = medicamentMService.getProductLinesID();
		return new ArrayList<Integer>(productLinesID);
	}
	
	List<Integer> getDistributorsIdInMedicament() {
		Set<Integer> distributorsId = medicamentMService.getDistributorsID();
		return new ArrayList<Integer>(distributorsId);
	}
	
	List<Integer> getDiseasesId() {
		return diseaseMService.getAllId();
	}

	List<Integer> getDistributorsId() {
		return distributorService.getAllId();
	}

	List<Integer> getProductLinesIdForAtc() {
		return atcService.getAllProductLinesId();
	}

	public List<Integer> getMedicamentAdditionalsIdForAtc() {
		return medicamentAdditionalService.getAllProductLinesId();
	}
	
	

}

@Service
class Save {
	
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private PrescriptionService prescriptionService; 
	@Autowired
	private MedicamentMService medicamentMService;
	@Autowired
	private DiseaseMService diseaseMService;
	@Autowired
	private DistributorService distributorService;
	@Autowired
	private ATCService atcService;
	@Autowired
	private MedicamentAdditionalService medicamentAdditionalService;
	
	void setInactiveStatusMedicament(List<Integer> medicamentsIdDB) {
		for (Integer integer : medicamentsIdDB) {
			Medicament medicamentByPackageID = medicamentMService.getMedicamentByPackageIDAndActive(integer, true);
			if (medicamentByPackageID != null) {
				medicamentByPackageID.setActive(false);
				medicamentMService.save(medicamentByPackageID);
			}
		}
	}
	void saveNotExistMedicaments(Crawler crawler, List<Integer> medicamentsIdWWW) throws Exception {
		for (Integer integer : medicamentsIdWWW) {
			Medicament medicament = crawler.getMedicament(integer);
			medicamentMService.save(medicament);
		}

	}

	void saveNotExistProductTypes(List<ProductType> productTypes, List<Integer> productTypesIdWWW) {
		for (ProductType productType : productTypes) {
			if(productTypesIdWWW.contains(productType.getProductTypeID())) productTypeService.save(productType);
		}
	}

	public void saveNotExistPrescriptions(List<Prescription> prescriptions, List<Integer> prescriptionsIdWWW) {
		for (Prescription prescription : prescriptions) {
			if(prescriptionsIdWWW.contains(prescription.getPrescriptionID())) prescriptionService.save(prescription);
		}
	}

	void saveNotExistDiseases(List<Disease> diseases, List<Integer> diseasesIdWWW) {
		for (Disease disease : diseases) {
			if(diseasesIdWWW.contains(disease.getDiseaseID())) diseaseMService.save(disease);
		}
	}
	public void saveNotExistDistributors(List<Distributor> distributors) {
		distributorService.save(distributors);
	}
	public void saveNotExistAtcs(List<ATC> atcs) {
		atcService.save(atcs);
	}
	public void saveNotExistMedicamentAdditionals(List<MedicamentAdditional> medicamentAdditionals) {
		medicamentAdditionalService.save(medicamentAdditionals);
		
	}
	
}


@Service
public class Processing {
	@Autowired
	private Crawler crawler;
	
	@Autowired
	private DownloadDataBase downloadDataBase;
	
	@Autowired
	private DownloadWWW downloadWWW;
	
	@Autowired
	private Save save;

	public void process() throws Exception {

		downloadWWW.createLists();

		List<Medicament> medicamentsFromWWW = downloadWWW.getMedicaments();
		List<Integer> medicamentsIdWWW = downloadWWW.getMedicamentsId();
		Collections.sort(medicamentsIdWWW);
		
		List<Integer> medicamentsIdDB = downloadDataBase.getMedicamentsId();

		if(!medicamentsIdWWW.isEmpty()) {

			compareAndShortList(medicamentsIdWWW, medicamentsIdDB);
			save.setInactiveStatusMedicament(medicamentsIdDB);
			save.saveNotExistMedicaments(crawler, medicamentsIdWWW);

			List<Integer> productLinesId = getProductLinesToDownload(medicamentsFromWWW, medicamentsIdWWW);
			
			List<Integer> productLinesIdForAtcWWW = new ArrayList<Integer>(productLinesId);
			List<Integer> productLinesIdForAtcDB = downloadDataBase.getProductLinesIdForAtc();
			compareAndShortList(productLinesIdForAtcWWW, productLinesIdForAtcDB);
			List<ATC> atcs = downloadWWW.getAtcs(productLinesIdForAtcWWW);
			save.saveNotExistAtcs(atcs);
			

			List<Integer> medicamentAdditionalsIdForAtcWWW = new ArrayList<Integer>(productLinesId);
			List<Integer> medicamentAdditionalsIdForAtcDB = downloadDataBase.getMedicamentAdditionalsIdForAtc();
			compareAndShortList(medicamentAdditionalsIdForAtcWWW, medicamentAdditionalsIdForAtcDB);
			List<MedicamentAdditional> medicamentAdditionals = downloadWWW.getMedicamentAdditionals(medicamentAdditionalsIdForAtcWWW);
			save.saveNotExistMedicamentAdditionals(medicamentAdditionals);
			
			List<Integer> distributorsIdMed = downloadDataBase.getDistributorsIdInMedicament();
			List<Integer> distributorsIdDB = downloadDataBase.getDistributorsId();
			compareAndShortList(distributorsIdMed, distributorsIdDB);
			List<Distributor> distributors = downloadWWW.getDistributors(distributorsIdMed);
			save.saveNotExistDistributors(distributors);

			List<Integer> productTypesIdWWW = downloadWWW.getProductTypesId();
			List<Integer> productTypesIdDB = downloadDataBase.getProductTypesId();
			List<ProductType> productTypes = downloadWWW.getProductTypes();
			compareAndShortList(productTypesIdWWW, productTypesIdDB);
			save.saveNotExistProductTypes(productTypes, productTypesIdWWW);
			
			List<Integer> prescriptionsIdWWW = downloadWWW.getPrescriptionsId();
			List<Prescription> prescriptions = downloadWWW.getPrescriptions();
			List<Integer> prescriptionIdDB = downloadDataBase.getPrescriptionId();
			compareAndShortList(prescriptionsIdWWW, prescriptionIdDB);
			save.saveNotExistPrescriptions(prescriptions, prescriptionsIdWWW);
			
			List<Disease> diseases = downloadWWW.getDiseases();
			List<Integer> diseasesIdWWW = downloadWWW.getDiseasesId();
			List<Integer> diseasesIdDB = downloadDataBase.getDiseasesId();
			compareAndShortList(diseasesIdWWW, diseasesIdDB);
			save.saveNotExistDiseases(diseases, diseasesIdWWW);
		}

	}

	private List<Integer> getProductLinesToDownload(List<Medicament> medicamentsFromWWW,
			List<Integer> medicamentsIdWWW) {
		Set<Integer> list = new HashSet<Integer>(); 
		for (Medicament medicament : medicamentsFromWWW) {
				list.add(medicament.getProductLineID());
		}
		return new ArrayList<Integer>(list);
	}

	private void compareAndShortList(List<Integer> idsWWW, List<Integer> idsDB) {
		List<Integer> idsDBCopy = new ArrayList<Integer>(idsDB);
		idsDB.removeAll(idsWWW);
		idsWWW.removeAll(idsDBCopy);
	}

}
