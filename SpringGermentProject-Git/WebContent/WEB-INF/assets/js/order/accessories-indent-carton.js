let poIdForSet = 0;
let styleIdForSet = 0;
let itemIdForSet = 0;
let colorIdForSet = 0;
let sizeIdForSet = 0;
let shippingMarkForSet = "";
let find = 0;


$('#btnSave').show();
$('#btnEdit').hide();

let unitList = {};

window.onload = () => {
	document.title = "Carton Indent";
	setDivideByValue();
	setTotalQtyForCarton();
	let plyList = ["2 Ply", "3 Ply", "4 Ply", "5 Ply", "7 Ply"]
	$("#ply").autocomplete({
		source: plyList
	});

	let typeList = ["WELL CORRUGATED $ GUM PASTING",
		"Korean Paper Liner 2 Side 150 GSM Medium Fluid 125 GSM",
		"Virgin Paper 'Australia' 2 Side 205 GSM Medium Fluid 150 GSM",
		"Liner Paper 'Thiland' 2 Side 170 GSM Medium Fluid 125 GSM"]
	$("#type").autocomplete({
		source: typeList
	});
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
		if (poIdForSet == itemList[i].name) poIdForSet = itemList[i].id;
		options += "<option value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("purchaseOrder").innerHTML = options;
	$('#purchaseOrder').selectpicker('refresh');
	$('#purchaseOrder').val(poIdForSet).change();
	poIdForSet = 0;
}


function sizeReqCheck() {

	let checkValue = $("#sizeReqCheck").is(':checked') ? 'checked' : 'unchecked';
	if (checkValue == 'checked') {
		$('#size').prop('disabled', false);
		$('#size').selectpicker('refresh');
		styleItemColorWiseSize();
	}
	else {
		$('#size').prop('disabled', true);
		$('#size').selectpicker('refresh');
		loadOrderQty('None');
	}

}

function sizeWiseOrderQty() {


	let size = $("#size").val();

	if (size != '0' && find == 0) {
		loadOrderQty(size);
	}

}

function loadOrderQty(size) {
	let buyerorderid = $("#purchaseOrder").val();
	let color = $("#colorName").val();
	let style = $("#styleNo").val();
	let item = $("#itemName").val();


	if (style != 0 && buyerorderid != '0' || item != '0' || color != '0') {

		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './styleitemColorWiseOrderQty',
			data: {

				buyerorderid: buyerorderid,
				color: color,
				style: style,
				item: item,
				size: size
			},
			success: function (data) {

				setOrder(data.size)

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
	else {
		alert("Information Incomplete");
	}
}


function styleItemColorWiseSize() {
	let buyerorderid = $("#purchaseOrder").val();
	let color = $("#colorName").val();
	let style = $("#styleNo").val();
	let item = $("#itemName").val();

	if (style != 0 && buyerorderid != '0' || item != '0' || color != '0') {

		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './styleitemColorWiseSize',
			data: {
				buyerorderid: buyerorderid,
				color: color,
				style: style,
				item: item
			},
			success: function (data) {
				LoadSize(data.size);
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



function LoadSize(data) {

	let itemList = data;
	let options = "<option value='0' selected>Select Size</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("size").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#size').val(sizeIdForSet).change();
	sizeIdForSet = 0;

}


function poWiseStyles() {
	let po = $("#purchaseOrder").val();
	//conosle.log("po " + po)
	if (po != 0) {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './poWiseStyles/' + po,
			data: {
			},
			success: function (data) {
				//conosle.log("dt " + data.result)
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
	////conosle.log("dtt "+data[0].id);
	let itemList = data;
	console.log(data);
	let options = "<option  value='0' selected>Select Style</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	//conosle.log("style " + stylevalue);
	$('#styleNo').val(styleIdForSet).change();
	styleIdForSet = 0;

}



function styleWiseItems() {
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
/*
function loadItems(data) {

	let itemList = data;
	let options = "<option  value='0' selected>Select Item Type</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option   value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("itemName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#itemName').val(itemValue).change();
	itemValue = 0;

}
*/
function styleItemsWiseColor() {

	let poId = $("#purchaseOrder option:selected").text();
	let styleId = $("#styleNo").val();

	//conosle.log("style " + style)
	if (styleId != 0) {
		console.log(`'${poId}'`, `'${styleId}'`);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getColorAndShippingListByMultipleStyleId',
			data: {
				purchaseOrders: `'${poId}'`,
				styleIdList: `'${styleId}'`
			},
			success: function (data) {
				let options = "<option value='0'>Select Color</option>";
				let colorList = data.colorList;
				length = colorList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + colorList[i].colorId + "'>" + colorList[i].colorName + "</option>";
				};
				$("#colorName").html(options);
				$('#colorName').selectpicker('refresh');
				$('#colorName').val(colorIdForSet).change();

				options = "<option value='0'>Select Shipping Mark</option>";
				let shippingMarkList = data.shippingMarkList;
				length = shippingMarkList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + shippingMarkList[i] + "'>" + shippingMarkList[i] + "</option>";
				};
				$("#shippingMark").html(options);
				$('#shippingMark').selectpicker('refresh');
				$('#shippingMark').val(shippingMarkForSet).change();

			}
		});


	}
}


function shipping() {
	let checkvalue = $("#shippingCheck").is(':checked') ? 'checked' : 'unchecked';
	if (checkvalue == 'checked') {
		$("#shippingmark").attr('disabled', false);
	} else {
		$("#shippingmark").attr('disabled', true);
	}

	let po = $("#purchaseOrder").val();
	let style = $("#styleNo").val();
	let item = $("#itemName").val();

	//conosle.log("Po " + po + " style " + style + " item " + item)


	if (po != '' && style != '' && item != '') {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './shippingMark/' + po + '/' + style + '/' + item,
			data: {
			},
			success: function (data) {
				loadShippingMarks(data);
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

function loadShippingMarks(data) {
	let itemList = data;
	let options = "<option  value='0' selected>Select Item Type</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option  value='" + i + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("shippingMark").innerHTML = options;
	$('#shippingMark').selectpicker('refresh');
	$('#shippingMark').val("0").change();
}

function sizeRequiredBoxaction() {
	let itemList = "";
	let options = "<option  value='0' selected>Select Item Type</option>";

	document.getElementById("size").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#size').val("0").change();
}



function LoadColors(data) {

	//conosle.log(" colors ")

	let itemList = data;
	let options = "<option  value='0' selected>Select Item Color</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("itemcolor").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#itemcolor').val("0").change();


}


function SizeWiseQty() {


	let style = $("#styleNo").val();
	let item = $("#itemName").val();
	let size = $("#size").val();
	let color = 0;
	color = $("#colorName").val();

	let type = 1;

	if (color == 0) {
		type = 1;
	} else {
		type = 2;
	}


	//conosle.log("style " + style)
	if (style != 0) {

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './SizeWiseQty/' + style + '/' + item + '/' + size + '/' + color + '/' + type,
			data: {
			},
			success: function (data) {
				setOrder(data.size)
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


//Accessories Carton 

function setOrder(data) {
	//console.log("order qty " + data[0].qty);
	let orderQty = parseFloat(data[0].qty);
	$("#orderQty").val(orderQty);

	let pcsPerCarton = $("#pcsPerCarton").val();
	pcsPerCarton = pcsPerCarton == '' || pcsPerCarton <= 0 ? 1 : pcsPerCarton;

	let qty = orderQty / pcsPerCarton;
	$("#pcsPerCarton").val(pcsPerCarton);
	$("#qty").val(qty.toFixed(2));

}

function setQty() {
	let orderQty = $("#orderQty").val();
	orderQty = orderQty == '' || orderQty <= 0 ? 0 : orderQty;

	let pcsPerCarton = $("#pcsPerCarton").val();
	pcsPerCarton = pcsPerCarton == '' || pcsPerCarton <= 0 ? 1 : pcsPerCarton;

	let qty = orderQty / pcsPerCarton;
	$("#orderQty").val(orderQty);
	//$("#pcsPerCarton").val(pcsPerCarton);
	$("#qty").val(qty.toFixed(2));
}
function setDivideByValue() {
	let option = $("#unit option:selected");
	let divideBy = option.attr('data-divide-value');

	$("#divideBy").val(divideBy);
	setTotalQtyForCarton();
}

function setTotalQtyForCarton() {
	let length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	let width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	let height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	let add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());
	let add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());
	let divideBy = parseFloat($('#divideBy').val() == '' ? "0" : $('#divideBy').val());

	let cbm = ((length1 + width1 + add1) * (width1 + height1 + add2) * 2) / divideBy;

	$('#cbm').val(cbm.toFixed(2));


}

function editAccessoriesCarton() {

	let indentType = $("#indentType").val();
	let autoId = $("#indentAutoId").val();
	let buyerId = $("#buyerName").val();
	let buyerOrderId = $("#purchaseOrder").val();
	let purchaseOrder = $("#purchaseOrder option:selected").text();
	let styleId = $("#styleNo").val();
	let styleNo = $("#styleNo option:selected").text();
	let itemId = $("#itemName").val();
	let colorId = $("#colorName").val();
	let colorName = $("#colorName option:selected").text();
	let shippingMark = $("#shippingMark option:selected").val();
	let accessoriesItemId = $("#accessoriesItem").val();
	let accessoriesName = $("#accessoriesItem option:selected").text();
	let sizeId = 0;
	let size = '';
	let isCheck = $("#sizeReqCheck").is(':checked');
	if (isCheck) {
		sizeId = $("#size").val();
		size = $("#size option:selected").text();
	}
	let unitId = $("#unit").val();
	let unit = $("#unit option:selected").text();
	let ply = $('#ply').val();
	let type = $('#type').val();
	let orderQty = parseFloat($('#orderQty').val() == '' ? "0" : $('#orderQty').val());
	let length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	let width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	let height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	let add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());
	let add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());
	let divideBy = parseFloat($('#divideBy').val() == '' ? "0" : $('#divideBy').val());

	let cbm = parseFloat($('#cbm').val() == '' ? "0" : $('#cbm').val());
	let totalQty = parseFloat($('#qty').val() == '' ? "0" : $('#qty').val());
	let cartonSize = $('#cartonSize').val();
	let userId = $("#userId").val();

	if (buyerId != 0) {
		if (buyerOrderId != 0) {
			if (styleId != 0) {
				if (itemId != 0) {
					if (colorId != 0) {
						if (accessoriesItemId != 0) {
							if (totalQty != 0) {
								if (unitId != 0) {
									if (ply != '') {
										if (length1 != 0) {
											if (width1 != 0) {
												if (height1 != 0) {
													if (confirm("Are you Sure to Edit this Carton Indent")) {
														if (indentType == 'newIndent') {
															let row = $("#row-" + autoId);

															row.attr('data-buyer-id', buyerId);
															row.attr('data-style-id', styleId);
															row.attr('data-item-id', itemId);
															row.attr('data-color-id', colorId);
															row.attr('data-shipping-mark', shippingMark);
															row.attr('data-size-id', sizeId);
															row.attr('data-order-quantity', orderQty);
															row.attr('data-accessories-item-id', accessoriesItemId);
															row.attr('data-type', type);
															row.attr('data-carton-size', cartonSize);
															row.attr('data-add1', add1);
															row.attr('data-add2', add2);
															row.attr('data-unit-id', unitId);
															row.attr('data-divide-by', divideBy);
										
															$("#purchaseOrder-" + autoId).text(purchaseOrder);
															$("#styleNo-" + autoId).text(styleNo);
															$("#colorName-" + autoId).text(colorName);
															$("#accessoriesName-" + autoId).text(accessoriesName);
															$("#ply-" + autoId).text(ply);
															$("#length-" + autoId).text(length1);
															$("#width-" + autoId).text(width1);
															$("#height-" + autoId).text(height1);
															$("#unit-" + autoId).text(unit);
															$("#size-" + autoId).text(size);
															$("#cbm-" + autoId).text(cbm);
															$("#totalQty-" + autoId).text(totalQty);
										
															successAlert("Indent Edit Successfully");
															$("#btnAdd").show();
															$("#btnEdit").hide();
														}else{
															console.log(autoId,indentId,buyerId,purchaseOrder,styleId,itemId,colorId,shippingMark,accessoriesItemId,sizeId,cartonSize,orderQty,length1,width1,height1,add1,add2,ply,type,unitId,divideBy,cbm,totalQty,userId);
															$.ajax({
																type: 'POST',
																dataType: 'json',
																url: './editAccessoriesCarton',
																data: {
																  autoId: autoId,
																  buyerId : buyerId,
																  poNo: purchaseOrder,
																  styleId: styleId,
																  itemId: itemId,
																  itemColorId: colorId,
																  shippingMark: shippingMark,
																  accessoriesItemId: accessoriesItemId,
																  accessoriesSizeId: sizeId,
																  catronSize: cartonSize,
																  orderqty: orderQty,
																  length1: length1,
																  width1: width1,
																  height1: height1,
																  add1: add1,
																  add2: add2,
																  ply : ply,
																  type : type,
																  unitId: unitId,
																  devideBy: divideBy,
																  cbm: cbm,
																  totalQty: totalQty,
																  user: userId
																},
																success: function (data) {
																  if (data.result == "Something Wrong") {
																	dangerAlert("Something went wrong");
																  } else if (data.result == "duplicate") {
																	dangerAlert("Duplicate Item Name..This Item Name Already Exist")
																  } else {
										  
																	if (data.result != 'Something Wrong') {
										  
																		let row = $("#row-" + autoId);

																		row.attr('data-buyer-id', buyerId);
																		row.attr('data-style-id', styleId);
																		row.attr('data-item-id', itemId);
																		row.attr('data-color-id', colorId);
																		row.attr('data-shipping-mark', shippingMark);
																		row.attr('data-size-id', sizeId);
																		row.attr('data-order-quantity', orderQty);
																		row.attr('data-accessories-item-id', accessoriesItemId);
																		row.attr('data-type', type);
																		row.attr('data-carton-size', cartonSize);
																		row.attr('data-add1', add1);
																		row.attr('data-add2', add2);
																		row.attr('data-unit-id', unitId);
																		row.attr('data-divide-by', divideBy);
													
																		$("#purchaseOrder-" + autoId).text(purchaseOrder);
																		$("#styleNo-" + autoId).text(styleNo);
																		$("#colorName-" + autoId).text(colorName);
																		$("#accessoriesName-" + autoId).text(accessoriesName);
																		$("#ply-" + autoId).text(ply);
																		$("#length-" + autoId).text(length1);
																		$("#width-" + autoId).text(width1);
																		$("#height-" + autoId).text(height1);
																		$("#unit-" + autoId).text(unit);
																		$("#size-" + autoId).text(size);
																		$("#cbm-" + autoId).text(cbm);
																		$("#totalQty-" + autoId).text(totalQty);
													
																		successAlert("Indent Edit Successfully");
																		$("#btnAdd").show();
																		$("#btnEdit").hide();
																	} else {
																	  warningAlert("Something Wrong");
																	}
										  
																  }
																}
															  });
														}
													
													}
													
												} else {
													warningAlert("Please Enter Height Value....");
													$("#height1").focus();
												}
											} else {
												warningAlert("Please Enter Width Value....");
												$("#width1").focus();
											}
										} else {
											warningAlert("Please Enter Length Value....");
											$("#length1").focus();
										}
									} else {
										warningAlert("Please Enter Ply....");
										$("#ply").focus();
									}

								} else {
									warningAlert("Please Select Fabrics Unit....");
									$("#unit").focus();
								}

							} else {
								warningAlert("Quantity is empty ... Please Enter Quantity");
								$("#qty").focus();
							}
						} else {
							warningAlert("Fabrics item Not Selected... Please Select any Fabrics item");
							$("#fabricsItem").focus();
						}
					} else {
						warningAlert("Color not selected... Please Select Color Name");
						$("#itemColor").focus();
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
			warningAlert("Purchase Order... Please Select any purchase order");
			$("#purchaseOrder").focus();
		}

	} else {
		warningAlert("Buyer Name Not Selected... Please Select any Buyer Name");
		$("#buyerName").focus();
	}
}

function addCartonIndent() {
	let user = $("#user_hidden").val();
	let buyerId = $("#buyerName").val();
	let buyerOrderId = $("#purchaseOrder").val();
	let purchaseOrder = $("#purchaseOrder option:selected").text();
	let styleId = $("#styleNo").val();
	let styleNo = $("#styleNo option:selected").text();
	let itemId = $("#itemName").val();
	let colorId = $("#colorName").val();
	let colorName = $("#colorName option:selected").text();
	let shippingMark = $("#shippingMark option:selected").val();
	let accessoriesItemId = $("#accessoriesItem").val();
	let accessoriesName = $("#accessoriesItem option:selected").text();
	let sizeId = 0;
	let size = '';
	let isCheck = $("#sizeReqCheck").is(':checked');
	if (isCheck) {
		sizeId = $("#size").val();
		size = $("#size option:selected").text();
	}
	let unitId = $("#unit").val();
	let unit = $("#unit option:selected").text();
	let ply = $('#ply').val();
	let type = $('#type').val();
	let orderQty = parseFloat($('#orderQty').val() == '' ? "0" : $('#orderQty').val());
	let length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	let width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	let height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	let add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());
	let add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());
	let divideBy = parseFloat($('#divideBy').val() == '' ? "0" : $('#divideBy').val());

	let cbm = parseFloat($('#cbm').val() == '' ? "0" : $('#cbm').val());
	let totalQty = parseFloat($('#qty').val() == '' ? "0" : $('#qty').val());
	let cartonSize = $('#cartonSize').val();
	if (buyerId != 0) {
		if (buyerOrderId != 0) {
			if (styleId != 0) {
				if (itemId != 0) {
					if (colorId != 0) {
						if (accessoriesItemId != 0) {
							if (totalQty != 0) {
								if (unitId != 0) {
									if (ply != '') {
										if (length1 != 0) {
											if (width1 != 0) {
												
													let indentDataList = $("#dataList tr");
													let length = indentDataList.length;
													let listRowId = 0;
													if (length > 0) listRowId = indentDataList[length - 1].id.slice(4);

													let row = `<tr id='row-${++listRowId}' class='newIndentRow' data-item-type='newIndent' data-buyer-id='${buyerId}' data-style-id='${styleId}' data-item-id='${itemId}' data-color-id='${colorId}' 
													data-shipping-mark='${shippingMark}' data-size-id='${sizeId}' data-order-quantity='${orderQty}' data-accessories-item-id='${accessoriesItemId}' data-type='${type}' data-add1='${add1}' data-add2='${add2}'   data-unit-id='${unitId}' data-divide-by='${divideBy}'  data-carton-size='${cartonSize}' >
																<td id='purchaseOrder-${listRowId}'>${purchaseOrder}</td>
																<td id='styleNo-${listRowId}'>${styleNo}</td>
																<td id='colorName-${listRowId}'>${colorName}</td>
																<td id='accessoriesName-${listRowId}'>${accessoriesName}</td>
																<td id='ply-${listRowId}'>${ply}</td>
																<td id='length-${listRowId}'>${length1}</td>
																<td id='width-${listRowId}'>${width1}</td>
																<td id='height-${listRowId}'>${height1}</td>
																<td id='unit-${listRowId}'>${unit}</td>
																<td id='size-${listRowId}'>${size}</td>
																<td id='cbm-${listRowId}'>${cbm}</td>
																<td id='totalQty-${listRowId}'>${totalQty}</td>
																<td><i class='fa fa-edit' onclick="viewIndent('${listRowId}','newIndent')" style='cursor:pointer;'> </i></td>
																<td><i class='fa fa-trash' onclick="deleteIndent('${listRowId}','newIndent')" style='cursor:pointer;'> </i></td>
															</tr>`;

													$("#dataList").append(row);
												
											} else {
												warningAlert("Please Enter Width Value....");
												$("#width1").focus();
											}
										} else {
											warningAlert("Please Enter Length Value....");
											$("#length1").focus();
										}
									} else {
										warningAlert("Please Enter Ply....");
										$("#ply").focus();
									}

								} else {
									warningAlert("Please Select Fabrics Unit....");
									$("#unit").focus();
								}

							} else {
								warningAlert("Quantity is empty ... Please Enter Quantity");
								$("#qty").focus();
							}
						} else {
							warningAlert("Fabrics item Not Selected... Please Select any Fabrics item");
							$("#fabricsItem").focus();
						}
					} else {
						warningAlert("Color not selected... Please Select Color Name");
						$("#itemColor").focus();
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
			warningAlert("Purchase Order... Please Select any purchase order");
			$("#purchaseOrder").focus();
		}

	} else {
		warningAlert("Buyer Name Not Selected... Please Select any Buyer Name");
		$("#buyerName").focus();
	}

}

function fieldRefresh(){
	
	$("#buyerName").val(0);
	$("#purchaseOrder").val(0).change();
	$("#styleNo").val(0).change();
	$("#itemName").val(0).change();
	$("#colorName").val(0).change();
	$("#shippingMark").val(0).change();
	$("#accessoriesItem").val(0).change();
	
	$("#sizeReqCheck").prop('checked',false);
	
	$("#unit").val(0);
	$('#ply').val("");
	$('#type').val("");
	$('#orderQty').val("");
	$('#length1').val("");
	$('#width1').val("");
	$('#height1').val("");
	$('#add1').val("");
	$('#add2').val("");
	$('#divideBy').val("");

	$('#cbm').val("");
	$('#qty').val("");
	$('#cartonSize').val("");
	$("#btnAdd").show();
	$("#btnEdit").hide();
}

function viewIndent(autoId, indentType) {

	$("#indentType").val(indentType);
	let row = $("#row-" + autoId);

	$("#indentAutoId").val(autoId);

	poIdForSet = $("#purchaseOrder-" + autoId).text();
	styleIdForSet = row.attr('data-style-id');
	itemIdForSet = row.attr('data-item-id');
	colorIdForSet = row.attr('data-color-id');
	sizeIdForSet = row.attr('data-size-id');
	shippingMarkForSet = row.attr('data-shipping-mark');
	$("#orderQty").val(parseFloat(row.attr('data-order-quantity')).toFixed(2));
	$("#accessoriesItem").val(row.attr('data-accessories-item-id')).change();
	$("#ply").val($("#ply-" + autoId).text());
	$("#type").val(row.attr('data-type'));
	$("#length1").val($("#length-" + autoId).text());
	$("#width1").val($("#width-" + autoId).text());
	$("#height1").val($("#height-" + autoId).text());
	$("#add1").val(parseFloat(row.attr('data-add1')).toFixed(2));
	$("#add2").val(parseFloat(row.attr('data-add2')).toFixed(2));
	$("#unit").val(row.attr('data-unit-id'));
	$("#divideBy").val(parseFloat(row.attr('data-divide-by')).toFixed(2));
	$("#cbm").val($("#cbm-" + autoId).text());
	$("#qty").val($("#totalQty-" + autoId).text());
	$("#cartonSize").val(row.attr('data-carton-size'));
	$("#buyerName").val(row.attr('data-buyer-id')).change();
	$("#btnAdd").hide();
	$("#btnEdit").show();
}
function deleteIndent(autoId, indentType) {
	let indentId = $("#indentId").val();

	if (confirm("Are You Sure To Delete this Carton Indent?")) {
		if (indentType == 'newIndent') {
			$("#row-" + autoId).remove();
		} else {
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: './deleteCartonIndent',
				data: {
					autoId: autoId,
					indentId: indentId
				},
				success: function (data) {
					if (data.result) {
						successAlert("Delete Successfully... ")
						$("#row-" + autoId).remove();
					} else {
						warningAlert("Something Wrong..");
					}
				}
			});
		}
	}

}


function confirmAction() {


	let userId = $("#userId").val();
	let cartonIndentId = $("#indentId").val();

	let rowList = $("#dataList tr");
	let length = rowList.length;


	if (length > 0) {
		if (confirm("Are you Confirm to Save This Accessories Indent?")) {
			newIndentList = $("tr.newIndentRow");

			let cartonItems = {};
			cartonItems['list'] = [];

			newIndentList.each((index, indentRow) => {
				let id = indentRow.id.slice(4);

				const indent = {
					buyerId: indentRow.getAttribute('data-buyer-id'),
					purchaseOrder: $("#purchaseOrder-" + id).text(),
					styleId: indentRow.getAttribute('data-style-id'),
					itemId: indentRow.getAttribute('data-item-id'),
					colorId: indentRow.getAttribute('data-color-id'),
					shippingMark: indentRow.getAttribute('data-shipping-mark'),
					sizeId: indentRow.getAttribute('data-size-id'),
					orderQty: indentRow.getAttribute('data-order-quantity'),
					accessoriesItemId: indentRow.getAttribute('data-accessories-item-id'),
					type: indentRow.getAttribute('data-type'),
					cartonSize: indentRow.getAttribute('data-carton-size'),
					ply: $("#ply-" + id).text(),
					length: $("#length-" + id).text(),
					width: $("#width-" + id).text(),
					height: $("#height-" + id).text(),
					add1: indentRow.getAttribute('data-add1'),
					add2: indentRow.getAttribute('data-add2'),
					unitId: indentRow.getAttribute('data-unit-id'),
					divideBy: indentRow.getAttribute('data-divide-by'),
					cbm: $("#cbm-" + id).text(),
					totalQty: $("#totalQty-" + id).text(),
					userId: userId
				}

				cartonItems.list.push(indent);
			})

			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: './confirmCartonIndent',
				data: {
					cartonIndentId: cartonIndentId,
					cartonItems: JSON.stringify(cartonItems),
				},
				success: function (data) {
					if (data.result != 'something wrong') {
						$("#cartonIndentId").text(data.result);
						$("#indentId").val(data.result);
						alert("Carton Indent Save Successfully;")
					} else {
						alert("Incomplete...Something Wrong");
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
	} else {
		warningAlert("You have not added any Carton... Please Add any Carton");
	}

}

function refreshAction(){
	location.reload();
}

function cartonIndentReport() {
	let indentId = $("#indentId").val();
	if (indentId != 'New') {
	  let url = "cartonIndentReportView/" + indentId;
	  window.open(url, '_blank');
	}
  }

function searchIndent(indentId) {

	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './searchCartonIndent',
		data: {
			indentId: indentId
		},
		success: function (data) {
			$("#dataList").empty();
			let row = '';
			data.cartonIndentList.forEach((indent) => {

				console.log(indent);
				let id = indent.autoId;
				row += `<tr id='row-${id}' class='oldIndentRow' data-item-type='oldIndent' data-buyer-id='${indent.buyerId}' data-style-id='${indent.styleId}' data-item-id='${indent.itemId}' data-color-id='${indent.itemColorId}' 
		  data-shipping-mark='${indent.shippingMark}' data-size-id='${indent.accessoriesSizeId}' data-order-quantity='${indent.orderqty}' data-accessories-item-id='${indent.accessoriesItemId}' data-type='${indent.type}' data-add1='${indent.add1}' data-add2='${indent.add2}'   data-unit-id='${indent.unitId}' data-divide-by='${indent.devideBy}'  data-carton-size='${indent.catronSize}' >
					  <td id='purchaseOrder-${id}'>${indent.poNo}</td>
					  <td id='styleNo-${id}'>${indent.style}</td>
					  <td id='colorName-${id}'>${indent.itemColor}</td>
					  <td id='accessoriesName-${id}'>${indent.accessoriesItem}</td>
					  <td id='ply-${id}'>${indent.ply}</td>
					  <td id='length-${id}'>${parseFloat(indent.length1).toFixed(2)}</td>
					  <td id='width-${id}'>${parseFloat(indent.width1).toFixed(2)}</td>
					  <td id='height-${id}'>${parseFloat(indent.height1).toFixed(2)}</td>
					  <td id='unit-${id}'>${indent.unit}</td>
					  <td id='size-${id}'>${indent.accessoriesSize}</td>
					  <td id='cbm-${id}'>${parseFloat(indent.cbm).toFixed(2)}</td>
					  <td id='totalQty-${id}'>${parseFloat(indent.totalQty).toFixed(2)}</td>
					  <td><i class='fa fa-edit' onclick="viewIndent('${id}','oldIndent')" style='cursor:pointer;'> </i></td>
					  <td><i class='fa fa-trash' onclick="deleteIndent('${id}','oldIndent')" style='cursor:pointer;'> </i></td>
				  </tr>`;
			});
			$("#dataList").append(row);
			$("#cartonIndentId").text(data.cartonIndentList[0].indentId);
			$("#indentId").val(data.cartonIndentList[0].indentId);
			$("#exampleModal").modal('hide');
		}
	});
}

function saveAccessoriesCarton() {

	let user = $("#user_hidden").val();
	let poNo = $("#purchaseOrder option:selected").text();
	let style = $("#styleNo").val();
	let item = $("#itemName").val();
	let itemColor = $("#colorName").val();
	let shippingMark = $("#shippingmark").val();
	let accessoriesItem = $("#accessoriesItem").val();
	let accessoriesSize = $("#accessoriesSize").val();
	let unit = $("#unit").val();
	let orderQty = parseFloat($('#orderQty').val() == '' ? "0" : $('#orderQty').val());
	let length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	let width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	let height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	let add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());
	let divideBy = parseFloat($('#divideBy').val() == '' ? "0" : $('#divideBy').val());
	let length2 = parseFloat($('#length2').val() == '' ? "0" : $('#length2').val());
	let width2 = parseFloat($('#width2').val() == '' ? "0" : $('#width2').val());
	let height2 = parseFloat($('#height2').val() == '' ? "0" : $('#height2').val());
	let add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());
	let ply = parseFloat($('#ply').val() == '' ? "0" : $('#ply').val());

	let totalQty = parseFloat($('#qty').val() == '' ? "0" : $('#qty').val());

	if (poNo != '0') {
		if (style != '0') {
			if (item != '0') {
				if (itemColor != '0') {
					if (accessoriesItem != '0') {
						$.ajax({
							type: 'POST',
							dataType: 'json',
							url: './saveAccessoriesCarton',
							data: {
								user: user,
								poNo: poNo,
								style: style,
								item: item,
								itemColor: itemColor,
								shippingMark: shippingMark,
								accessoriesItem: accessoriesItem,
								accessoriesSize: accessoriesSize,
								unit: unit,
								orderqty: orderQty,
								length1: length1,
								width1: width1,
								height1: height1,
								add1: add1,
								divideBy: divideBy,
								length2: length2,
								width2: width2,
								height2: height2,
								add2: add2,
								totalQty: totalQty,
								ply: ply
							},
							success: function (data) {
								//alert(data);
								$("#dataList").empty();
								$("#dataList").append(AccessoriesCartonDataShowInTable(data.result));
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
					else {
						alert("Provide Accessories Item");
					}
				}
				else {
					alert("Provide Item Color");
				}
			}
			else {
				alert("Provide Item Name");
			}
		}
		else {
			alert("Provide Style No");
		}
	}
	else {
		alert("Provide Purchase Order No");
	}
}


function AccessoriesCartonDataShowInTable(data) {
	let rows = [];
	let length = data.length;

	for (let i = 0; i < length; i++) {
		rows.push(drawRowDataCartonTable(data[i], i + 1));
	}

	return rows;
}

function drawRowDataCartonTable(rowData, c) {

	let row = $("<tr />")
	row.append($("<td>" + c + "</td>"));
	row.append($("<td>" + rowData.poNo + "</td>"));
	row.append($("<td>" + rowData.style + "</td>"));
	row.append($("<td>" + rowData.itemName + "</td>"));
	row.append($("<td>" + rowData.itemColor + "</td>"));
	row.append($("<td>" + rowData.shippingMark + "</td>"));
	row.append($("<td>" + rowData.accessoriesName + "</td>"));
	row.append($("<td>" + rowData.cartonSize + "</td>"));
	row.append($("<td>" + rowData.totalQty + "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=\"accessoriesCartonItemSet(" + rowData.autoId + ")\"> </i></td>"));
	return row;
}



function accessoriesCartonItemSet(id) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './accessoriesCartonItemSet/' + id,
		success: function (data) {
			setAccessoriesItemCartonDetails(data.result);
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


function setAccessoriesItemCartonDetails(data) {
	let itemList = data;

	$('#accIndentId').val(itemList[0].autoid);
	$('#orderQty').val(itemList[0].orderqty);
	$('#length1').val(itemList[0].length1);
	$('#width1').val(itemList[0].width1);
	$('#height1').val(itemList[0].height1);
	$('#add1').val(itemList[0].add1);

	$('#length2').val(itemList[0].length2);
	$('#width2').val(itemList[0].width2);
	$('#height2').val(itemList[0].height2);
	$('#add2').val(itemList[0].add2);

	$('#divideBy').val(itemList[0].divideBy);
	$('#qty').val(itemList[0].totalQty);
	$('#ply').val(itemList[0].ply);
	$('#cartonSize').val(itemList[0].accessoriesSize);




	styleIdForSet = itemList[0].style;
	itemIdForSet = itemList[0].item;
	colorIdForSet = itemList[0].itemColor;


	$('#purchaseOrder option').map(function () {
		if ($(this).text() == itemList[0].poNo) return this;
	}).attr('selected', 'selected').change();


	$('#shippingCheck').val(itemList[0].shippingMark);
	//$('#colorName').val(itemList[0].poitemcolor);
	$('#accessoriesItem').val(itemList[0].accessoriesItem);
	$('#cartonSize').val(itemList[0].accessoriesSize);

	//

	$('#sizeReqCheck').prop("checked", true);


	find = 1;

	$('#btnSave').prop('disabled', true);
	$('#btnEdit').prop('disabled', false);
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