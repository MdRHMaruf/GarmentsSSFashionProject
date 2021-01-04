let stylevalue = 0;
let itemvalue = 0;
let colorvalue = 0;
let sizevalue = 0;
let find = 0;

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
	let buyerId = $("#buyerName").val();

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


				let inPercent = $("#inPercent").val();
				inPercent = inPercent == '' ? 0 : inPercent;

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
								on boed.autoId = sv.linkedAutoId and sv.type = 1
								left join tbStyleSize ss
								on sv.sizeId = ss.id 
								where ${queryPurchaseOrder + " " + queryStylesId + " " + queryItemsId + " " + queryColorsId + " " + queryShippingMarks} and boed.sizeGroupId = 'SIZEGROUPID'
								group by ${groupBy} boed.sizeGroupId,ss.sortingNo,ss.id,ss.sizeName
								order by boed.sizeGroupId, ${groupBy} ss.sortingNo`;
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
										tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + item.sizeList[j].sizeName + "</th>";
									}
									tables += `<th scope="col"><i class="fa fa-edit"></i></th>
												<th scope="col"><i class="fa fa-trash"></i></th>
												</tr>
											</thead>
											<tbody id="orderList-${sizeGroupId}" class="orderPreview">`
									isClosingNeed = true;
								}
								tables += `<tr id='orderRow-${i}' class='orderPreviewRow' data-size-required='true' data-size-group-id="${item.sizeGroupId}" data-style-id='${item.styleId}' data-item-id='${item.itemId}' data-color-id='${item.itemColorId}'>
											<td id='purchaseOrder-${i}'>${item.purchaseOrder}</td>
											<td id='styleNo-${i}'>${item.styleNo}</td>
											<td id='itemName-${i}'>${item.itemname}</td>
											<td id='itemColor-${i}'>${item.itemcolor}</td>
											<td id='shippingMark-${i}'>${item.shippingmark}</td>
											<td>OrderQty</td>`;
								let sizeList = item.sizeList;
								let sizeListLength = sizeList.length;

								for (let j = 0; j < sizeListLength; j++) {

									tables += `<td id='orderQty-${i}${sizeList[j].sizeId}' class='sizes-${i}'>${sizeList[j].sizeQuantity}</td>`
								}
								tables += `<td><i class='fa fa-edit' > </i></td><td><i class='fa fa-trash'> </i></td></tr>`;


								tables += `<tr>
												<td colspan="5" rowspan="2"></td>
												<td>(%) Qty</td>`
								for (let j = 0; j < sizeListLength; j++) {
									if (sizeList[j].sizeQuantity > 0) {
										tables += `<td><span id='inPercent-${i}${sizeList[j].sizeId}'>${inPercent}</span>% (<span id='percentQty-${i}${sizeList[j].sizeId}'>${parseFloat((sizeList[j].sizeQuantity * inPercent) / 100).toFixed(1)}</span>)</td>`;
									} else {
										tables += `<td></td>`;
									}
								}
								tables += "<td colspan='2' rowspan='2'></td></tr>"

								tables += `<tr>
												<td>Total</td>`
								for (let j = 0; j < sizeListLength; j++) {
									if (sizeList[j].sizeQuantity > 0) {
										tables += `<td><input id='totalQty-${i}${sizeList[j].sizeId}' class='form-control-sm max-width-100 min-width-60 total-${i} sizeGroup-${item.sizeGroupId}' type='number' value="${(parseFloat(sizeList[j].sizeQuantity) + ((sizeList[j].sizeQuantity * inPercent) / 100)).toFixed(1)}"/></td>`;
									} else {
										tables += `<td></td>`;
									}
								}
								tables += "</tr>"
							}
							tables += "</tbody></table> </div></div>";

							$("#tableList").empty();
							$("#tableList").append(tables);


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
															<th ><i class="fa fa-edit"></i></th>
															<th ><i class="fa fa-trash"></i></th>
														</tr>
													</thead>
													<tbody id="orderList" class="orderPreview">`
							let dataList = data.dataList;
							let length = dataList.length;
							let orderQty = 0;
							for (let i = 0; i < length; i++) {
								let item = dataList[i];
								tables += `<tr id='orderRow-${i}' class='orderPreviewRow' data-size-required='false' data-style-id='${item.styleId}' data-item-id='${item.itemId}' data-color-id='${item.itemColorId}'>
											<td id='purchaseOrder-${i}'>${item.purchaseOrder} </td>
											<td id='styleNo-${i}'>${item.styleNo} </td>
											<td id='itemName-${i}'>${item.itemname} </td>
											<td id='color-${i}'>${item.itemcolor} </td>
											<td id='shippingMark-${i}'>${item.shippingmark} </td>
											<td id='orderQty-${i}'>${parseFloat(item.orderqty).toFixed(1)} </td>
											<td><span id='inPercent-${i}'>${inPercent}</span>% (<span id="percentQty-${i}">${parseFloat((item.orderqty * inPercent) / 100).toFixed(1)} </span>) </td>
											<td><input class='form-control-sm max-width-100 min-width-60' id='totalQty-${i}' type="number" value="${(parseFloat(item.orderqty) + ((item.orderqty * inPercent) / 100)).toFixed(1)}"/></td>
											<td ><i class="fa fa-edit" onclick="editAction(${i})" style='cursor:pointer;'></i></td>
											<td ><i class="fa fa-trash" onclick="deleteAction(${i})" style='cursor:pointer;'></i></td>
										</tr>`;
								orderQty += parseFloat(item.orderqty);
							}
							tables += "</tbody></table> </div></div>";
							$("#tableList").empty();
							$("#tableList").append(tables);


							$("#orderQty").val(orderQty);
							setGrandQty();
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
})

$("#btnAdd").click(() => {
	let rowList = $(".orderPreviewRow");
	let length = rowList.length;

	if (length > 0) {
		let accessoriesItem = $("#accessoriesItem").val();
		let accessoriesSize = $("#accessoriesSize").val();
		let accessoriesColor = $("#accessoriesColor").val();
		let accessoriesBrand = $("#brand").val();
		let unit = $("#unit").val();
		let grandQty = $("#grandQty").val();

		if (accessoriesItem != 0) {
			if (unit > 0) {
				for (let i = 0; i < accessoriesItem.length; i++) {
					let accessoriesItemId = accessoriesItem[i];
					let accessoriesItemName = $("#accessoriesItem option[value='" + accessoriesItemId + "']").text();

					rowList.each((index, row) => {
						let rowId = row.id.slice(9);
						console.log(row);
						let isSizeRequired = row.getAttribute('data-size-required');

						let purchaseOrder = $("#purchaseOrder-" + rowId).text();
						let styleId = row.getAttribute('data-style-id');
						let itemId = row.getAttribute('data-item-id');
						let colorId = row.getAttribute('data-color-id');
						let styleNo = $("#styleNo-" + rowId).text();
						let itemName = $("#itemName-" + rowId).text();
						let color = $("#color-" + rowId).text();;
						let shippingMark = $("#shippingMark-" + rowId).text();
						let totalRequired = $("#totalQty-" + rowId).val();
						if (isSizeRequired) {
							let newRow = `<tr>
										<td>${i}</td>
										<td>${purchaseOrder}</td>
										<td>${styleNo}</td>
										<td>${itemName}</td>
										<td>${color}</td>
										<td>${shippingMark}</td>
										<td>${accessoriesItemName}</td>
										<td></td>
										<td>${totalRequired}</td>
										<td ><i class="fa fa-edit" onclick="editAction(${i})" style='cursor:pointer;'></i></td>
									</tr>`
							$("#dataList").append(newRow);
						} else {

						}
					});
				}
			} else {
				warningAlert("Unit Selected.....Please Select Unit");
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

function setInPercentAndTotalInPreviewTable() {
	console.log("calculate");
	let reqPerPcs =$("#reqPerPcs").val();
	reqPerPcs =  parseFloat((reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs);

	let inPercent = $("#inPercent").val();
	inPercent =  parseFloat(inPercent == '' ? 0 : inPercent);

	let rowList = $(".orderPreviewRow");
	let length = rowList.length;

	rowList.each((index, row) => {

		let rowId = row.id.slice(9);
		console.log(row);
		let isSizeRequired = row.getAttribute('data-size-required');

		if (isSizeRequired == "true") {
			let sizes = $(".sizes-" + rowId);
			sizes.each((index, td) => {
				let cellId = td.id.slice(9);
				let orderQyt = parseFloat($("#orderQty-" + cellId).text());
				let totalQty = (orderQyt * reqPerPcs);
				let percentQty = (totalQty * inPercent) / 100;
				totalQty = totalQty + percentQty;

				console.log(cellId,inPercent,percentQty);
				$("#inPercent-" + cellId).text(inPercent.toFixed(1));
				$("#percentQty-" + cellId).text(percentQty.toFixed(1));
				$("#totalQty-" + cellId).val(totalQty.toFixed(1));
			})

		} else {

			let orderQyt = parseFloat($("#orderQty-" + rowId).text());
			let totalQty = (orderQyt * reqPerPcs);
			let percentQty = (totalQty * inPercent) / 100;
			totalQty = totalQty + percentQty;


			$("#inPercent-" + rowId).text(inPercent.toFixed(1));
			$("#percentQty-" + rowId).text(percentQty.toFixed(1));
			$("#totalQty-" + rowId).val(totalQty.toFixed(1));
		}

	});
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


function setGrandQty() {

	let orderQty = $("#orderQty").val();
	console.log("order qty", orderQty);
	orderQty = orderQty == '' ? 0 : orderQty;
	let dozenQty = parseFloat(orderQty / 12).toFixed(1);

	let reqPerPcs = $("#reqPerPcs").val();
	reqPerPcs = (reqPerPcs == 0 || reqPerPcs == '') ? 1 : reqPerPcs;

	let reqPerDozen = 12 * reqPerPcs;

	let reqQty = orderQty * reqPerPcs;

	let inPercent = $("#inPercent").val();
	inPercent = inPercent == '' ? 0 : inPercent;

	let percentQty = (reqQty * inPercent) / 100;

	let totalQty = reqQty + percentQty;

	$("#dozenQty").val(dozenQty);
	$("#reqPerDozen").val(reqPerDozen);
	$("#percentQty").val(percentQty);
	$("#totalQty").val(totalQty);

	let unitValue = parseFloat($('#unit').val() == '0' ? "1" : unitList[$('#unit').val()].unitValue);
	unitValue = unitValue == 0 ? 1 : unitValue;

	let grandQty = parseFloat((totalQty / unitValue));
	$("#grandQty").val(grandQty);
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
				let url = "printAccessoriesIndent";
				window.open(url, '_blank');

			}
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
	console.log("order qty " + data[0].qty);
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

	let grandQty = parseFloat((totalQty / unitValue));


	$("#grandQty").val(grandQty);

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


	let po = $("#purchaseOrder").val();

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
	let itemList = data;
	let options = "<option  value='0' selected>Select Style</option>";
	let length = itemList.length;
	for (let i = 0; i < length; i++) {
		options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	console.log("style " + stylevalue);
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



function confrimEvent() {
	let user = $("#user_hidden").val();
	let aiNo = $("#aiNo").val();

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

	let autoid = $("#accIndentId").val();
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

	let unit = $("#unit").text();
	let grandqty = $("#grandQty").val();
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
	let grandqty = $("#grandQty").val();
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
	let rows = [];
	let length = data.length;

	for (let i = 0; i < length; i++) {
		rows.push(drawRowDataTable(data[i], i + 1));
	}

	return rows;
}

function drawRowDataTable(rowData, c) {

	let row = $("<tr />")
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
	let itemList = data;


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