function searchSewingLayout(buyerId,buyerorderId,styleId,itemId,lineId){
	
	var productionDate=$('#production'+itemId).html();
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: './searchProductionInfo',
        data:{
        	buyerId:buyerId,
        	buyerorderId:buyerorderId,
        	styleId:styleId,
        	itemId:itemId,
        	lineId:lineId,
        	productionDate:productionDate
        	},
        success: function (data) {
          if (data== "Success") {
      		var url = "printSewingHourlyReport";
    		window.open(url, '_blank');

          }
        }
      });

}

function refreshAction(){
	  location.reload();
}


function setProductPlanInfoForSewing(buyerId,buyerorderId,styleId,itemId,planQty){


	var buyername=$('#buyerId'+buyerId).html();
	var purchaseOrder=$('#purchaseOrder'+buyerorderId).html();
	var styleNo=$('#styleId'+styleId).html();
	var itemName=$('#itemId'+itemId).html();

	$('#buyerName').val(buyername);
	$('#purchaseOrder').val(purchaseOrder);
	$('#styleNo').val(styleNo);
	$('#itemName').val(itemName);
	$('#planQty').val(planQty);


	$('#buyerId').val(buyerId);
	$('#buyerorderId').val(buyerorderId);
	$('#styleId').val(styleId);
	$('#itemId').val(itemId);

	$('#exampleModal').modal('hide');

	$.ajax({
		type: 'GET',
		dataType: 'json',
		data:{
			buyerId:buyerId,
			buyerorderId:buyerorderId,
			purchaseOrder:purchaseOrder,
			styleId:styleId,
			itemId:itemId,
			planQty:planQty
		},
		url: './searchSewingLineSetup/',
		success: function (data) {


			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawLineItem(data.result);
			}
		}
	});
}

function drawLineItem(dataList) {


	let lineoption = "";
	lineoption += "<select id='lineId' class='selectpicker lineselect form-control' data-live-search='true' data-style='btn-light border-secondary form-control-sm' onchange='linewisemachineload()'>"; 
	
	lineoption += "<option id='lineId' value='0'>Select Line</option>" 
		
	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];
		
		lineoption += "<option id='lineId' value='"+item.lineId+"'>"+item.lineName+"</option>" 
		
	}

	lineoption+="</select>";
	
	document.getElementById("lineoption").innerHTML = lineoption;
	
	 $('.lineselect').selectpicker('refresh');
	
	 $('#planQty').val(parseFloat(dataList[0].planQty).toFixed(2));
	 $('#dailyTargetQty').val(parseFloat(dataList[0].dailyTarget).toFixed(2));
	 $('#dailyLineTargetQty').val(parseFloat(dataList[0].dailyLineTarget).toFixed(2));
	 $('#hours').val(parseFloat(dataList[0].hours).toFixed(2));
	 $('#hourlyTarget').val(parseFloat(dataList[0].hourlyTarget).toFixed(2));

}


function linewisemachineload(){
	var lineId=$('#lineId').val();
	var buyerId=$('#buyerId').val();
	var buyerorderId=$('#buyerorderId').val();
	var purchaseOrder=$('#purchaseOrder').val();
	var styleId=$('#styleId').val();
	var itemId=$('#itemId').val();
	

	
	$.ajax({
		type: 'GET',
		dataType: 'json',
		data:{
			lineId:lineId,
			buyerId:buyerId,
			buyerorderId:buyerorderId,
			styleId:styleId,
			itemId:itemId,
			purchaseOrder:purchaseOrder
		},
		url: './lineWiseMachineList/',
		success: function (data) {

		
			
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawTableItem(data.result,data.sizelistresult,data.processList);
			}
		}
	});
}


function saveAction() {
	var userId = $('#userId').val();
	var buyerId = $('#buyerId').val();
	var buyerOrderId = $('#buyerOrderId').val();
	var purchaseOrder = $('#purchaseOrder').val();
	var styleId = $('#styleId').val();
	var itemId = $('#itemId').val();
	var lineId = $('#lineId').val();
	var platQty = $('#planQty').val();
	var dailyTarget = $('#dailyTargetQty').val();
	var dailyLineTarget = $('#dailyLineTargetQty').val();
	var hours = $('#hours').val();
	var hourlyTarget = $('#hourlyTarget').val();
	var layoutDate = $('#layoutDate').val();
	var layoutName = $('#type').val();

	var resultList = [];

	if (buyerId == '' || buyerOrderId == '' || styleId == '' || itemId == '' || layoutDate == '' || lineId=='') {
		alert("information Incomplete");
	}
	else {

		var i = 0;
		var value = 0;
		var j = 0;
		$('.itemRow').each(function () {

			var id = $(this).attr("data-id");

			var machineId = id;
			var employeeId = $(".employee-"+id).val();
			var processId = $("#process").val();

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
			var layoutValue = proQty1 + ":" + proQty2 + ":" + proQty3 + ":" + proQty4 + ":" + proQty5 + ":" + proQty6 + ":" + proQty7 + ":" + proQty8 + ":" + proQty9 + ":" + proQty10;

			resultList[i] = machineId+"*"+processId+"*"+employeeId + "*" + lineId + "*" + totalQty + "*" + layoutValue;
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
				url: './saveLineInceptionRejectDetails/',
				success: function (data) {
	
					alert("Line Inspection Reject Save Successfully...");
					//refreshAction();	        
				}
			});
		}
		
	}
}

function getOptions(dataList) {
	let options = "";
	var length = dataList.length;

	options += "<option value='0'>Select Inspection</option>"
	for (var i = 0; i < length; i++) {
		var item = dataList[i];

		options += "<option  value='" + item.processId + "'>" + item.processName + "</option>"
	}
	return options;
};

function drawTableItem(dataList,sizeList,processDataList) {

	const processList = getOptions(processDataList);
	
	var length = dataList.length;
	
	var sizeLength = sizeList.length;

	
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];


		if(i==0){
			tables += `<div class="row">
				<div class="table-responsive" >
				<table class="table  table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				<th  class='width-120'>Machine Name </th>
				<th  class='width-120'>Employee Name</th>
				<th  class='width-120'>Process Name</th>

				<th  class='t-col'>08-09</th>
				<th  class='t-col'>09-10</th>
				<th  class='t-col'>10-11</th>
				<th  class='t-col'>11-12</th>
				<th  class='t-col'>12-01</th>
				<th  class='t-col'>02-03</th>
				<th  class='t-col'>03-04</th>
				<th  class='t-col'>04-05</th>
				<th  class='t-col'>05-06</th>
				<th  class='t-col'>06-07</th>
				<th  class='t-col'>Total</th>
				<th  class='t-col'>Edit</th>
				</tr>
				</thead>
				<tbody id="dataList">`

		}

		
		
		tables += "<tr class='itemRow'  data-id='"+ item.machineId +"'  data-toggle='collapse' data-parent='#accordion1-"+item.machineId+"' href='#collapseOne-"+item.machineId+"'>" +
		"<td class='width-120'>" + item.machineName + "</br></td>" +
		"<td class='width-120'>" + item.operatorName + "<input  type='hidden' class='from-control min-height-20 employee-"+item.machineId+"'  value='"+parseFloat(item.operatorId).toFixed()+"' /></td>"+ 
		"<th><select id='process' class='selectpicker employee-width tableSelect process-" + item.machineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + processList + "</select></th>" + 
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h1'  value='' /></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h2'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h3'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h4'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h5'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h6'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h7'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h8'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h9'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-h10'  value=''/></td>" +
		"<td><input  type='text' onkeyup='setTotalQty(" + item.machineId + ")' class='form-control-sm t-col' id='line-" + item.machineId + "-total'  value=''/></td>" +
		"<td class='t-col'><i class='fa fa-edit' onclick='setProductPlanInfoForSewing(${list.buyerId},${list.buyerorderId},${list.styleId},${list.itemId},${list.planQty})'> </i></td>"
		
	}


	tables += "</tbody></table> </div></div>";
	
	document.getElementById("tableList").innerHTML = tables;
	$('.tableSelect').selectpicker('refresh');
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

var today = new Date();
document.getElementById("layoutDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);

