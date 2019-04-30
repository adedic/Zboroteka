$(function() {
    $('[data-toggle="tooltip"]').tooltip({
        trigger: "hover"
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