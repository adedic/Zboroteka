$(function() {
	$('[data-toggle="tooltip"]').tooltip({
		trigger : "hover"
	});

})

var titlePrikazi = "Prikaži izbornk";
var titleSakrij = "Sakrij izbornk";

$("#menu-toggle").attr("data-original-title", titleSakrij);

$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#wrapper").toggleClass("toggled");

	titleMenu = $("#menu-toggle").attr("data-original-title");

	if (titleMenu == titleSakrij) {
		$("#menu-toggle").attr("data-original-title", titlePrikazi);
	} else if (titleMenu == titlePrikazi) {
		$("#menu-toggle").attr("data-original-title", titleSakrij);
	}

	if ($("#menu-icon").hasClass("fa-caret-left")) {
		$("#menu-icon").removeClass("fa-caret-left");

		$("#menu-icon").addClass("fa-caret-right");

	} else if ($("#menu-icon").hasClass("fa-caret-right")) {
		$("#menu-icon").removeClass("fa-caret-right");

		$("#menu-icon").addClass("fa-caret-left");
	}

});

var commonModul = (function() {
	var showAlert = function(options) {
		// window.scrollTo(0,0);
		var settings = $.extend({
			// defaultne vrijednosti
			message : "",
			alertLevel : "danger",
			position : "appendTo",
			elementId : "showAlertBox"

		// alertType: "green",
		// textColor: "white",
		// dismiss: "auto",
		// fadeInSpeed: 3000
		// fadeOutSpeed: 800,
		// closeAfter: 3000,
		// progressAnimation: false
		}, options);

		var alertDiv = $('<div class=" alert alert-'
				+ settings.elementId.replace(/\\/, "")
				+ ' undvAlert alert alert-'
				+ settings.alertLevel
				+ ' alert-dismissible fade show" role="alert">'
				+ settings.message
				+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');

		switch (settings.position) {
		case 'insertAfter':
			// ako nakon inputa postoji input-group-append element onda se alert
			// dodaje nakon tog elementa da se prikaze ispravno
			var nextEl = $('#' + settings.elementId)
					.next(".input-group-append");
			if (nextEl.length == 0) {
				alertDiv.insertAfter($('#' + settings.elementId));
			} else {
				alertDiv.insertAfter($('#' + settings.elementId).parent());
			}
			break;
		case 'insertBefore':
			alertDiv.insertBefore($('#' + settings.elementId));
			break;
		case 'appendTo':
			alertDiv.appendTo($('#' + settings.elementId));
			break;
		case 'prependTo':
			alertDiv.prependTo($('#' + settings.elementId));
			break;
		case 'replace':
			$('#' + settings.elementId).html(alertDiv);
			break;
		default:
			alertDiv.insertAfter($('#' + settings.elementId));
		}

		return alertDiv.fadeIn("slow");
	};

	var removeAlert = function(pElementId) {
		$(".alert-" + pElementId).remove();
	};

	var removeAllAlerts = function() {
		$(".undvAlert").remove();
	};

	// Public API
	return {
		// dodaje novu poruku na ekran
		showAlert : showAlert,
		// uklanja sve alerte za zadani element sa ekrana
		removeAlert : removeAlert,
		// uklanja sve alert-e sa ekrana
		removeAllAlerts : removeAllAlerts,
	}
})();
