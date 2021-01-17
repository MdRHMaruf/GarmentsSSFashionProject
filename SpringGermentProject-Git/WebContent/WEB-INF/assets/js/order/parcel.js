
let id;
let poId = 0;
let styleId = 0;
let itemId = 0;
let colorId = 0;
let sizeId = 0;
let sampleId = 0;



window.onload = () => {
	document.title = "Parcel";

	let sessionObject = JSON.parse(sessionStorage.getItem("pendingParcelItem") ? sessionStorage.getItem("pendingParcelItem") : false);
	let itemList = sessionObject.itemList ? sessionObject.itemList : [];
	if (sessionObject) {

		$("#buyerName").val(sessionObject.buyerId).change();
		drawSessionDataTable(sessionObject.itemList);

	}
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
	let options = "<option value='0'>Select Purchase Order</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("purchaseOrder").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#purchaseOrder').val(poId).change();
	poId = 0;
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
	let options = "<option id='itemType' value='0'>Select Style</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option id='itemType' value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	console.log("Sytel id= sdf",styleId);
	$('#styleNo').val(styleId).change();
	styleId = 0;

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
				let options = "<option id='itemName' value='0'>Select Item Name</option>";
				let length = itemList.length;
				for (let i = 0; i < length; i++) {
					options += "<option id='itemName' value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
				};
				document.getElementById("itemName").innerHTML = options;
				$('#itemName').selectpicker('refresh');
				$("#itemName").prop("selectedIndex", 1).change();
				itemId = 0;
			}
		});
	} else {
		let options = "<option id='itemName' value='0'>Select Item Name</option>";
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
	let options = "<option id='colorName' value='0'>Select Color Type</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option id='colorName'  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("colorName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#colorName').val(colorId).change();
	colorId = 0;

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
				let options = "<option id='size' value='0'>Select Item Color</option>";
				let length = colorList.length;
				for (let i = 0; i < length; i++) {
					options += "<option id='size' value='" + colorList[i].id + "'>" + colorList[i].name + "</option>";
				};
				document.getElementById("size").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$('#size').val(sizeId).change();
				sizeId = 0;
			}
		});
	} else {
		let options = "<option id='size' value='0' selected>Select Item Color</option>";
		$("#size").html(options);
		$('#size').selectpicker('refresh');
		$('#size').val(0).change();
		sizeId = 0;
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




									let row = `<tr id='row-${id}' class='newParcelItem' data-type='newParcelItem' data-buyer-id='${buyerId}' data-purchase-order-id='${purchaseOrderId}' data-style-id='${styleId}' data-item-id='${itemId}' data-color-id='${colorId}' data-size-id='${sizeId}' data-sample-id='${sampleId}'>
													<td id='styleNo-${id}'>${styleNo}</td>
													<td id='purchaseOrder-${id}'>${purchaseOrder}</td>
													<td id='color-${id}'>${colorName}</td>
													<td id='size-${id}'>${sizeName}</td>
													<td id='sampleType-${id}'>${sampleType}</td>
													<td id='quantity-${id}'>${quantity}</td>
													<td ><i class='fa fa-edit' onclick="parcelItemSet('${id}','new')"></i></td>
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

function itemEditAction() {

	let autoId = $("#parcelItemAutoId").val();
	let itemType = $("#itemType").val();
	let parcelId = $("#parcelId").val();
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
	let userId = $("#userId").val();

	if (buyerId != 0) {
		if (purchaseOrderId != 0) {
			if (styleId != 0) {
				if (itemId != 0) {
					if (colorId != 0) {
						if (sizeId != 0) {
							if (sampleId != 0) {
								if (quantity != 0) {
									if (itemType == 'new') {

										$("#row-" + autoId).attr("data-buyer-id", buyerId);
										$("#row-" + autoId).attr("data-purchase-order-id", purchaseOrderId);
										$("#row-" + autoId).attr("data-style-id", styleId);
										$("#row-" + autoId).attr("data-item-id", itemId);
										$("#row-" + autoId).attr("data-color-id", colorId);
										$("#row-" + autoId).attr("data-size-id", sizeId);
										$("#row-" + autoId).attr("data-sample-id", sampleId);
										$("#purchaseOrder-" + autoId).text(purchaseOrder);
										$("#styleNo-" + autoId).text(styleNo);
										$("#color-" + autoId).text(colorName);
										$("#size-" + autoId).text(sizeName);
										$("#sampleType-" + autoId).text(sampleType);
										$("#quantity-" + autoId).text(quantity);
										$("#btnAdd").prop("disabled", false);
										$("#btnEdit").prop("disabled", true);

										let sessionObject = JSON.parse(sessionStorage.getItem("pendingParcelItem") ? sessionStorage.getItem("pendingParcelItem") : "{}");
										let itemList = sessionObject.itemList ? sessionObject.itemList : [];

										for (let i = 0; i < itemList.length; i++) {
											if (itemList[i].autoId == autoId) {
												itemList[i] = {
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
												}
												sessionObject = {
													"buyerId": buyerId,
													"itemList": itemList
												}

												sessionStorage.setItem("pendingParcelItem", JSON.stringify(sessionObject));
												break;
											}
										}
									} else {
										$.ajax({
											type: 'GET',
											dataType: 'json',
											url: './editParcelItem',
											data: {
												autoId: autoId,
												parcelId : parcelId,
												buyerId: buyerId,
												purchaseOrderId: purchaseOrderId,
												purchaseOrder: purchaseOrder,
												styleId: styleId,
												colorId: colorId,
												sizeId: sizeId,
												sampleId: sampleId,
												quantity: quantity,
												userId: userId
											},
											success: function (data) {
												if (data.result == "Something Wrong") {
													dangerAlert("Something went wrong");
												} else if (data.result == "duplicate") {
													dangerAlert("Duplicate Item Name..This Item Name Already Exist")
												} else {
													$("#btnAdd").prop("disabled", false);
													$("#btnEdit").prop("disabled", true);
													$("#dataList").empty();
													drawItemDataTable(data.result);
													successAlert("Costing Item Edit Successfully");
													if (sessionStorage.getItem("pendingParcelItem")) {
														const pendingParcelItem = JSON.parse(sessionStorage.getItem("pendingParcelItem"));
														if (buyerId == pendingParcelItem.buyerId) {
															drawSessionDataTable(pendingParcelItem.itemList);
														}
													}
												}
											}
										});
									}
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
						drawItemDataTable(costingList);
						if (sessionStorage.getItem("pendingParcelItem")) {
							const pendingParcelItem = JSON.parse(sessionStorage.getItem("pendingParcelItem"));
							if (styleId == pendingParcelItem.styleId && itemId == pendingParcelItem.itemId) {
								drawSessionDataTable(pendingParcelItem.itemList);
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

	$("#parcelItemAutoId").val(autoId);
	$("#itemType").val(itemType);
	const row = $("#row-" + autoId);
	console.log(row);
	poId = row.attr('data-purchase-order-id');
	styleId = row.attr('data-style-id');
	colorId = row.attr('data-color-id');
	sizeId = row.attr('data-size-id');

	$("#sampleType").val(row.attr('data-sample-id')).change();
	$("#quantity").val($("#quantity-" + autoId).text());
	$("#buyerName").val(row.attr('data-buyer-id')).change();
	$("#btnAdd").prop("disabled", true);
	$("#btnEdit").prop("disabled", false);
}

function refreshAction() {
	sessionStorage.setItem("pendingParcelItem", false);
	location.reload();
}

function itemRefreshAction() {
	$("#purchaseOrder").val(0).change();
	$("#styleNo").val(0).change()
	$("#itemName").val(0).change()
	$("#colorName").val(0).change()
	$("#size").val(0).change()
	$("#sampleType").val(0).change()
	$("#quantity").val('');

	$("#btnAdd").prop("disabled", false);
	$("#btnEdit").prop("disabled", true);
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

	console.log(dispatchedDate);


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


$("#btnConfirm").click(() => {
	let rowList = $("#dataList tr");
	let length = rowList.length;
	let parcelId = $("#parcelId").val();

	if (length > 0) {
		rowList = $("tr.newParcelItem");
		length = rowList.length;

		let parcelItemList = '';

		let buyerId = $("#buyerName").val();
		let courierId = $("#courierName").val();
		let trackingNo = $("#trackingNo").val();
		let dispatchedDate = $("#dispatchedDate").val();
		dispatchedDate = dispatchedDate.slice(0, dispatchedDate.indexOf('T')) + " " + dispatchedDate.slice(dispatchedDate.indexOf('T') + 1) + ":00";
		console.log("date =", dispatchedDate);

		let deliveryBy = $("#deliveryBy").val();
		let deliveryTo = $("#deliveryTo").val();
		let mobileNo = $("#mobileNo").val();
		let unitId = $("#unit").val();
		//let deiveryDate = new Date($("#deiveryDate").val()).toLocaleDateString('fr-CA');;
		let grossWeight = $("#grossWeight").val() == '' ? '0' : $("#grossWeight").val();
		let rate = $("#rate").val() == '' ? '0' : $("#rate").val();
		let amount = $("#amount").val() == '' ? '0' : $("#amount").val();
		let remarks = $("#remarks").val();
		let userId = $("#userId").val();
		let parcelItems = {};
		parcelItems['list'] = [];

		if (buyerId != '0') {
			if (courierId != '0') {
				if (trackingNo != '') {
					if (dispatchedDate) {
						if (unitId != '0') {
							for (let i = 0; i < length; i++) {
								const newRow = rowList[i];
								const id = newRow.id.slice(4);

								const item = {
									buyerId: newRow.getAttribute('data-buyer-id'),
									styleId: newRow.getAttribute('data-style-id'),
									purchaseOrderId: newRow.getAttribute('data-purchase-order-id'),
									purchaseOrder: $("#purchaseOrder-" + id).text(),
									colorId: newRow.getAttribute('data-color-id'),
									sizeId: newRow.getAttribute('data-size-id'),
									sampleId: newRow.getAttribute('data-sample-id'),
									quantity: $("#quantity-" + id).text(),
									userId: userId
								}

								parcelItems.list.push(item);
							}
							//parcelItemList = parcelItemList.slice(0, -1);
							if (confirm("Are you sure to confirm..")) {
								if(parcelId == ""){
									$.ajax({
										type: 'POST',
										dataType: 'json',
										url: './confirmParcel',
										data: {
											buyerId: buyerId,
											courierId: courierId,
											trackingNo: trackingNo,
											dispatchedDate: dispatchedDate,
											deliveryBy: deliveryBy,
											deliveryTo: deliveryTo,
											mobileNo: mobileNo,
											parcelItems: JSON.stringify(parcelItems),
											unitId: unitId,
											grossWeight: grossWeight,
											rate: rate,
											amount: amount,
											remarks: remarks,
											userId: userId
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
								}else{
									$.ajax({
										type: 'POST',
										dataType: 'json',
										url: './editParcel',
										data: {
											parcelId : parcelId,
											buyerId: buyerId,
											courierId: courierId,
											trackingNo: trackingNo,
											dispatchedDate: dispatchedDate,
											deliveryBy: deliveryBy,
											deliveryTo: deliveryTo,
											mobileNo: mobileNo,
											parcelItems: JSON.stringify(parcelItems),
											unitId: unitId,
											grossWeight: grossWeight,
											rate: rate,
											amount: amount,
											remarks: remarks,
											userId: userId
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
								}
								

							}
						} else {
							warningAlert("Unit not selected... Please Select Unit");
							$("#unit").focus();
						}
					} else {
						warningAlert("Dispatched Date not selected... Please Select Dispatched Date");
						$("#dispatchedDate").focus();
					}
				} else {
					warningAlert("Tracking No is Empty... Please Enter Tracking No");
					$("#trackingNo").focus();
				}
			} else {
				warningAlert("Courier not selected... Please Select Courier Name");
				$("#courierName").focus();
			}
		} else {
			warningAlert("Buyer not selected... Please Select Buyer Name");
			$("#buyerName").focus();
		}

	} else {
		warningAlert("Please Enter Any Parcel Item Name...");
	}
});


function getParcelDetails(autoId) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getParcelDetails',
		data: {
			autoId: autoId
		},
		success: function (data) {
			setData(data.parcelInfo)
			$("#parcelId").val(autoId);
			$("#dataList").empty();
			drawItemDataTable(data.parcelItems , false);
			if (sessionStorage.getItem("pendingParcelItem")) {
				const pendingParcelItem = JSON.parse(sessionStorage.getItem("pendingParcelItem"));
				if ($("#buyerName").val() == pendingParcelItem.buyerId) {
					drawSessionDataTable(pendingParcelItem.itemList);
				}
			}
			$("#exampleModal").modal('hide');
		}
	});

}

function setData(data) {

	$("#buyerName").val(data.buyerId).change();
	$("#courierName").val(data.courierId).change();
	$("#trackingNo").val(data.trackingNo);
	let dispatchedDate = new Date(data.dispatchedDate);
	$("#dispatchedDate").val(dispatchedDate.getFullYear() + "-" + dispatchedDate.getMonth() + "-" + dispatchedDate.getDate() + "T" + ('0' + dispatchedDate.getHours()).slice(-2) + ":" + ('0' + dispatchedDate.getMinutes()).slice(-2));
	$("#deliveryBy").val(data.deliveryBy).change();
	$("#deliveryTo").val(data.deliveryTo);
	$("#mobileNo").val(data.mobileNo);
	$("#unit").val(data.unitId);
	$("#grossWeight").val(data.grossWeight);
	$("#rate").val(data.rate);
	$("#amount").val(data.amount);
	$("#remarks").val(data.remarks);

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
	let url = "parcelReportView/"+id;
	window.open(url, '_blank');
}



function amountCalculate() {

	let grossWeight = parseFloat($("#grossWeight").val() == '' ? "0" : $("#grossWeight").val());
	let rate = parseFloat($("#rate").val() == '' ? "0" : $("#rate").val());

	let totalAmount = parseFloat(grossWeight * rate);
	$("#amount").val(totalAmount);

}


function drawSessionDataTable(data) {
	let rows = "";
	const length = data.length;

	for (var i = 0; i < length; i++) {
		const rowData = data[i];
		const id = rowData.autoId;
		rows += `<tr id='row-${id}' class='newParcelItem' data-type='newParcelItem' data-buyer-id='${rowData.buyerId}' data-purchase-order-id='${rowData.purchaseOrderId}' data-style-id='${rowData.styleId}' data-item-id='${rowData.itemId}' data-color-id='${rowData.colorId}' data-size-id='${rowData.sizeId}' data-sample-id='${rowData.sampleId}'>
					<td id='styleNo-${id}'>${rowData.styleNo}</td>
					<td id='purchaseOrder-${id}'>${rowData.purchaseOrder}</td>
					<td id='color-${id}'>${rowData.colorName}</td>
					<td id='size-${id}'>${rowData.sizeName}</td>
					<td id='sampleType-${id}'>${rowData.sampleType}</td>
					<td id='quantity-${id}'>${rowData.quantity}</td>
					<td ><i class='fa fa-edit' onclick="parcelItemSet('${id}','new')"></i></td>
					<td ><i class='fa fa-trash' onclick="deleteParcelItem('${id}','new','${rowData.styleId}','${rowData.itemId}')"></i></td>
				</tr>`;
		//rows.push(drawRowDataTable(data[i], i));
	}

	$("#dataList").append(rows);
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
	if (tempList.length > 0)
		$("#dataList").append(row);
}

function drawItemDataTable(data , isTotalShow=true) {
	let rows = "";
	const length = data.length;

	for (var i = 0; i < length; i++) {
		const rowData = data[i];
		const id = rowData.autoId;
		rows += `<tr id='row-${id}' class='oldParcelItem' data-type='oldParcelItem' data-buyer-id='${rowData.buyerId}' data-purchase-order-id='${rowData.purchaseOrderId}' data-style-id='${rowData.styleId}' data-item-id='${rowData.itemId}' data-color-id='${rowData.colorId}' data-size-id='${rowData.sizeId}' data-sample-id='${rowData.sampleId}'>
					<td id='styleNo-${id}'>${rowData.styleNo}</td>
					<td id='purchaseOrder-${id}'>${rowData.purchaseOrder}</td>
					<td id='color-${id}'>${rowData.colorName}</td>
					<td id='size-${id}'>${rowData.sizeName}</td>
					<td id='sampleType-${id}'>${rowData.sampleType}</td>
					<td id='quantity-${id}'>${rowData.quantity}</td>
					<td ><i class='fa fa-edit' onclick="parcelItemSet('${id}','old')"></i></td>
					<td ><i class='fa fa-trash' onclick="deleteParcelItem('${id}','old')"></i></td>
				</tr>`;
		//rows.push(drawRowDataTable(data[i], i));
	}

	$("#dataList").append(rows);
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
	if (tempList.length > 0 && isTotalShow)
		$("#dataList").append(row);
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
