$(document).on('ready', function() {
	$("#input-21").fileinput({
		maxFileSize : 1024,
		allowedFileExtensions : [ "jpg", "gif", "png" ],
		previewFileType : "image",
		browseClass : "btn btn-success",
		browseLabel : "Pick Image",
		browseIcon : "<i class=\"glyphicon glyphicon-picture\"></i> ",
		removeClass : "btn btn-danger",
		removeLabel : "Delete",
		removeIcon : "<i class=\"glyphicon glyphicon-trash\"></i>",
		showRemove : false,
		showUpload : false
	});
});
