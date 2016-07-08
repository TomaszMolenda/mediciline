var retrieveSearch;
var packageID;
var chooseMedicamentDb;

function clickAddButton() {
	$("button[type='submit']").attr("disabled", true);
	$('input').val('');
	showModal($('#modalAddMedicament'));
}

function clickArchiveButton(clicked) {
	var id = $(clicked).parent().parent().parent().find('.tableMedicaments-id').html();
	var url = 'medicaments/archive/' + id + '.html';
	$('#btnArchive').parent().attr("href", url);
	showModal($('#modalArchiveMedicament'));
}
function clickInfoButton(clicked) {
	var id = $(clicked).parent().parent().parent().find('.tableMedicaments-id').html();
	getAdditional(id);
}

function clickSearchButton() {
	var search = $('#search').val();
	if(search.length >= 3) {
		searchMedicaments(search); //function in search.html
	}
	else {
		var error = /*[[#{WriteThreeSign}]]*/
		$('#errorForm').html(error).show().delay(3000).fadeOut();
	}
}

function createForm(medicament) {
	$("button[type='submit']").attr("disabled", false);
	$('#tableChooseMedicament').find('input').each(function(i, input){
			if(i == 0) $(input).val(medicament.productName);
			if(i == 1) $(input).val(medicament.producer);
			if(i == 2) $(input).val(medicament.form);
			if(i == 3) $(input).val(medicament.dosageObject.wholePackage);
			if(i == 4) $(input).val(medicament.dosageObject.unit);
			if(i == 5) $(input).val(medicament.price);
			if(i == 6) $(input).val(medicament.packageID);
	});
}

function submitAddMedicament() {
	$('#loadingAdd').prop('hidden', false);
	$("input[id='date']").val(dateLong) //from fragments/date.html
}

function clickArchiveButtonConfirm() {
	$('#loadingArchive').prop('hidden', false);
	$('#btnCancel').prop('disabled', true);
	$('#btnArchive').prop('disabled', true);
}

function clickChooseMedicament() {
	chooseMedicamentDb = $.grep(retrieveSearch, function(e){ return e.packageID == packageID; });
	createForm(chooseMedicamentDb[0]);
	$('#modalSearchMedicament').modal('hide');
}



function getAdditional(id) {
	$.ajax({
		url: '/api/medicaments/information',
		dataType: 'json',
		data:{id:id},
		success: function(data){
			appendInformation(data);
			$('#modalAdditionalInformation').modal({
				  backdrop: 'static',
				  keyboard: false
				}).show();
		},
		error: function(xhr) {
			console.log('erroradd')
		}
	});
}

function appendInformation(data) {
	$('.content-of-information').html('');
	$('#modalAdditionalInformationTitle').append(data.productName);
	$('#composition').append(data.medicamentAdditional.composition);
	$('#effects').append(data.medicamentAdditional.effects);
	$('#indications').append(data.medicamentAdditional.indications);
	$('#contraindications').append(data.medicamentAdditional.contraindications);
	$('#precaution').append(data.medicamentAdditional.precaution);
	$('#pregnancy').append(data.medicamentAdditional.pregnancy);
	$('#sideeffects').append(data.medicamentAdditional.sideeffects);
	$('#interactions').append(data.medicamentAdditional.interactions);
	$('#dosage').append(data.medicamentAdditional.dosage);
	$('#remark').append(data.medicamentAdditional.remark);
	$.each(data.atcs, function(index, element) {
		$('#atc').append("<tr><td>" + element.atcCode + "</td><td>" + element.atcName + "</td></tr>");
		});
	$('#distributorName').append(data.distributor.distributorName);
	$('#distributorShortName').append(data.distributor.distributorShortName);
	$('#distributorPostalCode').append(data.distributor.postalCode);
	$('#distributorCity').append(data.distributor.city);
	$('#distributorAddress').append(data.distributor.address);
	$('#distributorEmail').append(data.distributor.email);
	$('#distributorWWW').append(data.distributor.www);
	$('#distributorTel').append(data.distributor.tel);
	$('#distributorFax').append(data.distributor.fax);
	$('#productType').append(data.productType.typeDescr);
	$('#prescription').append(data.prescription.name);
	$.each(data.diseases, function(index, element) {
		$('#diseases').append("<tr><td>" + element.diseaseNameShort + "</td><td>" + element.diseaseName + "</td></tr>");
	});
}

function searchMedicaments(search){
	$.ajax({
		url: '/api/medicamentsdb/search',
		data:{search:search},
		type: 'GET',
		beforeSend: function(xhr) {
			
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function(data){
			retrieveSearch = data;
				clearContent($('#tableSearch > tbody'));
				createTable(data, $('#tableSearch > tbody'));
			showModal($('#modalSearchMedicament'));
		},
		error: function(xhr) {
			console.log(xhr);
		}
	});
	
	$('#tableSearch > tbody').delegate('tr','mouseover mouseleave', function(e){
	    if (e.type == 'mouseover') {
	        $(this).addClass('success');
	    } else {
	        $(this).removeClass('success');
	    }
	});
	$('#tableSearch').on('click', 'tbody > tr', function(){
		$(this).addClass('info');
		$(this).siblings().removeClass('info');
		packageID = $(this).find('.packageID').html();
		});
	}

function createTable(data, tbody) {
	$.each(data, function(index, element){
		tbody.append("<tr>" + 
					"<td>" + element.productName + "</td>" + 
					"<td>" + element.producer + "</td>" + 
					"<td>" + element.pack + "</td>" + 
					"<td>" + element.price + "</td>" + 
					"<td class=\"packageID\">" + element.packageID + "</td>" + 
					"</tr>");
	});
}

function clearContent(clear) {
	clear.html('');
}


