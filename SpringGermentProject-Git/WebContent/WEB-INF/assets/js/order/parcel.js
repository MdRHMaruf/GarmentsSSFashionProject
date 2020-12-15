let itemId = 0;
let id;
let poNoValue = 0;
let styleValue = 0;
let colorValue = 0;


window.onload = () => {
	document.title = "Parcel";
}
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
	let options = "<option id='purchaseOrder' value='0' selected>Select Purchase Order</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option id='purchaseOrder' value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("purchaseOrder").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#purchaseOrder').val(poNoValue).change();
	poNoValue = 0;
}


function poWiseStyles() {

	let po = $("#purchaseOrder").val();

	if (po != 0) {

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './poWiseStyles/' + po,
			data: {},
			success: function (data) {
				loadStyles(data.result);
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

function loadStyles(data) {

	let itemList = data;
	let options = "<option id='itemType' value='0' selected>Select Style</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option id='itemType' value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#styleNo').val(styleValue).change();
	styleValue = 0;

}


function styleWiseItemLoad() {

	let styleId = $("#styleNo").val();

	if (styleId != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleWiseItem',
			data: {
				styleId: styleId
			},
			success: function (data) {

				let itemList = data.itemList;
				let options = "<option id='itemName' value='0' selected>Select Item Name</option>";
				let length = itemList.length;
				for (let i = 0; i < length; i++) {
					options += "<option id='itemName' value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
				};
				document.getElementById("itemName").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$("#itemName").prop("selectedIndex", 1).change();
				itemId = 0;
			}
		});
	} else {
		let options = "<option id='itemName' value='0' selected>Select Item Name</option>";
		$("#itemName").html(options);
		$('#itemName').selectpicker('refresh');
		$('#itemName').val(0).change();
	}

}


function styleItemsWiseColor() {
	let buyerOrderId = $("#purchaseOrder").val();
	let style = $("#styleNo").val();
	let item = $('#itemName').val();


	if (item != '0') {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './styleItemsWiseColor/',
			data: {
				buyerorderid: buyerOrderId,
				style: style,
				item: item
			},
			success: function (data) {
				loadItemsWiseColor(data.result);
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



function loadItemsWiseColor(data) {

	let itemList = data;
	let options = "<option id='colorName' value='0' selected>Select Color Type</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option id='colorName'  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("colorName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#colorName').val(colorValue).change();
	colorValue = 0;

}


function styleItemWiseColorSizeLoad() {
	let purchaseOrder = $("#purchaseOrder").val();
	let styleId = $("#styleNo").val();
	let itemId = $("#itemName").val();
	let colorName = $("#colorName").val();



	if (styleId != 0 && itemId != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './styleitemColorWiseSize',
			data: {
				buyerorderid: purchaseOrder,
				style: styleId,
				item: itemId,
				color: colorName
			},
			success: function (data) {

				let colorList = data.size;
				let options = "<option id='size' value='0' selected>Select Item Color</option>";
				let length = colorList.length;
				for (let i = 0; i < length; i++) {
					options += "<option id='size' value='" + colorList[i].id + "'>" + colorList[i].name + "</option>";
				};
				document.getElementById("size").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$('#size').val(size).change();
				itemColorIdForSet = 0;
			}
		});
	} else {
		let options = "<option id='size' value='0' selected>Select Item Color</option>";
		$("#size").html(options);
		$('#size').selectpicker('refresh');
		$('#size').val(0).change();
		itemColorIdForSet = 0;
	}
}



function itemAddAction() {

	const rowList = $("#dataList tr");
	const length = rowList.length;

	let buyerName = $("#buyerName option:selected").text();
	let buyerId = $("#buyerName").val();
	let purchaseOrder = $("#purchaseOrder option:selected").text();
	let purchaseOrderId = $("#purchaseOrder").val();
	let styleNo = $("#styleNo option:selected").text();
	let styleId = $("#styleNo").val();
	let itemName = $("#itemName option:selected").text();
	let itemId = $("#itemName").val();
	let colorName = $("#colorName option:selected").text();
	let colorId = $("#colorName").val();
	let sizeId = $("#size").val();
	let sizeName = $("#size option:selected").text();
	let sampleId = $("#sampleType").val();
	let sampleType = $("#sampleType option:selected").text();
	let quantity = $("#quantity").val() == "" ? 0 : $("#quantity").val();


	let sessionObject = JSON.parse(sessionStorage.getItem("pendingParcelItem") ? sessionStorage.getItem("pendingParcelItem") : "{}");
	let itemList = sessionObject.itemList ? sessionObject.itemList : [];

	if (buyerId != 0) {
		if (purchaseOrderId != 0) {
			if (styleId != 0) {
				if (itemId != 0) {
					if (colorId != 0) {
						if (sizeId != 0) {
							if (sampleId != 0) {
								if (quantity != 0) {


									const id = length;
									itemList.push({
										"autoId": id,
										"buyerId": buyerId,
										"buyerName": buyerName,
										"purchaseOrderId": purchaseOrderId,
										"purchaseOrder": purchaseOrder,
										"styleId": styleId,
										"styleNo": styleNo,
										"itemId": itemId,
										"itemName": itemName,
										"colorName": colorName,
										"colorId": colorId,
										"sizeName": sizeName,
										"sizeId": sizeId,
										"sampleType": sampleType,
										"sampleId": sampleId,
										"quantity": quantity
									});




									let row = `<tr id='row-${id}' class='newCosting' data-type='newCosting' data-buyer-id='${buyerId}' data-purchase-order-id='${purchaseOrderId}' data-style-id='${styleId}' data-item-id='${itemId}' data-color-id='${colorId}' data-size-id='${sizeId}' data-sample-id='${sampleId}'>
													<td id='styleNo-${id}'>${styleNo}</td>
													<td id='purchaseOrder-${id}'>${purchaseOrder}</td>
													<td id='color-${id}'>${colorName}</td>
													<td id='size-${id}'>${sizeName}</td>
													<td id='sampleType-${id}'>${sampleType}</td>
													<td id='quantity-${id}'>${quantity}</td>
													<td ><i class='fa fa-edit' onclick="costingItemSet('${id}','new')"></i></td>
													<td ><i class='fa fa-trash' onclick="deleteParcelItem('${id}','new','${styleId}','${itemId}')"></i></td>
												</tr>`;
									if (length > 1) $('#dataList tr:last').remove();
									$("#dataList").append(row);

									const tempList = $("#dataList tr");
									let totalQuantity = 0;
									$.each(tempList, (i, tr) => {
										let id = tr.id.slice(4);
										totalQuantity += parseFloat($("#quantity-" + id).text());
									});

									row = `<tr>
												<td colspan='5' class="text-right">Grand Total=</td>
												<td>${totalQuantity}</td>
												<td colspan='2'></td>
											</tr>`;
									$("#dataList").append(row);
									sessionObject = {
										"buyerId": buyerId,
										"itemList": itemList
									}

									sessionStorage.setItem("pendingParcelItem", JSON.stringify(sessionObject));

								} else {
									warningAlert("Empty Quantity ... Please Enter Quantity");
									$("#quantity").focus();
								}
							} else {
								warningAlert("Sample is not selected ... Please Select Sample Type");
								$("#sampleType").focus();
							}
						} else {
							warningAlert("Size Not Selected... Please Select Size");
							$("#size").focus();
						}
					} else {
						warningAlert("Color Name not selected... Please Select Color Name");
						$("#colorName").focus();
					}
				} else {
					warningAlert("Item Name not selected... Please Select Item Name");
					$("#itemName").focus();
				}
			} else {
				warningAlert("Style No not selected... Please Select Style No");
				$("#styleNo").focus();
			}
		} else {
			warningAlert("Purchase Order not selected... Please Select Purchase Order");
			$("#purchaseOrder").focus();
		}
	} else {
		warningAlert("Buyer not selected... Please Select Buyer Name");
		$("#buyerName").focus();
	}


}
function deleteParcelItem(autoId, rowType, styleId, itemId) {
	if (confirm("Are you sure to Delete this item?")) {
		if (rowType == 'new') {
			const buyerId = $("#row-" + autoId).attr("data-buyer-id");
			console.log(buyerId);
			const pendingParcelItem = JSON.parse(sessionStorage.getItem("pendingParcelItem"));
			const newItemList = pendingParcelItem.itemList.filter(item => item.buyerId != buyerId);
			pendingParcelItem.itemList = newItemList;
			sessionStorage.setItem("pendingParcelItem", JSON.stringify(pendingParcelItem));

			$("#row-" + autoId).remove();
		} else {
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: './deleteParcelItem',
				data: {
					autoId: autoId,
					styleId: styleId,
					itemId: itemId
				},
				success: function (data) {
					if (data.result == "Something Wrong") {
						dangerAlert("Something went wrong");
					} else if (data.result == "duplicate") {
						dangerAlert("Duplicate Item Name..This Item Name Already Exist")
					} else {

						var costingList = data.result;
						// if (costingList.size > 0) {
						//   itemIdForSet = costingList[0].itemId;
						//   $("#styleName").val(costingList[0].styleId).change();
						// }
						$("#dataList").empty();
						$("#dataList").append(drawDataTable(costingList));
						if (sessionStorage.getItem("pendingParcelItem")) {
							const pendingParcelItem = JSON.parse(sessionStorage.getItem("pendingParcelItem"));
							if (styleId == pendingParcelItem.styleId && itemId == pendingParcelItem.itemId) {
								$("#dataList").append(drawSessionDataTable(pendingParcelItem.itemList));
							}
						}
					}
				}
			});
		}
		$('#dataList tr:last').remove();
		const tempList = $("#dataList tr");
		let totalQuantity = 0;
		
		$.each(tempList, (i, tr) => {
			let id = tr.id.slice(4);
			totalQuantity += parseFloat($("#quantity-" + id).text());
		});
		if (tempList.length > 0) {
			row = `<tr>
					<td colspan='5' class="text-right">Grand Total=</td>
					<td>${totalQuantity}</td>
					<td colspan='2'></td>
				</tr>`;
			$("#dataList").append(row);
		}


	}

}

function parcelItemSet(autoId, itemType) {
	if (itemType == 'new') {
	  $("#itemAutoId").val(autoId);
	  $("#itemType").val("new");
	  const row = $("#row-" + autoId);
	  console.log(row);
	  particularItemIdForSet = row.attr('data-particular-id');
	  $("#particularType").val(row.attr('data-particular-type')).change();
	  $("#particularName").val(row.attr('data-particular-id')).change();
	  $("#unit").val(row.attr('data-unit-id')).change();
	  $("#commission").val(row.attr('data-commission'));
	  $("#width").val($("#width-" + autoId).text());
	  $("#yard").val($("#yard-" + autoId).text());
	  $("#gsm").val($("#gsm-" + autoId).text());
	  $("#consumption").val($("#consumption-" + autoId).text());
	  $("#unitPrice").val($("#unitPrice-" + autoId).text());
	} else {
	  $("#itemType").val("old");
	  $.ajax({
		type: 'GET',
		dataType: 'json',
		url: './searchCostingItem',
		data: {
		  autoId: autoId,
		},
		success: function (data) {
		  if (data.result == "Something Wrong") {
			dangerAlert("Something went wrong");
		  } else if (data.result == "duplicate") {
			dangerAlert("Duplicate Item Name..This Item Name Already Exist")
		  } else {
  
			var costing = data.result;
			$("#itemAutoId").val(costing.autoId);
			$("#itemType").val("old");
			itemIdForSet = costing.itemId;
			//$("#styleName").val(costing.styleId).change();
			particularItemIdForSet = costing.particularId;
			$("#particularType").val(costing.particularType).change();
			$("#particularName").val(costing.particularId).change();
			$("#unit").val(costing.unitId).change();
			$("#commission").val(costing.commission);
			var date = costing.date.split("/");
			$("#submissionDate").val(date[2] + "-" + date[1] + "-" + date[0]);
			$("#width").val(costing.width);
			$("#yard").val(costing.yard);
			$("#gsm").val(costing.gsm);
			$("#consumption").val(costing.consumption);
			$("#unitPrice").val(costing.unitPrice);
  
		  }
		}
	  });
	}
	$("#btnAdd").prop("disabled", true);
	$("#btnEdit").prop("disabled", false);
  }

function refreshAction() {
	location.reload();
}

function insertParcel() {

	let user = $("#userId").val();
	let styleNo = $("#styleNo").val();
	let itemName = $("#itemName").val();
	let sampleType = $("#sampleType").val();


	let dispatchedDate = new Date($("#dispatchedDate").val()).toLocaleDateString('fr-CA');


	let courierName = $("#courierName").val();
	let trackingNo = $("#trackingNo").val();
	let grossWeight = $("#grossWeight").val();
	let unit = $("#unit").val();
	let totalQty = $("#totalQty").val();
	let parcel = $("#parcel").val();
	let rate = $("#rate").val();
	let amount = $("#amount").val();
	let deiveryDate = new Date($("#deiveryDate").val()).toLocaleDateString('fr-CA');;
	let delieryTime = $("#delieryTime").val();
	let deliveryTo = $("#deliveryTo").val();




	if (styleNo != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './insertParcel',
			data: {
				user: user,
				styleNo: styleNo,
				itemName: itemName,
				sampleType: sampleType,
				dispatchedDate: dispatchedDate,
				courierName: courierName,
				trackingNo: trackingNo,
				grossWeight: grossWeight,
				unit: unit,
				totalQty: totalQty,
				parcel: parcel,
				rate: rate,
				amount: amount,
				deiveryDate: deiveryDate,
				delieryTime: delieryTime,
				deliveryTo: deliveryTo,
			},
			success: function (data) {
				if (data == 'success') {
					alert("Successfully Inserted")
					refreshAction();
				} else {
					alert("Parcel Insertion Failed")
				}
			}
		});
	} else {

		alert("Select Style");
	}

}



function getParcelDetails(id) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getParcelDetails/' + id,
		data: {

		},
		success: function (data) {
			setData(data)

		}
	});

}

function setData(data) {
	console.log(' item name ' + data[0].itemName)
	itemId = data[0].itemName;
	$("#styleNo").val(data[0].styleNo).change();
	id = data[0].id;

	$("#itemName").val(data[0].itemName).change();
	$("#sampleType").val(data[0].sampleType).change();
	$("#dispatchedDate").val(data[0].dispatchedDate)
	$("#courierName").val(data[0].courierName).change();
	$("#trackingNo").val(data[0].trackingNo);
	$("#grossWeight").val(data[0].grossWeight);
	$("#unit").val(data[0].unit);
	$("#totalQty").val(data[0].totalQty);
	$("#parcel").val(data[0].parcel);
	$("#rate").val(data[0].rate);
	$("#amount").val(data[0].amount);
	$("#deiveryDate").val(data[0].deiveryDate)
	$("#delieryTime").val(data[0].delieryTime);
	$("#deliveryTo").val(data[0].deliveryTo);
	$('#exampleModal').modal('hide');


	$("#save").attr('disabled', true);
	$("#edit").attr('disabled', false);

}


function editParcel() {

	let user = $("#userId").val();
	let styleNo = $("#styleNo").val();
	let itemName = $("#itemName").val();
	let sampleType = $("#sampleType").val();


	let dispatchedDate = new Date($("#dispatchedDate").val()).toLocaleDateString('fr-CA');


	let courierName = $("#courierName").val();
	let trackingNo = $("#trackingNo").val();
	let grossWeight = $("#grossWeight").val();
	let unit = $("#unit").val();
	let totalQty = $("#totalQty").val();
	let parcel = $("#parcel").val();
	let rate = $("#rate").val();
	let amount = $("#amount").val();
	let deiveryDate = new Date($("#deiveryDate").val()).toLocaleDateString('fr-CA');;
	let delieryTime = $("#delieryTime").val();
	let deliveryTo = $("#deliveryTo").val();




	if (styleNo != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './editParcel',
			data: {
				id: id,
				styleNo: styleNo,
				itemName: itemName,
				sampleType: sampleType,
				dispatchedDate: dispatchedDate,
				courierName: courierName,
				trackingNo: trackingNo,
				grossWeight: grossWeight,
				unit: unit,
				totalQty: totalQty,
				parcel: parcel,
				rate: rate,
				amount: amount,
				deiveryDate: deiveryDate,
				delieryTime: delieryTime,
				deliveryTo: deliveryTo,
			},
			success: function (data) {
				if (data == 'success') {
					alert("Successfully Updated")
					$("#save").attr('disabled', false);
					$("#edit").attr('disabled', true);
				} else {
					alert("Parcel Update Failed")
				}
			}
		});
	} else {

		alert("Select Style");
	}

}



function parcelReport(id) {

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './parcelRepor/' + id,
		data: {

		},
		success: function (data) {
			if (data == 'yes') {
				let url = "parcelReportView";
				window.open(url, '_blank');
			}
		}
	});
}



function amountCalculate() {

	let grossWeight = parseFloat($("#grossWeight").val() == '' ? "0" : $("#grossWeight").val());
	let rate = parseFloat($("#rate").val() == '' ? "0" : $("#rate").val());

	let totalAmount = parseFloat(grossWeight * rate);
	$("#amount").val(totalAmount);

}


function successAlert(message) {
	var element = $(".alert");
	element.hide();
	element = $(".alert-success");
	document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
	element.show();
	setTimeout(() => {
		element.toggle('fade');
	}, 2500);
}

function warningAlert(message) {
	var element = $(".alert");
	element.hide();
	element = $(".alert-warning");
	document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
	element.show();
	setTimeout(() => {
		element.toggle('fade');
	}, 2500);
}

function dangerAlert(message) {
	var element = $(".alert");
	element.hide();
	element = $(".alert-danger");
	document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
	element.show();
	setTimeout(() => {
		element.toggle('fade');
	}, 2500);
}
