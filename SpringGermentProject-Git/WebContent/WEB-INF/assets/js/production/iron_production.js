
let processRejectValueList = {};

$("#btnProcessOk").click(() => {
	closeProcessAddEvent();
	$("#processListModal").modal('hide');
})

function printLayoutDetails(buyerId, buyerOrderId, styleId, itemId, layoutDate) {
	var layoutDate = $('#layout' + itemId).html();
	const type = $("#type").val();
	const layoutName = "Iron Layout";
	var url = `printLayoutInfo/${buyerId}@${buyerOrderId}@${styleId}@${itemId}@${layoutDate}@${type}@${layoutName}`;
	window.open(url, '_blank');

}


function openProcessModel(lineId, hourId) {
	lineValue = lineId;
	let id = "reject-" + lineId + "-h" + hourId;
	processQty = id;
	const spqValue = $("#fp-" + lineId + "-h" + hourId).val() ? $("#fp-" + lineId + "-h" + hourId).val() : 0;
	const passValue = $("#pass-" + lineId + "-h" + hourId).val() ? $("#pass-" + lineId + "-h" + hourId).val() : 0;
	const rejectValue = $("#reject-" + lineId + "-h" + hourId).val() ? $("#reject-" + lineId + "-h" + hourId).val() : 0;
	$("#productionQty").text(spqValue);
	$("#passQty").text(passValue);
	$("#rejectQty").text(rejectValue);
	$("#hourId").val(hourId);
	$("#lineId").val(lineId);

	$('.processListItemRow').each(function () {
		let processId = $(this).attr("data-id");
		if (processRejectValueList[lineId] && processRejectValueList[lineId]['h' + hourId] && processRejectValueList[lineId]['h' + hourId]['process-' + processId]) {
			$("#processValue-" + processId).val(processRejectValueList[lineId]['h' + hourId]['process-' + processId].qty);
			$("#processRemarks-" + processId).val(processRejectValueList[lineId]['h' + hourId]['process-' + processId].remarks);
			$("#processReIssueCheck-" + processId).prop('checked', processRejectValueList[lineId]['h' + hourId]['process-' + processId].isReIssuePass);
		} else {
			$("#processValue-" + processId).val('0');
			$("#processRemarks-" + processId).val('');
			$("#processReIssueCheck-" + processId).prop('checked', false);
		}
	});
	//$('#processListModal').modal('toggle');
	$('#processListModal').modal('show');
	//$('#processListModal').	
}

function closeProcessAddEvent() {

	let hourId = $("#hourId").val();
	let lineId = $("#lineId").val();
	let reIssueValue = 0;
	let processValue = 0;

	$('.processListItemRow').each(function () {
		let processId = $(this).attr("data-id");
		if (!processRejectValueList[lineId]) processRejectValueList[lineId] = {};
		if (!processRejectValueList[lineId]['h' + hourId]) processRejectValueList[lineId]['h' + hourId] = {};

		processValue = $('#processValue-' + processId).val() == '' ? 0 : $('#processValue-' + processId).val();

		if (processRejectValueList[lineId]['h' + hourId]['process-' + processId]) {
			processRejectValueList[lineId]['h' + hourId]['process-' + processId].qty = processValue;
			processRejectValueList[lineId]['h' + hourId]['process-' + processId].remarks = $("#processRemarks-" + processId).val();
			processRejectValueList[lineId]['h' + hourId]['process-' + processId].isReIssuePass = $("#processReIssueCheck-" + processId).prop('checked') || 'true';
		} else {
			processRejectValueList[lineId]['h' + hourId]['process-' + processId] = {};
			processRejectValueList[lineId]['h' + hourId]['process-' + processId].processId = processId;
			processRejectValueList[lineId]['h' + hourId]['process-' + processId].qty = processValue;
			processRejectValueList[lineId]['h' + hourId]['process-' + processId].remarks = $("#processRemarks-" + processId).val();
			processRejectValueList[lineId]['h' + hourId]['process-' + processId].isReIssuePass = $("#processReIssueCheck-" + processId).prop('checked') || 'true';
		}


		if (processId != 'reject')
			reIssueValue += processValue;
	});

}
function setProductPlanInfo(buyerId, buyerOrderId, styleId, itemId, planQty,countNo) {


	var buyerName = $('#buyerId' + buyerId).html();
	var purchaseOrder = $('#purchaseOrder' + buyerOrderId).html();
	var styleNo = $('#styleId' + styleId).html();
	var itemName = $('#itemId' + itemId).html();
	var type = $('#type').val();
	let productionDate = $('#productionDate-'+countNo).text();

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
			layoutName: type,
			productionDate: productionDate
		},
		url: './searchFinishingPassData',
		success: function (data) {
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawItemTable(data.result, data.employeeresult);
			}
		}
	});
}


function getOptions(dataList) {
	let options = "";
	var length = dataList.length;

	options += "<option value='0'>Select Employee</option>"
	for (var i = 0; i < length; i++) {
		var item = dataList[i];

		options += "<option  value='" + item.employeeId + "'>" + item.employeeName + "</option>"
	}
	return options;
};

function drawItemTable(dataList, employeeResult) {

	const employeeList = getOptions(employeeResult);

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

				<th scope="col" class="min-width-120">Line </th>
				<th scope="col">Employee Name</th>
				<th scope="col">Type</th>
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

		tables += "<tr class='itemRow' data-id='" + item.lineId + "'>" +
			"<th >" + item.lineName + "</br><input  type='hidden' class='form-control-sm line-" + item.lineId + "'  value='" + parseFloat(item.lineId).toFixed() + "' /></th>" +
			"<th><select id='employee-" + item.lineId + "'  class='selectpicker employee-width tableSelect employee-" + item.lineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + employeeList + "</select></th>" +
			"<td><p style='color:black;font-weight:bold;'>F.P.</p><p style='color:green;font-weight:bold;'>Pass</p><p style='color:red;font-weight:bold;'>Reject</p></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h1'  value='" + Math.round(item.hour1) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h1'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h1'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h2'  value='" + Math.round(item.hour2) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h2'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h2'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h3'  value='" + Math.round(item.hour3) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h3'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h3'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h4'  value='" + Math.round(item.hour4) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h4'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h4'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h5'  value='" + Math.round(item.hour5) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h5'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h5'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h6'  value='" + Math.round(item.hour6) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h6'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h6'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h7'  value='" + Math.round(item.hour7) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h7'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h7'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h8'  value='" + Math.round(item.hour8) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h8'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h8'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h9'  value='" + Math.round(item.hour9) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h9'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h9'  value='' /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h10'  value='" + Math.round(item.hour10) + "' readonly/><input  type='number' onchange='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h10'  value='' /><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h10'  value='' /></td>" +
			"<td><input  type='number' id='fp-" + item.lineId + "-total' readonly class='form-control-sm'/><input  type='number' id='pass-" + item.lineId + "-total' readonly class='form-control-sm'/><input  type='number' id='reject-" + item.lineId + "-total' readonly class='form-control-sm'/></td>" +
			"</tr>"
		//"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm'><i class='fa fa-edit'></i></button></td>
		$('#dailyTargetQty').val(parseFloat(item.dailyTarget).toFixed(2));
		$('#dailyLineTargetQty').val(parseFloat(item.dailyLineTarget).toFixed(2));
		$('#hours').val(10);
		$('#hourlyTarget').val(parseFloat(item.hourlyTarget).toFixed(2));

	}

	tables += "</tbody></table> </div></div>";
	// tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
	$('.tableSelect').selectpicker('refresh');

	dataList.forEach((data) => {
		$("#employee-" + data.lineId).val(data.employeeId).change();
	})
}


function drawSearchItemTable(dataList, employeeResult) {

	const employeeList = getOptions(employeeResult);

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

				<th scope="col" class="min-width-120">Line </th>
				<th scope="col">Employee Name</th>
				<th scope="col">Type</th>
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

		if(item.layoutName=='6'){
			tables += "<tr class='itemRow' id='row-" + item.lineId + "' data-id='" + item.lineId + "' data-auto-id='" + item.autoId + "'>" +
			"<th >" + item.lineName + "</br><input  type='hidden' class='form-control-sm line-" + item.lineId + "'  value='" + parseFloat(item.lineId).toFixed() + "' /></th>" +
			"<th><select id='employee-" + item.lineId + "'  class='selectpicker employee-width tableSelect employee-" + item.lineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + employeeList + "</select></th>" +
			"<td>F.P.</td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h1'  value='" + Number(item.hour1).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h2'  value='" + Number(item.hour2).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h3'  value='" + Number(item.hour3).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h4'  value='" + Number(item.hour4).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h5'  value='" + Number(item.hour5).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h6'  value='" + Number(item.hour6).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h7'  value='" + Number(item.hour7).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h8'  value='" + Number(item.hour8).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h9'  value='" + Number(item.hour9).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number'  class='form-control-sm' id='fp-" + item.lineId + "-h10'  value='" + Number(item.hour10).toFixed(0) + "' readonly /></td>" +
			"<td><input  type='number' id='line-" + item.lineId + "-total' readonly class='form-control-sm' value='" + (Number(item.hour1) + Number(item.hour2) + Number(item.hour3) + Number(item.hour4) + Number(item.hour5) + Number(item.hour6) + Number(item.hour7) + Number(item.hour8) + Number(item.hour9) + Number(item.hour10)).toFixed(0) + "'/></td>" +
			"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm' onclick='editLineData(" + item.autoId + ","+item.lineId+","+item.layoutName+")'><i class='fa fa-edit'></i></button></td></tr>"

		}
		if(item.layoutName=='9'){
			tables += "<tr class='itemRow' id='row-" + item.lineId + "' data-id='" + item.lineId + "' data-auto-id='" + item.autoId + "'>" +
			"<th></th>" +
			"<th></th>" +
			"<td>Pass</td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h1'  value='" + Number(item.hour1).toFixed(0) + "' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h2'  value='" + Number(item.hour2).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h3'  value='" + Number(item.hour3).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h4'  value='" + Number(item.hour4).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h5'  value='" + Number(item.hour5).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h6'  value='" + Number(item.hour6).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h7'  value='" + Number(item.hour7).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h8'  value='" + Number(item.hour8).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h9'  value='" + Number(item.hour9).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h10'  value='" + Number(item.hour10).toFixed(0) + "'/></td>" +
			"<td><input  type='number' id='line-" + item.lineId + "-total' readonly class='form-control-sm' value='" + (Number(item.hour1) + Number(item.hour2) + Number(item.hour3) + Number(item.hour4) + Number(item.hour5) + Number(item.hour6) + Number(item.hour7) + Number(item.hour8) + Number(item.hour9) + Number(item.hour10)).toFixed(0) + "'/></td>" +
			"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm' onclick='editLineData(" + item.autoId + ","+item.lineId+","+item.layoutName+")'><i class='fa fa-edit'></i></button></td></tr>"

		}
		else if(item.layoutName=='10'){
			tables += "<tr class='itemRow' id='row-" + item.lineId + "' data-id='" + item.lineId + "' data-auto-id='" + item.autoId + "'>" +
			"<th></th>" +
			"<th></th>" +
			"<td>Reject</td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",1)' class='form-control-sm' id='reject-" + item.lineId + "-h1'  value='" + Number(item.hour1).toFixed(0) + "' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",2)' class='form-control-sm' id='reject-" + item.lineId + "-h2'  value='" + Number(item.hour2).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",3)' class='form-control-sm' id='reject-" + item.lineId + "-h3'  value='" + Number(item.hour3).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",4)' class='form-control-sm' id='reject-" + item.lineId + "-h4'  value='" + Number(item.hour4).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",5)' class='form-control-sm' id='reject-" + item.lineId + "-h5'  value='" + Number(item.hour5).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",6)' class='form-control-sm' id='reject-" + item.lineId + "-h6'  value='" + Number(item.hour6).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",7)' class='form-control-sm' id='reject-" + item.lineId + "-h7'  value='" + Number(item.hour7).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",8)' class='form-control-sm' id='reject-" + item.lineId + "-h8'  value='" + Number(item.hour8).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",9)' class='form-control-sm' id='reject-" + item.lineId + "-h9'  value='" + Number(item.hour9).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' onfocus='openProcessModel("+item.lineId+",10)' class='form-control-sm' id='reject-" + item.lineId + "-h10'  value='" + Number(item.hour10).toFixed(0) + "'/></td>" +
			"<td><input  type='number' id='reject-" + item.lineId + "-total' readonly class='form-control-sm' value='" + (Number(item.hour1) + Number(item.hour2) + Number(item.hour3) + Number(item.hour4) + Number(item.hour5) + Number(item.hour6) + Number(item.hour7) + Number(item.hour8) + Number(item.hour9) + Number(item.hour10)).toFixed(0) + "'/></td>" +
			"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm' onclick='editLineData(" + item.autoId + ","+item.lineId+","+item.layoutName+")'><i class='fa fa-edit'></i></button></td></tr>"

		}

	}

	tables += "</tbody></table> </div></div>";
	// tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
	$('.tableSelect').selectpicker('refresh');

	dataList.forEach(item => {
		$("#employee-" + item.lineId).val(item.operatorId).change();
	});
}

function setTotalQty(id) {

	let fpQty1 = parseFloat(($("#fp-" + id + "-h1").val() == '' ? "0" : $("#fp-" + id + "-h1").val()));
	let fpQty2 = parseFloat(($("#fp-" + id + "-h2").val() == '' ? "0" : $("#fp-" + id + "-h2").val()));
	let fpQty3 = parseFloat(($("#fp-" + id + "-h3").val() == '' ? "0" : $("#fp-" + id + "-h3").val()));
	let fpQty4 = parseFloat(($("#fp-" + id + "-h4").val() == '' ? "0" : $("#fp-" + id + "-h4").val()));
	let fpQty5 = parseFloat(($("#fp-" + id + "-h5").val() == '' ? "0" : $("#fp-" + id + "-h5").val()));
	let fpQty6 = parseFloat(($("#fp-" + id + "-h6").val() == '' ? "0" : $("#fp-" + id + "-h6").val()));
	let fpQty7 = parseFloat(($("#fp-" + id + "-h7").val() == '' ? "0" : $("#fp-" + id + "-h7").val()));
	let fpQty8 = parseFloat(($("#fp-" + id + "-h8").val() == '' ? "0" : $("#fp-" + id + "-h8").val()));
	let fpQty9 = parseFloat(($("#fp-" + id + "-h9").val() == '' ? "0" : $("#fp-" + id + "-h9").val()));
	let fpQty10 = parseFloat(($("#fp-" + id + "-h10").val() == '' ? "0" : $("#fp-" + id + "-h10").val()));
	let fpQty11 = parseFloat(($("#fp-" + id + "-h11").val() == '' ? "0" : $("#fp-" + id + "-h11").val()));
	let fpQty12 = parseFloat(($("#fp-" + id + "-h12").val() == '' ? "0" : $("#fp-" + id + "-h12").val()));

	let totalQty = fpQty1 + fpQty2 + fpQty3 + fpQty4 + fpQty5 + fpQty6 + fpQty7 + fpQty8 + fpQty9 + fpQty10 + fpQty11 + fpQty12;

	$("#fp-" + id + '-total').val(totalQty);


	let passQty1 = parseFloat(($("#pass-" + id + "-h1").val() == '' ? "0" : $("#pass-" + id + "-h1").val()));
	let passQty2 = parseFloat(($("#pass-" + id + "-h2").val() == '' ? "0" : $("#pass-" + id + "-h2").val()));
	let passQty3 = parseFloat(($("#pass-" + id + "-h3").val() == '' ? "0" : $("#pass-" + id + "-h3").val()));
	let passQty4 = parseFloat(($("#pass-" + id + "-h4").val() == '' ? "0" : $("#pass-" + id + "-h4").val()));
	let passQty5 = parseFloat(($("#pass-" + id + "-h5").val() == '' ? "0" : $("#pass-" + id + "-h5").val()));
	let passQty6 = parseFloat(($("#pass-" + id + "-h6").val() == '' ? "0" : $("#pass-" + id + "-h6").val()));
	let passQty7 = parseFloat(($("#pass-" + id + "-h7").val() == '' ? "0" : $("#pass-" + id + "-h7").val()));
	let passQty8 = parseFloat(($("#pass-" + id + "-h8").val() == '' ? "0" : $("#pass-" + id + "-h8").val()));
	let passQty9 = parseFloat(($("#pass-" + id + "-h9").val() == '' ? "0" : $("#pass-" + id + "-h9").val()));
	let passQty10 = parseFloat(($("#pass-" + id + "-h10").val() == '' ? "0" : $("#pass-" + id + "-h10").val()));
	let passQty11 = parseFloat(($("#pass-" + id + "-h11").val() == '' ? "0" : $("#pass-" + id + "-h11").val()));
	let passQty12 = parseFloat(($("#pass-" + id + "-h12").val() == '' ? "0" : $("#pass-" + id + "-h12").val()));

	if (fpQty1 - passQty1 < 0) passQty1 = fpQty1;
	if (fpQty2 - passQty2 < 0) passQty2 = fpQty2;
	if (fpQty3 - passQty3 < 0) passQty3 = fpQty3;
	if (fpQty4 - passQty4 < 0) passQty4 = fpQty4;
	if (fpQty5 - passQty5 < 0) passQty5 = fpQty5;
	if (fpQty6 - passQty6 < 0) passQty6 = fpQty6;
	if (fpQty7 - passQty7 < 0) passQty7 = fpQty7;
	if (fpQty8 - passQty8 < 0) passQty8 = fpQty8;
	if (fpQty9 - passQty9 < 0) passQty9 = fpQty9;
	if (fpQty10 - passQty10 < 0) passQty10 = fpQty10;
	if (fpQty11 - passQty11 < 0) passQty11 = fpQty11;
	if (fpQty12 - passQty12 < 0) passQty12 = fpQty12;

	totalQty = passQty1 + passQty2 + passQty3 + passQty4 + passQty5 + passQty6 + passQty7 + passQty8 + passQty9 + passQty10 + passQty11 + passQty12;

	$("#pass-" + id + "-h1").val(passQty1);
	$("#pass-" + id + "-h2").val(passQty2);
	$("#pass-" + id + "-h3").val(passQty3);
	$("#pass-" + id + "-h4").val(passQty4);
	$("#pass-" + id + "-h5").val(passQty5);
	$("#pass-" + id + "-h6").val(passQty6);
	$("#pass-" + id + "-h7").val(passQty7);
	$("#pass-" + id + "-h8").val(passQty8);
	$("#pass-" + id + "-h9").val(passQty9);
	$("#pass-" + id + "-h10").val(passQty10);
	$("#pass-" + id + "-h11").val(passQty11);
	$("#pass-" + id + "-h12").val(passQty12);
	$("#pass-" + id + '-total').val(totalQty);

	let reject = [];
	let rejectTotal = 0;
	reject.push(parseFloat(fpQty1 - passQty1));
	reject.push(parseFloat(fpQty2 - passQty2));
	reject.push(parseFloat(fpQty3 - passQty3));
	reject.push(parseFloat(fpQty4 - passQty4));
	reject.push(parseFloat(fpQty5 - passQty5));
	reject.push(parseFloat(fpQty6 - passQty6));
	reject.push(parseFloat(fpQty7 - passQty7));
	reject.push(parseFloat(fpQty8 - passQty8));
	reject.push(parseFloat(fpQty9 - passQty9));
	reject.push(parseFloat(fpQty10 - passQty10));
	reject.push(parseFloat(fpQty11 - passQty11));
	reject.push(parseFloat(fpQty12 - passQty12));

	let length = reject.length;

	for (let i = 0; i < length; i++) {
		if (reject[i] < 0) {
			$("#reject-" + id + "-h" + (i + 1)).val(0);
			rejectTotal += 0;
		} else {
			$("#reject-" + id + "-h" + (i + 1)).val(reject[i]);
			rejectTotal += reject[i];
		}
	}
	$("#reject-" + id + "-total").val(rejectTotal);

}



function processValueCalculate(inputField) {

	let otherProcessValue = 0;
	let inputValue = Number(inputField.value);

	const inputFieldId = inputField.id;

	$('.processListItemRow').each(function () {
		let id = $(this).attr("data-id");
		if (id != 'reject' && 'processValue-' + id != inputFieldId) {
			otherProcessValue += Number($("#processValue-" + id).val());
		}
	});


	let rejectQty = Number($("#rejectQty").text());

	if ((otherProcessValue + inputValue) > rejectQty) {
		inputValue = rejectQty - otherProcessValue;
	}

	inputField.value = inputValue;
}



function saveAction() {
	let passType = $('#passType').val();
	let rejectType = $('#rejectType').val();
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

	var resultList = [];

	console.log(buyerId)
	console.log(buyerOrderId)
	console.log(styleId)
	console.log(itemId)
	console.log(layoutDate)

	if (buyerId == '' || buyerOrderId == '' || styleId == '' || itemId == '' || layoutDate == '') {
		alert("information Incomplete");
	}
	else {

		var i = 0;
		var value = 0;
		var j = 0;
		$('.itemRow').each(function () {

			var id = $(this).attr("data-id");

			var lineId = $(".line-" + id).val();
			var employeeId = $("#employee-" + id).val();
			

			var proQty1 = parseFloat(($("#pass-" + id + "-h1").val() == '' ? "0" : $("#pass-" + id + "-h1").val()));
			var proQty2 = parseFloat(($("#pass-" + id + "-h2").val() == '' ? "0" : $("#pass-" + id + "-h2").val()));
			var proQty3 = parseFloat(($("#pass-" + id + "-h3").val() == '' ? "0" : $("#pass-" + id + "-h3").val()));
			var proQty4 = parseFloat(($("#pass-" + id + "-h4").val() == '' ? "0" : $("#pass-" + id + "-h4").val()));
			var proQty5 = parseFloat(($("#pass-" + id + "-h5").val() == '' ? "0" : $("#pass-" + id + "-h5").val()));
			var proQty6 = parseFloat(($("#pass-" + id + "-h6").val() == '' ? "0" : $("#pass-" + id + "-h6").val()));
			var proQty7 = parseFloat(($("#pass-" + id + "-h7").val() == '' ? "0" : $("#pass-" + id + "-h7").val()));
			var proQty8 = parseFloat(($("#pass-" + id + "-h8").val() == '' ? "0" : $("#pass-" + id + "-h8").val()));
			var proQty9 = parseFloat(($("#pass-" + id + "-h9").val() == '' ? "0" : $("#pass-" + id + "-h9").val()));
			var proQty10 = parseFloat(($("#pass-" + id + "-h10").val() == '' ? "0" : $("#pass-" + id + "-h10").val()));

			var totalQty = proQty1 + proQty2 + proQty3 + proQty4 + proQty5 + proQty6 + proQty7 + proQty8 + proQty9 + proQty10;
			

			//reject
			let rejectQty1 = parseFloat(($("#reject-" + id + "-h1").val() == '' ? "0" : $("#reject-" + id + "-h1").val()));
			let rejectQty2 = parseFloat(($("#reject-" + id + "-h2").val() == '' ? "0" : $("#reject-" + id + "-h2").val()));
			let rejectQty3 = parseFloat(($("#reject-" + id + "-h3").val() == '' ? "0" : $("#reject-" + id + "-h3").val()));
			let rejectQty4 = parseFloat(($("#reject-" + id + "-h4").val() == '' ? "0" : $("#reject-" + id + "-h4").val()));
			let rejectQty5 = parseFloat(($("#reject-" + id + "-h5").val() == '' ? "0" : $("#reject-" + id + "-h5").val()));
			let rejectQty6 = parseFloat(($("#reject-" + id + "-h6").val() == '' ? "0" : $("#reject-" + id + "-h6").val()));
			let rejectQty7 = parseFloat(($("#reject-" + id + "-h7").val() == '' ? "0" : $("#reject-" + id + "-h7").val()));
			let rejectQty8 = parseFloat(($("#reject-" + id + "-h8").val() == '' ? "0" : $("#reject-" + id + "-h8").val()));
			let rejectQty9 = parseFloat(($("#reject-" + id + "-h9").val() == '' ? "0" : $("#reject-" + id + "-h9").val()));
			let rejectQty10 = parseFloat(($("#reject-" + id + "-h10").val() == '' ? "0" : $("#reject-" + id + "-h10").val()));

			let totalRejectQty = rejectQty1 + rejectQty2 + rejectQty3 + rejectQty4 + rejectQty5 + rejectQty6 + rejectQty7 + rejectQty8 + rejectQty9 + rejectQty10;
			
			let passValue = passType+":"+proQty1 + ":" + proQty2 + ":" + proQty3 + ":" + proQty4 + ":" + proQty5 + ":" + proQty6 + ":" + proQty7 + ":" + proQty8 + ":" + proQty9 + ":" + proQty10;
			let rejectValue = rejectType+":"+rejectQty1 + ":" + rejectQty2 + ":" + rejectQty3 + ":" + rejectQty4 + ":" + rejectQty5 + ":" + rejectQty6 + ":" + rejectQty7 + ":" + rejectQty8 + ":" + rejectQty9 + ":" + rejectQty10;

			resultList[i] = employeeId + "*" + lineId + "*" + totalQty + "*"+totalRejectQty+"*" + passValue+"*"+rejectValue;
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
					processValues: JSON.stringify(processRejectValueList),
					layoutDate: layoutDate,
					layoutName: layoutName,
					userId: userId
				},
				url: './saveIronProductionDetails/',
				success: function (data) {

					alert("Iron Production Save Successfully...");
					//refreshAction();	        
				}
			});
		}

	}
}


function editLineData(lineId) {
	const row = document.getElementById("row-" + lineId);
	const id = lineId;
	const autoId = row.getAttribute('data-auto-id');

	var employeeId = $("#employee-" + id).val();
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
				operatorId: employeeId,
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

function searchLayoutDetails(buyerId, buyerOrderId, styleId, itemId, layoutDate) {
	$('#buyerId').val(buyerId);
	$('#buyerOrderId').val(buyerOrderId);
	$('#styleId').val(styleId);
	$('#itemId').val(itemId);
	const type = $("#type").val();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './searchIronData',
		data: {
			buyerId: buyerId,
			buyerorderId: buyerOrderId,
			styleId: styleId,
			itemId: itemId,
			layoutDate: layoutDate,
			layoutName: type
		},
		success: function (data) {
			console.log(data)
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawSearchItemTable(data.result, data.employeeList);
				///$("#btnSubmit").prop('disabled', true);
				$("#ironListModal").modal('hide');
			}
		}
	});
}

function refreshAction() {
	location.reload();
}



var today = new Date();
document.getElementById("layoutDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


