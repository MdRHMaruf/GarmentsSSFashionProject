let totalFileLength, totalUploaded, fileCount, filesUploaded, percentage = 0;
let styleIdForSet = 0;
let itemIdForSet = 0;
let sizeValueListForSet = [];
let sizesListByGroup = JSON;

window.onload = () => {
	document.getElementById('files').addEventListener('change', onFileSelect, false);
	document.getElementById('uploadButton').addEventListener('click', startUpload, false);

	document.title = "Buyer Purchase Order";
	let userId = $("#userId").val();
	$("#loader").show();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './sizesLoadByGroup',
		data: {},
		success: function (obj) {
			sizesListByGroup = [];
			sizesListByGroup = obj.sizeList;
		}
	});

	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getBuyerPOItemsList',
		data: {
			buyerPoNo: "0",
			userId: userId
		},
		success: function (data) {
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawItemTable(data.result);
			}
			$("#loader").hide();
		}
	});
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

function itemSizeAdd() {

	let buyerPOId = $("#buyerPOId").val();
	let buyerId = $("#buyerName").val();
	let styleId = $("#styleNo").val();
	let itemId = $("#itemType").val();
	let factoryId = $("#factory").val();
	let colorId = $("#color").val();
	let sizeGroupId = $("#sizeGroup").val();
	let customerOrder = $("#customerOrder").val();
	let purchaseOrder = $("#purchaseOrder").val();
	let shippingMark = $("#shippingMark").val();
	let userId = $("#userId").val();
	let totalUnit = 0;

	let rowList = $("tr.dataRow");
	let isExist = false;
	console.log("length=", rowList.length);
	for (let i = 0; i < rowList.length; i++) {
		row = rowList[i];
		if (row.getAttribute('data-style-id') == styleId &&
			row.getAttribute('data-item-id') == itemId && row.getAttribute('data-customer-order') == customerOrder && row.getAttribute('data-purchase-order') == purchaseOrder && row.getAttribute('data-color-id') == colorId && row.getAttribute('data-size-group-id') == sizeGroupId) {
			isExist = true;
			break;
		}
	};
	if (!isExist) {
		if (buyerId != 0) {
			if (styleId != 0) {
				if (itemId != 0) {
					if (factoryId != 0) {
						if (colorId != 0) {
							if (sizeGroupId != 0) {
								if (purchaseOrder != "") {
									let sizeListLength = $(".sizeValue").length;
									let sizeList = "";
									for (let i = 0; i < sizeListLength; i++) {
										let quantity = $("#sizeValue" + i).val().trim() == "" ? "0" : $("#sizeValue" + i).val().trim();
										let id = $("#sizeId" + i).val().trim();
										sizeList += "id=" + id + ",quantity=" + quantity + " ";
										totalUnit += Number(quantity);
									}

									$("#loader").show();
									$.ajax({
										type: 'POST',
										dataType: 'json',
										url: './addItemToBuyerPO',
										data: {
											autoId: "0",
											buyerPOId: buyerPOId,
											buyerId: buyerId,
											styleId: styleId,
											itemId: itemId,
											factoryId: factoryId,
											colorId: colorId,
											customerOrder: customerOrder,
											purchaseOrder: purchaseOrder,
											shippingMark: shippingMark,
											sizeGroupId: sizeGroupId,
											sizeListString: sizeList,
											totalUnit: totalUnit,
											unitCmt: 0,
											totalPrice: 0,
											unitFob: 0,
											totalAmount: 0,
											userId: userId
										},
										success: function (data) {
											// warningAlert("");
											if (data.result == "Something Wrong") {
												dangerAlert("Something went wrong");
											} else if (data.result == "duplicate") {
												dangerAlert("Duplicate Item Name..This Item Name Already Exist")
											} else {
												drawItemTable(data.result);
											}
											$("#loader").hide();
										}
									});
								} else {
									alert("Purchase Order Not Set... Please Select Purchase Order");
									//warningAlert("Purchase Order Not Set... Please Select Purchase Order");
									$("#purchaseOrder").focus();
								}
							} else {
								alert("Size Group not selected ... Please Select Size group");
								// warningAlert("Size Group not selected ... Please Select Size group");
								$("#sizeGroup").focus();
							}
						} else {
							alert("Color Not Selected... Please Select Color");
							$("#color").focus();
						}
					} else {
						alert("Factory not selected... Please Select Factory Name");
						$("#factoryId").focus();
					}
				} else {
					alert("Item Type not selected... Please Select Item Type");
					$("#itemType").focus();
				}
			} else {
				alert("Style No not selected... Please Select Style No");
				$("#styleNo").focus();
			}
		} else {
			alert("Buyer Name not selected... Please Select Buyer Name");
			$("#buyerName").focus();
		}
	} else {
		warningAlert("This Item Already Exist");
	}


}


function itemSizeEdit() {

	let buyerPOId = $("#buyerPOId").val();
	let itemAutoId = $("#itemAutoId").val();
	let buyerId = $("#buyerName").val();
	let styleId = $("#styleNo").val();
	let itemId = $("#itemType").val();
	let factoryId = $("#factory").val();
	let colorId = $("#color").val();
	let sizeGroupId = $("#sizeGroup").val();
	let customerOrder = $("#customerOrder").val();
	let purchaseOrder = $("#purchaseOrder").val();
	let shippingMark = $("#shippingMark").val();
	let unitCmt = $("#unitCmt" + itemAutoId).val();
	let unitFob = $("#unitFob" + itemAutoId).val();
	let userId = $("#userId").val();
	let totalUnit = 0;
	let totalPrice = 0;
	let totalAmount = 0;


	if (buyerId != 0) {
		if (styleId != 0) {
			if (itemId != 0) {
				if (factoryId != 0) {
					if (colorId != 0) {
						if (sizeGroupId != 0) {
							if (purchaseOrder != "") {
								let sizeListLength = $(".sizeValue").length;
								let sizeList = "";
								for (let i = 0; i < sizeListLength; i++) {
									let quantity = $("#sizeValue" + i).val().trim() == "" ? "0" : $("#sizeValue" + i).val().trim();
									let id = $("#sizeId" + i).val().trim();
									sizeList += "id=" + id + ",quantity=" + quantity + " ";
									totalUnit += Number(quantity);
								}
								totalPrice = totalUnit * unitCmt;
								totalAmount = totalUnit * unitFob;
								$("#loader").show();
								$.ajax({
									type: 'POST',
									dataType: 'json',
									url: './editBuyerPoItem',
									data: {
										autoId: itemAutoId,
										buyerPOId: buyerPOId,
										buyerId: buyerId,
										styleId: styleId,
										itemId: itemId,
										factoryId: factoryId,
										colorId: colorId,
										customerOrder: customerOrder,
										purchaseOrder: purchaseOrder,
										shippingMark: shippingMark,
										sizeGroupId: sizeGroupId,
										sizeListString: sizeList,
										totalUnit: totalUnit,
										unitCmt: unitCmt,
										totalPrice: totalPrice,
										unitFob: unitFob,
										totalAmount: totalAmount,
										userId: userId
									},
									success: function (data) {
										if (data.result == "Something Wrong") {
											dangerAlert("Something went wrong");
										} else if (data.result == "duplicate") {
											dangerAlert("Duplicate Item Name..This Item Name Already Exist")
										} else {
											drawItemTable(data.result);
										}
										$("#loader").hide();
									}
								});
							} else {
								alert("Purchase Order Not Set... Please Select Purchase Order");
								$("#purchaseOrder").focus();
							}
						} else {
							alert("Size Group not selected ... Please Select Size group");
							$("#sizeGroup").focus();
						}
					} else {
						alert("Color Not Selected... Please Select Color");
						$("#color").focus();
					}
				} else {
					alert("Factory not selected... Please Select Factory Name");
					$("#factoryId").focus();
				}
			} else {
				alert("Item Type not selected... Please Select Item Type");
				$("#itemType").focus();
			}
		} else {
			alert("Style No not selected... Please Select Style No");
			$("#styleNo").focus();
		}
	} else {
		alert("Buyer Name not selected... Please Select Buyer Name");
		$("#buyerName").focus();
	}

}

function submitAction() {
	let buyerPoId = $("#buyerPOId").val();
	let buyerId = $("#buyerName").val();
	let shipmentDate = $("#shipmentDate").val();
	let inspectionDate = $("#inspectionDate").val();
	let paymentTerm = $("#paymentTerm").val();
	let currency = $("#currency").val();
	let totalRow = $("#tableList tr");
	let rowList = $("#tableList tr.dataRow");

	let totalUnit = 0;
	let unitCmt = 0;
	let totalPrice = 0;
	let unitFob = 0;
	let totalAmount = 0;

	console.log("rowList", rowList);
	rowList.each((index, row) => {
		console.log(row);
		let autoId = row.getAttribute('data-auto-id');
		console.log("auto id=", autoId);
		totalUnit += Number($("#totalUnit" + autoId).text());
		unitCmt += Number($("#unitCmt" + autoId).val());
		totalPrice += Number($("#totalPrice" + autoId).text());
		unitFob += Number($("#unitFob" + autoId).val());
		totalAmount += Number($("#totalAmount" + autoId).text());
	});

	let note = $("#note").val();
	let remarks = $("#remarks").val();
	let userId = $("#userId").val();

	let changedItems = {};
	changedItems['list'] = [];
	rowList = $("#tableList tr.changed");
	rowList.each((index, row) => {
		let autoId = row.getAttribute('data-auto-id');
		let item = {
			autoId: autoId,
			unitCmt: Number($("#unitCmt" + autoId).val()),
			totalPrice: Number($("#totalPrice" + autoId).text()),
			unitFob: Number($("#unitFob" + autoId).val()),
			totalAmount: Number($("#totalAmount" + autoId).text())
		}
		changedItems.list.push(item);
	});


	if (buyerId != 0) {
		if (totalRow.length != 0) {
			if (shipmentDate) {
				if (inspectionDate) {
					$("#loader").show();
					$.ajax({
						type: 'POST',
						dataType: 'json',
						url: './submitBuyerPO',
						data: {
							buyerPoId: buyerPoId,
							buyerId: buyerId,
							paymentTerm: paymentTerm,
							currency: currency,
							totalUnit: totalUnit,
							unitCmt: unitCmt,
							totalPrice: totalPrice,
							unitFob: unitFob,
							totalAmount: totalAmount,
							shipmentDate: shipmentDate,
							inspectionDate : inspectionDate,
							note: note,
							remarks: remarks,
							changedItemsList: JSON.stringify(changedItems),
							userId: userId
						},
						success: function (data) {
							if (data.result == "Something Wrong") {
								dangerAlert("Something went wrong");
							} else if (data.result == "duplicate") {
								dangerAlert("Duplicate Buyer Name..This Unit Name Already Exist")
							} else {
								successAlert("Buyer Purchase Order Save Successfully");
								refreshAction();
							}
							$("#loader").hide();
						}
					});
				} else {
					warningAlert("Inspection Date Not Selected... Please Select Inspection Date");
					$("#inspectionDate").focus();
				}
			} else {
				warningAlert("Shipment Date Not Selected... Please Select Shipment Date");
				$("#shipmentDate").focus();
			}
		}
		else {
			alert("At first Add Size Wise Buyer Order Estimate");
		}
	} else {
		alert("Buyer Name not selected... Please Select Buyer Name");
		$("#buyerName").focus();
	}
}


function buyerPoEditAction() {

	let buyerPoId = $("#buyerPOId").val();
	let buyerId = $("#buyerName").val();
	let shipmentDate = $("#shipmentDate").val();
	let inspectionDate = $("#inspectionDate").val();
	let paymentTerm = $("#paymentTerm").val();
	let currency = $("#currency").val();
	let rowList = $("#tableList tr.dataRow");

	let totalUnit = 0;
	let unitCmt = 0;
	let totalPrice = 0;
	let unitFob = 0;
	let totalAmount = 0;

	rowList.each((index, row) => {
		let autoId = row.getAttribute('data-auto-id');
		totalUnit += Number($("#totalUnit" + autoId).text());
		unitCmt += Number($("#unitCmt" + autoId).val());
		totalPrice += Number($("#totalPrice" + autoId).text());
		unitFob += Number($("#unitFob" + autoId).val());
		totalAmount += Number($("#totalAmount" + autoId).text());
	});

	let changedItems = {};
	changedItems['list'] = [];
	rowList = $("#tableList tr.changed");
	rowList.each((index, row) => {
		let autoId = row.getAttribute('data-auto-id');
		let item = {
			autoId: autoId,
			unitCmt: Number($("#unitCmt" + autoId).val()),
			totalPrice: Number($("#totalPrice" + autoId).text()),
			unitFob: Number($("#unitFob" + autoId).val()),
			totalAmount: Number($("#totalAmount" + autoId).text())
		}
		changedItems.list.push(item);
	});

	let note = $("#note").val();
	let remarks = $("#remarks").val();
	let userId = $("#userId").val();

	console.log("Edit function call");
	if (buyerPoId != "0") {
		if (buyerId != 0) {
			if (shipmentDate) {
				if (inspectionDate) {
					$("#loader").show();
					$.ajax({
						type: 'POST',
						dataType: 'json',
						url: './editBuyerPO',
						data: {
							buyerPoId: buyerPoId,
							buyerId: buyerId,
							paymentTerm: paymentTerm,
							currency: currency,
							totalUnit: totalUnit,
							unitCmt: unitCmt,
							totalPrice: totalPrice,
							unitFob: unitFob,
							totalAmount: totalAmount,
							shipmentDate: shipmentDate,
							inspectionDate : inspectionDate,
							note: note,
							remarks: remarks,
							changedItemsList: JSON.stringify(changedItems),
							userId: userId
						},
						success: function (data) {
							if (data.result == "Something Wrong") {
								dangerAlert("Something went wrong");
							} else if (data.result == "duplicate") {
								dangerAlert("Duplicate Buyer Name..This Unit Name Already Exist")
							} else {
								successAlert("Buyer Purchase Order Edit Successfully");
							}
							$("#loader").hide();
						}
					});
				} else {
					warningAlert("Inspection Date Not Selected... Please Select Inspection Date");
					$("#inspectionDate").focus();
				}
			} else {
				warningAlert("Shipment Date Not Selected... Please Select Shipment Date");
				$("#shipmentDate").focus();
			}
		} else {
			alert("Buyer Name not selected... Please Select Buyer Name");
			$("#buyerName").focus();
		}
	} else {
		alert("Something Wrong... Buyer Purchase Order Id not found");
		$("#buyerName").focus();
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
				$("#shipmentDate").val(buyerPo.shipmentDate);
				$("#inspectionDate").val(buyerPo.inspectionDate);
				$("#paymentTerm").val(buyerPo.paymentTerm).change();
				$("#currency").val(buyerPo.currency).change();
				$("#note").val(buyerPo.note);
				$("#remarks").val(buyerPo.remarks);

				drawItemTable(buyerPo.itemList);
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

function sizeLoadByGroup() {

	let groupId = $("#sizeGroup").val().trim();
	let child = "";
	let length = 0;
	if (groupId != "0") {
		length = sizesListByGroup['groupId' + groupId].length;
		for (let i = 0; i < length; i++) {
			//child += " <div class=\"list-group-item pt-0 pb-0 sizeNameList\"> <div class=\"form-group row mb-0\"><label for=\"sizeId" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" class=\"col-md-6 col-form-label-sm pb-0 mb-0\" style=\"height:25px;\">" + sizesListByGroup['groupId' + groupId][i].sizeName + "</label><input type=\"number\" class=\"form-control-sm col-md-6\" id=\"sizeValue" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" style=\"height:25px;\"></div></div>";
			child += `<div class="list-group-item pt-0 pb-0"> 
				<div class="form-group row mb-0">
				<label for="sizeValue${i}" class="col-md-6 col-form-label-sm pb-0 mb-0" style="height:25px;">${sizesListByGroup['groupId' + groupId][i].sizeName}</label>
				<input type="number" class="form-control-sm col-md-6 sizeValue" id="sizeValue${i}" style="height:25px;">
				<input type="hidden" id="sizeId${i}" value="${sizesListByGroup['groupId' + groupId][i].sizeId}">
				</div>
				</div>`;
		}

	}
	$("#listGroup").html(child);

	$(".sizeValue").each((index, inputText) => {
		inputText.addEventListener('keyup', (event) => {
			if (event.keyCode == 13) {
				$("#sizeValue" + (index + 1)).focus();
			}
		})
	});
	if (sizeValueListForSet.length > 0) {
		for (let i = 0; i < length; i++) {
			$("#sizeValue" + i).val(sizeValueListForSet[i].sizeQuantity);
		}
		sizeValueListForSet = [];
	}

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

function drawItemTable(dataList) {
	let length = dataList.length;
	sizeGroupId = "";
	let tables = "";
	let isClosingNeed = false;
	for (let i = 0; i < length; i++) {
		let item = dataList[i];
		console.log(item);
		if (sizeGroupId != item.sizeGroupId) {
			if (isClosingNeed) {
				tables += "</tbody></table> </div></div>";
			}
			sizeGroupId = item.sizeGroupId;
			tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">
				<tr>
				<th scope="col" class="min-width-150">Style</th>
				<th scope="col" class="min-width-150">Item Name</th>
				<th scope="col" class="min-width-150">Color</th>
				<th scope="col">Customer Order</th>
				<th scope="col">Purchase Order</th>
				<th scope="col">Shipping Mark</th>
				<th scope="col">Sizes Reg-Tall-N/A</th>`
			let sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
			for (let j = 0; j < sizeListLength; j++) {
				tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
			}
			tables += `<th scope="col">Total Units</th>
				<th scope="col">Unit CMT</th>
				<th scope="col">Total Price</th>
				<th scope="col">Unit FOB</th>
				<th scope="col">Total Price</th>
				<th scope="col"><i class="fa fa-edit"></i></th>
				<th scope="col"><i class="fa fa-trash"></i></th>
				</tr>
				</thead>
				<tbody id="dataList">`
			isClosingNeed = true;
		}
		tables += `<tr id='itemRow-${item.autoId}' class='dataRow notChanged' data-auto-id='${item.autoId}' data-purchase-order='${item.purchaseOrder}' data-style-id='${item.styleId}' data-item-id='${item.itemId}' data-customer-order='${item.customerOrder}' data-color-id='${item.colorId}' data-size-group-id='${item.sizeGroupId}'>
			<td>${item.style}</td>
			<td>${item.itemName}</td>
			<td>${item.colorName}</td>
			<td>${item.customerOrder}</td>
			<td>${item.purchaseOrder}</td>
			<td>${item.shippingMark}</td>
			<td>${item.sizeReg}</td>`
		let sizeList = item.sizeList;
		let sizeListLength = sizeList.length;

		for (let j = 0; j < sizeListLength; j++) {

			tables += "<td>" + sizeList[j].sizeQuantity + "</td>"
		}
		tables += `<td class='totalUnit' id='totalUnit${item.autoId}'>${item.totalUnit}</td>
			<td class='unitCmt' ><input id='unitCmt${item.autoId}' class='form-control-sm min-width-60 max-width-100' type='number' value='${item.unitCmt.toFixed(2)}' onkeyup="unitCmtFobTotalChange('${item.autoId}')"></td>
			<td class='totalPrice' id='totalPrice${item.autoId}'>${item.totalPrice.toFixed(2)}</td>
			<td class='unitFob'><input id='unitFob${item.autoId}' class='form-control-sm min-width-60 max-width-100' value="${item.unitFob.toFixed(2)}" onkeyup="unitCmtFobTotalChange('${item.autoId}')"></td>
			<td class='totalAmount' id='totalAmount${item.autoId}'>${item.totalAmount.toFixed(2)}</td>
			<td><i class='fa fa-edit' onclick="setBuyerPoItemDataForEdit('${item.autoId}')" style='cursor : pointer;'> </i></td>
			<td><i class='fa fa-trash' onclick="deleteBuyerPoItem('${item.autoId}')" style='cursor : pointer;'> </i></td></tr>`;

	}
	tables += "</tbody></table> </div></div>";

	document.getElementById("tableList").innerHTML = tables;
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
	var purchaseOrderId = $("#buyerPOId").val();
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

				xhr.open("POST", "save-product/" + purpose + "/" + user + "/" + buyerName + "/" + purchaseOrderId);
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



