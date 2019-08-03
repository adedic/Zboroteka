$("#showSongEditorFormBtn").click(function(e) {
		e.preventDefault();
		// validate form osnovni podaci
		// ako je validacija prosla
		if (!$("#createSongForm")[0].checkValidity()) {
			$("#createSongForm").addClass("was-validated");
			commonModul
					.showAlert({
						elementId : 'showAlertBox',
						message : "Popunite tražene podatke kako biste nastaviti s unosom pjesme!",
						alertLevel : 'danger'
					});
		} else {
			commonModul.removeAllAlerts();
			// poziv skivenog gumba za inicijalno popunjavanje
			// naslova, autora, tonaliteta i placeholdera za tekst i
			// akorde
			$("#btnSetEditorVal").click();

			$("#mainSongInfo").hide();
			$("#formSongText").show();
			$(".previewBtns").hide();
		}
	});

$("#backToMainSongData").click(function(e) {
	e.preventDefault();

	$("#formSongText").hide();
	$("#mainSongInfo").show();

	// poziv skriveni gumb za azurirati sadrzaj editora u pocetnu formu
	$("#btnUpdateFormContent").click();
});

$("#saveSongBtn").click(function(e) {
	e.preventDefault();
	// ajax poziv na controller
	console.log($('#createSongForm').serialize());
	$.ajax({
		type : "POST",
		url : "createSong",
		data : $('#createSongForm').serialize() + "rawSongText=" + $("#songEditor").val(),
		suppressErrors : true
	}).done(function(data) {
		debugger;
		commonModul.removeAllAlerts();
		if (data.status == "ok") {
			commonModul.showAlert({
				elementId : 'showAlertBox',
				message : "Uspješno dodavanje nove pjesme!",
				alertLevel : 'success'
			});
			
			//TODO redirect na detalje
		} else {
			// provjera statusa, validacija nepostojecih akorda
			commonModul.showAlert({
				elementId : 'showAlertBox',
				message : "Nespješno dodavanje nove pjesme! Uneseni su akordi koji ne postoje: " + data.result.unrecognizedChords,
				alertLevel : 'danger'
			});
		}
	});

});
