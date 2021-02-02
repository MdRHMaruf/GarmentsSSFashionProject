window.onload = () => {
	document.title = "File Upload";
	document.getElementById('files').addEventListener('change', onFileSelect, false);
	document.getElementById('uploadButton').addEventListener('click', startUpload, false);
	//document.getElementById('find').addEventListener('click', find1, false);
}


//To log everything on console
function debug(s) {
	var debug = document.getElementById('debug');
	if (debug) {
		debug.innerHTML = debug.innerHTML + '<br/>' + s;
	}
}

//Will be called when upload is completed
function onUploadComplete(e) {
	totalUploaded += document.getElementById('files').files[filesUploaded].size;
	filesUploaded++;
	// debug('complete ' + filesUploaded + " of " + fileCount);
	//  debug('totalUploaded: ' + totalUploaded);
	if (filesUploaded < fileCount) {
		uploadNext();
	} else {
		var bar = document.getElementById('bar');
		bar.style.width = '100%';
		bar.innerHTML = '100% complete';
		//notification();
	}
}

function notification() {
	if (percentage == 100) {
		alert("Files Uploaded")
	}
}

//Will be called when user select the files in file control
function onFileSelect(e) {
	var bar = document.getElementById('bar');
	bar.style.width = 0 + '%';
	bar.innerHTML = 0 + ' % complete';

	var files = e.target.files; // FileList object
	var output = [];
	fileCount = files.length;
	totalFileLength = 0;
	for (var i = 0; i < fileCount; i++) {
		var file = files[i];
		output.push(file.name, ' (', file.size, ' bytes, ', file.lastModifiedDate.toLocaleDateString(), ')');
		output.push('<br/>');
		debug('add ' + file.size);
		totalFileLength += file.size;
	}
	// document.getElementById('selectedFiles').innerHTML = output.join('');
	debug('totalFileLength:' + totalFileLength);
}

//This will continueously update the progress bar
function onUploadProgress(e) {
	if (e.lengthComputable) {
		var percentComplete = parseInt((e.loaded + totalUploaded) * 100 / totalFileLength);
		var bar = document.getElementById('bar');
		bar.style.width = percentComplete + '%';
		bar.innerHTML = percentComplete + ' % complete';
		console.log(" bar prog " + percentComplete)

		percentage = percentComplete;

		console.log(" percentage prog " + percentage)
	} else {
		debug('unable to compute');
	}
}

//the Ouchhh !! moments will be captured here
function onUploadFailed(e) {
	alert("Error uploading file");
}

//Pick the next file in queue and upload it to remote server
function uploadNext() {

	var departments=[];
	$('#sectionSearch :selected').each(function(i, selectedElement) {
		departments[i]=$(selectedElement).val();
		i++;
	});
	
	for (var i = 0; i < departments.length; i++) {
		console.log(" depts "+departments[i])
	}

	var heading=$('#heading').val()
	var textbody=$("#textbody").val()
	var userid=$("#userId").val()
	if(heading!=""){
		var xhr = new XMLHttpRequest();
		var fd = new FormData();
		var file = document.getElementById('files').files[filesUploaded];
		fd.append("multipartFile", file);
		xhr.upload.addEventListener("progress", onUploadProgress, false);
		xhr.addEventListener("load", onUploadComplete, false);
		xhr.addEventListener("error", onUploadFailed, false);

	//	let url = new URL('save-notice');
		
		//url.searchParams.set(heading, textbody);

		xhr.open("POST", "save-notice/"+heading+"/"+departments+"/"+textbody+"/"+userid);
		debug('uploading ' + file.name);
		console.log(" file name "+fd)
		xhr.send(fd);
	}else{
		alert("Insert Notice Heading")
	}


}

//Let's begin the upload process

function startUpload() {

	totalUploaded = filesUploaded = 0;
	uploadNext();

}
