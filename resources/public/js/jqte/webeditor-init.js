/* http://stackoverflow.com/questions/16587869/jquery-text-editor
$.fn.jqteVal = function(value) {
  if(typeof value === 'undefined') {
    return $(this).closest("."+vars.css).find("."+vars.css+"_editor").html();
  } else {
    $(this).closest("."+vars.css).find("."+vars.css+"_editor").html(value);
  }
}
*/

//:TODO add insert image and appropriate attributes for better indenting or float...
function insertImage(to, imgSrc) {
    var id = "rand" + (Math.random() + "").slice(2);
    var editor = document.getElementById("editor");
    var doc = (editor.tagName.toLowerCase() == "iframe") ?
        (editor.contentDocument || editor.contentWindow.document) : document;
    var imgHtml = "<img src='" + imgSrc + "' id=" + id + ">";
    var sel;

    if (doc.queryCommandSupported("InsertHTML")) {
        doc.execCommand("insertHTML", false, imgHtml);
    } else if ( (sel = doc.selection) && sel.type != "Control") {
        var range = sel.createRange();
        range.pasteHTML(imgHtml);
        range.collapse(false);
        range.select();
    }

    return doc.getElementById(id);
};




var EDITOR_CLASSNAME = 'editor';
var JQTE_EDITOR_CLASSNAME = 'jqte_editor';
var JQTE_SUBMIT_BTNID = 'jqte_submit';
var WEBEDITOR_IMAGE_BLOCKID = 'images';
var JQTE_RESIZABLE_CLASSNAME = 'jqte_resizable';
var enableImageButton = function() {
  $('.jqte_toolbar').append('<div class="jqte_tool jqte_tool_22 unselectable" role="button"><a class="jqte_tool_icon unselectable"></a></div>');
  $('.jqte_tool_22 a').click(function(){
    $('[type=file]:enabled').click();
  });
}



// jqte init
$(function(){

	$('.'+EDITOR_CLASSNAME).jqte({
    blur: function() {
      console.log("blur");
      $('.'+JQTE_RESIZABLE_CLASSNAME).resizable();
    },
    change: function() {
      console.log("change");
    }
  });
  enableImageButton();

/*   // copy-paste
  $(JQTE_EDITOR_CLASSNAME).pasteImageReader(function(results) {
      document.execCommand("insertimage", 0, results.dataURL);
  });
 */

	// settings of status
  var jqteToggle = function(jqteStatus) {
		$('.'+EDITOR_CLASSNAME).jqte({"status" : jqteStatus});
//    enableImageButton();
    if( ! jqteStatus ){
      $('.'+EDITOR_CLASSNAME).hide();
      $('#'+JQTE_SUBMIT_BTNID).hide();
      $('#'+WEBEDITOR_IMAGE_BLOCKID).hide();
    } else {
      $('.'+JQTE_EDITOR_CLASSNAME).show();
      $('#'+JQTE_SUBMIT_BTNID).show();
      $('#'+WEBEDITOR_IMAGE_BLOCKID).show();
    }
  }

  // toggle button setting
	var jqteStatus = true;
	$(".jqte_status").click(function() {
		jqteStatus = jqteStatus ? false : true;
    jqteToggle(jqteStatus);
	});

  $('.'+JQTE_RESIZABLE_CLASSNAME).resizable({
    autoResize: true
  });

});

$(function(){
  $('.'+JQTE_EDITOR_CLASSNAME).pasteImageReader(function(results) {
    var editorWidth = $(this).parent().width();

    var id = "rand" + (Math.random() + "").slice(2);
    var imgHtml = "<p class='"+ JQTE_RESIZABLE_CLASSNAME
        +"' max-width='"+ editorWidth  +"'>"
        +"<img src='" + results.dataURL + "' id=" + id + "></p>";
    document.execCommand("insertHTML", false, imgHtml);

    var img = $('#'+ id);
    var imgWidth = img.width();
    var imgHeight = img.height();
//    alert( imgWidth +" : "  + imgHeight );
    img.css({width: '100%', height: '100%'});
    img.parent().width(imgWidth).height(imgHeight);
/*     if( ! img.parent().css('ui-resizable') ) {
      img.parent().resizable();
      img.parent().append('<p><br></p>');
    } */

//    insertImage(results.dataURL);
//      document.execCommand("insertHTML", 0, results.dataURL);
  });
});



// insertFileDrops
// REF:
/* $(function() {

  $(JQTE_EDITOR_CLASSNAME)
  .on('dragenter dragover', false).on('drop', function (e) {
  var readFileIntoDataUrl = function (fileInfo) {
    var loader = $.Deferred(),
        fReader = new FileReader();
    fReader.onload = function (e) {
      loader.resolve(e.target.result);
    };
    fReader.onerror = loader.reject;
    fReader.onprogress = loader.notify;
    fReader.readAsDataURL(fileInfo);
    return loader.promise();
  };

    var insertFiles = function (files) {
      alert( this );
      $(this).focus();
      $.each(files, function (idx, fileInfo) {
        if (/^image\//.test(fileInfo.type)) {
          $.when(readFileIntoDataUrl(fileInfo)).done(function (dataUrl) {
            document.execCommand('insertimage', dataUrl);
          }).fail(function (e) {
            options.fileUploadError("file-reader", e);
          });
        } else {
          options.fileUploadError("unsupported-file-type", fileInfo.type);
        }
      });
    };

    var dataTransfer = e.originalEvent.dataTransfer;
    e.stopPropagation();
    e.preventDefault();
    if (dataTransfer && dataTransfer.files && dataTransfer.files.length > 0) {

      alert( this );
      insertFiles(dataTransfer.files);
    }
  });
}); */

// jqte image prevew and dragdrop
// REF: http://home.jejaju.com/play/jQueryTE/
// apply drag and resize REF: https://forum.jquery.com/topic/drag-resize-images-divs
$(function () {
  var DROP_TIMEOUT = 3000;
  var droppedImage;
  $(document).on('drop','.jqte [contenteditable]',function(){
//    console.log(this,arguments);
    if(droppedImage) {
      $(droppedImage).remove();
      droppedImage=undefined;
    }
    setTimeout(function(){
      document.execCommand('unselect')
    }, DROP_TIMEOUT);
  });
  $(document).on('dragstart','#images img',function(){
//    console.log(this,arguments);
    droppedImage=this;
  }).on('dragend','#images img',function(){
//    console.log(this,arguments);
  });
  $(document).on('change','[type=file]',function(e){
    var here=$(this);
    here.parent().append(here.clone());
    here.hide().prop('disabled',true);
    $.each($(this).prop('files'),function(i,file){
      var reader = new FileReader();
      reader.onload = function (e) {
        $('<img/>',{src:e.target.result}).css({maxHeight:200 }).appendTo(here.parent());
      }
      reader.readAsDataURL(file);
    });
  });
});
