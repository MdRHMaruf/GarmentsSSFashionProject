let stylevalue = 0;
let itemvalue = 0;
let colorvalue = 0;
let sizevalue = 0;
let find = 0;


$('#btnSave').show();
$('#btnEdit').hide();

let unitList = {};

window.onload = () => {
	document.title = "Carton Indent";

	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getUnitList',
		data: {},
		success: function (data) {
			unitList = {};

			$("#unit").empty();
			$("#unit").append("<option value='0'>Select Unit</option>");
			data.unitList.forEach(unit => {
				unitList[unit.unitId] = unit;
				$("#unit").append(`<option value='${unit.unitId}'>${unit.unitName}</option>`);
			});

			$('#unit').selectpicker('refresh');
			$('#unit').val('0').change();
		}
	});

}



function btnInstallEvent() {
	let purchaseOrder = $("#purchaseOrder option:selected").text();
	let userId = $("#user_hidden").val();
	let styleId = $("#styleNo").val();
	let itemId = $("#itemName").val();
	let colorId = $("#colorName").val();
	let installAccessories = $("#sameAsAccessories").val();
	let forAccessories = $("#accessoriesItem").val();

	if (purchaseOrder != '') {
		if (styleId != '0') {
			if (itemId != '0') {
				if (colorId != '0') {
					if (installAccessories != '0') {
						if (forAccessories != '0') {
							$.ajax({
								type: 'POST',
								dataType: 'json',
								url: './InstallDataAsSameParticular',
								data: {
									userId: userId,
									purchaseOrder: purchaseOrder,
									styleId: styleId,
									itemId: itemId,
									colorId: colorId,
									installAccessories: installAccessories,
									forAccessories: forAccessories
								},
								success: function (data) {

									$("#dataList").empty();
									$("#dataList").append(AccessoriesDataShowInTable(data.result));

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
							alert("Provide Accessories Name");
						}
					}
					else {
						alert("Provide Accessories Name");
					}

				}
				else {
					alert("Provide Color Name");
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
		alert("Provide Purchase Order");
	}


}

function sizeReqCheck() {

	let checkvalue = $("#sizeReqCheck").is(':checked') ? 'checked' : 'unchecked';
	if (checkvalue == 'checked') {
		$('#size').prop('disabled', false);
		$('#size').selectpicker('refresh');
		styleitemColorWiseSize();
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


function styleitemColorWiseSize() {
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
	else {
		alert("Information Incomplete");
	}


}

function setOrder(data) {
	//conosle.log("order qty " + data[0].qty);
	let orderqty = parseFloat(data[0].qty);
	$("#orderQty").val(orderqty);

	let indozen = parseFloat((orderqty / 12));

	$("#qtyInDozen").val(indozen);

	$("#reqPerPcs").val(1);

	$("#reqPerDozen").val(indozen);

	$("#perUnit").val(1);
	$("#dividedBy").val(1);
	$("#totalBox").val(orderqty);

	let ReqQty = parseFloat($('#reqPerPcs').val() == '' ? "0" : $('#reqPerPcs').val()) * parseFloat($('#totalBox').val() == '' ? "0" : $('#totalBox').val());

	let extraQty = parseFloat($('#extraIn').val() == '' ? "0" : $('#extraIn').val());



	let extraValue = (ReqQty * extraQty) / 100

	$("#percentQty").val(extraValue);

	let totalQty = ReqQty + extraValue;
	$("#totalQty").val(totalQty);

	let unitValue = parseFloat($('#unit').val() == '' ? "0" : unitList[$('#unit').val()].unitValue);

	let unitQty = parseFloat((totalQty / unitValue));


	$("#unitQty").val(unitQty);

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
	$('#size').val(sizevalue).change();
	sizevalue = 0;

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
	let options = "<option  value='0' selected>Select Style</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	//conosle.log("style " + stylevalue);
	$('#styleNo').val(stylevalue).change();
	stylevalue = 0;

}



function styleWiseItems() {
	let buyerorderid = $("#purchaseOrder").val();
	let style = $("#styleNo").val();

	if (style != 0 && buyerorderid != '0') {

		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './stylewiseitems',
			data: {
				buyerorderid: buyerorderid,
				style: style
			},
			success: function (data) {
				loadItems(data.result);
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

function loadItems(data) {

	let itemList = data;
	let options = "<option  value='0' selected>Select Item Type</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option   value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("itemName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#itemName').val(itemvalue).change();
	itemvalue = 0;

}

function styleItemsWiseColor() {
	let buyerorderid = $("#purchaseOrder").val();
	let style = $("#styleNo").val();
	let item = $('#itemName').val();
	if (item != '0') {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './styleItemsWiseColor/',
			data: {
				buyerorderid: buyerorderid,
				style: style,
				item: item
			},
			success: function (data) {
				loatItemsWiseColor(data.result);
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


function loatItemsWiseColor(data) {

	let itemList = data;
	let options = "<option id='colorName' value='0' selected>Select Color Type</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option id='colorName'  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("colorName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#colorName').val(colorvalue).change();
	colorvalue = 0;

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
				loadShppingMarks(data);
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

function loadShppingMarks(data) {
	let itemList = data;
	let options = "<option  value='0' selected>Select Item Type</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option  value='" + i + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("shippingmark").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#shippingmark').val("0").change();
}

function sizeRequiredBoxaction() {
	let itemList = "";
	let options = "<option  value='0' selected>Select Item Type</option>";

	document.getElementById("size").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#size').val("0").change();
}

function itemWiseColor() {
	let style = $("#styleNo").val();
	let item = $("#itemName").val();

	//conosle.log("style " + style)
	if (style != 0) {

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './itemWiseColor/' + style + '/' + item,
			data: {},
			success: function (data) {

				LoadColors(data.color);
				//itemWiseSize();

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


//Accessories Curton 
function setTotalQtyForCurton() {


	let length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	let width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	let height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	let add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());

	let multiplication1 = length1 + width1 + height1 + add1;

	let length2 = parseFloat($('#length2').val() == '' ? "0" : $('#length2').val());
	let width2 = parseFloat($('#width2').val() == '' ? "0" : $('#width2').val());
	let height2 = parseFloat($('#height2').val() == '' ? "0" : $('#height2').val());
	let add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());

	let devideBy = parseFloat($('#devideBy').val() == '' ? "0" : $('#devideBy').val());

	let multiplication2 = length2 + width2 + height2 + add2;

	let totalqty = (multiplication1 * multiplication2 * 2) / devideBy;


	$('#qty').val(totalqty);


}

function editAccessoriesCurton() {
	let autoid = $("#accIndentId").val();
	let user = $("#user_hidden").val();
	let poNo = $("#purchaseOrder option:selected").text();
	let style = $("#styleNo").val();
	let item = $("#itemName").val();
	let itemColor = $("#colorName").val();
	let shippingMark = $("#shippingmark").val();
	let accessoriesItem = $("#accessoriesItem").val();
	let accessoriesSize = $("#accessoriesSize").val();
	let unit = $("#unit").val();

	let orderqty = parseFloat($('#orderQty').val() == '' ? "0" : $('#orderQty').val());
	let length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	let width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	let height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	let add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());

	let devideBy = parseFloat($('#devideBy').val() == '' ? "0" : $('#devideBy').val());

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
							url: './editAccessoriesCurton',
							data: {
								autoid: autoid,
								user: user,
								poNo: poNo,
								style: style,
								item: item,
								itemColor: itemColor,
								shippingMark: shippingMark,
								accessoriesItem: accessoriesItem,
								accessoriesSize: accessoriesSize,
								unit: unit,
								orderqty: orderqty,
								length1: length1,
								width1: width1,
								height1: height1,
								add1: add1,
								devideBy: devideBy,
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

function saveAccessoriesCurton() {

	let user = $("#user_hidden").val();
	let poNo = $("#purchaseOrder option:selected").text();
	let style = $("#styleNo").val();
	let item = $("#itemName").val();
	let itemColor = $("#colorName").val();
	let shippingMark = $("#shippingmark").val();
	let accessoriesItem = $("#accessoriesItem").val();
	let accessoriesSize = $("#accessoriesSize").val();
	let unit = $("#unit").val();
	let orderqty = parseFloat($('#orderQty').val() == '' ? "0" : $('#orderQty').val());
	let length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	let width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	let height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	let add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());
	let devideBy = parseFloat($('#devideBy').val() == '' ? "0" : $('#devideBy').val());
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
							url: './saveAccessoriesCurton',
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
								orderqty: orderqty,
								length1: length1,
								width1: width1,
								height1: height1,
								add1: add1,
								devideBy: devideBy,
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

function btnAllCartonIndent() {
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './getAllAccessoriesCartonData',
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

	$('#devideBy').val(itemList[0].devideBy);
	$('#qty').val(itemList[0].totalQty);
	$('#ply').val(itemList[0].ply);
	$('#cartonSize').val(itemList[0].accessoriesSize);




	stylevalue = itemList[0].style;
	itemvalue = itemList[0].item;
	colorvalue = itemList[0].itemColor;


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