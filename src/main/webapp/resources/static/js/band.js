
$("#saveUpdateBandBtn").click(function(e) {
	e.preventDefault();

	if (!$("#bandForm")[0].checkValidity()) {
		$("#bandForm").addClass("was-validated");
		commonModul.removeAllAlerts();
		commonModul.showAlert({
					elementId : 'showAlertBox',
					message : "Popuni tražene podatke!",
					alertLevel : 'danger'
				});
	} else {
		console.log($('#bandForm').serialize());
		$.ajax({
			type : "POST",
			url : "createUpdateBand",
			data : $('#bandForm').serialize(),
			suppressErrors : true
		}).done(function(data) {
			debugger;
			commonModul.removeAllAlerts();
			if (data.status == "ok") {
				if(data.result == "spremanje") {
					// redirect na detalje
					window.setTimeout(function() {

			            localStorage.setItem("bandSaved", "Uspješno spremanje glazbene grupe!");
						window.location.href = '/zboroteka/band/info';
		            }, 500);
					
				} else if(data.result == "ažuriranje") {
					$("#btnPreview").click();
					commonModul.showAlert({
						elementId : 'showAlertBox',
						message : "Uspješno ažuriranje glazbene grupe!",
						alertLevel : 'success'
					});
				}
				
			}
		});
	}

});