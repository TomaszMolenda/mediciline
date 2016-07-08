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


