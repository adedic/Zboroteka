var songValidate = (function() {
	
	//checks if there is no chords in editor text
	var chordsNotFoundInEditor = function(foundChords) {
		
		if(foundChords.length == 0) {
	    	commonModul.removeAllAlerts();
    		commonModul.showAlert({
				elementId : 'showAlertBox',
				message : "Unesi tekst i akorde za korištenje transpose opcije!",
				alertLevel : 'danger'
			});
    		return true;
	    }
		return false;
		
	}
	

	//checks if chords in editor textare invalid and unrecognized
	var chordsInvalid = function(foundChordsStr) {
		//ajax poziv provjera akorda postoje li
		$.ajax({
			type : "POST",
			url : "checkChordsExist",
			data : "foundChords=" + foundChordsStr,
			suppressErrors : true
		}).done(function(data) {
			debugger;
			commonModul.removeAllAlerts();
			if (data.status == "error") {
				// provjera statusa, validacija nepostojecih akorda
				commonModul.showAlert({
					elementId : 'showAlertBox',
					message : "Nespješno transponiranje pjesme! Uneseni su akordi koji ne postoje: " + data.result,
					alertLevel : 'danger'
				});
				return true;
			} else if(data.status == "ok") { 
				
				commonModul.showAlert({
					elementId : 'showAlertBox',
					message : "Uspješno transponiranje pjesme!,
					alertLevel : 'danger'
				});
				return false;
			}
		});
		
	}

    // Public API
    return {
        //transponira akorde
        chordsNotFoundInEditor: chordsNotFoundInEditor,
        chordsInvalid: chordsInvalid
    }
})();
}