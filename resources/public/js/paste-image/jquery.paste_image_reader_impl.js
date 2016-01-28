$("html").pasteImageReader(function (results) {
		var dataURL, filename;
		filename = results.filename, dataURL = results.dataURL;
		$data.text(dataURL);
		$size.val(results.file.size);
		$type.val(results.file.type);
		$test.attr('href', dataURL);
		var img = document.createElement('img');
		img.src = dataURL;
		var w = img.width;
		var h = img.height;
		$width.val(w); $height.val(h);
		$("div#editor-box").height(h);
		return $(".active").css({
backgroundImage: "url(" + dataURL + ")"
}).data({ 'width': w, 'height': h });
		});

var $data, $size, $type, $test, $width, $height;
$(function () {
		$data = $('.data');
		$size = $('.size');
		$type = $('.type');
		$test = $('#test');
		$width = $('#width');
		$height = $('#height');
		$('.target').on('click', function () {
			var $this = $(this);
			var bi = $this.css('background-image');
			if (bi != 'none') {
			$data.text(bi.substr(4, bi.length - 6));
			}

			$('.active').removeClass('active');
			$this.addClass('active');

			$this.toggleClass('contain');

			$width.val($this.data('width'));
			$height.val($this.data('height'));
			if ($this.hasClass('contain')) {
			$this.css({ 'width': $this.data('width'), 'height': $this.data('height'), 'z-index': '10' });
			} else {
			$this.css({ 'width': '', 'height': '', 'z-index': '' });
			}
			});
});
