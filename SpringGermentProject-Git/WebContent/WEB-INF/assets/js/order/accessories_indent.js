let stylevalue = 0;
let itemvalue = 0;
let colorvalue = 0;
let sizevalue = 0;
let find = 0;

$('#btnSave').show();
$('#btnEdit').hide();

let unitList = {};

window.onload = () => {
	document.title = "Accessories Indent";
	$("#loader").show();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getUnitList',
		data: {},
		success: function (data) {
			unitList = {};
			data.unitList.forEach(unit => {
				unitList[unit.unitId] = unit;
			});
			$("#loader").hide();
		}
	});
	//$("#dataTable").DataTable();

}

const styleListLoadByMultiplePurchaseOrder = function () {
	return new Promise((resolve, reject) => {
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
					console.log("style load function");
					resolve();
				}
			});
		}
	});

};
const itemNameLoadByMultipleStyleId = function () {
	return new Promise((resolve, reject) => {
		if ($("#styleNo").val().length > 0) {
			let styleIdList = '';
			$("#styleNo").val().forEach(id => {
				styleIdList += `'${id}',`;
			});
			styleIdList = styleIdList.slice(0, -1);
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
					resolve();
				}
			});
		}
	})
}
const colorAndShippingListByMultipleStyleId = function () {
	return new Promise((resolve, reject) => {
		if ($("#styleNo").val().length > 0 && $("#purchaseOrder").val().length > 0) {
			let styleIdList = '';
			$("#styleNo").val().forEach(id => {
				styleIdList += `'${id}',`;
			});
			styleIdList = styleIdList.slice(0, -1);

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
					purchaseOrders: poList,
					styleIdList: styleIdList
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
						options += "<option value='" + shippingMarkList[i] + "'>" + shippingMarkList[i] + "</option>";
					};
					$("#shippingMark").html(options);
					$('#shippingMark').selectpicker('refresh');
					console.log("color shipping mark load function");
					resolve()
				}
			});
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
$('#buyerName').on('hide.bs.select', function (e, clickedIndex, isSelected, previousValue) {
	if ($("#buyerName").val().length > 0) {
		let buyerIdList = '';
		$("#buyerName").val().forEach(id => {
			buyerIdList += `'${id}',`;
		});
		buyerIdList = buyerIdList.slice(0, -1);
		$("#loader").show();
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
				$("#loader").hide();
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
		$("#loader").show();
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
				$("#loader").hide();
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
			$("#loader").show();
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: './getColorAndShippingListByMultipleStyleId',
				data: {
					purchaseOrders: poList,
					styleIdList: styleIdList
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
						options += "<option value='" + shippingMarkList[i] + "'>" + shippingMarkList[i] + "</option>";
					};
					$("#shippingMark").html(options);
					$('#shippingMark').selectpicker('refresh');
					$("#loader").hide();
				}
			});
		} else {
			$("#loader").show();
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
					$("#loader").hide();
				}
			});
		}
		$("#loader").show();
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
				$("#loader").hide();
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

$("#btnRecyclingData").click(() => {
	let buyersId = $("#buyerName").val();
	let purchaseOrdersId = $("#purchaseOrder").val();
	let stylesId = $("#styleNo").val();
	let itemsId = $("#itemName").val();
	let colorsId = $("#color").val();
	let shippingMarks = $("#shippingMark").val();

	let colPurchaseOrder = 'boed.purchaseOrder,';
	let colStyleId = 'boed.styleId,';
	let colStyleNo = 'sc.styleNo,';
	let colItemId = 'boed.itemId,';
	let colItemName = 'id.itemName,';
	let colColorId = 'boed.colorId,';
	let colColorName = 'c.colorName,'
	let colShippingMark = 'boed.shippingMarks,';

	let checkPurchaseOrder = $("#checkPurchaseOrder").prop('checked');
	let checkStyleNo = $("#checkStyleNo").prop('checked');
	let checkItemName = $("#checkItemName").prop('checked');
	let checkColor = $("#checkColor").prop('checked');
	let checkShippingMark = $("#checkShippingMark").prop('checked');
	let checkSizeRequired = $("#checkSizeRequired").prop('checked');
	let checkSQ = $("#checkSQ").prop('checked');
	let checkSKU = $("#checkSKU").prop('checked');

	let groupPurchaseOrder = 'boed.purchaseOrder,';
	let groupStyleId = 'boed.styleId,';
	let groupStyleNo = 'sc.styleNo,'
	let groupItemId = 'boed.itemId,';
	let groupItemName = 'id.itemName,';
	let groupColorId = 'boed.colorId,';
	let groupColorName = 'c.colorName,'
	let groupShippingMark = 'boed.ShippingMarks,';

	

	if (purchaseOrdersId.length > 0) {
		if (stylesId.length > 0) {
			if (itemsId.length > 0) {

				let purchaseOrderText = $('#purchaseOrder').find('option:selected').map(function () {
					return $(this).text();
				}).get().join(', ');
			
				let styleNoText = $('#styleNo').find('option:selected').map(function () {
					return $(this).text();
				}).get().join(', ');
			
				let itemNameText = $('#itemName').find('option:selected').map(function () {
					return $(this).text();
				}).get().join(', ');
			
				let colorNameText = $('#color').find('option:selected').map(function () {
					return $(this).text();
				}).get().join(', ');

				purchaseOrdersId = '';
				$("#purchaseOrder").val().forEach(id => {
					purchaseOrdersId += `'${id}',`;
				});
				purchaseOrdersId = purchaseOrdersId.slice(0, -1);

				stylesId = '';
				$("#styleNo").val().forEach(id => {
					stylesId += `'${id}',`;
				});
				stylesId = stylesId.slice(0, -1);

				itemsId = '';
				$("#itemName").val().forEach(id => {
					itemsId += `'${id}',`;
				});
				itemsId = itemsId.slice(0, -1);

				let queryPurchaseOrder = ` boed.purchaseOrder in (${purchaseOrdersId}) `;
				let queryStylesId = ` and boed.styleId in (${stylesId}) `;
				let queryItemsId = ` and  boed.itemId in (${itemsId}) `;

				let queryColorsId = '';
				let queryShippingMarks = '';

				if (colorsId.length > 0) {
					colorsId = '';
					$("#color").val().forEach(id => {
						colorsId += `'${id}',`;
					});
					colorsId = colorsId.slice(0, -1);
					queryColorsId = ` and  boed.colorId in (${colorsId}) `;
				}
				if (shippingMarks.length > 0) {
					shippingMarks = '';
					$("#shippingMark").val().forEach(id => {
						shippingMarks += `'${id}',`;
					});
					shippingMarks = shippingMarks.slice(0, -1);
					queryShippingMarks = ` and  boed.shippingMarks in (${shippingMarks}) `;
				}

				if (checkPurchaseOrder) {
					colPurchaseOrder = `'${$("#purchaseOrder").val().toString()}' as purchaseOrder,`;
					groupPurchaseOrder = ``;
				}
				if (checkStyleNo) {
					colStyleId = `'${$("#styleNo").val().toString()}' as styleId,`;
					colStyleNo = `'' as styleNo,`;

					groupStyleId = ``;
					groupStyleNo = ``;
				}
				if (checkItemName) {
					colItemId = `'${$("#itemName").val().toString()}' as itemId,`;
					colItemName = `'' as itemName,`;

					groupItemId = '';
					groupItemName = '';
				}
				if (checkColor) {
					colColorId = `'${$("#color").val().toString()}' as colorId,`;
					colColorName = `'' as colorName,`;

					groupColorId = '';
					groupColorName = '';
				}
				if (checkShippingMark) {
					colShippingMark = `'${$("#shippingMark").val().toString()}' as ShippingMarks,`;
					groupShippingMark = '';
				}

				let reqPerPcs = $("#reqPerPcs").val();
				reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

				let inPercent = $("#inPercent").val();
				inPercent = parseFloat(inPercent == '' ? 0 : inPercent);


				if (checkSizeRequired) {

					let groupBy = groupPurchaseOrder.concat(groupStyleId, groupStyleNo, groupItemId, groupItemName, groupColorId, groupColorName, groupShippingMark);

					let query = `select ${colPurchaseOrder} ${colStyleId} ${colStyleNo} ${colItemId} ${colItemName} ${colColorId} ${colColorName} ${colShippingMark} sum(boed.TotalUnit) as orderQty,boed.sizeGroupId
								from TbBuyerOrderEstimateDetails boed
								left join TbStyleCreate sc
								on boed.StyleId = sc.StyleId
								left join tbItemDescription id
								on boed.ItemId = id.itemId
								left join tbColors c
								on boed.ColorId = c.ColorId
								where ${queryPurchaseOrder + " " + queryStylesId + " " + queryItemsId + " " + queryColorsId + " " + queryShippingMarks} 
								group by ${groupBy} boed.sizeGroupId 
								order by ${groupBy} boed.sizeGroupId`;

					let query2 = `select ss.id,boed.sizeGroupId,ss.sizeName, sum(sv.sizeQuantity) as orderQty,ss.sortingNo
								from TbBuyerOrderEstimateDetails boed
								left join TbStyleCreate sc
								on boed.StyleId = sc.StyleId
								left join tbItemDescription id
								on boed.ItemId = id.itemId
								left join tbColors c
								on boed.ColorId = c.ColorId
								left join tbSizeValues sv
								on boed.autoId = sv.linkedAutoId and sv.type = 1 and boed.sizeGroupId = sv.sizeGroupId 
								left join tbStyleSize ss
								on sv.sizeId = ss.id 
								where ${queryPurchaseOrder + " " + queryStylesId + " " + queryItemsId + " " + queryColorsId + " " + queryShippingMarks} and boed.sizeGroupId = 'SIZEGROUPID'
								group by ${groupBy} boed.sizeGroupId,ss.sortingNo,ss.id,ss.sizeName
								order by boed.sizeGroupId, ${groupBy} ss.sortingNo`;
					$("#loader").show();
					$.ajax({
						type: 'GET',
						dataType: 'json',
						url: './getAccessoriesRecyclingDataWithSize',
						data: {
							query: query,
							query2: query2
						},
						success: function (data) {
							let dataList = data.dataList;
							let length = dataList.length;
							sizeGroupId = "";
							let tables = "";
							let isClosingNeed = false;
							let rowSpan = 3;
							if (checkSQ) rowSpan++;
							if (checkSKU) rowSpan++;
							for (let i = 0; i < length; i++) {
								let item = dataList[i];

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
											<th  class="min-width-150">Purchase Order</th>
											<th  class="min-width-150">Style No</th>
											<th  class="min-width-150">Item Name</th>
											<th >Color</th>
											<th >Shipping Mark</th>
											<th >Field Type</th>`
									let sizeListLength = item.sizeList.length;
									for (let j = 0; j < sizeListLength; j++) {
										tables += `<th id='sizeHeading-${sizeGroupId}${item.sizeList[j].sizeId}' class='sizeHeading-${sizeGroupId}' class="min-width-60 mx-auto" scope="col">${item.sizeList[j].sizeName}</th>`;
									}
									tables += `</tr>
											</thead>
											<tbody id="orderPreviewList-${sizeGroupId}" class="orderPreview">`
									isClosingNeed = true;
								}
								tables += `<tr id='orderRow-${i}' class='orderPreviewRow' data-size-required='true' data-size-group-id="${item.sizeGroupId}" data-style-id='${item.styleId}' data-item-id='${item.itemId}' data-color-id='${item.itemColorId}' data-check-sq='${checkSQ}' data-check-sku='${checkSKU}'>
											<td id='purchaseOrder-${i}'>${checkPurchaseOrder?purchaseOrderText:item.purchaseOrder}</td>
											<td id='styleNo-${i}'>${checkStyleNo?styleNoText:item.styleNo}</td>
											<td id='itemName-${i}'>${checkItemName?itemNameText:item.itemname}</td>
											<td id='color-${i}'>${checkColor?colorNameText:item.itemcolor}</td>
											<td id='shippingMark-${i}'>${item.shippingmark}</td>
											<td>OrderQty</td>`;
								let sizeList = item.sizeList;
								let sizeListLength = sizeList.length;

								for (let j = 0; j < sizeListLength; j++) {

									tables += `<td id='orderQty-${i}${sizeList[j].sizeId}' data-size-id='${sizeList[j].sizeId}' class='sizes-${i}'>${sizeList[j].sizeQuantity}</td>`
								}

								tables += `</tr><tr>
												<td colspan="5" rowspan="${rowSpan}"></td>
												<td>(%) Qty</td>`
								for (let j = 0; j < sizeListLength; j++) {
									if (sizeList[j].sizeQuantity > 0) {

										tables += `<td><span id='inPercent-${i}${sizeList[j].sizeId}'>${inPercent}</span>% (<span id='percentQty-${i}${sizeList[j].sizeId}'>${parseFloat((sizeList[j].sizeQuantity * reqPerPcs * inPercent) / 100).toFixed(1)}</span>)</td>`;
									} else {
										tables += `<td></td>`;
									}
								}

								tables += `</tr><tr>
												<td>Total(Pcs)</td>`
								for (let j = 0; j < sizeListLength; j++) {
									if (sizeList[j].sizeQuantity > 0) {
										tables += `<td><input id='totalQty-${i}${sizeList[j].sizeId}' class='form-control-sm max-width-100 min-width-60 total-${i} sizeGroup-${item.sizeGroupId}' type='number' onkeyup="setInPercentInPreviewTable('${i}${sizeList[j].sizeId}'),calculateTotalQtyAndUnitQty()" value="${(parseFloat(sizeList[j].sizeQuantity * reqPerPcs) + ((sizeList[j].sizeQuantity * reqPerPcs * inPercent) / 100)).toFixed(1)}"/></td>`;
									} else {
										tables += `<td></td>`;
									}
								}
								tables += `</tr><tr>
								<td>Unit Qty</td>`
								for (let j = 0; j < sizeListLength; j++) {
									if (sizeList[j].sizeQuantity > 0) {
										tables += `<td><input id='unitQty-${i}${sizeList[j].sizeId}' class='form-control-sm max-width-100 min-width-60 unitQty-${i}' type='number' onkeyup="setTotalByUnitQtyInPreviewTable('${i}${sizeList[j].sizeId}'),calculateTotalQtyAndUnitQty()" value="${(parseFloat(sizeList[j].sizeQuantity * reqPerPcs) + ((sizeList[j].sizeQuantity * reqPerPcs * inPercent) / 100)).toFixed(1)}"/></td>`;
									} else {
										tables += `<td></td>`;
									}
								}
								if (checkSQ) {
									tables += `</tr><tr>
								<td>SQ</td>`
									for (let j = 0; j < sizeListLength; j++) {
										if (sizeList[j].sizeQuantity > 0) {
											tables += `<td><input id='sq-${i}${sizeList[j].sizeId}' class='form-control-sm max-width-100 min-width-60 sq-${i}' type='text'/></td>`;
										} else {
											tables += `<td></td>`;
										}
									}
								}
								if (checkSKU) {
									tables += `</tr><tr>
								<td>SKU</td>`
									for (let j = 0; j < sizeListLength; j++) {
										if (sizeList[j].sizeQuantity > 0) {
											tables += `<td><input id='sku-${i}${sizeList[j].sizeId}' class='form-control-sm max-width-100 min-width-60 sku-${i}' type='text'/></td>`;
										} else {
											tables += `<td></td>`;
										}
									}
								}
								tables += "</tr>"
							}
							tables += "</tbody></table> </div></div>";

							$("#tableList").empty();
							$("#tableList").append(tables);
							setTotalOrderQty();
							setUnitQty();
							$("#loader").hide();
						}
					});
				} else {

					let query = `select ${colPurchaseOrder} ${colStyleId} ${colStyleNo} ${colItemId} ${colItemName} ${colColorId} ${colColorName} ${colShippingMark} sum(boed.TotalUnit) as orderQty
								from TbBuyerOrderEstimateDetails boed
								left join TbStyleCreate sc
								on boed.StyleId = sc.StyleId
								left join tbItemDescription id
								on boed.ItemId = id.itemId
								left join tbColors c
								on boed.ColorId = c.ColorId
								where ${queryPurchaseOrder + " " + queryStylesId + " " + queryItemsId + " " + queryColorsId + " " + queryShippingMarks}`;

					let groupBy = groupPurchaseOrder.concat(groupStyleId, groupStyleNo, groupItemId, groupItemName, groupColorId, groupColorName, groupShippingMark);

					if (groupBy.length > 0) {
						groupBy = groupBy.slice(0, -1);
						query += `group by ${groupBy} 
							 order by ${groupBy}`;
					}
					$("#loader").show();
					$.ajax({
						type: 'GET',
						dataType: 'json',
						url: './getAccessoriesRecyclingData',
						data: {
							query: query
						},
						success: function (data) {


							let tables = `<div class="row mt-1">
												<div class="col-md-12 table-responsive" >
													<table class="table table-hover table-bordered table-sm mb-0 small-font">
													<thead class="no-wrap-text bg-light">
														<tr>
															<th  class="min-width-150">Purchase Order</th>
															<th  class="min-width-150">Style No</th>
															<th  class="min-width-150">Item Name</th>
															<th >Color</th>
															<th >Shipping Mark</th>
															<th >Order Qty</th>
															<th >% Qty</th>
															<th >Total Qty</th>
															<th >Unit Qty</th>
															${checkSQ ? '<th >SQ</th>' : ''}
															${checkSKU ? '<th >SKU</th>' : ''}
														</tr>
													</thead>
													<tbody id="orderList" class="orderPreview">`
							let dataList = data.dataList;
							let length = dataList.length;

							for (let i = 0; i < length; i++) {
								let item = dataList[i];
								tables += `<tr id='orderRow-${i}' class='orderPreviewRow' data-size-required='false' data-style-id='${item.styleId}' data-item-id='${item.itemId}' data-color-id='${item.itemColorId}'  data-check-sq='${checkSQ}' data-check-sku='${checkSKU}'>
											<td id='purchaseOrder-${i}'>${checkPurchaseOrder?purchaseOrderText:item.purchaseOrder} </td>
											<td id='styleNo-${i}'>${checkStyleNo?styleNoText:item.styleNo} </td>
											<td id='itemName-${i}'>${checkItemName?itemNameText:item.itemname} </td>
											<td id='color-${i}'>${checkColor?colorNameText:item.itemcolor} </td>
											<td id='shippingMark-${i}'>${item.shippingmark} </td>
											<td id='orderQty-${i}'>${parseFloat(item.orderqty).toFixed(1)}</td>
											<td><span id='inPercent-${i}'>${inPercent}</span>% (<span id="percentQty-${i}">${parseFloat((item.orderqty * inPercent) / 100).toFixed(1)}</span>) </td>
											<td><input class='form-control-sm max-width-100 min-width-60' id='totalQty-${i}' type="number" onkeyup="setInPercentInPreviewTable('${i}'),calculateTotalQtyAndUnitQty()" value="${(parseFloat(item.orderqty * reqPerPcs) + ((item.orderqty * reqPerPcs * inPercent) / 100)).toFixed(1)}"/></td>
											<td><input class='form-control-sm max-width-100 min-width-60' id='unitQty-${i}' type="number" onkeyup="setTotalByUnitQtyInPreviewTable('${i}'),calculateTotalQtyAndUnitQty()" value="${(parseFloat(item.orderqty * reqPerPcs) + ((item.orderqty * reqPerPcs * inPercent) / 100)).toFixed(1)}"/></td>
											${checkSQ ? `<td><input class='form-control-sm max-width-100 min-width-60' id='sq-${i}' type='text' /></td>` : ''}
											${checkSKU ? `<td><input class='form-control-sm max-width-100 min-width-60' id='sku-${i}' type='text' /></td>` : ''}
										</tr>`;

							}
							tables += "</tbody></table> </div></div>";
							$("#tableList").empty();
							$("#tableList").append(tables);
							setTotalOrderQty();
							setUnitQty();
							$("#loader").hide();
						}
					});
				}
				$('#btnAdd').show();
				$('#btnEdit').hide();

			} else {
				warningAlert("Please Select Any Item Name");
				$("#itemName").focus();
			}
		} else {
			warningAlert("Please Select Any Style NO");
			$("#styleNo").focus();
		}
	} else {
		warningAlert("Please Select Any Purchase Order");
		$("#purchaseOrder").focus();
	}
})

$("#btnAdd").click(() => {
	let rowList = $(".orderPreviewRow");
	let length = rowList.length;

	if (length > 0) {
		let accessoriesItemId = $("#accessoriesItem").val();
		let accessoriesItemName = $("#accessoriesItem option:selected").text();

		let accessoriesSize = $("#accessoriesSize").val();
		let accessoriesColorId = $("#accessoriesColor").val();
		let accessoriesBrandId = $("#brand").val();
		let unitId = $("#unit").val();
		let unitQty = $("#unitQty").val();
		let checkSQ = $("#checkSQ").prop('checked');
		let checkSKU = $("#checkSKU").prop('checked');

		let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
		unitValue = unitValue == 0 ? 1 : unitValue;

		let accessoriesDataList = $("#dataList tr");
		length = accessoriesDataList.length;
		let listRowId = 0;
		if (length > 0) listRowId = accessoriesDataList[length - 1].id.slice(13);

		if (accessoriesItemId != 0) {
			if (unitId != 0) {

				rowList.each((index, row) => {
					let rowId = row.id.slice(9);
					//conosle.log(row);
					let isSizeRequired = row.getAttribute('data-size-required');
					let sizeGroupId = row.getAttribute('data-size-group-id');
					let purchaseOrder = $("#purchaseOrder-" + rowId).text();
					let styleId = row.getAttribute('data-style-id');
					let itemId = row.getAttribute('data-item-id');
					let colorId = row.getAttribute('data-color-id');
					let styleNo = $("#styleNo-" + rowId).text();
					let itemName = $("#itemName-" + rowId).text();
					let color = $("#color-" + rowId).text();;
					let shippingMark = $("#shippingMark-" + rowId).text();

					let isExist = false;
					accessoriesDataList.each((index, row) => {
						let id = row.id.slice(13);
						
						if (isExist || (row.getAttribute('data-accessories-item-id') == accessoriesItemId && $('#indentPurchaseOrder-' + id).text() == purchaseOrder && row.getAttribute('data-style-id') == styleId && row.getAttribute('data-item-id') == itemId && row.getAttribute('data-color-id') == colorId && $('#indentShippingMark-' + id).text() == shippingMark && (isSizeRequired == 'true' ? row.getAttribute('data-size-group-id') == sizeGroupId : true))) {
							isExist = true;
						}
					});

					console.log("isExist", isExist);

					if (!isExist) {
						if (isSizeRequired == 'true') {

							let sizes = $(".sizes-" + rowId);
							sizes.each((index, td) => {
								let cellId = td.id.slice(9);
								let sizeId = td.getAttribute('data-size-id');
								let sizeName = $("#sizeHeading-" + sizeGroupId + sizeId).text();
								let orderQty = $("#orderQty-" + cellId).text();
								let dozenQty = parseFloat(orderQty / 12).toFixed(2);
								let reqPerPcs = $("#reqPerPcs").val();
								reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);
								let reqPerDozen = parseFloat((orderQty * reqPerPcs) / 12).toFixed(2);

								let perUnit = $("#perUnit").val();
								let totalBox = $("#totalBox").val();
								let divideBy = $("#divideBy").val();
								divideBy = parseFloat(divideBy == '' || divideBy == 0 ? 1 : divideBy);
								let inPercent = $("#inPercent-" + cellId).text();
								let percentQty = $("#percentQty-" + cellId).text();
								let totalQty = $("#totalQty-" + cellId).val();
								let unitQty = $("#unitQty-" + cellId).val();
								let sqNo = checkSQ?$("#sq-"+cellId).val():'';
								let skuNo = checkSKU?$("#sku-"+cellId).val():'';
								if (unitQty > 0) {

									let newRow = `<tr id='newIndentRow-${++listRowId}' class='newIndentRow' data-style-id='${styleId}' data-item-id='${itemId}' data-color-id='${colorId}' data-size-group-id='${sizeGroupId}' data-size-id='${sizeId}' 
											data-accessories-size='${accessoriesSize}' data-accessories-item-id='${accessoriesItemId}' data-accessories-color-id='${accessoriesColorId}' 
											data-accessories-brand-id='${accessoriesBrandId}' data-unit-id='${unitId}'
											data-order-qty='${orderQty}' data-dozen-qty='${dozenQty}' data-req-per-pcs='${reqPerPcs}' data-req-per-dozen='${reqPerDozen}' data-per-unit='${perUnit}' data-total-box='${totalBox}'
											data-divide-by='${divideBy}' data-in-percent='${inPercent}' data-percent-qty='${percentQty}' data-total-qty='${totalQty}' data-sq-no='${sqNo}' data-sku-no='${skuNo}'>
											<td>${++length}</td>
											<td id='indentPurchaseOrder-${listRowId}'>${purchaseOrder}</td>
											<td id='indentStyleNo-${listRowId}'>${styleNo}</td>
											<td id='indentItemName-${listRowId}'>${itemName}</td>
											<td id='indentColor-${listRowId}'>${color}</td>
											<td id='indentShippingMark-${listRowId}'>${shippingMark}</td>
											<td id='indentAccessoriesName-${listRowId}'>${accessoriesItemName}</td>
											<td>${sizeName}</td>
											<td id='indentUnitQty-${listRowId}'>${unitQty}</td>
											<td ><i class="fa fa-edit" onclick="setIndentItem('${listRowId}','newIndentRow')" style='cursor:pointer;'></i></td>
											<td ><i class="fa fa-trash" onclick="deleteIndentRow('${listRowId}','newIndentRow')" style='cursor:pointer;'></i></td>
										</tr>`
									//$('#dataTable').dataTable().fnDestroy();
									$("#dataList").append(newRow);
									// $('#dataTable').DataTable(({ 
									// 	"destroy": true, 
									// }));


								}

							})
						} else {
							let orderQty = $("#orderQty-" + rowId).text();
							let dozenQty = parseFloat(orderQty / 12).toFixed(2);
							let reqPerPcs = $("#reqPerPcs").val();
							reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);
							let reqPerDozen = parseFloat((orderQty * reqPerPcs) / 12).toFixed(2);
							let perUnit = $("#perUnit").val();
							let totalBox = $("#totalBox").val();
							let divideBy = $("#divideBy").val();
							divideBy = parseFloat(divideBy == '' || divideBy == 0 ? 1 : divideBy);
							let inPercent = $("#inPercent-" + rowId).text();
							let percentQty = $("#percentQty-" + rowId).text();
							let totalQty = $("#totalQty-" + rowId).val();
							let unitQty = ((totalQty / divideBy) / unitValue).toFixed(2);
							let sqNo = checkSQ?$("#sq-"+rowId).val():'';
							let skuNo = checkSKU?$("#sku-"+rowId).val():'';

							let newRow = `<tr id='newIndentRow-${++listRowId}' class='newIndentRow' data-style-id='${styleId}' data-item-id='${itemId}' data-color-id='${colorId}' 
											data-size-group-id='${sizeGroupId}' data-size-id='' data-accessories-size='${accessoriesSize}' data-accessories-item-id='${accessoriesItemId}' data-accessories-color-id='${accessoriesColorId}' 
											data-accessories-brand-id='${accessoriesBrandId}' data-unit-id='${unitId}'
											data-order-qty='${orderQty}' data-dozen-qty='${dozenQty}' data-req-per-pcs='${reqPerPcs}' data-req-per-dozen='${reqPerDozen}' data-per-unit='${perUnit}' data-total-box='${totalBox}'
											data-divide-by='${divideBy}' data-in-percent='${inPercent}' data-percent-qty='${percentQty}' data-total-qty='${totalQty}'  data-sq-no='${sqNo}' data-sku-no='${skuNo}'>
											<td>${++length}</td>
											<td id='indentPurchaseOrder-${listRowId}'>${purchaseOrder}</td>
											<td id='indentStyleNo-${listRowId}'>${styleNo}</td>
											<td id='indentItemName-${listRowId}'>${itemName}</td>
											<td id='indentColor-${listRowId}'>${color}</td>
											<td id='indentShippingMark-${listRowId}'>${shippingMark}</td>
											<td id='indentAccessoriesName-${listRowId}'>${accessoriesItemName}</td>
											<td></td>
											<td id='indentUnitQty-${listRowId}'>${unitQty}</td>
											<td ><i class="fa fa-edit" onclick="setIndentItem('${listRowId}','newIndentRow')" style='cursor:pointer;'></i></td>
											<td ><i class="fa fa-trash" onclick="deleteIndentRow('${listRowId}','newIndentRow')" style='cursor:pointer;'></i></td>
										</tr>`
							//$('#dataTable').dataTable().fnDestroy();
							$("#dataList").append(newRow);
							// $('#dataTable').DataTable(({ 
							// 	"destroy": true, 
							// }));

						}
						successAlert("Added To Temporary List... You should Confirm...");
					} else {
						warningAlert('This Accessories Already exist...');
					}

				});

			} else {
				warningAlert("Unit not Selected.....Please Select Unit");
				$("#unit").focus();
			}
		} else {
			warningAlert("Accessories Item Not Selected.....Please Select accessories Item");
			$("#accessoriesItem").focus();
		}
	} else {
		warningAlert("Please Recycling your data");
	}
})

function editAction() {
	let autoId = $("#autoId").val();
	let indentType = $("#indentType").val();

	let purchaseOrdersId = $("#purchaseOrder").val();
	let stylesId = $("#styleNo").val();
	let itemsId = $("#itemName").val();
	let colorsId = $("#color").val();
	let shippingMarks = $("#shippingMark").val();
	let checkSQ = $("#checkSQ").prop('checked');
	let checkSKU = $("#checkSKU").prop('checked');

	if (purchaseOrdersId.length > 0) {
		if (stylesId.length > 0) {
			if (itemsId.length > 0) {

				
				purchaseOrdersId = '';
				$("#purchaseOrder").val().forEach(id => {
					purchaseOrdersId += `${id},`;
				});
				purchaseOrdersId = purchaseOrdersId.slice(0, -1);

				let purchaseOrderText = $('#purchaseOrder').find('option:selected').map(function () {
					return $(this).text();
				}).get().join(', ');

				stylesId = '';
				$("#styleNo").val().forEach(id => {
					stylesId += `${id},`;
				});
				stylesId = stylesId.slice(0, -1);
				let styleNoText = $('#styleNo').find('option:selected').map(function () {
					return $(this).text();
				}).get().join(', ');

				itemsId = '';
				$("#itemName").val().forEach(id => {
					itemsId += `${id},`;
				});
				itemsId = itemsId.slice(0, -1);

				let itemNameText = $('#itemName').find('option:selected').map(function () {
					return $(this).text();
				}).get().join(', ');


				let colorNameText = '';
				if (colorsId.length > 0) {
					colorsId = '';
					$("#color").val().forEach(id => {
						colorsId += `${id},`;
					});
					colorsId = colorsId.slice(0, -1);
					colorNameText = $('#color').find('option:selected').map(function () {
						return $(this).text();
					}).get().join(',');
				} else {
					colorsId = '';
				}


				if (shippingMarks.length > 0) {
					shippingMarks = '';
					$("#shippingMark").val().forEach(id => {
						shippingMarks += `${id},`;
					});
					shippingMarks = shippingMarks.slice(0, -1);
				} else {
					shippingMarks = '';
				}

				let accessoriesItemId = $("#accessoriesItem").val();
				let accessoriesItemName = $("#accessoriesItem option:selected").text();

				let accessoriesSize = $("#accessoriesSize").val();
				let accessoriesColorId = $("#accessoriesColor").val();
				let accessoriesBrandId = $("#brand").val();
				let unitId = $("#unit").val();

				let orderQty = $("#orderQty").val();
				let dozenQty = parseFloat(orderQty / 12).toFixed(2);
				let reqPerPcs = $("#reqPerPcs").val();
				reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);
				let reqPerDozen = parseFloat(12 * reqPerPcs).toFixed(2);
				let perUnit = $("#perUnit").val();
				let totalBox = $("#totalBox").val();
				let divideBy = $("#divideBy").val();
				divideBy = parseFloat((divideBy == 0 || divideBy == '') ? 1 : divideBy);
				let inPercent = $("#inPercent").val();
				let percentQty = $("#percentQty").val();
				let totalQty = $("#totalQty").val();

				let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
				unitValue = unitValue == 0 ? 1 : unitValue;
				let unitQty = ((totalQty / divideBy) / unitValue).toFixed(2);

				let userId = $("#userId").val();
				if (indentType == 'newIndentRow') {

					let row = $("#newIndentRow-" + autoId);
					successAlert("Edit Successfully....");
					$("#indentPurchaseOrder-" + autoId).text(purchaseOrdersId);
					row.attr('data-style-id', stylesId);
					row.attr('data-item-id', itemsId);
					row.attr('data-color-id', colorsId);
					$("#indentStyleNo-" + autoId).text(styleNoText);
					$("#indentItemName-" + autoId).text(itemNameText);
					$("#indentColor-" + autoId).text(colorNameText);
					$("#indentShippingMark-" + autoId).text(shippingMarks);
					row.attr('data-accessories-item-id', accessoriesItemId);
					$("#indentAccessoriesName-" + autoId).text(accessoriesItemName);
					row.attr('data-accessories-size', accessoriesSize);
					row.attr('data-accessories-color-id', accessoriesColorId);
					row.attr('data-accessories-brand-id', accessoriesBrandId);
					row.attr('data-unit-id', unitId);

					row.attr('data-order-qty', orderQty);
					row.attr('data-dozen-qty', dozenQty);
					row.attr('data-req-per-pcs', reqPerPcs);
					row.attr('data-req-per-dozen', reqPerDozen);
					row.attr('data-per-unit', perUnit);
					row.attr('data-total-box', totalBox);
					row.attr('data-divide-by', divideBy);
					row.attr('data-in-percent', inPercent);
					row.attr('data-percent-qty', percentQty);
					row.attr('data-total-qty', totalQty);
					$("#indentUnitQty-" + autoId).text(unitQty);
					$("#unitQty").attr("readonly", true);
					$("#totalQty").attr("readonly", true);
					$("#btnAdd").show();
					$("#btnEdit").hide();
				} else {

					let accessoriesIndentNo = $("#accessoriesIndentId").val();
					$("#loader").show();
					$.ajax({
						type: 'POST',
						dataType: 'json',
						url: './editAccessoriesIndent',
						data: {
							autoid: autoId,
							purchaseOrder: purchaseOrdersId,
							styleId: stylesId,
							itemId: itemsId,
							itemColorId: colorsId,
							shippingmark: shippingMarks,
							aiNo: accessoriesIndentNo,
							accessoriesId: accessoriesItemId,
							accessoriesname: accessoriesItemName,
							accessoriessize: accessoriesSize,
							accessoriesColorId: accessoriesColorId,
							indentBrandId: accessoriesBrandId,
							orderqty: orderQty,
							qtyindozen: dozenQty,
							reqperpcs: reqPerPcs,
							reqperdozen: reqPerDozen,
							perunit: perUnit,
							totalbox: totalBox,
							dividedby: divideBy,
							extrainpercent: inPercent,
							percentqty: percentQty,
							totalqty: totalQty,
							unitId: unitId,
							grandqty: unitQty,
							user: userId
						},
						success: function (data) {


							if (data == 'successfull') {
								let row = $("#oldIndentRow-" + autoId);
								successAlert("Edit Successfully....");
								$("#indentPurchaseOrder-" + autoId).text(purchaseOrdersId);
								row.attr('data-style-id', stylesId);
								row.attr('data-item-id', itemsId);
								row.attr('data-color-id', colorsId);
								$("#indentStyleNo-" + autoId).text(styleNoText);
								$("#indentItemName-" + autoId).text(itemNameText);
								$("#indentColor-" + autoId).text(colorNameText);
								$("#indentShippingMark-" + autoId).text(shippingMarks);
								row.attr('data-accessories-item-id', accessoriesItemId);
								$("#indentAccessoriesName-" + autoId).text(accessoriesItemName);
								row.attr('data-accessories-size', accessoriesSize);
								row.attr('data-accessories-color-id', accessoriesColorId);
								row.attr('data-accessories-brand-id', accessoriesBrandId);
								row.attr('data-unit-id', unitId);

								row.attr('data-order-qty', orderQty);
								row.attr('data-dozen-qty', dozenQty);
								row.attr('data-req-per-pcs', reqPerPcs);
								row.attr('data-req-per-dozen', reqPerDozen);
								row.attr('data-per-unit', perUnit);
								row.attr('data-total-box', totalBox);
								row.attr('data-divide-by', divideBy);
								row.attr('data-in-percent', inPercent);
								row.attr('data-percent-qty', percentQty);
								row.attr('data-total-qty', totalQty);
								$("#indentUnitQty-" + autoId).text(unitQty);
								$("#unitQty").attr("readonly", true);
								$("#totalQty").attr("readonly", true);
								$("#btnAdd").show();
								$("#btnEdit").hide();
							} else {
								alert(data);
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

			} else {
				warningAlert("Please Select Any Item Name");
				$("#itemName").focus();
			}
		} else {
			warningAlert("Please Select Any Style NO");
			$("#styleNo").focus();
		}
	} else {
		warningAlert("Please Select Any Purchase Order");
		$("#purchaseOrder").focus();
	}


}

function refreshAction() {
	$("#buyerName").selectpicker('deselectAll');
	$("#purchaseOrder").selectpicker('deselectAll');
	$("#styleNo").selectpicker('deselectAll');
	$("#itemName").selectpicker('deselectAll');
	$("#color").selectpicker('deselectAll');
	$("#shippingMark").selectpicker('deselectAll');

	$("#checkPurchaseOrder").prop('checked', false);
	$("#checkStyleNo").prop('checked', false);
	$("#checkItemName").prop('checked', false);
	$("#checkColor").prop('checked', false);
	$("#checkShippingMark").prop('checked', false);

	$("#accessoriesItem").val(0).change();
	$("#accessoriesSize").val("");
	$("#accessoriesColor").val(0).change();
	$("#brand").val(0).change();
	$("#unit").val(0).change();
	$("#unitQty").val(0);

	$("#orderQty").val(0);
	$("#dozenQty").val(0);
	$("#reqPerPcs").val(1);
	$("#reqPerDozen").val(12);
	$("#perUnit").val(0);
	$("#totalBox").val(0);
	$("#divideBy").val(1);
	$("#inPercent").val(0);
	$("#percentQty").val(0);
	$("#totalQty").val(0);

	$("#orderQty").val(0);
	$("#tableList").empty();

	$("#autoId").val("");
	$("#indentType").val("");
	$("#unitQty").attr("readonly", true);
	$("#totalQty").attr("readonly", true);
	$('#btnAdd').show();
	$('#btnEdit').hide();

}


function confirmAction() {
	let userId = $("#userId").val();
	let accessoriesIndentId = $("#accessoriesIndentId").val();

	let rowList = $("#dataList tr");
	let length = rowList.length;


	if (length > 0) {
		if (confirm("Are you Confirm to Save This Accessories Indent?")) {
			newIndentList = $("tr.newIndentRow");

			let accessoriesItems = {};
			accessoriesItems['list'] = [];

			newIndentList.each((index, indentRow) => {
				let id = indentRow.id.slice(13);

				const indent = {
					purchaseOrder: $("#indentPurchaseOrder-" + id).text(),
					styleId: indentRow.getAttribute('data-style-id'),
					styleNo: $("#indentStyleNo-" + id).text(),
					itemId: indentRow.getAttribute('data-item-id'),
					itemName: $("#indentItemName-" + id).text(),
					colorId: indentRow.getAttribute('data-color-id'),
					colorName: $("#indentColor-" + id).text(),
					shippingMark: $("#indentShippingMark-" + id).text(),
					sizeId: indentRow.getAttribute('data-size-id'),
					accessoriesItemId: indentRow.getAttribute('data-accessories-item-id'),
					accessoriesSize: indentRow.getAttribute('data-accessories-size'),
					accessoriesColorId: indentRow.getAttribute('data-accessories-color-id'),
					accessoriesBrandId: indentRow.getAttribute('data-accessories-brand-id'),
					orderQty: indentRow.getAttribute('data-order-qty'),
					dozenQty: indentRow.getAttribute('data-dozen-qty'),
					reqPerPcs: indentRow.getAttribute('data-req-per-pcs'),
					reqPerDozen: indentRow.getAttribute('data-req-per-dozen'),
					perUnit: indentRow.getAttribute('data-per-unit'),
					totalBox: indentRow.getAttribute('data-total-box'),
					divideBy: indentRow.getAttribute('data-divide-by'),
					inPercent: indentRow.getAttribute('data-in-percent'),
					percentQty: indentRow.getAttribute('data-percent-qty'),
					totalQty: indentRow.getAttribute('data-total-qty'),
					unitId: indentRow.getAttribute('data-unit-id'),
					unitQty: $("#indentUnitQty-" + id).text(),
					sqNo: indentRow.getAttribute('data-sq-no'),
					skuNo: indentRow.getAttribute('data-sku-no'),
					userId: userId
				}

				accessoriesItems.list.push(indent);
			})
			$("#loader").show();
			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: './confirmAccessoriesIndent',
				data: {
					accessoriesIndentId: accessoriesIndentId,
					accessoriesItems: JSON.stringify(accessoriesItems),
				},
				success: function (data) {
					if (data.result != 'something wrong') {
						$("#accessoriesIndentId").val(data.result);
						$("#accessoriesId").text(data.result);
						alert("Accessories Save Successfully;")
						$("#loader").hide();
						location.reload();
					} else {
						alert("Incomplete...Something Wrong");
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
	} else {
		warningAlert("You have not added any indent id... Please Insert Any indent Id");
	}

}

function setIndentItem(rowId, indentType) {

	let row = $(`#${indentType}-${rowId}`);
	let poArray = $("#indentPurchaseOrder-" + rowId).text().trim().split(',');
	console.log("array", poArray);
	$("#purchaseOrder").selectpicker('val', poArray);

	let styleIdList = row.attr('data-style-id').trim().split(',');
	let itemIdList = row.attr('data-item-id').trim().split(',');
	let colorIdList = row.attr('data-color-id').trim().split(',');
	let shippingMarkList = $("#indentShippingMark-" + rowId).text().trim().split(',');

	$("#accessoriesItem").val(row.attr('data-accessories-item-id')).change();
	$("#accessoriesSize").val(row.attr('data-accessories-size'));
	$("#accessoriesColor").val(row.attr('data-accessories-color-id')).change();
	$("#brand").val(row.attr('data-accessories-brand-id')).change();
	$("#unit").val(row.attr('data-unit-id')).change();
	$("#unitQty").val(parseFloat($("#indentUnitQty-" + rowId).text()).toFixed(2));
	$("#orderQty").val(parseFloat(row.attr('data-order-qty')).toFixed(2));
	$("#dozenQty").val(parseFloat(row.attr('data-dozen-qty')).toFixed(2));
	$("#reqPerPcs").val(parseFloat(row.attr('data-req-per-pcs')).toFixed(2));
	$("#reqPerDozen").val(parseFloat(row.attr('data-req-per-dozen')).toFixed(2));
	$("#perUnit").val(row.attr('data-per-unit'));
	$("#totalBox").val(row.attr('data-total-box'));
	$("#divideBy").val(parseFloat(row.attr('data-divide-by')).toFixed(2));
	$("#inPercent").val(parseFloat(row.attr('data-in-percent')).toFixed(2));
	$("#percentQty").val(parseFloat(row.attr('data-percent-qty')).toFixed(2));
	$("#totalQty").val(parseFloat(row.attr('data-total-qty')).toFixed(2));
	$("#autoId").val(rowId);
	$("#indentType").val(indentType);
	$("#unitQty").attr("readonly", false);
	$("#totalQty").attr("readonly", false);
	$('#btnAdd').hide();
	$('#btnEdit').show();

	$('html, body').animate({
		scrollTop: $("html,body").offset().top
	}, 400)

	styleListLoadByMultiplePurchaseOrder().then(() => {
		console.log("style id", styleIdList)
		$("#styleNo").selectpicker('val', styleIdList);
		itemNameLoadByMultipleStyleId().then(() => {
			$("#itemName").selectpicker('val', itemIdList);
			colorAndShippingListByMultipleStyleId().then(() => {
				console.log("Color id", colorIdList)
				$("#color").selectpicker('val', colorIdList);
				$("#shippingMark").selectpicker('val', shippingMarkList);
			});
		});
	});

}

function deleteIndentRow(rowId, indentType) {
	if (confirm("Are you sure to Delete this Accessories?")) {
		if (indentType == 'newIndentRow') {
			$("#newIndentRow-" + rowId).remove();
		} else {
			let accessoriesIndentNo = $("#accessoriesIndentId").val();
			$("#loader").show();
			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: './deleteAccessoriesIndent',
				data: {
					accessoriesIndentId: accessoriesIndentNo,
					indentAutoId: rowId
				},
				success: function (data) {
					if (data.result != 'something wrong') {
						alert("Accessories Indent Item Delete Successfully..");
						$("#oldIndentRow-" + rowId).remove();
					} else {
						alert("Incomplete...Something Wrong");
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
	}
}
function saveEvent() {


	let user = $("#user_hidden").val();
	let POno = $("#purchaseOrder option:selected").text();
	//let POno=$("#purchaseOrder").val();
	let style = $("#styleNo").val();
	let item = $("#itemName").val();


	let itemColor = $("#colorName").val();
	let ShippingMark = $("#shippingmark").val();

	let accessoriesItem = $("#accessoriesItem").val();

	let accessoriesSize = $("#accessoriesSize").val();
	let size = $("#size").val();

	let orderqty = $("#orderQty").val();
	let qtyindozen = $("#qtyInDozen").val();


	let reqperpcs = $("#reqPerPcs").val();

	let reqperdozen = $("#reqPerDozen").val();
	let perunit = $("#perUnit").val();
	let totalbox = $("#totalBox").val();
	let dividedby = $("#dividedBy").val();
	let extraInpercent = $("#extraIn").val();
	let percentqty = $("#percentQty").val();
	let totalqty = $("#totalQty").val();

	let unit = $("#unit").val();
	let unitQty = $("#unitQty").val();
	let brand = $("#brand").val();
	let accessoriescolor = $("#color").val();

	if (POno == 0) {
		alert("Select Purchase Order No")
	} else if (style == 0) {
		alert("Select Style No")
	} else if (item == 0) {
		alert("Select Item Name")
	} else if (accessoriesItem == 0) {
		alert("Select accessories Item name")
	} else {

		//conosle.log("style " + style)
		if (style != 0) {
			$("#loader").show();
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
					unitQty: unitQty,
					brand: brand,
					accessoriescolor: accessoriescolor
				},
				success: function (data) {

					$("#dataList").empty();
					$("#dataList").append(AccessoriesDataShowInTable(data.result));

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
	}
}




function setInPercentAndTotalInPreviewTable() {

	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	let inPercent = $("#inPercent").val();
	inPercent = parseFloat(inPercent == '' ? 0 : inPercent);

	let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
	unitValue = unitValue == 0 ? 1 : unitValue;

	let divideBy = $("#divideBy").val();
	divideBy = parseFloat(divideBy == '' || divideBy == 0 ? 1 : divideBy);

	let rowList = $(".orderPreviewRow");
	let length = rowList.length;

	rowList.each((index, row) => {

		let rowId = row.id.slice(9);
		//conosle.log(row);
		let isSizeRequired = row.getAttribute('data-size-required');

		if (isSizeRequired == "true") {
			let sizes = $(".sizes-" + rowId);
			sizes.each((index, td) => {
				let cellId = td.id.slice(9);
				let orderQyt = parseFloat($("#orderQty-" + cellId).text());
				let totalQty = (orderQyt * reqPerPcs);
				let percentQty = (totalQty * inPercent) / 100;
				totalQty = totalQty + percentQty;

				let unitQty = (totalQty / divideBy) / unitValue;

				//conosle.log(cellId,inPercent,percentQty);
				$("#inPercent-" + cellId).text(inPercent.toFixed(1));
				$("#percentQty-" + cellId).text(percentQty.toFixed(1));
				$("#totalQty-" + cellId).val(totalQty.toFixed(1));
				$("#unitQty-" + cellId).val(unitQty.toFixed(1));
			})

		} else {

			let orderQyt = parseFloat($("#orderQty-" + rowId).text());
			let totalQty = (orderQyt * reqPerPcs);
			let percentQty = (totalQty * inPercent) / 100;
			totalQty = totalQty + percentQty;
			let unitQty = (totalQty / divideBy) / unitValue;

			$("#inPercent-" + rowId).text(inPercent.toFixed(1));
			$("#percentQty-" + rowId).text(percentQty.toFixed(1));
			$("#totalQty-" + rowId).val(totalQty.toFixed(1));
			$("#unitQty-" + rowId).val(unitQty.toFixed(1));
		}

	});
}



function setInPercentInPreviewTable(id) {
	let orderQty = parseFloat($("#orderQty-" + id).text());
	let totalQty = $("#totalQty-" + id).val() == '' ? 0 : $("#totalQty-" + id).val();

	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
	unitValue = unitValue == 0 ? 1 : unitValue;

	let divideBy = $("#divideBy").val();
	divideBy = parseFloat(divideBy == '' || divideBy == 0 ? 1 : divideBy);

	let totalReqQty = orderQty * reqPerPcs;
	let percentQty = totalQty - totalReqQty;

	let inPercent = (percentQty * 100) / totalReqQty;

	let unitQty = (totalQty / divideBy) / unitValue;

	$("#inPercent-" + id).text(inPercent.toFixed(1));
	$("#percentQty-" + id).text(percentQty.toFixed(1));
	$("#unitQty-" + id).val(unitQty.toFixed(1));
}

function setTotalByUnitQtyInPreviewTable(id) {
	let orderQty = parseFloat($("#orderQty-" + id).text());
	let unitQty = $("#unitQty-" + id).val() == '' ? 0 : $("#unitQty-" + id).val();

	let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
	unitValue = unitValue == 0 ? 1 : unitValue;

	let divideBy = $("#divideBy").val();
	divideBy = parseFloat(divideBy == '' || divideBy == 0 ? 1 : divideBy);

	let totalQty = unitQty * unitValue * divideBy;

	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	let totalReqQty = orderQty * reqPerPcs;
	let percentQty = totalQty - totalReqQty;

	let inPercent = (percentQty * 100) / totalReqQty;
	$("#totalQty-" + id).val(totalQty.toFixed(1));
	$("#inPercent-" + id).text(inPercent.toFixed(1));
	$("#percentQty-" + id).text(percentQty.toFixed(1));
}

function calculateTotalQtyAndUnitQty() {
	let rowList = $(".orderPreviewRow");
	//let length = rowList.length;
	let totalQty = 0;
	let totalUnitQty = 0;
	let totalOrderQty = 0;
	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	rowList.each((index, row) => {

		let rowId = row.id.slice(9);
		//conosle.log(row);
		let isSizeRequired = row.getAttribute('data-size-required');

		if (isSizeRequired == "true") {
			let sizes = $(".sizes-" + rowId);
			sizes.each((index, td) => {
				let cellId = td.id.slice(9);
				//conosle.log(cellId,inPercent,percentQty);
				totalOrderQty += parseFloat($("#orderQty-" + cellId).text());
				totalQty += parseFloat($("#totalQty-" + cellId).val());
				totalUnitQty += parseFloat($("#unitQty-" + cellId).val());
			})

		} else {
			totalOrderQty += parseFloat($("#orderQty-" + rowId).text());
			totalQty += parseFloat($("#totalQty-" + rowId).val());
			totalUnitQty += parseFloat($("#unitQty-" + rowId).val());
		}

	});
	let totalReqQty = totalOrderQty * reqPerPcs;
	let percentQty = totalQty - totalReqQty;

	if (totalReqQty == 0) totalReqQty = 1;
	let inPercent = (percentQty * 100) / totalReqQty;

	$("#inPercent").val(inPercent.toFixed(2));
	$("#percentQty").val(percentQty.toFixed(2));
	$("#totalQty").val(totalQty.toFixed(2));
	$("#unitQty").val(totalUnitQty.toFixed(2));
}

function requiredperdozen() {
	let orderqty = $("#orderQty").val();
	let perpcs = $("#reqPerPcs").val();
	let qtyindozen = $("#qtyInDozen").val();
	let perdozen = perpcs * qtyindozen;
	let totalqty = orderqty * perpcs;

	$("#reqPerDozen").val(perdozen);

	$("#totalQty").val(totalqty);


}

function totalbox() {
	let orderqty = $("#orderQty").val();
	let perunit = $("#perUnit").val();

	let totalbox = orderqty / perunit;

	$("#totalBox").val(totalbox);

	$("#totalQty").val(totalbox);


}

function dividedBy() {
	let totalbox = $("#totalBox").val();
	let divideby = $("#dividedBy").val();

	let totalQty = totalbox / divideby;



	$("#totalQty").val(totalQty);


}

function setTotalOrderQty() {
	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	let inPercent = $("#inPercent").val();
	inPercent = parseFloat(inPercent == '' ? 0 : inPercent);

	let rowList = $(".orderPreviewRow");
	let totalOrderQty = 0;

	rowList.each((index, row) => {

		let rowId = row.id.slice(9);
		//conosle.log(row);
		let isSizeRequired = row.getAttribute('data-size-required');

		if (isSizeRequired == "true") {
			let sizes = $(".sizes-" + rowId);
			sizes.each((index, td) => {
				let cellId = td.id.slice(9);
				let orderQty = parseFloat($("#orderQty-" + cellId).text());
				totalOrderQty += orderQty;
			});
		} else {
			totalOrderQty += parseFloat($("#orderQty-" + rowId).text());
		}

	});

	$("#orderQty").val(totalOrderQty.toFixed(1));
}

function setTotalByUnitInEditMode() {
	let orderQty = parseFloat($("#orderQty").val());
	let unitQty = $("#unitQty").val() == '' ? 0 : $("#unitQty").val();

	let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
	unitValue = unitValue == 0 ? 1 : unitValue;

	let divideBy = $("#divideBy").val();
	divideBy = parseFloat(divideBy == '' || divideBy == 0 ? 1 : divideBy);

	let totalQty = unitQty * unitValue * divideBy;

	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	let totalReqQty = orderQty * reqPerPcs;
	let percentQty = totalQty - totalReqQty;


	let inPercent = (percentQty * 100) / totalReqQty;
	console.log(totalQty, percentQty, inPercent);
	$("#totalQty").val(totalQty.toFixed(1));
	$("#inPercent").val(inPercent.toFixed(1));
	$("#percentQty").val(percentQty.toFixed(1));
}


function setUnitByTotalInEditMode() {
	let orderQty = parseFloat($("#orderQty").val());
	let totalQty = $("#totalQty").val() == '' ? 0 : $("#totalQty").val();

	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
	unitValue = unitValue == 0 ? 1 : unitValue;

	let divideBy = $("#divideBy").val();
	divideBy = parseFloat(divideBy == '' || divideBy == 0 ? 1 : divideBy);

	let totalReqQty = orderQty * reqPerPcs;
	let percentQty = totalQty - totalReqQty;

	let inPercent = (percentQty * 100) / totalReqQty;

	let unitQty = (totalQty / divideBy) / unitValue;
	console.log(unitQty, percentQty, inPercent);
	$("#inPercent").val(inPercent.toFixed(1));
	$("#percentQty").val(percentQty.toFixed(1));
	$("#unitQty").val(unitQty.toFixed(1));
}
function setUnitQty() {

	let orderQty = $("#orderQty").val();
	//conosle.log("order qty", orderQty);
	orderQty = orderQty == '' ? 0 : orderQty;
	let dozenQty = parseFloat(orderQty / 12).toFixed(1);

	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = (reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs;

	let perUnit = $("#perUnit").val();
	perUnit = parseFloat(perUnit == '' ? 1 : perUnit);

	let totalBox = $("#totalBox").val();
	totalBox = parseFloat(totalBox == '' ? 0 : totalBox);

	let divideBy = $("#divideBy").val();
	divideBy = parseFloat(divideBy == '' ? 1 : divideBy);

	let reqPerDozen = 12 * reqPerPcs;

	let reqQty = orderQty * reqPerPcs;

	let inPercent = $("#inPercent").val();
	inPercent = inPercent == '' ? 0 : inPercent;

	let percentQty = (reqQty * inPercent) / 100;

	let totalQty = reqQty + percentQty;

	$("#dozenQty").val(dozenQty);
	$("#reqPerDozen").val(reqPerDozen);
	$("#percentQty").val(percentQty.toFixed(2));
	$("#totalQty").val(totalQty.toFixed(2));

	let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
	unitValue = unitValue == 0 ? 1 : unitValue;

	let unitQty = parseFloat(((totalQty / divideBy) / unitValue)).toFixed(2);
	$("#unitQty").val(unitQty);
}


function searchAccessoriesIndent(accessoriesIndentId) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getAccessoriesIndentList',
		data: {
			accessoriesIndentId: accessoriesIndentId
		},
		success: function (data) {

			$("#accessoriesId").text(data.result[0].aiNo);
			$("#accessoriesIndentId").val(data.result[0].aiNo);
			drawAccessoriesIndentListTable(data.result);

			$("#exampleModal").modal('hide');
		}
	});

}
function printAccessories() {
	printAccessoriesIndent($("#accessoriesIndentId").val());
}

function printAccessoriesIndent(aiNo) {
	let url = "printAccessoriesIndent/" + aiNo;
	window.open(url, '_blank');

	// $.ajax({
	// 	type: 'GET',
	// 	dataType: 'json',
	// 	url: './accessoriesIndentInfo',
	// 	data: {
	// 		aiNo: aiNo
	// 	},
	// 	success: function (data) {
	// 		if (data == "Success") {
	// 			let url = "printAccessoriesIndent";
	// 			window.open(url, '_blank');

	// 		}
	// 	}
	// });

}

function drawAccessoriesIndentListTable(data) {
	let oldRows = '';
	let length = $("#dataList tr").length;
	//$('#dataTable').dataTable().fnDestroy();
	data.forEach((indent) => {
		let autoId = indent.autoid;
		oldRows += `<tr id='oldIndentRow-${autoId}' class='oldIndentRow' data-style-id='${indent.styleId}' data-item-id='${indent.itemId}' data-color-id='${indent.itemColorId}' data-size-id='${indent.size}' 
										data-accessories-size='${indent.accessoriessize}' data-accessories-item-id='${indent.accessoriesId}' data-accessories-color-id='${indent.accessoriesColorId}' 
										data-accessories-brand-id='${indent.indentBrandId}' data-unit-id='${indent.unitId}'
										data-order-qty='${indent.orderqty}' data-dozen-qty='${indent.qtyindozen}' data-req-per-pcs='${indent.reqperpcs}' data-req-per-dozen='${indent.reqperdozen}' data-per-unit='${indent.perunit}' data-total-box='${indent.totalbox}'
										data-divide-by='${indent.dividedby}' data-in-percent='${indent.extrainpercent}' data-percent-qty='${indent.percentqty}' data-total-qty='${indent.totalqty}' data-sq-no='${indent.sqNo}' data-sku-no='${indent.skuNo}'>
										<td>${++length}</td>
										<td id='indentPurchaseOrder-${autoId}'>${indent.purchaseOrder}</td>
										<td id='indentStyleNo-${autoId}'>${indent.styleNo}</td>
										<td id='indentItemName-${autoId}'>${indent.itemname}</td>
										<td id='indentColor-${autoId}'>${indent.itemcolor}</td>
										<td id='indentShippingMark-${autoId}'>${indent.shippingmark}</td>
										<td id='indentAccessoriesName-${autoId}'>${indent.accessoriesName}</td>
										<td>${indent.sizeName}</td>
										<td id='indentUnitQty-${autoId}'>${parseFloat(indent.requiredUnitQty).toFixed(2)}</td>
										<td ><i class="fa fa-edit" onclick="setIndentItem('${autoId}','oldIndentRow')" style='cursor:pointer;'></i></td>
										<td ><i class="fa fa-trash" onclick="deleteIndentRow('${autoId}','oldIndentRow')" style='cursor:pointer;'></i></td>
									</tr>`

	});
	$("#dataList").empty();
	$("#dataList").append(oldRows);
	// $('#dataTable').DataTable(({ 
	// 	"destroy": true, 
	// }));

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
	$("#accessoriesIndentListSearch").on("keyup", function () {
		let value = $(this).val().toLowerCase();
		$("#dataList tr").filter(function () {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});

/*let idListMicro = ["buyerName","brand","modelNo","motor","factoryId","departmentId","employee","lineId","btnSave"];
idListMicro.forEach((id,index)=>{
  $('#'+id).keyup(function(event){
	if(event.keyCode ===13){
	  event.preventDefault();
	  $("#"+idListMicro[index+1]).focus();
	}
  });
})*/
/*jQuery.extend(jQuery.expr[':'], {
	focusable: function (el, index, selector) {
		return $(el).is('a, button, :input, [tabindex]');
	}
});

$(document).on('keypress', 'input,select,selectpicker,button', function (e) {
	if (e.which == 13) {
		e.preventDefault();
		// Get all focusable elements on the page
		var $canfocus = $(':focusable');
		var index = $canfocus.index(document.activeElement) + 1;
		if (index >= $canfocus.length) index = 0;
		$canfocus.eq(index).focus();
	}
});*/

/*jQuery.extend(jQuery.expr[':'], {
	focusable: function (el, index, selector) {
		return $(el).is('a, button, :input, [tabindex]');
	}
});

$(document).on('keypress', 'input,select', function (e) {
	if (e.which == 13) {
		e.preventDefault();
		var $canfocus = $(':focusable');
		var index = $canfocus.index(document.activeElement) + 1;
		if (index >= $canfocus.length) index = 0;
		$canfocus.eq(index).focus();
	}
});*/
