   
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
   
    
    function updateEditorValue(newText, editor) {
    	//isprazni postojeci tekst na formi
	    editor.getSession().setValue("");
	    
	    //OVO POSTAVLJA VRIJEDNOST na editor 
	    editor.getSession().setValue(newText);
    }
    
	    
    function transposeChords(editor, transposeValue) {
	    var newText = editor.getSession().getValue();
		
		//Pronalazi indekse akorda u tekstu i puni polje objekata u koje sprema indeks pronadenog akorda, naziv i duljinu cijelog teksta akorda
	    var foundChords = [];
	    songUtil.createChordsWithMatchIndex(editor.getSession().getValue(), foundChords);
	    
	    console.log("foundChords " + foundChords);
	    
	    var chordsValid = true;
	    //TODO NE ULAZI U VALIDACIJU
	    if(songValidate.chordsNotFoundInEditor(foundChords) == true) 
	    	chordsValid = false;
	   //PROVJERA POSTOJANJA AKORDA BACKEND
	    else if(songValidate.chordsInvalid(foundChords) == true) 
	        	chordsValid = false;
	    
        //ako su validacije prosle
        //TRANSPONIRANJE I ZAMJENA AKORDA
	    if(chordsValid)
	    	newText = songUtil.transposeChords(transposeValue, editor, foundChords);
	}

    function editorHtml (content, options) {
        var html = '';

        html += '<div class="md-loading"><span class="md-icon-container"><span class="md-icon"></span></span></div>';
        html += '<div class="md-toolbar">';
            html += '<div class="btn-toolbar" role="toolbar">';

                html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnHeader1 + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="h1">H1</button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnHeader2 + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="h2">H2</button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnHeader3 + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="h3">H3</button>';
                html += '</div>'; // .btn-group

                html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnBold + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="bold"><i class="fas fa-bold"></i></button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnItalic + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="italic"><i class="fas fa-italic"></i></button>';

               html += '</div>'; // .btn-group
               
               html += '<div class="btn-group mr-2" role="group">';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnKeepFormat + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="keepFormat"><i class="far fa-file-audio"></i></button>';
                    html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnChord + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="chordBracket"><i class="fas fa-guitar"></i></button>';
                    
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
                html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.transposeUp + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="transposeUp"><i class="fas fa-plus"></i></button>';
                html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.transposeDown + '" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="transposeDown"><i class="fas fa-minus"></i></button>';
	            html += '</div>'; // .btn-group
	          
	            
	            /*INIT i update BUTTON - HIDDEN*/
	            html += '<div class="btn-group mr-2" role="group">';
                html += '<button type="button" id="btnSetEditorVal" hidden="hidden" data-mdtooltip="tooltip" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="setEditorVal"></button>';
                html += '<button type="button" id="btnUpdateFormContent" hidden="hidden" data-mdtooltip="tooltip" class="md-btn btn btn-sm btn-outline-info editBtns" data-btn="updateFormContent"></button>';
	            html += '</div>'; // .btn-group
	         
                
                
                
                if (options.fullscreen === true) {
                    html += '<div class="btn-group pull-right">';
                        html += '<button type="button" data-mdtooltip="tooltip" title="' + options.label.btnFullscreen + '"  class="md-btn btn btn-sm btn-outline-secondary" data-btn="fullscreen"><i class="fas fa-compress"></i> ' + '</button>';
                    html += '</div>'; // .btn-group
                }

                if (options.preview === true) {
                    html += '<div class="btn-group pull-right">';
                        html += '<button type="button" class="md-btn btn btn-sm btn-outline-info btn-edit active" data-btn="edit"><i class="fas fa-edit"></i> ' + options.label.btnEdit + '</button>';
                        html += '<button type="button" class="md-btn btn btn-sm btn-outline-info btn-preview editBtns" data-btn="preview"><i class="fas fa-eye"></i> ' + options.label.btnPreview + '</button>';
                    html += '</div>'; // .btn-group
                }
                
	            
	            /*ONLY TEXT OR CHORDS OR BOTH*/
                html += '<div class="btn-group mr-2" role="group">';
                html += '</div>'; // .btn-group
                html += '<div class="btn-group btn-group-toggle mr-2" role="group" id="radio-btns" data-toggle="buttons">';
                html += '<label data-mdtooltip="tooltip" title="' + options.label.onlyText + '" class="md-btn btn btn-sm btn-outline-secondary active" data-btn="onlyText"><input type="radio" name="options" id="option1" autocomplete="off" checked value="onlyText"><i class="fas fa-font"></i></label>';
                html += '<label data-mdtooltip="tooltip" title="' + options.label.onlyChords + '" class="md-btn btn btn-sm btn-outline-secondary" data-btn="onlyChords"><input type="radio" name="options" id="option2" autocomplete="off" value="onlyChords"><i class="fas fa-music"></i> </label>';
                html += '<label data-mdtooltip="tooltip" title="' + options.label.textAndChords + '" class="md-btn btn btn-sm btn-outline-secondary" data-btn="textAndChords"><input type="radio" name="options" id="option3" autocomplete="off" value="textAndChords"><i class="fas fa-font"></i> + <i class="fas fa-music"></i></label>';
                html += '</div>'; // .btn-group
	            

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
            
            //check if btn function is forbidden in text and chord section
            function checkForbiddenBtn() {
            	commonModul.removeAllAlerts();
                var chordAndTextIndexFirst = editor.getSession().getValue().indexOf("```");
            	var chordAndTextIndexLast = editor.getSession().getValue().lastIndexOf("```");
            	var cursorIndex = editor.session.doc.positionToIndex(editor.selection.getCursor());
            	

                if(cursorIndex >= chordAndTextIndexFirst && cursorIndex <= chordAndTextIndexLast)  {
                	commonModul.showAlert({
        				elementId : 'showAlertBox',
        				message : "Izvrši naredbu izvan oznaka za tekst i akorde: ```   ```!",
        				alertLevel : 'danger'
        			});
                	return true;
                }  
                return false;
            }
           

            // Toolbar events
            container.find('.md-btn').click(function () {
                var btnType = $(this).data('btn'),
                    selectedText = editor.session.getTextRange(editor.getSelectionRange());

                if (btnType === 'h1') {
                	if(!checkForbiddenBtn())
                		insertBeforeText(editor, '#');

                } else if (btnType === 'h2') {
                	if(!checkForbiddenBtn())
                		insertBeforeText(editor, '##');

                } else if (btnType === 'h3') {
                	if(!checkForbiddenBtn())
                		insertBeforeText(editor, '###');

                } else if (btnType === 'ul') {
                	if(!checkForbiddenBtn())
                		insertBeforeText(editor, '*');

                } else if (btnType === 'ol') {
                	if(!checkForbiddenBtn())
                		insertBeforeText(editor, '1.');

                } else if (btnType === 'bold') {
                	if(!checkForbiddenBtn())
                		editor.execCommand('bold');

                } else if (btnType === 'italic') {
                	if(!checkForbiddenBtn())
                		editor.execCommand('italic');

                } else if (btnType === 'link') {
                	if(!checkForbiddenBtn())
                		editor.execCommand('link');

                } else if (btnType === 'keepFormat') {
        	    	commonModul.removeAllAlerts();
                	//provjers ako je ova oznaka vec dodana da je ne doda
                	if(!editor.getSession().getValue().includes("```")) {
                		
                		//postavljanje na kraj editora
                		var row = editor.session.getLength()-1;
                		var column = editor.session.getLine(row).length;// or simply Infinity
                		editor.gotoLine(row + 1, column);
                		
                		snippetManager.insertSnippet(editor, '\n```\n\n[C]\t\t[Am]\n\nTekst i akordi pjesme\n\n```');
                	} else {
                		commonModul.showAlert({
            				elementId : 'showAlertBox',
            				message : "Upiši tekst i akorde pjesme unutar oznaka ```   ```!",
            				alertLevel : 'warning'
            			});
                	}
                	
                	
                } else if (btnType === 'chordBracket') {
        	    	commonModul.removeAllAlerts();
                	
        	    	var chordAndTextIndexFirst = editor.getSession().getValue().indexOf("```");
        	    	var chordAndTextIndexLast = editor.getSession().getValue().lastIndexOf("```");
        	    	var cursorIndex = editor.session.doc.positionToIndex(editor.selection.getCursor());
        	    	

                    if(cursorIndex < chordAndTextIndexFirst || cursorIndex > chordAndTextIndexLast)  {
                    	commonModul.showAlert({
            				elementId : 'showAlertBox',
            				message : "Upiši akord unutar oznaka za tekst i akorde: ```   ```!",
            				alertLevel : 'warning'
            			});
                    	return;
                    }
        	    	
                    var selectedText = editor.session.getTextRange(editor.getSelectionRange());
                    
                    if (selectedText === '') {
                        snippetManager.insertSnippet(editor, '[${1:C#m}]');
                    } else {
                        snippetManager.insertSnippet(editor, '[' + selectedText + ']');
                    }
                   
                }  else if (btnType === 'transposeUp') {
        	    	commonModul.removeAllAlerts();
                    console.log("TRANSPOSE +1");
                    
        	        transposeChords(editor, 1);
                	
                  
                } else if (btnType === 'transposeDown') {
        	    	commonModul.removeAllAlerts();
                    console.log("TRANSPOSE -1");
                    
        	        transposeChords(editor, -1);
                	
        	        
                } else if (btnType === 'onlyText') {
                	songUtil.showOnlyText(editor);
                    console.log("onlyText");
                	
                } else if (btnType === 'onlyChords') {
                	songUtil.showOnlyChords(editor);
                    console.log("onlyChords");
                	
                } else if (btnType === 'textAndChords') {
                    console.log("textAndChords");
                	
                }
                /*else if (btnType === 'image') {
                	if(!checkForbiddenBtn()) {
	                    if (selectedText === '') {
	                        snippetManager.insertSnippet(editor, '![${1:text}](http://$2)');
	                    } else {
	                        snippetManager.insertSnippet(editor, '![' + selectedText + '](http://$1)');
	                    }
                	}

                }*/ else if (btnType === 'setEditorVal') {//hidden btn
                	
                	$.ajax({
            			type : "POST",
            			url : "setHeadingAuthorKeyToEditor",
            			data : $('#createSongForm').serialize(),
            			suppressErrors : true
            		}).done(function(data) {
            			if(data.status == "ok") {
            				//PUNJENJE EDITORA PODACIMA UNESENIM NA FORMI PJESME
            				updateEditorValue(data.result, editor);
            			}
            				
        	        });
	                
                } else if(btnType === 'updateFormContent') {

            	    $("#songEditor").val(editor.getSession().getValue()).change();
            	    
                } else if (btnType === 'edit') {
                    preview = false;
                    
                    mdPreview.hide();
                    mdEditor.show();
            		$('.editBtns').show();
                    container.find('.btn-edit').addClass('active');
                    container.find('.btn-preview').removeClass('active');

                    if (fullscreen === true) {
                        adjustFullscreenLayout(mdEditor);
                    }

                } else if (btnType === 'preview') {
                    preview = true;
            		$('.editBtns').hide();

                    mdPreview.html('<p font-size:16px">' + defaults.label.loading+ '...</p>'); 
                    defaults.onPreview(editor.getSession().getValue(), function (content) {
                        
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
            btnKeepFormat: 'Umetni tekst i akorde',
            transposeUp: 'Transpose +1',
            transposeDown: 'Transpose -1',
            onlyText: 'Prikaži samo tekst',
            onlyChords: 'Prikaži samo akorde',
            textAndChords: 'Prikaži tekst i akorde',
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
