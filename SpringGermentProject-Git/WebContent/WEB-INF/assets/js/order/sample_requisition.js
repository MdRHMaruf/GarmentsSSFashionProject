var styleValue = 0;
var itemValue = 0;
var colorValue = 0;
var sizevalue = 0;
var poNoValue = 0;
var buyerId = 0;
var find = 0;



var sizeValueListForSet = [];
var sizesListByGroup = JSON;

function previewSampleRequsition(){
	var date=$('#sampleSearchDate').val();
	var userId=$('#userId').val();
	if(date!=null){
		var url = `printDateWiseSampleRequsition/${date}@${userId}`;
		window.open(url, '_blank');
	}
}


function printSampleRequisition(sampleReqId) {
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './sampleRequisitionInfo',
		data: {
			sampleReqId: sampleReqId
		},
		success: function (data) {
			if (data == "Success") {
				var url = "printsampleRequisition";
				window.open(url, '_blank');

			}
		}
	});
}

window.onload = ()=>{
	document.title = "Sample Requisition";

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


	var userId = $("#userId").val();
	console.log("userId "+userId);

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './userWiseNullSampleReqDataList',
		data: {
			userId: userId
		},
		success: function (data) {

			console.log("data "+data.result.length);

			if (data.result.length>0) {
				drawItemTable(data.result);
			} 
		}
	});

};

function sizeLoadByGroup() {

	var groupId = $("#sizeGroup").val().trim();
	var child = "";
	var length = 0;
	if (groupId != "0") {
		length = sizesListByGroup['groupId' + groupId].length;
		for (var i = 0; i < length; i++) {
			//child += " <div class=\"list-group-item pt-0 pb-0 sizeNameList\"> <div class=\"form-group row mb-0\"><label for=\"sizeId" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" class=\"col-md-6 col-form-label-sm pb-0 mb-0\" style=\"height:25px;\">" + sizesListByGroup['groupId' + groupId][i].sizeName + "</label><input type=\"number\" class=\"form-control-sm col-md-6\" id=\"sizeValue" + sizesListByGroup['groupId' + groupId][i].sizeId + "\" style=\"height:25px;\"></div></div>";
			child += " <div class=\"list-group-item pt-0 pb-0\"> <div class=\"form-group row mb-0\"><label for=\"sizeValue" + i + "\" class=\"col-md-6 col-form-label-sm pb-0 mb-0\" style=\"height:25px;\">" + sizesListByGroup['groupId' + groupId][i].sizeName + "</label><input type=\"number\" class=\"form-control-sm col-md-6 sizeValue\" id=\"sizeValue" + i + "\" style=\"height:25px;\"> <input type=\"hidden\" id=\"sizeId" + i + "\" value=" + sizesListByGroup['groupId' + groupId][i].sizeId + "></div></div>";
		}

	}
	$("#listGroup").html(child);
	if (sizeValueListForSet.length > 0) {
		for (var i = 0; i < length; i++) {
			$("#sizeValue" + i).val(sizeValueListForSet[i].sizeQuantity);
		}
		sizeValueListForSet = [];
	}

}

function searchSampleRequisition(v) {

	 $('#exampleModal').modal('hide');
	var user = $("#userId").val();
	var sampleReqId = v;

	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './searchSampleRequisition',
		data:{
			sampleReqId:sampleReqId,
			user:user
		},
		success: function (data) {
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawItemTable(data.result);
				$('#exampleModal').modal('hide');
			}
		}
	});
}

function confirmAction() {

	var buyerId = $("#buyerId").val();
	var styleId = $("#styleNo").val();
	var itemId = $("#itemName").val();
	var colorId = $("#colorName").val();
	var sizeGroupId = $("#sizeGroup").val();
	var sampleId = $("#sampleId").val();
	var buyerOrderId = $("#purchaseOrder").val();
	var purchaseOrder="";
	if(buyerOrderId!=0){
		purchaseOrder=$("#purchaseOrder :selected").text();
	}
	var inchargeId = $("#inchargeId").val();
	var marchendizerId = $("#marchendizerId").val();
	var instruction = $("#instruction").val();
	var sampleDeadline = $("#sampleDeadline").val();
	var sampleId = $("#sampleId").val();
	var userId = $("#userId").val();

	if(sampleId!='0'){
		
		if(sampleDeadline!=''){
			var sizeListLength = $(".sizeValue").length;
			var sizeList = "";
			for (var i = 0; i < sizeListLength; i++) {
				var quantity = $("#sizeValue" + i).val().trim() == "" ? "0" : $("#sizeValue" + i).val().trim();
				var id = $("#sizeId" + i).val().trim();
				sizeList += "id=" + id + ",quantity=" + quantity + " ";
			}
			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: './confirmItemToSampleRequisition',
				data: {
					buyerId: buyerId,
					styleId: styleId,
					itemId: itemId,
					colorId: colorId,
					buyerOrderId:buyerOrderId,
					purchaseOrder: purchaseOrder,
					userId: userId,
					inchargeId: inchargeId,
					marchendizerId: marchendizerId,
					instruction: instruction,
					sampleDeadline: sampleDeadline,
					sampleId: sampleId
				},
				success: function (data) {
					alert(data);
					refreshAction();
				}
			});
		}
		else{
			alert("Provide Deadline");
		}
		

	}
	else{
		alert("Provide Sample Type");
	}


}

function refreshAction() {
	location.reload();
}

function itemSizeEdit(){
	
	var sampleReqId = $("#sampleReqId").val();
	var sampleAutoId=$("#sampleAutoId").val();
	
	var buyerId = $("#buyerId").val();
	var styleId = $("#styleNo").val();
	var itemId = $("#itemName").val();
	var colorId = $("#colorName").val();
	var sizeGroupId = $("#sizeGroup").val();
	var sampleId = $("#sampleId").val();
	var buyerOrderId = $("#purchaseOrder").val();
	var purchaseOrder="";
	if(buyerOrderId!=0){
		purchaseOrder=$("#purchaseOrder :selected").text();
	}
	var inchargeId = $("#inchargeId").val();
	var instruction = $("#instruction").val();
	var sampleDeadline = $("#sampleDeadline").val();
	var sampleId = $("#sampleId").val();
	var userId = $("#userId").val();

	
	
	if(sampleReqId!='0'){
		if (styleId != 0) {
			if (itemId != 0) {
				if (colorId != 0) {
					if (sizeGroupId != 0) {
						var sizeListLength = $(".sizeValue").length;
						var sizeList = "";
						for (var i = 0; i < sizeListLength; i++) {
							var quantity = $("#sizeValue" + i).val().trim() == "" ? "0" : $("#sizeValue" + i).val().trim();
							var id = $("#sizeId" + i).val().trim();
							sizeList += "id=" + id + ",quantity=" + quantity + " ";
						}


						$.ajax({
							type: 'POST',
							dataType: 'json',
							url: './editItemToSampleRequisition',
							data: {
								autoId:sampleAutoId,
								sampleReqId:sampleReqId,
								buyerId: buyerId,
								styleId: styleId,
								itemId: itemId,
								colorId: colorId,
								buyerOrderId:buyerOrderId,
								purchaseOrder: purchaseOrder,
								sizeGroupId: sizeGroupId,
								sizeListString: sizeList,
								userId: userId,
								inchargeId: inchargeId,
								instruction: instruction,
								sampleDeadline: sampleDeadline,
								sampleId: sampleId
							},
							success: function (data) {
								if (data.result == "Something Wrong") {
									dangerAlert("Something went wrong");
								} else if (data.result == "duplicate") {
									dangerAlert("Duplicate Item Name..This Item Name Already Exist")
								} else {
									alert("Sample Requisition Size Update Successfully");
									drawItemTable(data.result);
								}
							}
						});
					} else {
						alert("Size Group not selected ... Please Select Size group");
						warningAlert("Size Group not selected ... Please Select Size group");
						$("#sizeGroup").focus();
					}
				} else {
					alert("Color Not Selected... Please Select Color");
					warningAlert("Color Not Selected... Please Select Color");
					$("#color").focus();
				}
			} else {
				alert("Item Type not selected... Please Select Item Type");
				warningAlert("Item Type not selected... Please Select Item Type");
				$("#itemType").focus();
			}
		} else {
			alert("Style No not selected... Please Select Style No");
			warningAlert("Style No not selected... Please Select Style No");
			$("#styleNo").focus();
		}
	}
	else{
		alert("Style No not selected... Please Select Style No");
		warningAlert("Style No not selected... Please Select Style No");
	}
	

}

function itemSizeAdd() {


	var buyerId = $("#buyerId").val();
	var styleId = $("#styleNo").val();
	var itemId = $("#itemName").val();
	var colorId = $("#colorName").val();
	var sizeGroupId = $("#sizeGroup").val();
	var sampleId = $("#sampleId").val();
	var buyerOrderId = $("#purchaseOrder").val();
	var purchaseOrder="";
	if(buyerOrderId!=0){
		purchaseOrder=$("#purchaseOrder :selected").text();
	}
	var inchargeId = $("#inchargeId").val();
	var instruction = $("#instruction").val();
	var sampleDeadline = $("#sampleDeadline").val();
	var sampleId = $("#sampleId").val();
	var userId = $("#userId").val();


	if (styleId != 0) {
		if (itemId != 0) {
				if (sizeGroupId != 0) {
					var sizeListLength = $(".sizeValue").length;
					var sizeList = "";
					for (var i = 0; i < sizeListLength; i++) {
						var quantity = $("#sizeValue" + i).val().trim() == "" ? "0" : $("#sizeValue" + i).val().trim();
						var id = $("#sizeId" + i).val().trim();
						sizeList += "id=" + id + ",quantity=" + quantity + " ";
					}


					$.ajax({
						type: 'POST',
						dataType: 'json',
						url: './addItemToSampleRequisition',
						data: {
							autoId: "0",
							buyerId: buyerId,
							styleId: styleId,
							itemId: itemId,
							colorId: colorId,
							buyerOrderId:buyerOrderId,
							purchaseOrder: purchaseOrder,
							sizeGroupId: sizeGroupId,
							sizeListString: sizeList,
							userId: userId,
							inchargeId: inchargeId,
							instruction: instruction,
							sampleDeadline: sampleDeadline,
							sampleId: sampleId
						},
						success: function (data) {
							if (data.result == "Something Wrong") {
								dangerAlert("Something went wrong");
							} else if (data.result == "duplicate") {
								dangerAlert("Duplicate Item Name..This Item Name Already Exist")
							} else {
								drawItemTable(data.result);
							}
						}
					});
				} else {
					alert("Size Group not selected ... Please Select Size group");
					warningAlert("Size Group not selected ... Please Select Size group");
					$("#sizeGroup").focus();
				}
		} else {
			alert("Item Type not selected... Please Select Item Type");
			warningAlert("Item Type not selected... Please Select Item Type");
			$("#itemType").focus();
		}
	} else {
		alert("Style No not selected... Please Select Style No");
		warningAlert("Style No not selected... Please Select Style No");
		$("#styleNo").focus();
	}


}



function drawItemTable(dataList) {


	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];

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
				<th scope="col">Purchase Order</th>`
				var sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
			for (var j = 0; j < sizeListLength; j++) {
				tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
			}
			tables += `<th scope="col">Total Units</th>
				<th scope="col"><i class="fa fa-edit"></i></th>
				<th scope="col"><i class="fa fa-trash"></i></th>
				</tr>
				</thead>
				<tbody id="dataList">`
				isClosingNeed = true;
		}
		tables += "<tr id='itemRow" + i + "' data-id='" + item.autoId + "'><td>" + item.styleNo + "</td><td>" + item.itemName + "</td><td>" + item.colorName + "</td><td>" + item.purchaseOrder + "</td>"
		var sizeList = item.sizeList;
		var sizeListLength = sizeList.length;
		var totalSizeQty = 0;
		for (var j = 0; j < sizeListLength; j++) {
			totalSizeQty = totalSizeQty + parseFloat(sizeList[j].sizeQuantity);
			tables += "<td>" + sizeList[j].sizeQuantity + "</td>"
		}
		tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'>" + totalSizeQty + "</td><td><i class='fa fa-edit' onclick='setSampleRequisitionItem(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteSampleRequisitioonItem(" + item.autoId + ","+item.sampleReqId+")'> </i></td></tr>";
	}
	tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
}

function deleteSampleRequisitioonItem(sapleAutoId,sampleReqId){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		data:{
			sapleAutoId:sapleAutoId,
			sampleReqId:sampleReqId
		},
		url: './deleteSampleRequisitionItem',
		success: function (data) {
			
			console.log("sampleDelete "+data.result);
			if(data.result=='Sorry You are already confirm sample requisition'){
				alert("Sorry You are already confirm sample requisition");
			}
			else if(data.result=='Something has wrong!!'){
				alert("Something has wrong!!");
			}
			else{
				alert("Sample Requisition Item Successfully Delete");
				drawItemTable(data.result);
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


function setSampleRequisitionItem(itemAutoId) {
	 //getBuyerPOItem
	$.ajax({
    type: 'POST',
    dataType: 'json',
    url: './getSampleRequistionItemData',
    data: {
      itemAutoId: itemAutoId
    },
    success: function (data) {
      if (data.result == "Something Wrong") {
        dangerAlert("Something went wrong");
      } else if (data.result == "duplicate") {
        dangerAlert("Duplicate Unit Name..This Unit Name Already Exist")
      } else {

        let item = data.result;

        $("#itemAutoId").val(item[0].autoId);
        $("#customerOrder").val(item[0].customerOrder);
        $("#purchaseOrder").val(item[0].purchaseOrder);
        $("#shippingMark").val(item[0].shippingMark);
        $("#factory").val(item[0].factoryId).change();
        $("#color").val(item[0].colorId).change();

        console.log("item.sizeList "+item[0].sizeList);
        sizeValueListForSet = item[0].sizeList;
        $("#sizeGroup").val(item[0].sizeGroupId).change();

        $("#itemAutoId").val(itemAutoId);
        $("#btnAdd").hide();
        $("#btnEditItemSize").show();
        
        $('#btnEditItemSize').prop('disabled', false);

        styleIdForSet = item[0].styleId;
        itemIdForSet = item[0].itemId;
        poNoValue=item[0].purchaseOrder;
        styleValue=item[0].styleId;
        itemValue=item[0].itemId;
        colorValue=item[0].colorId;
        $("#buyerId").val(item[0].buyerId).change();
        $("#sampleId").val(item[0].sampleId);

        console.log("sampleReqId "+item[0].sampleReqId);
        
        $('#sampleReqId').val(item[0].sampleReqId);
        $('#sampleAutoId').val(item[0].autoId);
      }
    }
  });

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

function buyerWisePoLoad() {
	var buyerId = $("#buyerId").val();
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

	var itemList = data;
	var options = "<option id='purchaseOrder' value='0' selected>Select Purchase Order</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option id='purchaseOrder' value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("purchaseOrder").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#purchaseOrder').val(poNoValue).change();
	poNoValue = 0;
}



/*function poWiseStyles() {

	var po = $("#purchaseOrder").val();

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
}*/

function loadStyles(data) {

	var itemList = data;
	var options = "<option id='styleNo' value='0' selected>Select Style</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option id='styleNo' value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#styleNo').val(styleValue).change();
	styleValue = 0;

}

function styleWiseItemLoad() {
	var styleId = $("#styleNo").val();

	if (styleId != 0) {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleWiseItem',
			data: {
				styleId: styleId
			},
			success: function (data) {

				var itemList = data.itemList;
				var options = "<option  value='0' selected>Select Item Name</option>";
				var length = itemList.length;
				for (var i = 0; i < length; i++) {
					options += "<option  value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
				};
				document.getElementById("itemName").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$('#itemName').val(itemValue).change();
				itemValue = 0;
			}
		});
		
		
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleWisePurchaseOrder/'+styleId,
			success: function (data) {

				var itemList = data.result;
				var options = "<option  value='0' selected>Select Purchase Order</option>";
				var length = itemList.length;
				for (var i = 0; i < length; i++) {
					options += "<option  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
				};
				document.getElementById("purchaseOrder").innerHTML = options;
				$('.selectpicker').selectpicker('refresh');
				$('#purchaseOrder').val(poNoValue).change();
				poNoValue = 0;
				
			}
		});
	} else {
		var options = "<option  value='0' selected>Select Item Name</option>";
		$("#itemName").html(options);
		$('#itemName').selectpicker('refresh');
		$('#itemName').val(0).change();
		itemValue = 0;
	}

}

function loatItems(data) {

	var itemList = data;
	var options = "<option id='itemType' value='0' selected>Select Item Type</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option id='itemType'  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("itemName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#itemName').val(itemValue).change();
	itemValue = 0;

}

function styleItemsWiseColor() {
	var buyerorderid = $("#purchaseOrder").val();
	var style = $("#styleNo").val();
	var item = $('#itemName').val();

	var poStatus = $('input[name=active]:checked').val();

	console.log("poStatus "+poStatus);

	if(poStatus!=0){
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
	else{
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './getAllColor/',
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

	var itemList = data;
	var options = "<option id='colorName' value='0' selected>Select Color Type</option>";
	var length = itemList.length;
	for (var i = 0; i < length; i++) {
		options += "<option id='colorName'  value='" + itemList[i].id + "'>" + itemList[i].name + "</option>";
	};
	document.getElementById("colorName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#colorName').val(colorValue).change();
	colorValue = 0;

}



$(document).ready(function () {
	$("input:text").focus(function () { $(this).select(); });
});

$(document).ready(function () {
	$("#search").on("keyup", function () {
		var value = $(this).val().toLowerCase();
		$("#datalist tr").filter(function () {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});


