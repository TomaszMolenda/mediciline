var idM;

function clickDrugsBitton(clicked) {
	idM = $(clicked).parent().parent().parent().find('.medicament-id').html();
	getDosages();
}
	