let totalFileLength, totalUploaded, fileCount, filesUploaded, percentage = 0;
let styleIdForSet = 0;
let itemIdForSet = 0;
let sizeValueListForSet = [];
let sizesListByGroup = JSON;

window.onload = () => {
	document.getElementById('files').addEventListener('change', onFileSelect, false);
	document.getElementById('uploadButton').addEventListener('click', startUpload, false);

	document.title = "Store File Upload & Download";
	let userId = $("#userId").val();
	
	
};



function printBuyerPO(buyerPoId) {
	$("#loader").show();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './buyerIndentInfo',
		data: {
			buyerPoId: buyerPoId
		},
		success: function (data) {
			if (data == "Success") {
				let url = "printBuyerPoOrder";
				window.open(url, '_blank');

			}
			$("#loader").hide();
		}
	});
}

function previewAction(type='') {

	buyerPoId = $("#buyerPOId").val();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './buyerIndentInfo',
		data: {
			buyerPoId: buyerPoId
		},
		success: function (data) {
			if (data == "Success") {
				let url = "printBuyerPoOrder";
				window.open(url, '_blank');

			}
			$("#loader").hide();
		}
	});
}

function buyerWiseStyleLoad() {
	let buyerId = $("#buyerName").val();

	// alert("buyerId "+buyerId);
	if (buyerId != 0) {
		$("#loader").show();
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getBuyerWiseStylesItem',
			data: {
				buyerId: buyerId
			},
			success: function (data) {

				let styleList = data.styleList;
				let options = "<option  value='0' selected>Select Style</option>";
				let length = styleList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
				};
				$("#styleNo").html(options);
				$('.selectpicker').selectpicker('refresh');
				$('#styleNo').val(styleIdForSet).change();
				styleIdForSet = 0;
				$("#loader").hide();
			}
		});
	} else {
		let options = "<option value='0' selected>Select Style</option>";
		$("#styleNo").html(options);
		$('#styleNo').selectpicker('refresh');
		$('#styleNo').val("0").change();
	}

}

function buyerWiseStyleLoad() {
	let buyerId = $("#buyerName").val();

	// alert("buyerId "+buyerId);
	if (buyerId != 0) {
		$("#loader").show();
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getBuyerWiseStylesItem',
			data: {
				buyerId: buyerId
			},
			success: function (data) {

				let styleList = data.styleList;
				let options = "<option  value='0' selected>Select Style</option>";
				let length = styleList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
				};
				$("#styleNo").html(options);
				$('.selectpicker').selectpicker('refresh');
				$('#styleNo').val(styleIdForSet).change();
				styleIdForSet = 0;
				$("#loader").hide();
			}
		});
	} else {
		let options = "<option value='0' selected>Select Style</option>";
		$("#styleNo").html(options);
		$('#styleNo').selectpicker('refresh');
		$('#styleNo').val("0").change();
	}

}


function buyerStyleWisePurchaseOrder() {
	

	var buyerId = $("#buyerName").val();
	var styleId = $("#styleNo").val();

		if (buyerId != '0') {
			if(styleId!='0'){
				$.ajax({
					type: 'POST',
					dataType: 'json',
					url: './buyerStyleWisePurchaseOrder/',
					data: {
						buyerId: buyerId,
						styleId: styleId
					},
					success: function (data) {
						let item = data.result;
						let options = "<option  value='0' selected>Select Purchase Order</option>";
						let length = item.length;
						for (let i = 0; i < length; i++) {
							options += "<option value='" + item[i].buyerOrderId + "'>" + item[i].purchaseOrder + "</option>";
						};
						$("#purchaseOrder").html(options);
						$('.selectpicker').selectpicker('refresh');
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
		else{
			alert("Provide Buyername");
		}
}
function styleWiseItemLoad() {
	let styleId = $("#styleNo").val();

	if (styleId != 0) {
		$("#loader").show();
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleWiseItem',
			data: {
				styleId: styleId
			},
			success: function (data) {

				let itemList = data.itemList;
				let options = "<option value='0' selected>Select Item Type</option>";
				let length = itemList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
				};
				document.getElementById("itemType").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$('#itemType').val(itemIdForSet).change();
				itemIdForSet = 0;
				$("#loader").hide();
			}
		});
	} else {
		let options = "<option value='0' selected>Select Item Type</option>";
		$("#itemType").html(options);
		$('#itemType').selectpicker('refresh');
		$('#itemType').val(itemIdForSet).change();
		itemIdForSet = 0;
	}

}





function searchBuyerPO(buyerPoNo) {
	$("#loader").show();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getBuyerPO',
		data: {
			buyerPoNo: buyerPoNo
		},
		success: function (data) {
			if (data.buyerPO == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.buyerPO == "duplicate") {
				dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
			} else {

				let buyerPo = data.buyerPO;
				console.log(buyerPo);
				$("#buyerPOId").val(buyerPo.buyerPoId);
				$("#buyerPOIdTitle").text(buyerPo.buyerPoId);
				$("#buyerName").val(buyerPo.buyerId).change();
				$("#shipmentDate").val(buyerPo.shipmentDate).change();
				$("#inspectionDate").val(buyerPo.inspectionDate).change();
				$("#paymentTerm").val(buyerPo.paymentTerm).change();
				$("#currency").val(buyerPo.currency).change();
				$("#note").val(buyerPo.note);
				$("#remarks").val(buyerPo.remarks);

				//drawItemTable(buyerPo.itemList);
				$('.modal').modal('hide')
				$("#btnPOSubmit").hide();
				$("#btnPOEdit").show();
				$("#btnPreview").show();
				//$("#btnPreview").prop("disabled", false);

				files(data.fileList)
				$("#loader").hide();
			}
		}
	});
}
var rowIdx = 0;
function files(data) {

	for (var i = 0; i < data.length; i++) {

		//console.log(" file name "+data[i].filename)
		$('#fileList').append(`<tr name="tr"  id="R${++rowIdx}" data-fileid="${data[i].autoid}">				
				<td id="filename-${rowIdx}" data-filename="${data[i].filename}">${data[i].filename}</td>
				<td id="R${rowIdx}">${data[i].uploadby}</td>
				<td id="R${rowIdx}" class="text-center"><i class="fa fa-download" onclick="download(this)"> </i></td>
				<td id="R${rowIdx}" class="text-center"><i class="fa fa-trash" onclick="del(this)"> </i></td>


		</tr>`);
	}

}



function download(a) {
	var rowIndex = $(a).closest('tr').index();
	var initindex = rowIndex + 1
	var fileid = $("#filename-" + initindex).text();

	console.log(" file name " + fileid)




	var file = fileid;

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



function del(a) {

	var rowIndex = $(a).closest('tr').index();
	var initindex = rowIndex + 1

	var fileid = $("#R" + initindex).attr("data-fileid");
	console.log(" file id " + fileid)
	var filename = $("#filename-" + initindex).text();
	console.log(" filename " + filename)
	$("#loader").show();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './delete/' + filename + "/" + fileid,
		data: {
		},
		success: function (data) {
			if (data == true) {
				alert("Successfully Deleted")
			}
			$("#loader").hide();
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


function setBuyerPoItemDataForEdit(itemAutoId) {
	$("#loader").show();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getBuyerPOItem',
		data: {
			itemAutoId: itemAutoId
		},
		success: function (data) {
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
			} else {

				let item = data.poItem;
				console.log(item);
				$("#itemAutoId").val(item.autoId);
				$("#customerOrder").val(item.customerOrder);
				$("#purchaseOrder").val(item.purchaseOrder);
				$("#shippingMark").val(item.shippingMark);
				$("#factory").val(item.factoryId).change();
				$("#color").val(item.colorId).change();

				sizeValueListForSet = item.sizeList;
				$("#sizeGroup").val(item.sizeGroupId).change();

				$("#itemAutoId").val(itemAutoId);
				$("#btnAdd").hide();
				$("#btnEdit").show();

				styleIdForSet = item.styleId;
				itemIdForSet = item.itemId;
				$("#buyerName").val(item.buyerId).change();
				$("#loader").hide();

			}
		}
	});

}

function deleteBuyerPoItem(itemAutoId) {

	let buyerPoId = $("#buyerPOId").val();
	let userId = $("#userId").val();
	if (confirm("Are you sure to Delete this item")) {
		$("#loader").show();
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './deleteBuyerPoItem',
			data: {
				buyerPoNo: buyerPoId,
				itemAutoId: itemAutoId,
				userId: userId
			},
			success: function (data) {
				if (data.result == "Something Wrong") {
					dangerAlert("Something went wrong");
				} else if (data.result == "duplicate") {
					dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
				} else {
					drawItemTable(data.result);
					$('.modal').modal('hide');
					let buyerPoId = $("#buyerPOId").val();
					if (buyerPoId != "0") {
						$("#btnPOSubmit").hide();
						$("#btnPOEdit").show();
						$("#btnPreview").show();
					}

				}
				$("#loader").hide();
			}
		});
	}

}

function reset() {
	let element = $(".alert");
	element.hide();
	$("#sizeGroup").val("0").change();
	$("#itemAutoId").val("0");
	$("#btnAdd").show();
	$("#btnEdit").hide();
}

function refreshAction() {
	location.reload();

}


function setData(unitId) {


	document.getElementById("unitId").value = unitId;
	document.getElementById("unitName").value = document.getElementById("unitName" + unitId).innerHTML;
	document.getElementById("unitValue").value = document.getElementById("unitValue" + unitId).innerHTML;
	$("#btnAdd").hide();
	$("#btnEdit").show();

}

function unitCmtFobTotalChange(autoId) {

	let totalUnit = Number($("#totalUnit" + autoId).text());
	let unitCmt = Number($("#unitCmt" + autoId).val());
	let totalPrice = totalUnit * unitCmt;
	let unitFob = Number($("#unitFob" + autoId).val());
	let totalAmount = totalUnit * unitFob;


	$("#itemRow-" + autoId).addClass('changed');
	$("#totalPrice" + autoId).text(totalPrice.toFixed(2));
	$("#totalAmount" + autoId).text(totalAmount.toFixed(2))
}



/*window.onload = () => {
	document.title = "File Upload";
	document.getElementById('files').addEventListener('change', onFileSelect, false);
	document.getElementById('uploadButton').addEventListener('click', startUpload, false);
	//document.getElementById('find').addEventListener('click', find1, false);
}*/




function uploadNext() {

	var i = 0;

	var buyerName = $("#buyerName").val();
	var purchaseOrderId = $("#purchaseOrder").val();
	var dept = $('#dept').val();
	
	if (buyerName != 0) {
		if (purchaseOrder != 0) {
			if (dept != 0) {

				i++;
				purpose = $("#purpose").val();

				var xhr = new XMLHttpRequest();
				var fd = new FormData();
				var file = document.getElementById('files').files[filesUploaded];
				fd.append("multipartFile", file);
				xhr.upload.addEventListener("progress", onUploadProgress, false);
				xhr.addEventListener("load", onUploadComplete, false);
				xhr.addEventListener("error", onUploadFailed, false);

				purchaseOrderId = 'bpo-'+purchaseOrderId;
				var user = $("#userId").val();

				xhr.open("POST", "save-store/" + purpose + "/" + user + "/" + buyerName + "/" + purchaseOrderId);
				debug('uploading ' + file.name);
				xhr.send(fd);
				add();

			} else {
				alert("Select Department")
			}
		} else {
			alert("Select Purchase Order");
		}
	} else {
		alert("Select Buyer");
	}
	/*if(i>0){
		add();
	}*/
}


function startUpload() {

	totalUploaded = filesUploaded = 0;
	uploadNext();

}

function add() {

	let dept = "0";
	let userId = $('#userId').val();
	let empCode = [];
	$('#receiver :selected').each(function (i, selectedElement) {
		empCode[i] = $(selectedElement).val();
		i++;
	});

	empCode = $('#receiver').val();
	let type;
	if (empCode != '') {
		type = 1;
	} else {
		type = 0;
	}
	$("#loader").show();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './saveFileAccessDetails',
		data: {
			dept: dept,
			userId: userId,
			empCode: empCode,
			type: type,
		},
		success: function (data) {
			$("#loader").hide();
		},
	});
}

function onUploadComplete(e) {
	totalUploaded += document.getElementById('files').files[filesUploaded].size;
	filesUploaded++;
	// debug('complete ' + filesUploaded + " of " + fileCount);
	//  debug('totalUploaded: ' + totalUploaded);
	if (filesUploaded < fileCount) {
		uploadNext();
	} else {
		let bar = document.getElementById('bar');
		bar.style.width = '100%';
		bar.innerHTML = '100% complete';
		//notification();
	}
}

function onUploadProgress(e) {
	if (e.lengthComputable) {
		let percentComplete = parseInt((e.loaded + totalUploaded) * 100 / totalFileLength);
		let bar = document.getElementById('bar');
		bar.style.width = percentComplete + '%';
		bar.innerHTML = percentComplete + ' % complete';
		console.log(" bar prog " + percentComplete)

		percentage = percentComplete;

		console.log(" percentage prog " + percentage)
	} else {
		debug('unable to compute');
	}
}

function onFileSelect(e) {
	let bar = document.getElementById('bar');
	bar.style.width = 0 + '%';
	bar.innerHTML = 0 + ' % complete';

	let files = e.target.files; // FileList object
	let output = [];
	fileCount = files.length;
	totalFileLength = 0;
	for (let i = 0; i < fileCount; i++) {
		let file = files[i];
		output.push(file.name, ' (', file.size, ' bytes, ', file.lastModifiedDate.toLocaleDateString(), ')');
		output.push('<br/>');
		debug('add ' + file.size);
		totalFileLength += file.size;
	}
	// document.getElementById('selectedFiles').innerHTML = output.join('');
	debug('totalFileLength:' + totalFileLength);
}

//the Ouchhh !! moments will be captured here
function onUploadFailed(e) {
	alert("Error uploading file");
}

function debug(s) {
	let debug = document.getElementById('debug');
	if (debug) {
		debug.innerHTML = debug.innerHTML + '<br/>' + s;
	}
}

function drawDataTable(data) {
	let rows = [];
	let length = data.length;

	for (let i = 0; i < length; i++) {
		rows.push(drawRowDataTable(data[i], i));
	}

	return rows;
}

function drawRowDataTable(rowData, c) {

	let row = $("<tr />")
	row.append($("<td>" + rowData.unitId + "</td>"));
	row.append($("<td id='unitName" + rowData.unitId + "'>" + rowData.unitName + "</td>"));
	row.append($("<td id='unitValue" + rowData.unitId + "'>" + rowData.unitValue + "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=\"setData(" + rowData.unitId + ")\"> </i></td>"));

	return row;
}

function successAlert(message) {
	let element = $(".alert");
	element.hide();
	element = $(".alert-success");
	document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
	element.show();
	setTimeout(() => {
		element.toggle('fade');
	}, 2500);
}

function warningAlert(message) {
	let element = $(".alert");
	element.hide();
	element = $(".alert-warning");
	document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
	element.show();
	setTimeout(() => {
		element.toggle('fade');
	}, 2500);
}

function dangerAlert(message) {
	let element = $(".alert");
	element.hide();
	element = $(".alert-danger");
	document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
	element.show();
	setTimeout(() => {
		element.toggle('fade');
	}, 2500);
}

$(document).ready(function () {
	$("input:text").focus(function () { $(this).select(); });
});
$(document).ready(function () {
	$("input").focus(function () { $(this).select(); });
});
$(document).ready(function () {
	$("#search").on("keyup", function () {
		let value = $(this).val().toLowerCase();
		$("#poList tr").filter(function () {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});

	// $('.datepicker').datepicker({
	// 	format: 'dd/mm/yyyy',
	// 	startDate: '-3d'
	// })
	
	// $("#shipmentDate").datepicker('setDate','2013-12-11');
});


function multidownload(v){
	var rowIndexx = $(v).closest('tr').index();
	console.log(" row index "+rowIndexx)
	var initindex=rowIndexx+1;
	
	var po=$("#id-"+initindex).attr("data-po");
	console.log(" po id "+po)
	po="bpo-"+po;
	

	//var rowIndex = $(a).closest('tr').index();
	//var initindex = rowIndex + 1
	//var fileid = $("#filename-" + initindex).text();

	//console.log(" file name " + fileid)




	var file = po;

	var user = $("#userId").val();
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "multidownload/" + file + '/' + user);
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


let idListMicro = ["buyerName","styleNo","itemType","factory","color","customerOrder","purchaseOrder","shippingMark","shipmentDate"
	,"inspectionDate","paymentTerm","currency","btnAdd","note","remarks","btnPOSubmit"];
idListMicro.forEach((id,index)=>{
  $('#'+id).keyup(function(event){
    if(event.keyCode ===13){
      event.preventDefault();
      $("#"+idListMicro[index+1]).focus();
    }
  });
})


