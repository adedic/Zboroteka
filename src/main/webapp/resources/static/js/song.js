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




$("#saveUpdateSongBtn").click(function(e) {
	if($("#optionPreview").val() == "")
		$("#optionPreview").val(3).change();
	e.preventDefault();

	// poziv skriveni gumb za azurirati sadrzaj editora u pocetnu formu
	$("#btnUpdateFormContent").click();
	// ajax poziv na controller
	console.log($('#createSongForm').serialize());
	$.ajax({
		type : "POST",
		url : "createUpdateSong",
		data : $('#createSongForm').serialize(),
		suppressErrors : true
	}).done(function(data) {
		debugger;
		commonModul.removeAllAlerts();
		if (data.status == "ok") {
			if(data.result.msg == "spremanje") {
				// redirect na detalje
				window.setTimeout(function() {

		            localStorage.setItem("songSaved", "Uspješno spremanje nove pjesme!");
					window.location.href = '/zboroteka/song/details?id='+data.result.songId;
	            }, 500);
				
			} else if(data.result.msg == "ažuriranje") {
				$("#btnPreview").click();
				commonModul.showAlert({
					elementId : 'showAlertBox',
					message : "Uspješno ažuriranje pjesme!",
					alertLevel : 'success'
				});
			}
			
		} else {

	        $("#btnEdit").click();
			// provjera statusa, validacija nepostojecih akorda
			commonModul.showAlert({
				elementId : 'showAlertBox',
				message : "Nespješno " + data.result.msg + " pjesme! Uneseni su akordi koji ne postoje: " + data.result.unrecognizedChords,
				alertLevel : 'danger'
			});

		}
	});

});

$("#searchSong").change(function(e) {
	$.ajax({
		type : "POST",
		url : "searchSong",
		data :  "query=" + $("#searchSong").val(),
		suppressErrors : true
	}).done(function(data) {
		
	});
});
