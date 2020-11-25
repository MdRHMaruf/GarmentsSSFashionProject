
function printLayoutDetails(buyerId, buyerOrderId, styleId, itemId, layoutDate) {
	var layoutDate = $('#layout' + itemId).html();
	const type = $("#type").val();
	const layoutName = "Line Inspection Layout";
	var url = `printLayoutInfo/${buyerId}@${buyerOrderId}@${styleId}@${itemId}@${layoutDate}@${type}@${layoutName}`;
	window.open(url, '_blank');


}

function setProductPlanInfo(buyerId, buyerOrderId, styleId, itemId, planQty) {


	var buyerName = $('#buyerId' + buyerId).html();
	var purchaseOrder = $('#purchaseOrder' + buyerOrderId).html();
	var styleNo = $('#styleId' + styleId).html();
	var itemName = $('#itemId' + itemId).html();
	var type = $('#type').val();

	$('#buyerName').val(buyerName);
	$('#purchaseOrder').val(purchaseOrder);
	$('#styleNo').val(styleNo);
	$('#itemName').val(itemName);
	$('#planQty').val(planQty);
	$('#buyerId').val(buyerId);
	$('#buyerOrderId').val(buyerOrderId);
	$('#styleId').val(styleId);
	$('#itemId').val(itemId);
	$('#exampleModal').modal('hide');
	$.ajax({
		type: 'GET',
		dataType: 'json',
		data: {
			buyerId: buyerId,
			buyerorderId: buyerOrderId,
			purchaseOrder: purchaseOrder,
			styleId: styleId,
			itemId: itemId,
			planQty: planQty,
			layoutName: type
		},
		url: './searchSewingLineSetup',
		success: function (data) {
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				//drawItemTable(data.result, data.employeeresult);

				loadLine(data.result);
				if (data.result.length > 0) {
					const item = data.result[0];
					$('#dailyTargetQty').val(parseFloat(item.dailyTarget).toFixed(2));
					$('#dailyLineTargetQty').val(parseFloat(item.dailyLineTarget).toFixed(2));
					$('#hours').val(10);
					$('#hourlyTarget').val(parseFloat(item.hourlyTarget).toFixed(2));
				}else{
					$('#dailyTargetQty').val(parseFloat(0).toFixed(2));
					$('#dailyLineTargetQty').val(parseFloat(0).toFixed(2));
					$('#hours').val(10);
					$('#hourlyTarget').val(parseFloat(0).toFixed(2));
				}

			}
		}
	});
}

function loadLine(dataList) {
	$("#line").children().remove();
	let options = '<option value="0">Select Line</option>'
	const length = dataList.length;
	for (let i = 0; i < length; i++) {
		options += `<option value="${dataList[i].lineId}"> ${dataList[i].lineName}</option>`
	}
	$("#line").append(options);
}

function lineChangeAction() {
	const lineId = $("#line").val();
	const buyerId = $("#buyerId").val();
	const buyerOrderId = $("#buyerOrderId").val();
	const styleId = $("#styleId").val();
	const itemId = $("#itemId").val();
	const layoutDate = $("#layoutDate").val();
	const type = $('#type').val();

	if (lineId != '0') {
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './searchLayoutLineData',
			data: {
				buyerId: buyerId,
				buyerorderId: buyerOrderId,
				styleId: styleId,
				itemId: itemId,
				layoutDate: layoutDate,
				layoutName: type,
				lineId: lineId
			},
			success: function (data) {

				if (data.result == "Something Wrong") {
					dangerAlert("Something went wrong");
				} else if (data.result == "duplicate") {
					dangerAlert("Duplicate Item Name..This Item Name Already Exist")
				} else {
					drawItemTable(data.result, data.processList);
					$("#btnSubmit").prop('disabled', true);
					$("#inspectionListModal").modal('hide');
				}
			}
		});
	} else {
		$("#tableList").innerHTML('');
	}
}

function getOptions(dataList) {
	let options = "";
	var length = dataList.length;

	options += "<option value='0'>Select Process</option>"
	for (var i = 0; i < length; i++) {
		var item = dataList[i];

		options += "<option  value='" + item.processId + "'>" + item.processName + "</option>"
	}
	return options;
};

function drawItemTable(dataList, processList) {

	const processOptions = getOptions(processList);

	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];
		if (i == 0) {
			tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				<th scope="col">M. Name </th>
				<th scope="col">Process Name</th>
				<th scope="col">Operator Name</th>

				<th scope="col">08-09</th>
				<th scope="col">09-10</th>
				<th scope="col">10-11</th>
				<th scope="col">11-12</th>
				<th scope="col">12-01</th>
				<th scope="col">02-03</th>
				<th scope="col">03-04</th>
				<th scope="col">04-05</th>
				<th scope="col">05-06</th>
				<th scope="col">06-07</th>
				<th scope="col">Total</th>
				
				</tr>
				</thead>
				<tbody id="dataList">`

			//<th scope="col">Edit</th>

		}

		tables += "<tr class='itemRow' id='row-" + item.machineId + "' data-id='" + item.machineId + "' data-machine-id='"+item.machineId+"' data-auto-id='" + item.autoId + "'>" +
			"<td>" + item.machineName + "</br><input  type='hidden' class='form-control-sm line-" + item.machineId + "'  value='" + parseFloat(item.machineId).toFixed() + "' /></td>" +
			"<td><select id='process-" + item.machineId + "'  class='selectpicker employee-width tableSelect employee-" + item.machineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + processOptions + "</select></td>" +
			"<td>"+item.operatorName+"</td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h1'  value='" + Number(item.hour1).toFixed(0) + "' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h2'  value='" + Number(item.hour2).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h3'  value='" + Number(item.hour3).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h4'  value='" + Number(item.hour4).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h5'  value='" + Number(item.hour5).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h6'  value='" + Number(item.hour6).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h7'  value='" + Number(item.hour7).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h8'  value='" + Number(item.hour8).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h9'  value='" + Number(item.hour9).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h10'  value='" + Number(item.hour10).toFixed(0) + "'/></td>" +
			"<td><input  type='number' id='line-" + item.machineId + "-total' readonly class='form-control-sm' value='" + (Number(item.hour1) + Number(item.hour2) + Number(item.hour3) + Number(item.hour4) + Number(item.hour5) + Number(item.hour6) + Number(item.hour7) + Number(item.hour8) + Number(item.hour9) + Number(item.hour10)).toFixed(0) + "'/></td>" +
			"</tr>"
		//"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm'><i class='fa fa-edit'></i></button></td>
		// $('#dailyTargetQty').val(parseFloat(item.dailyTarget).toFixed(2));
		// $('#dailyLineTargetQty').val(parseFloat(item.dailyLineTarget).toFixed(2));
		// $('#hours').val(10);
		// $('#hourlyTarget').val(parseFloat(item.hourlyTarget).toFixed(2));

	}

	tables += "</tbody></table> </div></div>";
	// tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
	$('.tableSelect').selectpicker('refresh');
}


function drawSearchItemTable(dataList, processList) {

	const processOptions = getOptions(processList);

	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;


	for (var i = 0; i < length; i++) {
		var item = dataList[i];
		if (i == 0) {
			tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				<th scope="col">M. Name </th>
				<th scope="col">Process Name</th>
				<th scope="col">Operator Name</th>

				<th scope="col">08-09</th>
				<th scope="col">09-10</th>
				<th scope="col">10-11</th>
				<th scope="col">11-12</th>
				<th scope="col">12-01</th>
				<th scope="col">02-03</th>
				<th scope="col">03-04</th>
				<th scope="col">04-05</th>
				<th scope="col">05-06</th>
				<th scope="col">06-07</th>
				<th scope="col">Total</th>
				<th scope="col">Edit</th>
				</tr>
				</thead>
				<tbody id="dataList">`

			$("#buyerName").val(item.buyerName);
			$("#purchaseOrder").val(item.purchaseOrder);
			$("#styleNo").val(item.styleNo);
			$("#itemName").val(item.itemName);
			$('#planQty').val(parseFloat(item.planQty).toFixed(2));
			$('#dailyTargetQty').val(parseFloat(item.dailyTarget).toFixed(2));
			$('#dailyLineTargetQty').val(parseFloat(item.dailyLineTarget).toFixed(2));
			$('#hours').val(10);
			$('#hourlyTarget').val(parseFloat(item.hourlyTarget).toFixed(2));

		}

		tables += "<tr class='itemRow' id='row-" + item.machineId + "' data-id='" + item.machineId + "' data-machine-id='"+item.machineId+"' data-auto-id='" + item.autoId + "'>" +
			"<td>" + item.machineName + "</br><input  type='hidden' class='form-control-sm line-" + item.machineId + "'  value='" + parseFloat(item.machineId).toFixed() + "' /></td>" +
			"<td><select id='process-" + item.machineId + "'  class='selectpicker employee-width tableSelect employee-" + item.machineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + processOptions + "</select></td>" +
			"<td>"+item.operatorName+"</td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h1'  value='" + Number(item.hour1).toFixed(0) + "' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h2'  value='" + Number(item.hour2).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h3'  value='" + Number(item.hour3).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h4'  value='" + Number(item.hour4).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h5'  value='" + Number(item.hour5).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h6'  value='" + Number(item.hour6).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h7'  value='" + Number(item.hour7).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h8'  value='" + Number(item.hour8).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h9'  value='" + Number(item.hour9).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm' id='line-" + item.machineId + "-h10'  value='" + Number(item.hour10).toFixed(0) + "'/></td>" +
			"<td><input  type='number' id='line-" + item.machineId + "-total' readonly class='form-control-sm' value='" + (Number(item.hour1) + Number(item.hour2) + Number(item.hour3) + Number(item.hour4) + Number(item.hour5) + Number(item.hour6) + Number(item.hour7) + Number(item.hour8) + Number(item.hour9) + Number(item.hour10)).toFixed(0) + "'/></td>" +
			"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm' onclick='editLineData(" + item.machineId + ")'><i class='fa fa-edit'></i></button></td></tr>"

	}

	tables += "</tbody></table> </div></div>";
	// tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
	$('.tableSelect').selectpicker('refresh');

	dataList.forEach(item => {
		$("#process-" + item.machineId).val(item.processId).change();
	});
}

function setTotalQty(id) {

	var totalQtyLineId = "#line-" + id + '-total';

	//alert("totalQtyLineId "+totalQtyLineId);

	var Qty1 = parseFloat(($("#line-" + id + "-h1").val() == '' ? "0" : $("#line-" + id + "-h1").val()));
	var Qty2 = parseFloat(($("#line-" + id + "-h2").val() == '' ? "0" : $("#line-" + id + "-h2").val()));
	var Qty3 = parseFloat(($("#line-" + id + "-h3").val() == '' ? "0" : $("#line-" + id + "-h3").val()));
	var Qty4 = parseFloat(($("#line-" + id + "-h4").val() == '' ? "0" : $("#line-" + id + "-h4").val()));
	var Qty5 = parseFloat(($("#line-" + id + "-h5").val() == '' ? "0" : $("#line-" + id + "-h5").val()));
	var Qty6 = parseFloat(($("#line-" + id + "-h6").val() == '' ? "0" : $("#line-" + id + "-h6").val()));
	var Qty7 = parseFloat(($("#line-" + id + "-h7").val() == '' ? "0" : $("#line-" + id + "-h7").val()));
	var Qty8 = parseFloat(($("#line-" + id + "-h8").val() == '' ? "0" : $("#line-" + id + "-h8").val()));
	var Qty9 = parseFloat(($("#line-" + id + "-h9").val() == '' ? "0" : $("#line-" + id + "-h9").val()));
	var Qty10 = parseFloat(($("#line-" + id + "-h10").val() == '' ? "0" : $("#line-" + id + "-h10").val()));

	var totalQty = Qty1 + Qty2 + Qty3 + Qty4 + Qty5 + Qty6 + Qty7 + Qty8 + Qty9 + Qty10;

	$(totalQtyLineId).val(totalQty);

}
function saveAction() {
	var type = $('#type').val();
	var userId = $('#userId').val();
	var buyerId = $('#buyerId').val();
	var buyerOrderId = $('#buyerOrderId').val();
	var purchaseOrder = $('#purchaseOrder').val();
	var styleId = $('#styleId').val();
	var itemId = $('#itemId').val();
	var platQty = $('#planQty').val();
	var dailyTarget = $('#dailyTargetQty').val();
	var dailyLineTarget = $('#dailyLineTargetQty').val();
	var hours = $('#hours').val();
	var hourlyTarget = $('#hourlyTarget').val();
	var layoutDate = $('#layoutDate').val();
	var layoutName = $('#type').val();
	var lineId = $("#line").val();
	var employeeId = $("#employeeName").val();

	var resultList = [];
	if(employeeId != '0'){
		if (buyerId == '' || buyerOrderId == '' || styleId == '' || itemId == '' || layoutDate == '') {
			alert("information Incomplete");	
		}
		else {
	
			var i = 0;
			var value = 0;
			var j = 0;
			$('.itemRow').each(function () {
	
				var id = $(this).attr("data-id");
	
				var machineId = $(this).attr("data-machine-id");
				var processId = $("#process-" + id).val();
				var rejectvalue = 0;
				var totalRejectQty = 0;
	
				var proQty1 = parseFloat(($("#line-" + id + "-h1").val() == '' ? "0" : $("#line-" + id + "-h1").val()));
				var proQty2 = parseFloat(($("#line-" + id + "-h2").val() == '' ? "0" : $("#line-" + id + "-h2").val()));
				var proQty3 = parseFloat(($("#line-" + id + "-h3").val() == '' ? "0" : $("#line-" + id + "-h3").val()));
				var proQty4 = parseFloat(($("#line-" + id + "-h4").val() == '' ? "0" : $("#line-" + id + "-h4").val()));
				var proQty5 = parseFloat(($("#line-" + id + "-h5").val() == '' ? "0" : $("#line-" + id + "-h5").val()));
				var proQty6 = parseFloat(($("#line-" + id + "-h6").val() == '' ? "0" : $("#line-" + id + "-h6").val()));
				var proQty7 = parseFloat(($("#line-" + id + "-h7").val() == '' ? "0" : $("#line-" + id + "-h7").val()));
				var proQty8 = parseFloat(($("#line-" + id + "-h8").val() == '' ? "0" : $("#line-" + id + "-h8").val()));
				var proQty9 = parseFloat(($("#line-" + id + "-h9").val() == '' ? "0" : $("#line-" + id + "-h9").val()));
				var proQty10 = parseFloat(($("#line-" + id + "-h10").val() == '' ? "0" : $("#line-" + id + "-h10").val()));
	
				var totalQty = proQty1 + proQty2 + proQty3 + proQty4 + proQty5 + proQty6 + proQty7 + proQty8 + proQty9 + proQty10;
				var layoutValue = type + ":" + proQty1 + ":" + proQty2 + ":" + proQty3 + ":" + proQty4 + ":" + proQty5 + ":" + proQty6 + ":" + proQty7 + ":" + proQty8 + ":" + proQty9 + ":" + proQty10;
	
				resultList[i] = machineId + "*" + processId + "*" + totalQty + "*" + totalRejectQty + "*" + layoutValue + "*" + rejectvalue;
				i++;
			});
			resultList = "[" + resultList + "]"
	
			if (confirm("Are you sure to Submit?")) {
				$.ajax({
					type: 'POST',
					dataType: 'json',
					data: {
						buyerId: buyerId,
						buyerorderId: buyerOrderId,
						purchaseOrder: purchaseOrder,
						styleId: styleId,
						itemId: itemId,
						platQty: platQty,
						dailyTarget: dailyTarget,
						dailyLineTarget: dailyLineTarget,
						hours: hours,
						hourlyTarget: hourlyTarget,
						resultlist: resultList,
						layoutDate: layoutDate,
						layoutName: layoutName,
						lineId : lineId,
						employeeId : employeeId,
						userId: userId
					},
					url: './saveLineInceptionLayoutLineDetails/',
					success: function (data) {
	
						alert("Line Inspection Layout Save Successfully...");
						//refreshAction();	        
					}
				});
			}
	
		}
	}else{
		alert("Please Select Employee/Supervisor Name...");
		$("#employeeName").focus();
	}
	
}


function editLineData(lineId) {
	const row = document.getElementById("row-" + lineId);
	const id = lineId;
	const autoId = row.getAttribute('data-auto-id');

	var processId = $("#process-" + id).val();
	var proQty1 = parseFloat(($("#line-" + id + "-h1").val() == '' ? "0" : $("#line-" + id + "-h1").val()));
	var proQty2 = parseFloat(($("#line-" + id + "-h2").val() == '' ? "0" : $("#line-" + id + "-h2").val()));
	var proQty3 = parseFloat(($("#line-" + id + "-h3").val() == '' ? "0" : $("#line-" + id + "-h3").val()));
	var proQty4 = parseFloat(($("#line-" + id + "-h4").val() == '' ? "0" : $("#line-" + id + "-h4").val()));
	var proQty5 = parseFloat(($("#line-" + id + "-h5").val() == '' ? "0" : $("#line-" + id + "-h5").val()));
	var proQty6 = parseFloat(($("#line-" + id + "-h6").val() == '' ? "0" : $("#line-" + id + "-h6").val()));
	var proQty7 = parseFloat(($("#line-" + id + "-h7").val() == '' ? "0" : $("#line-" + id + "-h7").val()));
	var proQty8 = parseFloat(($("#line-" + id + "-h8").val() == '' ? "0" : $("#line-" + id + "-h8").val()));
	var proQty9 = parseFloat(($("#line-" + id + "-h9").val() == '' ? "0" : $("#line-" + id + "-h9").val()));
	var proQty10 = parseFloat(($("#line-" + id + "-h10").val() == '' ? "0" : $("#line-" + id + "-h10").val()));

	const totalQty = proQty1 + proQty2 + proQty3 + proQty4 + proQty5 + proQty6 + proQty7 + proQty8 + proQty9 + proQty10;

	const userId = $("#userId").val();
	if (confirm("Are you sure to Edit?")) {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './editLayoutLineData',
			data: {
				autoId: autoId,
				lineId: lineId,
				processId: processId,
				hour1: proQty1,
				hour2: proQty2,
				hour3: proQty3,
				hour4: proQty4,
				hour5: proQty5,
				hour6: proQty6,
				hour7: proQty7,
				hour8: proQty8,
				hour9: proQty9,
				hour10: proQty10,
				total: totalQty,
				userId: userId
			},
			success: function (data) {
				if (data.result == "Successful")
					alert("Edit Successful...");
				else
					alert(data.result);
				//refreshAction();	        
			}
		});
	}

}

function searchLayoutDetails(buyerId, buyerOrderId, styleId, itemId, layoutDate,lineId) {

	const type = $("#type").val();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './searchLayoutLineData',
		data: {
			buyerId: buyerId,
			buyerorderId: buyerOrderId,
			styleId: styleId,
			itemId: itemId,
			layoutDate: layoutDate,
			layoutName: type,
			lineId: lineId
		},
		success: function (data) {

			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawSearchItemTable(data.result, data.processList);
				$("#btnSubmit").prop('disabled', true);
				$("#inspectionListModal").modal('hide');
			}
		}
	});
}

function refreshAction() {
	location.reload();
}



var today = new Date();
document.getElementById("layoutDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);



