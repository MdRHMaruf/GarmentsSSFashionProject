var totalFileLength, totalUploaded, fileCount, filesUploaded, percentage = 0;

var purpose = "";
let code=[];

window.onload = () => {
	document.title = "File Upload";
	document.getElementById('files').addEventListener('change', onFileSelect, false);
	document.getElementById('uploadButton').addEventListener('click', startUpload, false);
	document.getElementById('find').addEventListener('click', find1, false);
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
	
	var i=0;

	var buyerName=$("#buyerName").val();
	var purchaseOrder=$("#purchaseOrder").val();
	var dept = $('#dept').val();
	if(buyerName!=0){
		if(purchaseOrder!=0){
			if(dept!=0){
				if ($("#purpose").val() == "") {
					alert("Insert Purpose");
				} else {
					i++;
					purpose = $("#purpose").val();

					var xhr = new XMLHttpRequest();
					var fd = new FormData();
					var file = document.getElementById('files').files[filesUploaded];
					fd.append("multipartFile", file);
					xhr.upload.addEventListener("progress", onUploadProgress, false);
					xhr.addEventListener("load", onUploadComplete, false);
					xhr.addEventListener("error", onUploadFailed, false);

					var user = $("#userId").val();

					xhr.open("POST", "save-product/" + purpose + "/" + user+ "/" + buyerName+ "/" + purchaseOrder);
					debug('uploading ' + file.name);
					xhr.send(fd);
					add();
				}
			}else{
				alert("Select Department")
			}
		}else{
			alert("Select Purchase Order");
		}
	}else{
		alert("Select Buyer");
	}
	/*if(i>0){
		add();
	}*/
}

//Let's begin the upload process

function startUpload() {

	totalUploaded = filesUploaded = 0;
	uploadNext();

}


function find1() {
	var start = $("#formDate").val();
	start = new Date(start).toLocaleDateString('fr-CA')

	var end = $("#endDate").val();
	end = new Date(end).toLocaleDateString('fr-CA')

	find(start, end)
}

function find(start, end) {

	var user = $("#userId").val();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './findfile/' + start + "/" + end + "/" + user,
		data: {



		},
		success: function (data) {
			$("#filetable").empty();
			patchData(data.result);

		},
		error: function (jqXHR, textStatus, errorThrown) {
			//alert("Server Error");
			if (jqXHR.status === 0) {
				alert('Not connect.\n Verify Network.');
			} else if (jqXHR.status == 404) {
				alert('Requested page not found.');
			} else if (jqXHR.status == 500) {
				alert('Internal Server Error.');
			} else if (errorThrown === 'parsererror') {
				alert('Requested JSON parse failed');
			} else if (errorThrown === 'timeout') {
				alert('Time out error');
			} else if (errorThrown === 'abort') {
				alert('Ajax request aborted ');
			} else {
				alert('Uncaught Error.\n' + jqXHR.responseText);
			}

		}
	});

}


function patchData(data) {
	var rows = [];


	for (var i = 0; i < data.length; i++) {
		//ert("ad "+data[i].aquisitionValue);
		rows.push(drawRow(data[i], i + 1));

	}

	$("#filetable").append(rows);

}

function drawRow(rowData, c) {

	//alert(rowData.aquisitionValue);

	var row = $("<tr />")
	row.append($("<td id='id-"+c+"'>" + rowData.id + "</td>"));
	row.append($("<td>" + rowData.filename + "</td>"));
	row.append($("<td>" + rowData.upby + "</td>"));
	row.append($("<td>" + rowData.upIp + "</td>"));
	row.append($("<td>" + rowData.upDateTime + "</td>"));
	row.append($("<td>" + rowData.purpose + "</td>"));
	row.append($("<td>" + rowData.DownBy + "</td>"));
	row.append($("<td>" + rowData.DownMachine + "</td>"));
	row.append($("<td>" + rowData.DownDatetime + "</td>"));
	row.append($("<td ><i class='fa fa-download' onclick=download('" + encodeURIComponent(rowData.filename) + "')  class=\"btn btn-primary\"> </i></td>"));
	row.append($("<td ><i class='fa fa-trash' onclick=del('" + encodeURIComponent(rowData.filename) + "','" + encodeURIComponent(rowData.id) + "')  class=\"btn btn-primary\"> </i></td>"));
	row.append($("<td><i class='fa fa-edit' id='sentMessage' href='#' data-toggle='modal' data-target='#largeModal' onclick='openModal(this,"+rowData.id+")'</i></td>"));

	return row;
}
//row.append($("<td ><i class='fa fa-edit' onclick='selectAction(this)'></i></td>"));
function del(filename,fileId) {

	var id = decodeURIComponent(fileId);
	var file = decodeURIComponent(filename);
	var user = $("#userId").val();

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './delete/' + file+ "/" + id,
		data: {



		},
		success: function (data) {
			if (data == true) {
				find1();
				alert("Successfully Deleted")
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {
			//alert("Server Error");
			if (jqXHR.status === 0) {
				alert('Not connect.\n Verify Network.');
			} else if (jqXHR.status == 404) {
				alert('Requested page not found.');
			} else if (jqXHR.status == 500) {
				alert('Internal Server Error.');
			} else if (errorThrown === 'parsererror') {
				alert('Requested JSON parse failed');
			} else if (errorThrown === 'timeout') {
				alert('Time out error');
			} else if (errorThrown === 'abort') {
				alert('Ajax request aborted ');
			} else {
				alert('Uncaught Error.\n' + jqXHR.responseText);
			}

		}
	});


}



function download(filename) {

	var file = decodeURIComponent(filename);
	var user = $("#userId").val();
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "download/" + file + '/' + user);
	xhr.responseType = 'arraybuffer';
	xhr.onload = function () {
		if (this.status === 200) {
			var filename = "";
			var disposition = xhr.getResponseHeader('Content-Disposition');
			if (disposition && disposition.indexOf('attachment') !== -1) {
				var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
				var matches = filenameRegex.exec(disposition);
				if (matches != null && matches[1]) {
					filename = matches[1].replace(/['"]/g, '');
				}
			}
			var type = xhr.getResponseHeader('Content-Type'); var blob = typeof File === 'function' ? new File([this.response], filename, { type: type }) : new Blob([this.response], { type: type });
			if (typeof window.navigator.msSaveBlob !== 'undefined') {
				// IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. 
				// These URLs will no longer resolve as the data backing the URL has been freed."
				window.navigator.msSaveBlob(blob, filename);
			} else {
				var URL = window.URL || window.webkitURL;
				var downloadUrl = URL.createObjectURL(blob); if (filename) {
					// use HTML5 a[download] attribute to specify filename
					var a = document.createElement("a");
					// safari doesn't support this yet
					if (typeof a.download === 'undefined') {
						window.location = downloadUrl;
					} else {
						a.href = downloadUrl;
						a.download = filename;
						document.body.appendChild(a);
						a.click();
					}
				} else {
					window.location = downloadUrl;
				}
				setTimeout(function () { URL.revokeObjectURL(downloadUrl); }, 100); // cleanup
			}
		}
	};
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

	xhr.send($.param({

	}));
}


//Create by Arman

function buyerWisePoLoad() {
	let buyerId = $("#buyerName").val();
	if (buyerId != 0) {

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './buyerWisePoLoad/' + buyerId,
			success: function (data) {
				loadPoNo(data.result);
			},
			error: function (jqXHR, textStatus, errorThrown) {
				//alert("Server Error");
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}
		});
	}
}

function loadPoNo(data) {

	let itemList = data;
	let options = "<option id='purchaseOrder' value='0'>Select Purchase Order</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option id='purchaseOrder' value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("purchaseOrder").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#purchaseOrder').change();
	//poId = 0;
}

function departmentWiseReceiver(){

	let deptId = $("#dept").val();
	if (deptId != 0) {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './departmentWiseReceiver/' + deptId,
			success: function (data) {
				loadReceiver(data);
			},
			error: function (jqXHR, textStatus, errorThrown) {
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}
			}
		});
	}
}

function loadReceiver(data) {

	let receiverList = data;
	let options = "<option value='0'>Select User</option>";
	let length = receiverList.length;
	for (let i = 0; i < length; i++) {
		options += "<option value='" + receiverList[i].id + "'>" + receiverList[i].name + "</option>";
	};
	document.getElementById("receiver").innerHTML = options;
	$('#receiver').selectpicker('refresh');
	$('#receiver').selectpicker('val',code);

}

function add(){

	var dept = $('#dept').val();
	var userId = $('#userId').val();
	var empCode=[];
	$('#receiver :selected').each(function(i, selectedElement) {
		empCode[i]=$(selectedElement).val();
		i++;
	});

	var empcode=$('#receiver').val();
	var type;
	if(empcode!=''){
		type=1;
	}else{
		type=0;
	}
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './saveFileAccessDetails',
		data:{
			dept:dept,
			userId:userId,
			empCode:empCode,
			type:type,
		},
		success: function (data) {
			/*if(data=="success"){
				alert("Inserted Successflly");
			}else{
				alert("Already Assigned");
			}*/
		},
	});
}

function openModal(a,id){

	var fileId = decodeURIComponent(id);
	$("#fileId").val(fileId);
	var Index = a.parentNode.parentNode.rowIndex;
	var id = $("#id-"+Index).text();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './getIdWiseFileLogDetails',
		data:{
			id:id,
		},
		success: function (data) {
			setFileLogDetails(data);
			$('#largeModal').modal('show');
		},
	});
}

var rowIdx=0;
function setFileLogDetails(data){
	$('#accessTable').empty();
	for(var i=0; i<data.length; i++){
		$('#accessTable').append(`<tr id="R${++rowIdx}" class='row-${data[i].dept}'  data-user-id='${data[i].id}'>
				<td class="row-index text-center" id='dept-${rowIdx}'><p>${data[i].name}</p></td>
				<td class="row-index text-center" id='id-${rowIdx}'><p>${data[i].value}<//p></td>
				<td class="row-index text-center"><i class='fa fa-edit' onclick='setDATA(${data[i].dept})'></i></td>

		</tr>`);
	}
}

function setDATA(dept){
	/*$('#dept').val(dept).change();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './setModalData',
		data:{
			dept:dept,
		},
		success: function (data) {
//			$('#dept').val(dept).change();
			for (var i = 0; i < data.length; i++) {
				//code.push(data[i].qty);
//				$('#receiver').selectpicker('refresh');
				//$("#receiver").selectpicker('val',data[i].qty);
			}
			setuser(data);

		},
	});*/




	var user = $('.row-'+dept);
	code=[];
	user.each((index,row)=>{
		code.push(row.getAttribute('data-user-id'));
	});
	$('#dept').val(dept).change();
	$('#largeModal').modal('hide');
}

function addNew(){

	var id = $("#fileId").val();
	var dept = $('#dept').val();
	var empCode=[];
	$('#receiver :selected').each(function(i, selectedElement) {
		empCode[i]=$(selectedElement).val();
		i++;
	});
	var userId = $('#userId').val();
	var empcode=$('#receiver').val();
	var type;
	if(empcode!=''){
		type=1;
	}else{
		type=0;
	}
	if(dept!=0){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './addNewPermission',
			data:{

				dept:dept,
				empCode:empCode,
				type:type,
				id:id,
				userId:userId,

			},
			success: function (data) {
				if(data=="success"){
					alert("Inserted Successflly");
				}else{
					alert("Already Assigned");
				}
			},
		});
	}else{
		alert("Select Department");
	}
}


let idListMicro = ["purpose","formDate","endDate","find","buyerName","purchaseOrder","dept","receiver","btnAdd"];
idListMicro.forEach((id,index)=>{
  $('#'+id).keyup(function(event){
    if(event.keyCode ===13){
      event.preventDefault();
      $("#"+idListMicro[index+1]).focus();
    }
  });
})

