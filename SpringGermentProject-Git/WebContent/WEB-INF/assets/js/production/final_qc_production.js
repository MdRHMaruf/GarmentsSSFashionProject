var processQty=0;
var lineValue=0;
function printProductionDetails(buyerId, buyerOrderId, styleId, itemId, layoutDate) {
	var layoutDate = $('#layout' + itemId).html();
	const type ='10,11';
	const layoutCategory="Production & Reject";
	const layoutName = "Final QC Production & Reject Report ";
	var url = `printProductionDetails/${buyerId}@${buyerOrderId}@${styleId}@${itemId}@${layoutDate}@${type}@${layoutName}@${layoutCategory}`;
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
				<th scope="col">07-08</th>
				<th scope="col">08-09</th>
				<th scope="col">Total</th>
				
				</tr>
				</thead>
				<tbody id="dataList">`

			//<th scope="col">Edit</th>

		}

		tables += "<tr class='itemRow' data-id='" + item.lineId + "'>" +
			"<th >" + item.lineName + "</br><input  type='hidden' class='form-control-sm line-" + item.lineId + "'  value='" + parseFloat(item.lineId).toFixed() + "' /></th>" +
			"<th><select id='employee-" + item.lineId + "'  class='selectpicker employee-width tableSelect employee-" + item.lineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + employeeList + "</select></th>" +
			"<td><p style='color:green;font-weight:bold;'>Pass</p><p style='color:red;font-weight:bold;'>Reject</p></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h1'  value=''/><input  type='number'  onfocus='openProcessModel("+item.lineId+",1)'  class='form-control-sm' id='reject-" + item.lineId + "-h1'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h2'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",2)' class='form-control-sm' id='reject-" + item.lineId + "-h2'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h3'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",3)' class='form-control-sm' id='reject-" + item.lineId + "-h3'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h4'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",4)' class='form-control-sm' id='reject-" + item.lineId + "-h4'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h5'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",5)' class='form-control-sm' id='reject-" + item.lineId + "-h5'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h6'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",6)' class='form-control-sm' id='reject-" + item.lineId + "-h6'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h7'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",7)' class='form-control-sm' id='reject-" + item.lineId + "-h7'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h8'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",8)' class='form-control-sm' id='reject-" + item.lineId + "-h8'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h9'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",9)' class='form-control-sm' id='reject-" + item.lineId + "-h9'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h10'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",10)' class='form-control-sm' id='reject-" + item.lineId + "-h10'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h11'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",11)' class='form-control-sm' id='reject-" + item.lineId + "-h11'  value='' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='pass-" + item.lineId + "-h12'  value=''/><input  type='number' onfocus='openProcessModel("+item.lineId+",12)' class='form-control-sm' id='reject-" + item.lineId + "-h12'  value='' /></td>" +
			"<td><input  type='number' id='line-" + item.lineId + "-total' readonly class='form-control-sm'/><input  type='number'  class='form-control-sm' id='reject-" + item.lineId + "-total'  value='' /></td>" +
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
}

function openProcessModel(v,h){
	
	lineValue=v;
	
	var id="reject-"+v+"-h"+h;
	processQty=id;
	$('#processListModal').modal('toggle');
	$('#processListModal').modal('show');
	//$('#processListModal').
	
}

function closeProcessAddEvent(){
	
	


	
	
	var value=0;
	$('.procsslistitemrow').each(function () {
		var id = $(this).attr("data-id");
	
		value=value+parseFloat(($('.processId-'+id).val()==''?"0":$('.processId-'+id).val()));

		
	});
	
	
	console.log("lineValue "+lineValue);
	
	
	
		var totalQtyLineId = "#reject-" + lineValue + '-total';

	//alert("totalQtyLineId "+totalQtyLineId);

	var Qty1 = parseFloat(($("#reject-" + lineValue + "-h1").val() == '' ? "0" : $("#reject-" + lineValue + "-h1").val()));
	var Qty2 = parseFloat(($("#reject-" + lineValue + "-h2").val() == '' ? "0" : $("#reject-" + lineValue + "-h2").val()));
	var Qty3 = parseFloat(($("#reject-" + lineValue + "-h3").val() == '' ? "0" : $("#reject-" + lineValue + "-h3").val()));
	var Qty4 = parseFloat(($("#reject-" + lineValue + "-h4").val() == '' ? "0" : $("#reject-" + lineValue + "-h4").val()));
	var Qty5 = parseFloat(($("#reject-" + lineValue + "-h5").val() == '' ? "0" : $("#reject-" + lineValue + "-h5").val()));
	var Qty6 = parseFloat(($("#reject-" + lineValue + "-h6").val() == '' ? "0" : $("#reject-" + lineValue + "-h6").val()));
	var Qty7 = parseFloat(($("#reject-" + lineValue + "-h7").val() == '' ? "0" : $("#reject-" + lineValue + "-h7").val()));
	var Qty8 = parseFloat(($("#reject-" + lineValue + "-h8").val() == '' ? "0" : $("#reject-" + lineValue + "-h8").val()));
	var Qty9 = parseFloat(($("#reject-" + lineValue + "-h9").val() == '' ? "0" : $("#reject-" + lineValue + "-h9").val()));
	var Qty10 = parseFloat(($("#reject-" + lineValue + "-h10").val() == '' ? "0" : $("#reject-" + lineValue + "-h10").val()));

	var totalQty = (Qty1 + Qty2 + Qty3 + Qty4 + Qty5 + Qty6 + Qty7 + Qty8 + Qty9 + Qty10)+value;

	$(totalQtyLineId).val(totalQty);
	
	$('#'+processQty).val(value)
	
	value=0;
	$('.procsslistitemrow').each(function () {
		var id = $(this).attr("data-id");
	
		$('.processId-'+id).val('');

		
	});
}



function setTotalQty(id) {

	var totalQtyLineId = "#line-" + id + '-total';

	//alert("totalQtyLineId "+totalQtyLineId);

	var Qty1 = parseFloat(($("#pass-" + id + "-h1").val() == '' ? "0" : $("#pass-" + id + "-h1").val()));
	var Qty2 = parseFloat(($("#pass-" + id + "-h2").val() == '' ? "0" : $("#pass-" + id + "-h2").val()));
	var Qty3 = parseFloat(($("#pass-" + id + "-h3").val() == '' ? "0" : $("#pass-" + id + "-h3").val()));
	var Qty4 = parseFloat(($("#pass-" + id + "-h4").val() == '' ? "0" : $("#pass-" + id + "-h4").val()));
	var Qty5 = parseFloat(($("#pass-" + id + "-h5").val() == '' ? "0" : $("#pass-" + id + "-h5").val()));
	var Qty6 = parseFloat(($("#pass-" + id + "-h6").val() == '' ? "0" : $("#pass-" + id + "-h6").val()));
	var Qty7 = parseFloat(($("#pass-" + id + "-h7").val() == '' ? "0" : $("#pass-" + id + "-h7").val()));
	var Qty8 = parseFloat(($("#pass-" + id + "-h8").val() == '' ? "0" : $("#pass-" + id + "-h8").val()));
	var Qty9 = parseFloat(($("#pass-" + id + "-h9").val() == '' ? "0" : $("#pass-" + id + "-h9").val()));
	var Qty10 = parseFloat(($("#pass-" + id + "-h10").val() == '' ? "0" : $("#pass-" + id + "-h10").val()));

	var totalQty = Qty1 + Qty2 + Qty3 + Qty4 + Qty5 + Qty6 + Qty7 + Qty8 + Qty9 + Qty10;

	$(totalQtyLineId).val(totalQty);

}



function saveAction() {
	var passType = $('#passType').val();
	var rejectType = $('#rejectType').val();
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
			var rejectQty1 = parseFloat(($("#reject-" + id + "-h1").val() == '' ? "0" : $("#reject-" + id + "-h1").val()));
			var rejectQty2 = parseFloat(($("#reject-" + id + "-h2").val() == '' ? "0" : $("#reject-" + id + "-h2").val()));
			var rejectQty3 = parseFloat(($("#reject-" + id + "-h3").val() == '' ? "0" : $("#reject-" + id + "-h3").val()));
			var rejectQty4 = parseFloat(($("#reject-" + id + "-h4").val() == '' ? "0" : $("#reject-" + id + "-h4").val()));
			var rejectQty5 = parseFloat(($("#reject-" + id + "-h5").val() == '' ? "0" : $("#reject-" + id + "-h5").val()));
			var rejectQty6 = parseFloat(($("#reject-" + id + "-h6").val() == '' ? "0" : $("#reject-" + id + "-h6").val()));
			var rejectQty7 = parseFloat(($("#reject-" + id + "-h7").val() == '' ? "0" : $("#reject-" + id + "-h7").val()));
			var rejectQty8 = parseFloat(($("#reject-" + id + "-h8").val() == '' ? "0" : $("#reject-" + id + "-h8").val()));
			var rejectQty9 = parseFloat(($("#reject-" + id + "-h9").val() == '' ? "0" : $("#reject-" + id + "-h9").val()));
			var rejectQty10 = parseFloat(($("#reject-" + id + "-h10").val() == '' ? "0" : $("#reject-" + id + "-h10").val()));

			var totalRejectQty = rejectQty1 + rejectQty2 + rejectQty3 + rejectQty4 + rejectQty5 + rejectQty6 + rejectQty7 + rejectQty8 + rejectQty9 + rejectQty10;
			
			
			var passValue = passType+":"+proQty1 + ":" + proQty2 + ":" + proQty3 + ":" + proQty4 + ":" + proQty5 + ":" + proQty6 + ":" + proQty7 + ":" + proQty8 + ":" + proQty9 + ":" + proQty10;
			var rejectValue = rejectType+":"+rejectQty1 + ":" + rejectQty2 + ":" + rejectQty3 + ":" + rejectQty4 + ":" + rejectQty5 + ":" + rejectQty6 + ":" + rejectQty7 + ":" + rejectQty8 + ":" + rejectQty9 + ":" + rejectQty10;

			resultList[i] =employeeId + "*" + lineId + "*" + totalQty+" * "+totalRejectQty+ "*" + passValue+"*"+rejectValue;
			i++;
		});
		resultList = "[" + resultList + "]"

		if(confirm("Are you sure to Submit?")){
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
					userId: userId
				},
				url: './saveLineInceptionLayoutDetails/',
				success: function (data) {
	
					alert("Final QC Production Save Successfully...");
					//refreshAction();	        
				}
			});
		}
		
	}
}




function searchLayoutDetails(buyerId, buyerOrderId, styleId, itemId, layoutDate) {

	const type = '10,11';
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: './searchProductionData',
		data: {
			buyerId: buyerId,
			buyerorderId: buyerOrderId,
			styleId: styleId,
			itemId: itemId,
			layoutDate: layoutDate,
			layoutName: type
		},
		success: function (data) {
			
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawSearchItemTable(data.result, data.employeeList);
				$("#btnSubmit").prop('disabled', true);
				$("#finishingListModal").modal('hide');
			}
		}
	});
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
		
	
		
		if(item.layoutName=='10'){
			tables += "<tr class='itemRow' id='row-" + item.lineId + "' data-id='" + item.lineId + "' data-auto-id='" + item.autoId + "'>" +
			"<th >" + item.lineName + "</br><input  type='hidden' class='form-control-sm line-" + item.lineId + "'  value='" + parseFloat(item.lineId).toFixed() + "' /></th>" +
			"<th><select id='employee-" + item.lineId + "'  class='selectpicker employee-width tableSelect employee-" + item.lineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + employeeList + "</select></th>" +
			"<td>Pass</td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h1'  value='" + Number(item.hour1).toFixed(0) + "' /></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h2'  value='" + Number(item.hour2).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h3'  value='" + Number(item.hour3).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h4'  value='" + Number(item.hour4).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h5'  value='" + Number(item.hour5).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h6'  value='" + Number(item.hour6).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h7'  value='" + Number(item.hour7).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h8'  value='" + Number(item.hour8).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h9'  value='" + Number(item.hour9).toFixed(0) + "'/></td>" +
			"<td><input  type='number' onkeyup='setTotalQty(" + item.lineId + ")' class='form-control-sm' id='line-" + item.lineId + "-h10'  value='" + Number(item.hour10).toFixed(0) + "'/></td>" +
			"<td><input  type='number' id='line-" + item.lineId + "-total' readonly class='form-control-sm' value='" + (Number(item.hour1) + Number(item.hour2) + Number(item.hour3) + Number(item.hour4) + Number(item.hour5) + Number(item.hour6) + Number(item.hour7) + Number(item.hour8) + Number(item.hour9) + Number(item.hour10)).toFixed(0) + "'/></td>" +
			"<td><button type='button' class='btn btn-sm btn-outline-dark btn-sm' onclick='editLineData(" + item.autoId + ","+item.lineId+","+item.layoutName+")'><i class='fa fa-edit'></i></button></td></tr>"

		}
		else if(item.layoutName=='11'){
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

function editLineData(autoId,lineId,type) {

	const id = lineId;

	var employeeId = $("#employee-" + id).val();
	
	var proQty1=0,proQty2=0,proQty3=0,proQty4=0,proQty5=0,proQty6=0,proQty7=0,proQty8=0,proQty9=0,proQty10=0,totalQty=0;
	
	if(type=='10'){
		proQty1 = parseFloat(($("#pass-" + id + "-h1").val() == '' ? "0" : $("#pass-" + id + "-h1").val()));
		proQty2 = parseFloat(($("#pass-" + id + "-h2").val() == '' ? "0" : $("#pass-" + id + "-h2").val()));
		proQty3 = parseFloat(($("#pass-" + id + "-h3").val() == '' ? "0" : $("#pass-" + id + "-h3").val()));
		proQty4 = parseFloat(($("#pass-" + id + "-h4").val() == '' ? "0" : $("#pass-" + id + "-h4").val()));
		proQty5 = parseFloat(($("#pass-" + id + "-h5").val() == '' ? "0" : $("#pass-" + id + "-h5").val()));
		proQty6 = parseFloat(($("#pass-" + id + "-h6").val() == '' ? "0" : $("#pass-" + id + "-h6").val()));
		proQty7 = parseFloat(($("#pass-" + id + "-h7").val() == '' ? "0" : $("#pass-" + id + "-h7").val()));
		proQty8 = parseFloat(($("#pass-" + id + "-h8").val() == '' ? "0" : $("#pass-" + id + "-h8").val()));
		proQty9 = parseFloat(($("#pass-" + id + "-h9").val() == '' ? "0" : $("#pass-" + id + "-h9").val()));
		proQty10 = parseFloat(($("#pass-" + id + "-h10").val() == '' ? "0" : $("#pass-" + id + "-h10").val()));

		totalQty = proQty1 + proQty2 + proQty3 + proQty4 + proQty5 + proQty6 + proQty7 + proQty8 + proQty9 + proQty10;
	}
	else if(type=='11'){
		proQty1 = parseFloat(($("#reject-" + id + "-h1").val() == '' ? "0" : $("#reject-" + id + "-h1").val()));
		proQty2 = parseFloat(($("#reject-" + id + "-h2").val() == '' ? "0" : $("#reject-" + id + "-h2").val()));
		proQty3 = parseFloat(($("#reject-" + id + "-h3").val() == '' ? "0" : $("#reject-" + id + "-h3").val()));
		proQty4 = parseFloat(($("#reject-" + id + "-h4").val() == '' ? "0" : $("#reject-" + id + "-h4").val()));
		proQty5 = parseFloat(($("#reject-" + id + "-h5").val() == '' ? "0" : $("#reject-" + id + "-h5").val()));
		proQty6 = parseFloat(($("#reject-" + id + "-h6").val() == '' ? "0" : $("#reject-" + id + "-h6").val()));
		proQty7 = parseFloat(($("#reject-" + id + "-h7").val() == '' ? "0" : $("#reject-" + id + "-h7").val()));
		proQty8 = parseFloat(($("#reject-" + id + "-h8").val() == '' ? "0" : $("#reject-" + id + "-h8").val()));
		proQty9 = parseFloat(($("#reject-" + id + "-h9").val() == '' ? "0" : $("#reject-" + id + "-h9").val()));
		proQty10 = parseFloat(($("#reject-" + id + "-h10").val() == '' ? "0" : $("#reject-" + id + "-h10").val()));

		totalQty = proQty1 + proQty2 + proQty3 + proQty4 + proQty5 + proQty6 + proQty7 + proQty8 + proQty9 + proQty10;
	}
	
	const userId = $("#userId").val();
	if(confirm("Are you sure to Edit?")){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './editLayoutLineData',
			data: {	
				autoId : autoId,
				lineId : lineId,
				operatorId : employeeId,
				hour1 : proQty1,
				hour2 : proQty2,
				hour3 : proQty3,
				hour4 : proQty4,
				hour5 : proQty5,
				hour6 : proQty6,
				hour7 : proQty7,
				hour8 : proQty8,
				hour9 : proQty9,
				hour10 : proQty10,
				total : totalQty,
				userId: userId
			},
			success: function (data) {
				if(data.result=="Successful")
					alert("Edit Successful...");
				else
					alert(data.result);
				//refreshAction();	        
			}
		});
	}

}

function refreshAction() {
	location.reload();
}



var today = new Date();
document.getElementById("layoutDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);


