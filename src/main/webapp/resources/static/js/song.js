 
$("#showSongEditorFormBtn").click(function(e) {
	e.preventDefault();
	//TODO validate form osnovni podaci
	//ako je validacija prosla
	if (!$("#createSongForm")[0].checkValidity()) {
        $("#createSongForm").addClass("¸was-validated");
        //TODO poruka
//        commonModul.showAlert({
//            elementId: 'showAlertBox',
//            message: "Popunite tražene podatke kako biste mogli spremiti pjesmu!",
//            alertLevel: 'danger'
//        });
    }  else { 

    	
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
	//ajax poziv na controller
	//TODO validacija raw teksta, ne smije biti prazan
    console.log($('#createSongForm').serialize());
    $.ajax({
        type: "POST",
        url: "createSong",
        data: $('#createSongForm').serialize(),
        suppressErrors: true
    }).done(function(data) {
        debugger;
        //provjeraStatusa.spremanjeDokumenataProvjera(data.status);
    });
    
});


