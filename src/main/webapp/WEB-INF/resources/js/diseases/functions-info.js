var idM;

function clickDrugsBitton(clicked) {
	idM = $(clicked).parent().parent().parent().find('.medicament-id').html();
	getDosages();
}

function unblockAddButton() {
	var checkedCount = $('#formAddMedicaments').find('input[type="checkbox"]:checked').length;
	if(checkedCount > 0)
		$('#formAddMedicaments').find('button').attr('disabled', false);
	else
		$('#formAddMedicaments').find('button').attr('disabled', true);
}

function unblockRemoveButton() {
	var checkedCount = $('#formRemoveMedicaments').find('input[type="checkbox"]:checked').length;
	if(checkedCount > 0)
		$('#formRemoveMedicaments').find('button').attr('disabled', false);
	else
		$('#formRemoveMedicaments').find('button').attr('disabled', true);
}
	