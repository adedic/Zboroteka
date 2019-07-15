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
		//TODO ajax poziv koji na editor postavlja heading, autora i tonalitet
		/*$.ajax({
			type : "POST",
			url : "setHeadingAuthorKeyToEditor",
			data : $('#createSongForm').serialize() + "&rawSongText=" + $('#songEditor').val(),
			suppressErrors : true
		}).done(function(data) {
			debugger;
			if(data.status == "ok") {
				//result je rawSongText u formatu:
				
				//TODO POSTAVITI U EDITOR
                //editor.session.replace(textarea.val(), data.result); 
				
			} 
		});*/
		
		$("#mainSongInfo").hide();
		$("#formSongText").show();
	}
});

$("#backToMainSongData").click(function(e) {
	e.preventDefault();

	$("#formSongText").hide();
	$("#mainSongInfo").show();
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
