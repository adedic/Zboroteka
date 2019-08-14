var songUtil = (function() {
	
    var transposeChords = function(transposeValue, editor) {
        //transposeValue je UP (+1) ili DOWN (-1)
        commonModul.removeAllAlerts();

        //DOHVATI TRENUTNI TONALITET - PREMA ODABRANOM id-u u formi
        var currentKey = parseInt($("#key").val());
        
        if ($("#key").val() == null) {
            if (transposeValue == 1) {
                //ako je vrijednost izasla iz polja na desni kraj- vrati na pocetak niza, postavi 1
                $("#key").val(1);
            } else if (transposeValue == -1) {
                //ako je vrijednost izasla iz polja na lijevi kraj- vrati na kraj niza, postavi 12
                $("#key").val(12);
            }
            //postavi vrijednost trenutnog tonaliteta koja se salje na backend
            currentKey = parseInt($("#key").val());
        }
        //////////////////////////

        //transponiraj akorde pjesme
        
        $.ajax({
            type: "POST",
            url: "transposeChords",
            data:  "rawSongText=" + editor.getSession().getValue() + "&transposeValue=" + transposeValue + "&currentKey=" + currentKey,
            async: false,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser  
            suppressErrors: true
        }).done(function(data) {
            debugger;
            
            songValidate.checkValidationMsg(data);

            if(data.status == "ok") {
                //azuriranje rawSongText na formi
                updateSongEditorValue(data.result.newText, editor);

                //azurirati trenutni tontalitet na formi
                $("#key").val(data.result.newKey).change();
            }

        });
    };
    
    var updateKey = function(transposeValue, editor, newText) {
    	//transposeValue je UP (+1) ili DOWN (-1)
        commonModul.removeAllAlerts();

        //DOHVATI TRENUTNI TONALITET - PREMA ODABRANOM id-u u formi
        var currentKey = parseInt($("#key").val());
        
        if ($("#key").val() == null) {
            if (transposeValue == 1) {
                //ako je vrijednost izasla iz polja na desni kraj- vrati na pocetak niza, postavi 1
                $("#key").val(1);
            } else if (transposeValue == -1) {
                //ako je vrijednost izasla iz polja na lijevi kraj- vrati na kraj niza, postavi 12
                $("#key").val(12);
            }
            //postavi vrijednost trenutnog tonaliteta koja se salje na backend
            currentKey = parseInt($("#key").val());
        }

        //update key in form and in rawText TODO OVO MAKNUTI, JER SE POZIVA NA BACKENDU AZURIRANJE
	        $.ajax({
	            type: "POST",
	            url: "updateKey",
	            data:  "rawSongText=" + newText + "&transposeValue=" + transposeValue + "&currentKey=" + currentKey,
	            suppressErrors: true
	        }).done(function(data) {
	            debugger;

	            if(data.status == "ok") {

                    //azurirati trenutni tontalitet na formi
	                $("#key").val(data.result.newKey).change();

	        		//ako je vrijednost izasla iz polja na desni kraj- vrati na pocetak niza, postavi 1
	                if(data.result.newKey == 13) {
		                $("#key").val(1).change(); 
		                console.log("postavi 1");
	                } else if(data.result.newKey == -1) {
	            		//ako je vrijednost izasla iz polja na lijevi kraj- vrati na kraj niza, postavi 12
		                $("#key").val(data.result.newKey).change(12); 
		                console.log("postavi 12");

	                }

	                console.log("data.result.newText:	" + data.result.newText);
	                //azuriranje rawSongText na formi
	                //TODO OVO PREMJESTITI IZVAN A OSTALO OBRISATI JER JE NA BACKENDU?
	                updateSongEditorValue(data.result.newText, editor);
	            }

	        });
    };
    
    var updateSongEditorValue = function (newText, editor) {
    	//isprazni postojeci tekst na formi
	    editor.getSession().setValue("");
	    
	    //OVO POSTAVLJA VRIJEDNOST na editor 
	    editor.getSession().setValue(newText);
	    
	    $("#songEditor").val(newText).change();
    }	

    //algoritam za transpose JS
    var transposeChord = function(chord, amount) {
        var scale = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
        var normalizeMap = {
            "Cb": "Bb",
            "Db": "C#",
            "Eb": "D#",
            "Fb": "E",
            "Gb": "F#",
            "Ab": "G#",
            "Bb": "A#",
            "E#": "F",
            "B#": "C"
        }

        return chord.replace(/[CDEFGAB](b|#)?/g, function(match) {
            var i = (scale.indexOf((normalizeMap[match] ? normalizeMap[match] : match)) + amount) % scale.length;
            return scale[i < 0 ? i + scale.length : i];
        })
    }

    
    var showTextChordsRadio = function (editor, option, preview) {
    	commonModul.removeAllAlerts();

    	if(option == "")
    		option = 3;
    	
    	$("#optionPreview").val(option).change();
    	
    	$.ajax({
            type: "POST",
            url: "showTextChordsRadio",
            data:  "rawSongText=" + editor.getSession().getValue() + "&option="+option,
            suppressErrors: true
        }).done(function(data) {
            debugger;
        		if(option == 1) {
        			$("#songPreview").val(data.result.onlyText).change();
        			$("#option1").addClass("active");
        			$("#option2").removeClass("active");
        			$("#option3").removeClass("active");
        			$("#btnPreview").click();
        			$('p').css({'color' : 'grey'});
        			
            		if (data.status == "noText") {
            			commonModul.showAlert({
            				elementId : 'showAlertBox',
            				message : "Unesi tekst pjesme!",
            				alertLevel : 'warning'
            			});
            		}
                } else if(option == 2) {
                	$("#songPreview").val(data.result.onlyChords).change();
        			$("#option1").removeClass("active");
        			$("#option2").addClass("active");
        			$("#option3").removeClass("active");
    				$("#btnPreview").click();
        			$('code').css({'font-size' : '16px'});
        			$('code').css({'color' : 'blue'});
        			$('p').css({'color' : 'grey'});
        			
            		if (data.status == "invalidChords") {
            			commonModul.showAlert({
            				elementId : 'showAlertBox',
            				message : "Unesi akorde pjesme!",
            				alertLevel : 'warning'
            			});
            		}
                } else if(option == 3) {
            		if (data.status == "noRawText") {
            			commonModul.showAlert({
            				elementId : 'showAlertBox',
            				message : "Unesi tekst i akorde pjesme!",
            				alertLevel : 'danger'
            			});
            		}
            		else if(data.status == "okBoth") {
            			$("#songPreview").val(data.result.textAndChords).change();
            			$("#option1").removeClass("active");
            			$("#option2").removeClass("active");
            			$("#option3").addClass("active");
            			$("#btnPreview").click();
            			$('p').css({'color' : 'grey'});
                	}
                }
	            
	        });

    	
    }
    

    // Public API
    return {
        //transponira akorde
        transposeChords: transposeChords,
        transposeChord: transposeChord,
        updateSongEditorValue: updateSongEditorValue,
        showTextChordsRadio: showTextChordsRadio
    }
})();