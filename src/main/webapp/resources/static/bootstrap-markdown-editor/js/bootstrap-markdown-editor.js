/*!
 * Bootstrap Markdown Editor v2.0.2 (https://github.com/inacho/bootstrap-markdown-editor)
 * Copyright 2016 Ignacio de Tomás <nacho@inacho.es>
 * Licensed under MIT (https://github.com/inacho/bootstrap-markdown-editor/blob/master/LICENSE)
 */

! function(a) {
    "use strict";

    function b(b, c, d, e, f) {	
        if (c.length) {
            f.show();
            var g = new FormData,
                h = 0;
            for (h = 0; h < c.length; h++) g.append("file" + h, c[h]);
            a.ajax({
                url: b,
                type: "POST",
                contentType: !1,
                data: g,
                processData: !1,
                cache: !1,
                dataType: "json"
            }).done(function(a) {
                var b = "";
                a.length > 1 && (b = "\n");
                for (var c = 0; c < a.length; c++) e.insertSnippet(d, "![](" + a[c] + ")" + b)
            }).always(function() {
                f.hide()
            })
        }
    }

    function c(b) {
        var c, d = a(window).height(),
            e = b.offset().top;
        d > e && (c = d - e, b.css("height", c + "px"))
    }

    function d(a, b) {
        a.commands.addCommand({
            name: "bold",
            bindKey: {
                win: "Ctrl-B",
                mac: "Command-B"
            },
            exec: function(a) {
                var c = a.session.getTextRange(a.getSelectionRange());
                "" === c ? b.insertSnippet(a, "**${1:text}**") : b.insertSnippet(a, "**" + c + "**")
            },
            readOnly: !1
        }), a.commands.addCommand({
            name: "italic",
            bindKey: {
                win: "Ctrl-I",
                mac: "Command-I"
            },
            exec: function(a) {
                var c = a.session.getTextRange(a.getSelectionRange());
                "" === c ? b.insertSnippet(a, "*${1:text}*") : b.insertSnippet(a, "*" + c + "*")
            },
            readOnly: !1
        }), a.commands.addCommand({
            name: "link",
            bindKey: {
                win: "Ctrl-K",
                mac: "Command-K"
            },
            exec: function(a) {
                var c = a.session.getTextRange(a.getSelectionRange());
                "" === c ? b.insertSnippet(a, "[${1:text}](http://$2)") : b.insertSnippet(a, "[" + c + "](http://$1)")
            },
            readOnly: !1
        })
    }

    function e(a, b) {
        0 === a.getCursorPosition().column ? (a.navigateLineStart(), a.insert(b + " ")) : (a.navigateLineStart(), a.insert(b + " "), a.navigateLineEnd())
    }

    function f(b, c) {
        var d = "";
        return d += '<div class="md-loading"><span class="md-icon-container"><span class="md-icon"></span></span></div>', d += '<div class="md-toolbar">', d += '<div class="btn-toolbar">', d += '<div class="btn-group mr-2" role="group">', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnHeader1 + '" class="md-btn btn btn-sm btn-outline-info" data-btn="h1">H1</button>', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnHeader2 + '" class="md-btn btn btn-sm btn-outline-info" data-btn="h2">H2</button>', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnHeader3 + '" class="md-btn btn btn-sm btn-outline-info" data-btn="h3">H3</button>', d += "</div>", d += '<div class="btn-group mr-2" role="group">', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnBold + '" class="md-btn btn btn-sm btn-outline-info" data-btn="bold"><i class="fas fa-bold"></i></button>', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnItalic + '" class="md-btn btn btn-sm btn-outline-info" data-btn="italic"><i class="fas fa-italic"></i></button>', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnKeepFormat + '" class="md-btn btn btn-sm btn-outline-info" data-btn="keepFormat">F</button>', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnChord + '" class="md-btn btn btn-sm btn-outline-info" data-btn="chordBracket">C#</button>', d += "</div>", d += '<div class="btn-group mr-2" role="group">', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnList + '" class="md-btn btn btn-sm btn-outline-info" data-btn="ul"><i class="fas fa-list"></i></button>', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnOrderedList + '" class="md-btn btn btn-sm btn-outline-info" data-btn="ol"><i class="fas fa-list-ol"></i></button>', d += "</div>", d += '<div class="btn-group mr-2" role="group">', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnLink + '" class="md-btn btn btn-sm btn-outline-info" data-btn="link"><i class="fas fa-link"></i></button>', d += '<button type="button" data-mdtooltip="tooltip" title="' + c.label.btnImage + '" class="md-btn btn btn-sm btn-outline-info" data-btn="image"><i class="far fa-image"></i></button>', c.imageUpload === !0 && (d += '<div data-mdtooltip="tooltip" title="' + c.label.btnUpload + '" class="btn btn-sm btn-outline-info md-btn-file"><i class="fas fa-upload"></i><input class="md-input-upload" type="file" multiple accept=".jpg,.jpeg,.png,.gif"></div>'), d += "</div>", c.fullscreen === !0 && (d += '<div class="btn-group mr-2" role="group">', d += '<button type="button" class="md-btn btn btn-sm btn-outline-secondary" data-btn="fullscreen"><i class="fas fa-compress"></i> ' + c.label.btnFullscreen + "</button>", d += "</div>"), c.preview === !0 && (d += '<div class="btn-group pull-right">', d += '<button type="button" class="md-btn btn btn-sm btn-outline-success btn-edit active" data-btn="edit"><i class="fas fa-edit"></i> ' + c.label.btnEdit + "</button>", d += '<button type="button" class="md-btn btn btn-sm btn-outline-success btn-preview" data-btn="preview"><i class="fas fa-eye"></i> ' + c.label.btnPreview + "</button>", d += "</div>"), d += "</div>", d += "</div>", d += '<div class="md-editor">' + a("<div>").text(b).html() + "</div>", d += '<div class="md-preview" style="display:none"></div>'
    }
    var g = {
        init: function(g) {
            var h, i = a.extend(!0, {}, a.fn.markdownEditor.defaults, g),
                j = this,
                k = !1,
                l = !1;
            j.addClass("md-textarea-hidden"), h = a("<div/>"), j.after(h), h.addClass("md-container").html(f(j.val(), i)), "function" == typeof a().tooltip && h.find('[data-mdtooltip="tooltip"]').tooltip({
                container: "body"
            });
            var m = h.find(".md-editor"),
                n = h.find(".md-preview"),
                o = h.find(".md-loading");
            h.css({
                width: i.width
            }), m.css({
                height: i.height,
                fontSize: i.fontSize
            }), n.css({
                height: i.height
            });
            var p, q = ace.edit(m[0]);
            return q.setTheme("ace/theme/" + i.theme), q.getSession().setMode("ace/mode/markdown"), q.getSession().setUseWrapMode(!0), q.getSession().setUseSoftTabs(i.softTabs), q.getSession().on("change", function() {
                j.val(q.getSession().getValue())
            }), q.setHighlightActiveLine(!1), q.setShowPrintMargin(!1), q.renderer.setShowGutter(!1), ace.config.loadModule("ace/ext/language_tools", function() {
                p = ace.require("ace/snippets").snippetManager, d(q, p)
            }), i.imageUpload && (h.find(".md-input-upload").on("change", function() {
                var c = a(this).get(0).files;
                b(i.uploadPath, c, q, p, o)
            }), h.on("dragenter", function(a) {
                a.stopPropagation(), a.preventDefault()
            }), h.on("dragover", function(a) {
                a.stopPropagation(), a.preventDefault()
            }), h.on("drop", function(a) {
                a.preventDefault();
                var c = a.originalEvent.dataTransfer.files;
                b(i.uploadPath, c, q, p, o)
            })), i.fullscreen === !0 && a(window).resize(function() {
                l === !0 && c(k === !1 ? m : n)
            }), h.find(".md-btn").click(function() {
                var b = a(this).data("btn"),
                    d = q.session.getTextRange(q.getSelectionRange());
                "chordBracket" === b ? q. e(q, "[]") : "keepFormat" === b ? q. e(q, "```") : "h1" === b ? e(q, "#") : "h2" === b ? e(q, "##") : "h3" === b ? e(q, "###") : "ul" === b ? e(q, "*") : "ol" === b ? e(q, "1.") : "bold" === b ? q.execCommand("bold") : "italic" === b ? q.execCommand("italic") : "link" === b ? q.execCommand("link") : "image" === b ? "" === d ? p.insertSnippet(q, "![${1:text}](http://$2)") : p.insertSnippet(q, "![" + d + "](http://$1)") : "edit" === b ? (k = !1, n.hide(), m.show(), h.find(".btn-edit").addClass("active"), h.find(".btn-preview").removeClass("active"), l === !0 && c(m)) : "preview" === b ? (k = !0, n.html('<pre style="text-align:center; font-size:16px">' + i.label.loading + "...</pre>"), i.onPreview(q.getSession().getValue(), function(a) {
                    n.html(a);

                    //kad se prikazuje preview
                    /*POSTAVI SVE PARAGRAFE NA TAG P*/
		           /* $('p').each(function(){
		  				var elemH2 = $(this);
		  				elemH2.replaceWith('<pre>' + elemH2.text() + '</pre>');
					});*/
					//testno dodatno





                }), m.hide(), n.show(), h.find(".btn-preview").addClass("active"), h.find(".btn-edit").removeClass("active"), l === !0 && c(n)) : "fullscreen" === b && (l === !0 ? (l = !1, a("body, html").removeClass("md-body-fullscreen"), h.removeClass("md-fullscreen"), m.css("height", i.height), n.css("height", i.height)) : (l = !0, a("body, html").addClass("md-body-fullscreen"), h.addClass("md-fullscreen"), c(k === !1 ? m : n)), q.resize()), q.focus()
            }), this
        },
        content: function() {
            var a = ace.edit(this.find(".md-editor")[0]);
            return a.getSession().getValue()
        },
        setContent: function(a) {
            var b = ace.edit(this.find(".md-editor")[0]);
            b.setValue(a, 1)
        }
    };
    a.fn.markdownEditor = function(b) {
        return g[b] ? g[b].apply(this, Array.prototype.slice.call(arguments, 1)) : "object" != typeof b && b ? void a.error("Method " + b + " does not exist on jQuery.markdownEditor") : g.init.apply(this, arguments)
    }, a.fn.markdownEditor.defaults = {
        width: "100%",
        height: "600px",
        fontSize: "14px",
        theme: "tomorrow",
        softTabs: !0,
        fullscreen: !0,
        imageUpload: !1,
        uploadPath: "",
        preview: !1,
        onPreview: function(a, b) {
            b(a);
        },
        label: {
            btnHeader1: "Header 1",
            btnHeader2: "Header 2",
            btnHeader3: "Header 3",
            btnBold: "Bold",
            btnItalic: "Italic",
            btnKeepFormat: "Keep text format",
            btnChord: "Akord",
            btnList: "Unordered list",
            btnOrderedList: "Ordered list",
            btnLink: "Link",
            btnImage: "Insert image",
            btnUpload: "Upload image",
            btnEdit: "Edit",
            btnPreview: "Preview",
            btnFullscreen: "Fullscreen",
            loading: "Loading"
        }
    }
}(jQuery);