//algoritam za transpose JS
function transposeChord(chord, amount) {
    var scale = ["C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"]
    var normalizeMap = {"Cb":"B", "Db":"C#", "Eb":"D#", "Fb":"E", "Gb":"F#", "Ab":"G#", "Bb":"A#",  "E#":"F", "B#":"C"}
    
    
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
    //Da bi se indeks pomjerio za traženu vrijednost transponiranja potrebno je indeks zbrojiti s tom vrijednošću
    
    //indeksTrazenogTonaliteta = -1
    
    //KOD ostatak = indeksTrazenogTonaliteta % scale.length =-1 % 12 = -1
    
    
    //ako je ostatak < 0 onda je trazeniIndeks = ostatak + scale.lenght = -1 + 12 = 11 -NE ULAZI U OVAJ IF
    
    //ako ostatak nije <0 onda je trazeniIndeks = ostatak = -1 = scale[-1] = B - ULAZI U OVAJ IF
    
    
    
    return chord.replace(/[CDEFGAB](b|#)?/g, function(match) {
        var i = (scale.indexOf((normalizeMap[match] ? normalizeMap[match] : match)) + amount) % scale.length;
        return scale[ i < 0 ? i + scale.length : i ];
    })
}


var songUtil = (function() {
	var transposeChords = function(transposeValue, editorSession) {
		//transposeValue je UP (+1) ili DOWN (-1)
		commonModul.removeAllAlerts();
		
		//DOHVATI TRENUTNI TONALITET - PREMA ODABRANOM id-u u formi
        var currentKey = parseInt($("#key").val());
        
        console.log("currentKey:	" + currentKey);
        
        //TRANSPOSE VRIJEDNOST = TRENUTNI INDEX TONALTIETA +1/-1 (npr iz G u G#)
        //var	transposeAmount = currentKey + transposeValue;

        console.log("transposeValue:	" + transposeValue);
        

        //console.log("transposeAmount:	" + transposeAmount);
        
	    //transponiraj akorde pjesme
	        $.ajax({
	            type: "POST",
	            url: "transposeChords",
	            data:  "rawSongText=" + editorSession.getValue() + "&transposeAmount=" + transposeValue + "&currentKey=" + currentKey,
	            suppressErrors: true
	        }).done(function(data) {
	            debugger;

	            if(data.status == "ok") {

	                console.log("data.result.newKey:	" + data.result.newKey);
                    //azurirati trenutni tontalitet na formi
	                $("#key").val(data.result.newKey).change();
                    //azurirati rawSongText na formi
	                //OVO POSTAVLJA VRIJEDNOST
	                editorSession.setValue(data.result.rawSongText);
	            }
	        });
	};

	// Public API
	return {
		//transponira akorde
		transposeChords : transposeChords,
	}
})();