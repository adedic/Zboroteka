$("#showSongEditorFormBtn").click(function(e) {
		e.preventDefault();
		// validate form osnovni podaci
		// ako je validacija prosla
		if (!$("#createSongForm")[0].checkValidity()) {
			$("#createSongForm").addClass("was-validated");
			commonModul.removeAllAlerts();
			commonModul.showAlert({
						elementId : 'showAlertBox',
						message : "Popuni tražene podatke za nastavak unosa pjesme!",
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
	        $("#btnEdit").click();
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
			
		} else  if(data.status == "noTextAndChords") {
			$("#btnEdit").click();
			// provjera statusa, validacija nepostojecih akorda
			commonModul.showAlert({
				elementId : 'showAlertBox',
				message : "Nespješno " + data.result.msg + " pjesme! Upiši tekst i akorde pjesme unutar oznaka ```   ```!",
				alertLevel : 'danger'
			});
		} else if(data.status == "invalidChords") {
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
		url : "/zboroteka/searchSong",
		data :  "query=" + $("#searchSong").val(),
		suppressErrors : true
	}).done(function(data) {
		window.setTimeout(function() {
            //localStorage.setItem("query", $("#searchSong").val());
	        localStorage.setItem("songsIDs", data.result);
			window.location.href = '/zboroteka/song/searchResults?songsIDs='+ data.result;
        }, 500);
		
	});
});
