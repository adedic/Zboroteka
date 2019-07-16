$("#showSongEditorFormBtn").click(function(e) {
	e.preventDefault();
	// validate form osnovni podaci
	// ako je validacija prosla
	if (!$("#createSongForm")[0].checkValidity()) {
		$("#createSongForm").addClass("¸was-validated");
		commonModul.showAlert({
			elementId : 'showAlertBox',
			message : "Popunite tražene podatke kako biste mogli spremiti pjesmu!",
			alertLevel : 'danger'
		});
	} else {
		//poziv skivenog gumba za inicijalno popunjavanje naslova, autora, tonaliteta i placeholdera za tekst i akorde
		//TODO AKORDE NE POPUNJAVATI AKO SU VEC POPUNJENI?
		$("#btnInitEditorVal").click();
		
		$("#mainSongInfo").hide();
		$("#formSongText").show();
	}
});

$("#backToMainSongData").click(function(e) {
	e.preventDefault();
	
	$("#formSongText").hide();
	$("#mainSongInfo").show();

	//TODO AKO NISU UNESENI AKORDI?
	//poziv skriveni gumb za isprazniti editor
	$("#btnClearContent").click();
});

$("#saveSongBtn").click(function(e) {
	e.preventDefault();
	// ajax poziv na controller
	// TODO validacija raw teksta, ne smije biti prazan
	console.log($('#createSongForm').serialize());
	$.ajax({
		type : "POST",
		url : "createSong",
		data : $('#createSongForm').serialize(),
		suppressErrors : true
	}).done(function(data) {
		debugger;
		// provjeraStatusa.spremanjeDokumenataProvjera(data.status);
		
		if(data.status == "ok") {
			commonModul.showAlert({
				elementId : 'showAlertBox',
				message : "Uspješno dodavanje nove pjesme!",   
				alertLevel : 'success'
			});
		} else {
			//TODO provjera statusa, validacija nepostojecih akorda
			commonModul.showAlert({
				elementId : 'showAlertBox',
				message : "Nespješno dodavanje nove pjesme!",
				alertLevel : 'danger'
			});
		}
	});

});
