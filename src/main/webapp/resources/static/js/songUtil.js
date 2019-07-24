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

        		//ako je vrijednost izasla iz polja na desni kraj- vrati na pocetak niza, postavi 1
                if(data.result.newKey == 13) {
	                $("#key").val(1).change(); 
	                console.log("postavi 1");
                } else if(data.result.newKey == -1) {
            		//ako je vrijednost izasla iz polja na lijevi kraj- vrati na kraj niza, postavi 12
	                $("#key").val(data.result.newKey).change(12); 
	                console.log("postavi 12");

                }
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
        var scale = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H"]
        var normalizeMap = {
            "Cb": "H",
            "Db": "C#",
            "Eb": "D#",
            "Fb": "E",
            "Gb": "F#",
            "Ab": "G#",
            "B": "A#",
            "E#": "F",
            "H#": "C"
        }

        //zamijeni pocetni izraz koji predstavlja akord, a zapocinje jednim od 12 slova koje predstavljaju tonalitet: CDEFGAB
        //u tom izrazu može se nalaziti i oznaka za sinizilicu b i povisilicu # koja zajedno s početnim slovom označuje tonalitet.

        //////////////////////////////////////////////////////////////////////////////////////////////////////

        //funkcija replace pretražuje string za određenu vrijednost ili ključnu riječ, ili regularni izraz
        //i vraća novi string u kojem su specificne vrijednosti zamijenjene
        //uzorak moze biti String ili RegExp, a zamjena moze biti string ili funkcija koja se zove za svako podudaranje (match)

        //U ovom slučaju zamjenska nije string nego funkcija. 

        /////////////////////////////////////////////////////////////////////////////////////////////////

        //Amount je interval za koji se miče, tj. transponira tonalitet. Može biti s pozitivnim ili negativnim predznakom.

        //normalizeMap koristi se da bi se prepoznali i nazivi u drugom formatu (kvintni krug).
        //Ako je akord unesen drugim imenom radi se provjera postoji li u normalizeMap. Ako postoji onda se koristi u normalize obliku, a ako ne koristi se u originalnom.??

        //U skali se traži indeks na kojem se nalazi traženi akord (match). Tom indeksu dodaje se interval transponiranja (amount).

        //Kvintni krug sastoji se od 12 vrijednosti. Radi se modulo s 12.

        //PRIMJER:
        //chord = D
        //amount = -3
        //scale.length = 12 (zadano)

        //KOD: match = (normalizeMap[match] ? normalizeMap[match] : match)
        //'D' ne postoji u normalizeMap pa se koristi 'D' (match)

        //Pronalazi se indeks ovog akorda u ljestvici svih tonaliteta - scale

        //KOD: scale.index(match)
        //indexPocetnogTonaliteta = 2 (nalazi se na trećem mjestu)

        //KOD: (indexPocetnogTonaliteta + amount) = 2 + (-3) = -1 
        //12+1=13

        //Da bi se indeks pomjerio za traženu vrijednost transponiranja potrebno je indeks zbrojiti s tom vrijednošću

        //indeksTrazenogTonaliteta = -1

        //KOD ostatak = indeksTrazenogTonaliteta % scale.length =-1 % 12 = -1

        //ako je ostatak < 0 onda je trazeniIndeks = ostatak + scale.lenght = -1 + 12 = 11 -NE ULAZI U OVAJ IF

        //ako ostatak nije <0 onda je trazeniIndeks = ostatak = -1 = scale[-1] = B - ULAZI U OVAJ IF

        return chord.replace(/[CDEFGAH](b|#)?/g, function(match) {
            var i = (scale.indexOf((normalizeMap[match] ? normalizeMap[match] : match)) + amount) % scale.length;
            return scale[i < 0 ? i + scale.length : i];
        })
    }

    
    var showTextChordsRadio = function (editor, option) {
    	
    	$.ajax({
            type: "POST",
            url: "showTextChordsRadio",
            data:  "rawSongText=" + editor.getSession().getValue() + "&option="+option,
            suppressErrors: true
        }).done(function(data) {
            debugger;

            if(option == 1) {
        		if (data.status == "noText") {
        			commonModul.showAlert({
        				elementId : 'showAlertBox',
        				message : "Unesi tekst pjesme!",
        				alertLevel : 'danger'
        			});
        		}
        		else if(data.status == "okText") {
        			$("#songPreview").val(data.result.onlyText).change();
        			$("#btnPreview").click();
            	}
            } else if(option == 2) {
        		if (data.status == "invalidChords") {
        			commonModul.showAlert({
        				elementId : 'showAlertBox',
        				message : "Unesi akorde pjesme!",
        				alertLevel : 'danger'
        			});
        		}
        		else if(data.status == "okChords") {
        			$("#songPreview").val(data.result.onlyChords).change();
        			$("#btnPreview").click();
            	}
            } else if(option == 3) {
        		if (data.status == "noRawText") {
        			commonModul.showAlert({
        				elementId : 'showAlertBox',
        				message : "Unesi text i akorde pjesme!",
        				alertLevel : 'danger'
        			});
        		}
        		else if(data.status == "okBoth") {
        			$("#songPreview").val(data.result.textAndChords).change();
        			$("#btnPreview").click();
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