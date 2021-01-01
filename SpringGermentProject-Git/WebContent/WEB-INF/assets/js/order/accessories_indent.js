var stylevalue = 0;
var itemvalue = 0;
var colorvalue = 0;
var sizevalue = 0;
var find = 0;

$("#aiNo").attr('disabled', true);
$("#shippingmark").attr('disabled', true);

$('#size').prop('disabled', true);
$('#btnSave').prop('disabled', false);
$('#btnEdit').prop('disabled', true);

let unitList = {};

window.onload = () => {
	document.title = "Accessories Indent";

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

	AINO();
}


function buyerWiseStyleLoad() {
	var buyerId = $("#buyerName").val();

	// alert("buyerId "+buyerId);
	if (buyerId != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getBuyerWiseStylesItem',
			data: {
				buyerId: buyerId
			},
			success: function (data) {

				var styleList = data.styleList;
				var options = "<option  value='0' selected>Select Style</option>";
				var length = styleList.length;
				for (var i = 0; i < length; i++) {
					options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
				};
				$("#styleNo").html(options);
				$('.selectpicker').selectpicker('refresh');
				$('#styleNo').val(styleIdForSet).change();
				styleIdForSet = 0;

			}
		});
	} else {
		var options = "<option value='0' selected>Select Style</option>";
		$("#styleNo").html(options);
		$('#styleNo').selectpicker('refresh');
		$('#styleNo').val("0").change();
	}

}
$('#buyerName').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
	if ($("#buyerName").val().length > 0) {
		let buyerIdList = '';
		$("#buyerName").val().forEach(id => {
			buyerIdList += `'${id}',`;
		});
		buyerIdList = buyerIdList.slice(0, -1);
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getPurchaseOrderAndStyleListByMultipleBuyers',
			data: {
				buyersId: buyerIdList
			},
			success: function (data) {
				let options = "";
				let buyerPoList = data.buyerPOList;
				let length = buyerPoList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + buyerPoList[i].name + "'>" + buyerPoList[i].name + "</option>";
				};

				$("#purchaseOrder").html(options);
				$('#purchaseOrder').selectpicker('refresh');

				options = "";
				let styleList = data.styleList;

				length = styleList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
				};
				$("#styleNo").html(options);
				$('#styleNo').selectpicker('refresh');

			}
		});
	} else {
		$("#purchaseOrder").empty();
		$("#purchaseOrder").selectpicker('refresh');
		$("#styleNo").empty();
		$("#styleNo").selectpicker('refresh');
	}
});

$('#purchaseOrder').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
	if ($("#purchaseOrder").val().length > 0) {
		let poList = '';
		$("#purchaseOrder").val().forEach(po => {
			poList += `'${po}',`;
		});
		poList = poList.slice(0, -1);
		let selectedStyleId = $("#styleNo").val();
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleListByMultiplePurchaseOrder',
			data: {
				purchaseOrders: poList
			},
			success: function (data) {
				let options = "";

				let styleList = data.styleList;

				length = styleList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + styleList[i].styleId + "'>" + styleList[i].styleNo + "</option>";
				};
				$("#styleNo").html(options);
				$('#styleNo').selectpicker('refresh');
				$("#styleNo").selectpicker('val', selectedStyleId).change();

			}
		});
	}
});

$('#styleNo').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
	if ($("#styleNo").val().length > 0) {
		let styleIdList = '';
		$("#styleNo").val().forEach(id => {
			styleIdList += `'${id}',`;
		});
		styleIdList = styleIdList.slice(0, -1);

		if ($("#purchaseOrder").val().length > 0) {

			let poList = '';
			$("#purchaseOrder").val().forEach(id => {
				poList += `'${id}',`;
			});
			poList = poList.slice(0, -1);
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: './getColorAndShippingListByMultipleStyleId',
				data: {
					purchaseOrders : poList,
					styleIdList : styleIdList
				},
				success: function (data) {
					let options = "";
					let colorList = data.colorList;
					length = colorList.length;
					for (let i = 0; i < length; i++) {
						options += "<option value='" + colorList[i].colorId + "'>" + colorList[i].colorName + "</option>";
					};
					$("#color").html(options);
					$('#color').selectpicker('refresh');

					options = "";
					let shippingMarkList = data.shippingMarkList;
					length = shippingMarkList.length;
					for (let i = 0; i < length; i++) {
						options += "<option value='" + i + "'>" + shippingMarkList[i] + "</option>";
					};
					$("#shippingMark").html(options);
					$('#shippingMark').selectpicker('refresh');

				}
			});
		} else {
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: './getPurchaseOrderByMultipleStyleId',
				data: {
					styleIdList: styleIdList
				},
				success: function (data) {
					let options = "";
					let buyerPoList = data.buyerPOList;
					let length = buyerPoList.length;
					for (let i = 0; i < length; i++) {
						options += "<option value='" + buyerPoList[i].name + "'>" + buyerPoList[i].name + "</option>";
					};

					$("#purchaseOrder").html(options);
					$('#purchaseOrder').selectpicker('refresh');

				}
			});
		}

		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getItemListByMultipleStyleId',
			data: {
				styleIdList: styleIdList
			},
			success: function (data) {
				let options = "";
				let itemList = data.itemList;
				let length = itemList.length;
				for (let i = 0; i < length; i++) {
					options += "<option value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
				};

				$("#itemName").html(options);
				$("#itemName").selectpicker('refresh');
				$('#itemName').selectpicker('selectAll');

			}
		});
	}
});

function refreshBuyerNameList() {
	$("#buyerName").selectpicker('deselectAll');
}
function refreshPurchaseOrderList() {
	$("#purchaseOrder").selectpicker('deselectAll');
}
function refreshStyleNoList() {
	$("#styleNo").selectpicker('deselectAll');
}
function refreshItemNameList() {
	$("#itemName").selectpicker('deselectAll');
}
function refreshColorList() {
	$("#color").selectpicker('deselectAll');
}
function refreshShippingMarkList() {
	$("#shippingMark").selectpicker('deselectAll');
}

function searchAccessoriesIndent(aiNo) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './accessoriesIndentInfo',
		data: {
			aiNo: aiNo
		},
		success: function (data) {
			if (data == "Success") {
				var url = "printAccessoriesIndent";
				window.open(url, '_blank');

			}
		}
	});

}

function btnInstallEvent() {
	var purchaseOrder = $("#purchaseOrder option:selected").text();
	var userId = $("#user_hidden").val();
	var styleId = $("#styleNo").val();
	var itemId = $("#itemName").val();
	var colorId = $("#colorName").val();
	var installAccessories = $("#sameAsAccessories").val();
	var forAccessories = $("#accessoriesItem").val();

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

	var checkvalue = $("#sizeReqCheck").is(':checked') ? 'checked' : 'unchecked';
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


	var size = $("#size").val();

	if (size != '0' && find == 0) {
		loadOrderQty(size);
	}

}

function loadOrderQty(size) {
	var buyerorderid = $("#purchaseOrder").val();
	var color = $("#colorName").val();
	var style = $("#styleNo").val();
	var item = $("#itemName").val();
	var size = size;

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


	var buyerorderid = $("#purchaseOrder").val();
	var color = $("#colorName").val();
	var style = $("#styleNo").val();
	var item = $("#itemName").val();

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
	console.log("order qty " + data[0].qty);
	var orderqty = parseFloat(data[0].qty);
	$("#orderQty").val(orderqty);

	var indozen = parseFloat((orderqty / 12));

	$("#qtyInDozen").val(indozen);

	$("#reqPerPcs").val(1);

	$("#reqPerDozen").val(indozen);

	$("#perUnit").val(1);
	$("#dividedBy").val(1);
	$("#totalBox").val(orderqty);

	var ReqQty = parseFloat($('#reqPerPcs').val() == '' ? "0" : $('#reqPerPcs').val()) * parseFloat($('#totalBox').val() == '' ? "0" : $('#totalBox').val());

	var extraQty = parseFloat($('#extraIn').val() == '' ? "0" : $('#extraIn').val());



	var extraValue = (ReqQty * extraQty) / 100

	$("#percentQty").val(extraValue);

	var totalQty = ReqQty + extraValue;
	$("#totalQty").val(totalQty);

	var unitValue = parseFloat($('#unit').val() == '' ? "0" : unitList[$('#unit').val()].unitValue);

	var grandQty = parseFloat((totalQty / unitValue));


	$("#grandQty").val(grandQty);

}


function LoadSize(data) {

	var itemList = data;
	var options = "<option value='0' selected>Select Size</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("size").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#size').val(sizevalue).change();
	sizevalue = 0;

}

function AINO() {

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './maxAIno',
		data: {




		},
		success: function (data) {
			$("#aiNo").val(data);


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



function poWiseStyles() {


	var po = $("#purchaseOrder").val();

	console.log("po " + po)
	if (po != 0) {

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './poWiseStyles/' + po,
			data: {

			},
			success: function (data) {
				console.log("dt " + data.result)
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
	//console.log("dtt "+data[0].id);
	var itemList = data;
	var options = "<option  value='0' selected>Select Style</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	console.log("style " + stylevalue);
	$('#styleNo').val(stylevalue).change();
	stylevalue = 0;

}



function styleWiseItems() {


	var buyerorderid = $("#purchaseOrder").val();
	var style = $("#styleNo").val();


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

				loatItems(data.result);


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


function loatItems(data) {

	var itemList = data;
	var options = "<option  value='0' selected>Select Item Type</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option   value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("itemName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#itemName').val(itemvalue).change();
	itemvalue = 0;

}

function styleItemsWiseColor() {
	var buyerorderid = $("#purchaseOrder").val();
	var style = $("#styleNo").val();
	var item = $('#itemName').val();
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

	var itemList = data;
	var options = "<option id='colorName' value='0' selected>Select Color Type</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option id='colorName'  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("colorName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#colorName').val(colorvalue).change();
	colorvalue = 0;

}

function shipping() {
	var checkvalue = $("#shippingCheck").is(':checked') ? 'checked' : 'unchecked';
	if (checkvalue == 'checked') {
		$("#shippingmark").attr('disabled', false);
	} else {
		$("#shippingmark").attr('disabled', true);
	}

	var po = $("#purchaseOrder").val();
	var style = $("#styleNo").val();
	var item = $("#itemName").val();

	console.log("Po " + po + " style " + style + " item " + item)


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
	var itemList = data;
	var options = "<option  value='0' selected>Select Item Type</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option  value='" + i + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("shippingmark").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#shippingmark').val("0").change();
}






function sizeRequiredBoxaction() {



	var itemList = "";
	var options = "<option  value='0' selected>Select Item Type</option>";

	document.getElementById("size").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#size').val("0").change();
}



function itemWiseColor() {


	var style = $("#styleNo").val();
	var item = $("#itemName").val();

	console.log("style " + style)
	if (style != 0) {

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './itemWiseColor/' + style + '/' + item,
			data: {




			},
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

	console.log(" colors ")

	var itemList = data;
	var options = "<option  value='0' selected>Select Item Color</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("itemcolor").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#itemcolor').val("0").change();


}


function SizeWiseQty() {


	var style = $("#styleNo").val();
	var item = $("#itemName").val();
	var size = $("#size").val();
	var color = 0;
	color = $("#colorName").val();

	var type = 1;

	if (color == 0) {
		type = 1;
	} else {
		type = 2;
	}


	console.log("style " + style)
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

function requiredperdozen() {
	var orderqty = $("#orderQty").val();
	var perpcs = $("#reqPerPcs").val();
	var qtyindozen = $("#qtyInDozen").val();
	var perdozen = perpcs * qtyindozen;
	var totalqty = orderqty * perpcs;

	$("#reqPerDozen").val(perdozen);

	$("#totalQty").val(totalqty);


}

function totalbox() {
	var orderqty = $("#orderQty").val();
	var perunit = $("#perUnit").val();

	var totalbox = orderqty / perunit;

	$("#totalBox").val(totalbox);

	$("#totalQty").val(totalbox);


}

function dividedBy() {
	var totalbox = $("#totalBox").val();
	var divideby = $("#dividedBy").val();

	var totalQty = totalbox / divideby;



	$("#totalQty").val(totalQty);


}


function percentageQty() {


	var ReqQty = parseFloat($('#reqPerPcs').val() == '' ? "0" : $('#reqPerPcs').val()) * parseFloat($('#totalBox').val() == '' ? "0" : $('#totalBox').val());

	var extraQty = parseFloat($('#extraIn').val() == '' ? "0" : $('#extraIn').val());


	var extraValue = (ReqQty * extraQty) / 100

	$("#percentQty").val(extraValue);

	var totalQty = ReqQty + extraValue;
	$("#totalQty").val(totalQty);

	var unitValue = parseFloat($('#unit').val() == '0' ? "0" : unitList[$('#unit').val()].unitValue);


	var grandQty = parseFloat((totalQty / unitValue));


	$("#grandQty").val(grandQty);
}


function confrimEvent() {
	var user = $("#user_hidden").val();
	var aiNo = $("#aiNo").val();

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './confrimAccessoriesIndent',
		data: {
			user: user,
			aiNo: aiNo
		},
		success: function (data) {

			alert(data);

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


function editEvent() {

	var autoid = $("#accIndentId").val();
	var user = $("#user_hidden").val();
	var POno = $("#purchaseOrder option:selected").text();
	//var POno=$("#purchaseOrder").val();
	var style = $("#styleNo").val();
	var item = $("#itemName").val();


	var itemColor = $("#colorName").val();
	var ShippingMark = $("#shippingmark").val();

	var accessoriesItem = $("#accessoriesItem").val();

	var accessoriesSize = $("#accessoriesSize").val();
	var size = $("#size").val();

	var orderqty = $("#orderQty").val();
	var qtyindozen = $("#qtyInDozen").val();


	var reqperpcs = $("#reqPerPcs").val();

	var reqperdozen = $("#reqPerDozen").val();
	var perunit = $("#perUnit").val();
	var totalbox = $("#totalBox").val();
	var dividedby = $("#dividedBy").val();
	var extraInpercent = $("#extraIn").val();
	var percentqty = $("#percentQty").val();
	var totalqty = $("#totalQty").val();

	var unit = $("#unit").text();
	var grandqty = $("#grandQty").val();
	var brand = $("#brand").val();
	var accessoriescolor = $("#color").val();

	if (POno == 0) {
		alert("Select Purchase Order No")
	} else if (style == 0) {
		alert("Select Style No")
	} else if (item == 0) {
		alert("Select Item Name")
	} else if (accessoriesItem == 0) {
		alert("Select accessories Item name")
	} else {

		console.log("style " + style)
		if (style != 0) {

			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: './editAccessoriesIndent',
				data: {
					autoid: autoid,
					po: POno,
					style: style,
					itemname: item,
					itemcolor: itemColor,
					shippingmark: ShippingMark,
					accessoriesname: accessoriesItem,
					accessoriessize: accessoriesSize,
					size: size,
					orderqty: orderqty,
					qtyindozen: qtyindozen,
					reqperpcs: reqperpcs,
					reqperdozen: reqperdozen,
					perunit: perunit,
					totalbox: totalbox,
					dividedby: dividedby,
					extrainpercent: extraInpercent,
					percentqty: percentqty,
					totalqty: totalqty,
					unit: unit,
					grandqty: grandqty,
					brand: brand,
					accessoriescolor: accessoriescolor


				},
				success: function (data) {

					alert(data);

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
}

function saveEvent() {


	var user = $("#user_hidden").val();
	var POno = $("#purchaseOrder option:selected").text();
	//var POno=$("#purchaseOrder").val();
	var style = $("#styleNo").val();
	var item = $("#itemName").val();


	var itemColor = $("#colorName").val();
	var ShippingMark = $("#shippingmark").val();

	var accessoriesItem = $("#accessoriesItem").val();

	var accessoriesSize = $("#accessoriesSize").val();
	var size = $("#size").val();

	var orderqty = $("#orderQty").val();
	var qtyindozen = $("#qtyInDozen").val();


	var reqperpcs = $("#reqPerPcs").val();

	var reqperdozen = $("#reqPerDozen").val();
	var perunit = $("#perUnit").val();
	var totalbox = $("#totalBox").val();
	var dividedby = $("#dividedBy").val();
	var extraInpercent = $("#extraIn").val();
	var percentqty = $("#percentQty").val();
	var totalqty = $("#totalQty").val();

	var unit = $("#unit").val();
	var grandqty = $("#grandQty").val();
	var brand = $("#brand").val();
	var accessoriescolor = $("#color").val();

	if (POno == 0) {
		alert("Select Purchase Order No")
	} else if (style == 0) {
		alert("Select Style No")
	} else if (item == 0) {
		alert("Select Item Name")
	} else if (accessoriesItem == 0) {
		alert("Select accessories Item name")
	} else {

		console.log("style " + style)
		if (style != 0) {

			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: './insertAccessoriesIndent',
				data: {

					po: POno,
					style: style,
					itemname: item,
					itemcolor: itemColor,
					shippingmark: ShippingMark,
					accessoriesname: accessoriesItem,
					accessoriessize: accessoriesSize,
					size: size,
					orderqty: orderqty,
					qtyindozen: qtyindozen,
					reqperpcs: reqperpcs,
					reqperdozen: reqperdozen,
					perunit: perunit,
					totalbox: totalbox,
					dividedby: dividedby,
					extrainpercent: extraInpercent,
					percentqty: percentqty,
					totalqty: totalqty,
					unit: unit,
					grandqty: grandqty,
					brand: brand,
					accessoriescolor: accessoriescolor


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
	}
}


function AccessoriesDataShowInTable(data) {
	var rows = [];
	var length = data.length;

	for (var i = 0; i < length; i++) {
		rows.push(drawRowDataTable(data[i], i + 1));
	}

	return rows;
}

function drawRowDataTable(rowData, c) {

	var row = $("<tr />")
	row.append($("<td>" + c + "</td>"));
	row.append($("<td>" + rowData.po + "</td>"));
	row.append($("<td>" + rowData.style + "</td>"));
	row.append($("<td>" + rowData.itemname + "</td>"));
	row.append($("<td>" + rowData.itemcolor + "</td>"));
	row.append($("<td>" + rowData.shippingmark + "</td>"));
	row.append($("<td>" + rowData.accessoriesName + "</td>"));
	row.append($("<td>" + rowData.sizeName + "</td>"));
	row.append($("<td>" + rowData.requiredUnitQty + "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=\"accessoriesItemSet(" + rowData.autoId + ")\"> </i></td>"));


	return row;
}



function accessoriesItemSet(id) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './accessoriesItemSet/' + id,
		success: function (data) {

			setAccessoriesItemDetails(data.result);
			// $("#dataList").empty();
			// $("#dataList").append(AccessoriesDataShowInTable(data.result));


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

function setAccessoriesItemDetails(data) {
	var itemList = data;


	$('#orderQty').val(itemList[0].orderqty);
	$('#reqPerPcs').val(itemList[0].reqperpcs);
	$('#perUnit').val(itemList[0].perunit);
	$('#totalQty').val(itemList[0].totalqty);
	$('#qtyInDozen').val(itemList[0].qtyindozen);
	$('#reqPerDozen').val(itemList[0].reqperdozen);
	$('#totalBox').val(itemList[0].totalbox);
	$('#grandQty').val(itemList[0].requiredUnitQty);
	$('#dividedBy').val(itemList[0].dividedby);
	$('#extraIn').val(itemList[0].extrainpercent);
	$('#percentQty').val(itemList[0].percentqty);
	$('#unit').val(itemList[0].unit);
	$('#brand').val(itemList[0].indentBrandId);
	$('#color').val(itemList[0].indentColorId);

	$('#accIndentId').val(itemList[0].autoid);

	stylevalue = itemList[0].style;
	itemvalue = itemList[0].itemname;
	colorvalue = itemList[0].itemcolor;
	sizevalue = itemList[0].sizeName;
	console.log("instyle " + stylevalue);
	console.log("incolorvalue " + colorvalue);

	$('#purchaseOrder option').map(function () {
		if ($(this).text() == itemList[0].po) return this;
	}).attr('selected', 'selected').change();


	$('#shippingCheck').val(itemList[0].shippingmark);
	//$('#colorName').val(itemList[0].poitemcolor);
	$('#accessoriesItem').val(itemList[0].accessoriesname);
	$('#accessoriesSize').val(itemList[0].accessoriessize);
	//

	$('#sizeReqCheck').prop("checked", true);


	find = 1;

	$('#btnSave').prop('disabled', true);
	$('#btnEdit').prop('disabled', false);
}

//Accessories Curton 
function setTotalQtyForCurton() {


	var length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	var width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	var height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	var add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());

	var multiplication1 = length1 + width1 + height1 + add1;

	var length2 = parseFloat($('#length2').val() == '' ? "0" : $('#length2').val());
	var width2 = parseFloat($('#width2').val() == '' ? "0" : $('#width2').val());
	var height2 = parseFloat($('#height2').val() == '' ? "0" : $('#height2').val());
	var add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());

	var devideBy = parseFloat($('#devideBy').val() == '' ? "0" : $('#devideBy').val());

	var multiplication2 = length2 + width2 + height2 + add2;

	var totalqty = (multiplication1 * multiplication2 * 2) / devideBy;


	$('#qty').val(totalqty);


}

function editAccessoriesCurton() {
	var autoid = $("#accIndentId").val();
	var user = $("#user_hidden").val();
	var poNo = $("#purchaseOrder option:selected").text();
	var style = $("#styleNo").val();
	var item = $("#itemName").val();
	var itemColor = $("#colorName").val();
	var shippingMark = $("#shippingmark").val();
	var accessoriesItem = $("#accessoriesItem").val();
	var accessoriesSize = $("#accessoriesSize").val();
	var unit = $("#unit").val();

	var orderqty = parseFloat($('#orderQty').val() == '' ? "0" : $('#orderQty').val());
	var length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	var width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	var height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	var add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());

	var devideBy = parseFloat($('#devideBy').val() == '' ? "0" : $('#devideBy').val());

	var length2 = parseFloat($('#length2').val() == '' ? "0" : $('#length2').val());
	var width2 = parseFloat($('#width2').val() == '' ? "0" : $('#width2').val());
	var height2 = parseFloat($('#height2').val() == '' ? "0" : $('#height2').val());
	var add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());
	var ply = parseFloat($('#ply').val() == '' ? "0" : $('#ply').val());

	var totalQty = parseFloat($('#qty').val() == '' ? "0" : $('#qty').val());

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
	var user = $("#user_hidden").val();
	var poNo = $("#purchaseOrder option:selected").text();
	var style = $("#styleNo").val();
	var item = $("#itemName").val();
	var itemColor = $("#colorName").val();
	var shippingMark = $("#shippingmark").val();
	var accessoriesItem = $("#accessoriesItem").val();
	var accessoriesSize = $("#accessoriesSize").val();
	var unit = $("#unit").val();

	var orderqty = parseFloat($('#orderQty').val() == '' ? "0" : $('#orderQty').val());
	var length1 = parseFloat($('#length1').val() == '' ? "0" : $('#length1').val());
	var width1 = parseFloat($('#width1').val() == '' ? "0" : $('#width1').val());
	var height1 = parseFloat($('#height1').val() == '' ? "0" : $('#height1').val());
	var add1 = parseFloat($('#add1').val() == '' ? "0" : $('#add1').val());

	var devideBy = parseFloat($('#devideBy').val() == '' ? "0" : $('#devideBy').val());

	var length2 = parseFloat($('#length2').val() == '' ? "0" : $('#length2').val());
	var width2 = parseFloat($('#width2').val() == '' ? "0" : $('#width2').val());
	var height2 = parseFloat($('#height2').val() == '' ? "0" : $('#height2').val());
	var add2 = parseFloat($('#add2').val() == '' ? "0" : $('#add2').val());
	var ply = parseFloat($('#ply').val() == '' ? "0" : $('#ply').val());

	var totalQty = parseFloat($('#qty').val() == '' ? "0" : $('#qty').val());

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
	var rows = [];
	var length = data.length;

	for (var i = 0; i < length; i++) {
		rows.push(drawRowDataCartonTable(data[i], i + 1));
	}

	return rows;
}

function drawRowDataCartonTable(rowData, c) {

	var row = $("<tr />")
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
	var itemList = data;

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
