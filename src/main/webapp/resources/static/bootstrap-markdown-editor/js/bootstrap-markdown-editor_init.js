   
(function ($) {

    "use strict";
 

    function uploadFiles (url, files, editor, snippetManager, loading) {
        if (! files.length) {
            return;
        }

        loading.show();

        var data = new FormData(),
            i = 0;

        for (i = 0; i < files.length; i++) {
            data.append('file' + i, files[i]);
        }

        $.ajax({
            url: url,
            type: 'POST',
            contentType: false,
            data: data,
            processData: false,
            cache: false,
            dataType: 'json'
        }).done (function (uploadedFiles) {

            var separation = '';
            if (uploadedFiles.length > 1) {
                separation = "\n";
            }

            for (var i = 0; i < uploadedFiles.length; i++) {
                snippetManager.insertSnippet(editor, '![](' + uploadedFiles[i] + ')' + separation);
            }

        }).always(function () {
            loading.hide();
        });
    }

    function adjustFullscreenLayout (mdPanel) {
        var hWindow = $(window).height(),
            tEditor = mdPanel.offset().top,
            hEditor;

        if(hWindow > tEditor) {
            hEditor = hWindow - tEditor;
            mdPanel.css('height', hEditor + 'px');
        }
    }

    function setShortcuts (editor, snippetManager) {
        editor.commands.addCommand({
            name: 'bold',
            bindKey: {win: 'Ctrl-B',  mac: 'Command-B'},
            exec: function (editor) {
                var selectedText = editor.session.getTextRange(editor.getSelectionRange());

                if (selectedText === '') {
                    snippetManager.insertSnippet(editor, '**${1:text}**');
                } else {
                    snippetManager.insertSnippet(editor, '**' + selectedText + '**');
                }
            },
            readOnly: false
        });

        editor.commands.addCommand({
            name: 'italic',
            bindKey: {win: 'Ctrl-I',  mac: 'Command-I'},
            exec: function (editor) {
                var selectedText = editor.session.getTextRange(editor.getSelectionRange());

                if (selectedText === '') {
                    snippetManager.insertSnippet(editor, '*${1:text}*');
                } else {
                    snippetManager.insertSnippet(editor, '*' + selectedText + '*');
                }
            },
            readOnly: false
        });

        editor.commands.addCommand({
            name: 'link',
            bindKey: {win: 'Ctrl-K',  mac: 'Command-K'},
            exec: function (editor) {
                var selectedText = editor.session.getTextRange(editor.getSelectionRange());

                if (selectedText === '') {
                    snippetManager.insertSnippet(editor, '[${1:text}](http://$2)');
                } else {
                    snippetManager.insertSnippet(editor, '[' + selectedText + '](http://$1)');
                }
            },
            readOnly: false
        });
    }

    function insertBeforeText (editor, string) {

        if (editor.getCursorPosition().column === 0) {
            editor.navigateLineStart();
            editor.insert(string + ' ');
        } else {
            editor.navigateLineStart();
            editor.insert(string + ' ');
            editor.navigateLineEnd();
        }
    }
    
    function insertHeading (editor, string, text) {

        if (editor.getCursorPosition().column === 0) {
            editor.navigateLineStart();
            editor.insert(string + ' ' + text + '\n\n');
        } else {
            editor.navigateLineStart();
            editor.insert(string + ' ' + text + '\n\n');
            editor.navigateLineEnd();
        }
    }
    
    String.prototype.replaceAt=function(index, char) {
        var a = this.split("");
        a[index] = char;
        return a.join("");
    }
    
    function replaceIndex(string, at, repl) {
	   return string.replace(/\S/g, function(match, i) {
	        if( i === at ) return repl;

	        return match;
	    });
	}
    
    function transposeChords(editor, transposeValue) {
	    var newText = editor.getSession().getValue();
	    
	    //pronalazi sve akorde, [tekst unutar zagrada]
	    var regexp = /\[(.*?)\]/gi; //gi je za sve, a ne samo jedan
	    var matches_chords = editor.getSession().getValue().match(regexp);

	    var chords =  [];
	    if(matches_chords != null)
	    	chords.push(...matches_chords);
	    
	    //razlika u pomaku indeksa
		var diff = 0;
		
    	var re = /\[(.*?)\]/gi;
		var str = editor.getSession().getValue();
		
		//Pronalazi indekse akorda u tekstu i puni polje objekata u koje sprema indeks pronadenog akorda, naziv i duljinu cijelog teksta akorda
	    var testChordsMap = [];
	    for(var i = 0; i < chords.length; i++) {
	    	
			var match = re.exec(str);	
			console.log("match found at " + match.index);
			if(match != null) {
				
				var chord = {
		    				"id" : i,
			    			"index" : match.index,
			    			"name": chords[i],
			    			"len": chords[i].length
			    			}
			  	testChordsMap.push(chord);
			 }
	    }
	    
	    console.log("\n\n\n");
	    
	    
	    for(var i = 0; i < testChordsMap.length; i++) {
	    	console.log("index" + testChordsMap[i].index);
	    	console.log("name" + testChordsMap[i].name);
	    	console.log("lenstr " + testChordsMap[i].name.length);
	    	
	    
	    	//Akord iz teksta koji se treba transponirati, maknute zagrade
	    	var chordToTrans = testChordsMap[i].name;
	    	chordToTrans = chordToTrans.replace('[','');
	    	chordToTrans = chordToTrans.replace(']','');
	    	
	    	//Transponirani akord
	    	var transposedChord = songUtil.transposeChord(chordToTrans, transposeValue);
	    	console.log("trazeni indeks za tekst before " +  testChordsMap[i].index);
	    	
	    	//Tekst od pocetka do indeksa na kojem je pronađen akord 
	    	var textBefore = newText.substr(0,  testChordsMap[i].index-1);
	    	
	    	//Tekst od indeksa na kojem je pronađen akord, taj indeks uvecan duljinu pronadenog akorda = ostatak teksta
	    	var textAfter = newText.substr(testChordsMap[i].index + testChordsMap[i].name.length);

	    	console.log("textBefore " + textBefore);
	    	console.log("textAfter " + textAfter);
	    	newText = textBefore + " " + '[' + transposedChord + ']'+ textAfter;
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	//AZURIRANJE, TJ POVECAVANJE INDEKSA SLJEDECEG ZA dodani TEKST
	    	//razlika duljine chordToTrans i transposedChord koji se dodaje uz zagrade = micanje indeksa sljedeceg akorda unazad
    		
    		var startLen = testChordsMap[i].name.length;
    		//chordToTrans.len = 4
    		var transLen = transposedChord.length + 2; //+2 je zbog zagrada 
    		//transposedChord+zagrade.len = 3
    		diff += startLen - transLen;
    		
    		console.log("razlika pocetnog i transpon " + diff);
    		//azuriraj matchIndeks sljedeceg akorda u tekstu s obzirom na promjene prethodnog
    		//dok ima akorda
    		if(i+1 <= testChordsMap.length - 1) {
	    		var matchNextIndex = testChordsMap[i+1].index - diff;
	    		testChordsMap[i+1].index = matchNextIndex;
	    		console.log("azurirani indeks sljedeceg " + testChordsMap[i+1].index);
    		}	    
    		debugger;
    		
    		

	    }
	    	
    	//isprazni postojeci tekst na formi
	    editor.getSession().setValue("");
	    
	    //OVO POSTAVLJA VRIJEDNOST na editor 
	    editor.getSession().setValue(newText);
    	console.log("new tekst trenutno " + editor.getSession().getValue());
	}

    function editorHtml (content, options) {
        var html = '';

        html += '<div class="md-loading"><span class="md-icon-container"><span class="md-icon"></span></span></div>';
        html += '<div class="md-toolbar">';
            html += '<div class="btn-toolbar" role="toolbar">';

                html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnHeader1 + '" class="md-btn btn btn-sm btn-outline-info" data-btn="h1">H1</button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnHeader2 + '" class="md-btn btn btn-sm btn-outline-info" data-btn="h2">H2</button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnHeader3 + '" class="md-btn btn btn-sm btn-outline-info" data-btn="h3">H3</button>';
                html += '</div>'; // .btn-group

                html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnBold + '" class="md-btn btn btn-sm btn-outline-info" data-btn="bold"><i class="fas fa-bold"></i></button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnItalic + '" class="md-btn btn btn-sm btn-outline-info" data-btn="italic"><i class="fas fa-italic"></i></button>';

               html += '</div>'; // .btn-group
               
               html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnKeepFormat + '" class="md-btn btn btn-sm btn-outline-info" data-btn="keepFormat"><i class="fas fa-music"></i></button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnChord + '" class="md-btn btn btn-sm btn-outline-info" data-btn="chordBracket"><i class="fas fa-guitar"></i></button>';
                    
                html += '</div>'; // .btn-group
/*
                html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnList + '" class="md-btn btn btn-sm btn-outline-info" data-btn="ul"><i class="fas fa-list"></i></button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnOrderedList + '" class="md-btn btn btn-sm btn-outline-info" data-btn="ol"><i class="fas fa-list-ol"></i></button>';
                html += '</div>'; // .btn-group

                html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnLink + '" class="md-btn btn btn-sm btn-outline-info" data-btn="link"><i class="fas fa-link"></i></button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnImage + '" class="md-btn btn btn-sm btn-outline-info" data-btn="image"><i class="far fa-image"></i></button>';
                    if (options.imageUpload === true) {
                        html += '<div data-mdtooltip="tooltip" title="' + options.label.btnUpload + '" class="btn btn-sm btn-outline-info md-btn-file"><i class="fas fa-upload"></i><input class="md-input-upload" type="file" multiple accept=".jpg,.jpeg,.png,.gif"></div>';
                    }
                html += '</div>'; // .btn-group

                 */
                
                /*TRANSPOSE TIPKE + I -*/
                html += '<div class="btn-group mr-2" role="group">';
                html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.transposeUp + '" class="md-btn btn btn-sm btn-outline-info" data-btn="transposeUp"><i class="fas fa-plus"></i></button>';
                html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.transposeDown + '" class="md-btn btn btn-sm btn-outline-info" data-btn="transposeDown"><i class="fas fa-minus"></i></button>';
	            html += '</div>'; // .btn-group
	            
	            /*INIT i clear BUTTON - HIDDEN*/
	            html += '<div class="btn-group mr-2" role="group">';
                html += '<button type="button" id="btnInitEditorVal" hidden="hidden" data-mdtooltip="tooltip" class="md-btn btn btn-sm btn-outline-info" data-btn="initEditorVal"></button>';
                html += '<button type="button" id="btnClearContent" hidden="hidden" data-mdtooltip="tooltip" class="md-btn btn btn-sm btn-outline-info" data-btn="clearContent"></button>';
	            html += '</div>'; // .btn-group
	         
                
                
                
                if (options.fullscreen === true) {
                    html += '<div class="btn-group pull-right">';
                        html += '<button type="button" class="md-btn btn btn-sm btn-outline-secondary" data-btn="fullscreen"><i class="fas fa-compress"></i> ' + options.label.btnFullscreen + '</button>';
                    html += '</div>'; // .btn-group
                }

                if (options.preview === true) {
                    html += '<div class="btn-group pull-right">';
                        html += '<button type="button" class="md-btn btn btn-sm btn-outline-info btn-edit active" data-btn="edit"><i class="fas fa-edit"></i> ' + options.label.btnEdit + '</button>';
                        html += '<button type="button" class="md-btn btn btn-sm btn-outline-info btn-preview" data-btn="preview"><i class="fas fa-eye"></i> ' + options.label.btnPreview + '</button>';
                    html += '</div>'; // .btn-group
                }

            html += '</div>'; // .btn-toolbar
        html += '</div>'; // .md-toolbar
        
        

        html += '<div class="md-editor">' + $('<div>').text(content).html() + '</div>';
        html += '<div class="md-preview" style="display:none"></div>';

        return html;
    }

    var methods = {
        init: function (options) {

            var defaults = $.extend(true, {}, $.fn.markdownEditor.defaults, options),
                plugin = this,
                container,
                preview = false,
                fullscreen = false;

            // Hide the textarea
            plugin.addClass('md-textarea-hidden');

            // Create the container div after textarea
            container = $('<div/>');
            plugin.after(container);

            // Replace the content of the div with our html
            container.addClass('md-container').html(editorHtml(plugin.val(), defaults));

            // If the Bootstrap tooltip library is loaded, initialize the tooltips of the toolbar
            if (typeof $().tooltip === 'function') {
                container.find('[data-mdtooltip="tooltip"]').tooltip({
                    container: 'body'
                });
            }

            var mdEditor = container.find('.md-editor'),
                mdPreview = container.find('.md-preview'),
                mdLoading = container.find('.md-loading');

            container.css({
                width: defaults.width
            });

            mdEditor.css({
                height: defaults.height,
                fontSize: defaults.fontSize
            });

            mdPreview.css({
                height: defaults.height
            });

            // Initialize Ace
            var editor = ace.edit(mdEditor[0]),
                snippetManager;

            editor.setTheme('ace/theme/' + defaults.theme);
            editor.getSession().setMode('ace/mode/markdown');
            editor.getSession().setUseSoftTabs(defaults.softTabs);
           
            
            // Sync ace with the textarea
            editor.getSession().on('change', function() {
                plugin.val(editor.getSession().getValue());
            });

            editor.setHighlightActiveLine(false);
            editor.setShowPrintMargin(false);
            editor.renderer.setShowGutter(false);

            ace.config.loadModule('ace/ext/language_tools', function () {
                snippetManager = ace.require('ace/snippets').snippetManager;
                setShortcuts(editor, snippetManager);
            });


            // Image drag and drop and upload events
            if (defaults.imageUpload) {

                container.find('.md-input-upload').on('change', function() {
                    var files = $(this).get(0).files;

                    uploadFiles(defaults.uploadPath, files, editor, snippetManager, mdLoading);
                });

                container.on('dragenter', function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                });

                container.on('dragover', function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                });

                container.on('drop', function (e) {
                    e.preventDefault();
                    var files = e.originalEvent.dataTransfer.files;

                    uploadFiles(defaults.uploadPath, files, editor, snippetManager, mdLoading);
                });
            }

            // Window resize event
            if (defaults.fullscreen === true) {
                $(window).resize(function () {
                    if (fullscreen === true) {
                        if (preview === false) {
                            adjustFullscreenLayout(mdEditor);
                        } else {
                            adjustFullscreenLayout(mdPreview);
                        }
                    }
                });
            }

            // Toolbar events
            container.find('.md-btn').click(function () {
                var btnType = $(this).data('btn'),
                    selectedText = editor.session.getTextRange(editor.getSelectionRange());

                if (btnType === 'h1') {
                    insertBeforeText(editor, '#');

                } else if (btnType === 'h2') {
                    insertBeforeText(editor, '##');

                } else if (btnType === 'h3') {
                    insertBeforeText(editor, '###');

                } else if (btnType === 'ul') {
                    insertBeforeText(editor, '*');

                } else if (btnType === 'ol') {
                    insertBeforeText(editor, '1.');

                } else if (btnType === 'bold') {
                    editor.execCommand('bold');

                } else if (btnType === 'italic') {
                    editor.execCommand('italic');

                } else if (btnType === 'link') {
                    editor.execCommand('link');

                } else if (btnType === 'keepFormat') {
                    snippetManager.insertSnippet(editor, '```\nTekst i akordi pjesme\n```');
                    
                } else if (btnType === 'chordBracket') {
                	
                    var selectedText = editor.session.getTextRange(editor.getSelectionRange());

                    if (selectedText === '') {
                        snippetManager.insertSnippet(editor, '[${1:C#}]');
                    } else {
                        snippetManager.insertSnippet(editor, '[' + selectedText + ']');
                    }
                    
                }  else if (btnType === 'transposeUp') {
                    console.log("TRANSPOSE +1");
                    
        	        var transposeValue = 1;
        	        transposeChords(editor, transposeValue);
                  
                } else if (btnType === 'transposeDown') {
                    console.log("TRANSPOSE -1");
//NE RADI DOBRO
        	        var transposeValue = -1;
        	        transposeChords(editor, transposeValue);

                    //songUtil.transposeChords(-1, editor);
                }
                else if (btnType === 'image') {
                    if (selectedText === '') {
                        snippetManager.insertSnippet(editor, '![${1:text}](http://$2)');
                    } else {
                        snippetManager.insertSnippet(editor, '![' + selectedText + '](http://$1)');
                    }

                } else if (btnType === 'initEditorVal') {
                	
                    $.ajax({
            			type : "POST",
            			url : "setHeadingAuthorKeyToEditor",
            			data : $('#createSongForm').serialize(),
            			suppressErrors : true
            		}).done(function(data) {
            			debugger;
            			if(data.status == "ok") {
            				//PUNJENJE EDITORA PODACIMA UNESENIM NA FORMI PJESME
            				if(data.result.heading != null)
            					insertHeading(editor, '#', data.result.heading);
            				
            				if(data.result.author != null && data.result.author != "")
            					snippetManager.insertSnippet(editor, 'Autor: '+ data.result.author);
            				if(data.result.key != null)
                            	snippetManager.insertSnippet(editor, '\n\nTonalitet: '+ data.result.key);
            				
            				//placeholder za tekst i akorde
                            snippetManager.insertSnippet(editor, '\n\n```\n\n[C#]\t\t[A]\n\nTekst i akordi pjesme \n\n```\n\n');
            			} 
        	        });
                	
                } else if(btnType === 'clearContent') {
                	
                	editor.setValue("");
                } else if (btnType === 'edit') {
                    preview = false;
                    
                    mdPreview.hide();
                    mdEditor.show();
                    container.find('.btn-edit').addClass('active');
                    container.find('.btn-preview').removeClass('active');

                    if (fullscreen === true) {
                        adjustFullscreenLayout(mdEditor);
                    }

                } else if (btnType === 'preview') {
                    preview = true;

                    mdPreview.html('<p font-size:16px">' + defaults.label.loading+ '...</p>'); 
                    defaults.onPreview(editor.getSession().getValue(), function (content) {
                        
                        console.log(content);
                        mdPreview.html(content);
                    });

                    mdEditor.hide();
                    mdPreview.show();
                    container.find('.btn-preview').addClass('active');
                    container.find('.btn-edit').removeClass('active');

                    if (fullscreen === true) {
                        adjustFullscreenLayout(mdPreview);
                    }

                } else if (btnType === 'fullscreen') {

                    if (fullscreen === true) {
                        fullscreen = false;

                        $('body, html').removeClass('md-body-fullscreen');
                        container.removeClass('md-fullscreen');

                        mdEditor.css('height', defaults.height);
                        mdPreview.css('height', defaults.height);

                    } else {
                        fullscreen = true;

                        $('body, html').addClass('md-body-fullscreen');
                        container.addClass('md-fullscreen');

                        if (preview === false) {
                            adjustFullscreenLayout(mdEditor);
                        } else {
                            adjustFullscreenLayout(mdPreview);
                        }
                    }

                    editor.resize();
                }

                editor.focus();
            });

            return this;
        },
        content: function () {
            var editor = ace.edit(this.find('.md-editor')[0]);
            return editor.getSession().getValue();
        },
        setContent: function(str) {
          var editor = ace.edit(this.find('.md-editor')[0]);
          editor.setValue(str, 1);
        }
    };

    $.fn.markdownEditor = function (options) {

        if (methods[options]) {
            return methods[options].apply(this, Array.prototype.slice.call(arguments, 1));

        } else if (typeof options === 'object' || ! options) {
            return methods.init.apply(this, arguments);

        } else {
            $.error('Method ' +  options + ' does not exist on jQuery.markdownEditor');
        }
    };

    $.fn.markdownEditor.defaults = {
        width: '100%',
        height: '800px',
        fontSize: '14px',
        theme: 'tomorrow',
        softTabs: true,
        fullscreen: true,
        imageUpload: false,
        uploadPath: '',
        preview: false,
        onPreview: function (content, callback) {
            callback(content);
        },
        label: {
            btnHeader1: 'Naslov 1',
            btnHeader2: 'Naslov 2',
            btnHeader3: 'Naslov 3',
            btnBold: 'Bold',
            btnItalic: 'Italic',
            btnKeepFormat: 'Tekst i akordi',
            transposeUp: 'Transpose +1',
            transposeDown: 'Transpose -1',
            btnChord: 'Akord',
            btnList: 'Lista',
            btnOrderedList: 'Numerirana lista',
            btnLink: 'Link',
            btnImage: 'Umetni sliku',
            btnUpload: 'Upload slike',
            btnEdit: 'Uređivanje',
            btnPreview: 'Pregled',
            btnFullscreen: 'Puni ekran',
            loading: 'Loading'
        }
    };

}(jQuery));
