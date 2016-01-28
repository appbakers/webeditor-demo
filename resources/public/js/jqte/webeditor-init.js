$(function(){
  $('.editor').jqte();
  $(".jqte_editor").pasteImageReader(function(results) {
      document.execCommand("insertimage", 0, results.dataURL);
  });
});
