function searchPolyDetails(buyerId, buyerOrderId, styleId, itemId) {
	var layoutDate = $('#production' + itemId).html();
	const type ='1';
	const layoutCategory="Poly Production";
	const layoutName = "Finishing Production & Reject Report ";
	var url = `printPolyDetails/${buyerId}@${buyerOrderId}@${styleId}@${itemId}@${layoutDate}@${type}@${layoutName}@${layoutCategory}`;
	window.open(url, '_blank');

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
				
				drawItemTable(data.result,data.employeeresult,data.sizelist);
				
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

		options += "<option id='employee' value='" + item.employeeId + "'>" + item.employeeName + "</option>"
	}
	return options;
};

function getPolyType() {
	let options = "";

	options += "<option id='polytype' value='0'>Select Type</option>"
	options += "<option id='polytype' value='1'>Blister Poly</option>"
	options += "<option id='polytype' value='2'>Single Poly</option>"
	options += "<option id='polytype' value='3'>Single Blister</option>"

	return options;
};






function saveAction(){
	
	var userId=$('#userId').val();
	var buyerId=$('#buyerId').val();
	var buyerorderId=$('#buyerorderId').val();
	var purchaseOrder=$('#purchaseOrder').val();
	var styleId=$('#styleId').val();
	var itemId=$('#itemId').val();
	var lineId=$('#lineId').val();
	var platQty=$('#planQty').val();
	var dailyTarget=$('#dailyTargetQty').val();
	var dailyLineTarget=$('#dailyLineTargetQty').val();
	var hours=$('#hours').val();
	var hourlyTarget=$('#hourlyTarget').val();
	var layoutDate=$('#layoutDate').val();
	var layoutName="1";
	
	var resultlist=[];

	if(buyerId=='' || buyerorderId=='' || styleId=='' || itemId=='' || layoutDate==''){
		alert("information Incomplete");
	}
	else{
		
		var i=0;
		var value=0;
		var j=0;
		$('.lineItemRow').each(function(){
			
				var rowdata=$(this).attr("data-id");
				
				
				
				//var employeeId=0;;
				
				var lineId=rowdata.substring(0,rowdata.indexOf("*"));
				
				var colorId=rowdata.substring(rowdata.indexOf("*")+1,rowdata.lastIndexOf("*"));
				var sizeId=rowdata.substring(rowdata.lastIndexOf("*")+1,rowdata.length);
				
				var employeeId=$("#employee-"+lineId).val();
				
				var polytype=parseFloat(($("#polytype-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$("#polytype-"+lineId+"-"+colorId+"-"+sizeId).val()));
				
				//machone 2025*31*1 etate venge size id ber korte hobe
				var pcsQty=parseFloat(($(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()));
				var unitqty=parseFloat(($(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()));

				var totalpoly=parseFloat(($(".poly-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".poly-"+lineId+"-"+colorId+"-"+sizeId).val()));

				
				var totalQty=pcsQty*unitqty;
				
				
				

				resultlist[i]=lineId+"*"+employeeId+"*"+colorId+"*"+sizeId+"*"+polytype+"*"+pcsQty+"*"+unitqty+"*"+totalQty+"*"+totalpoly;
				i++;
				

			
		});
		
		resultlist="["+resultlist+"]"
		
		

		$.ajax({
			type: 'POST',
			dataType: 'json',
			data:{
				buyerId:buyerId,
				buyerorderId:buyerorderId,
				purchaseOrder:purchaseOrder,
				styleId:styleId,
				itemId:itemId,
				lineId:lineId,
				platQty:platQty,
				dailyTarget:dailyTarget,
				dailyLineTarget:dailyLineTarget,
				hours:hours,
				hourlyTarget:hourlyTarget,
				resultlist:resultlist,
				layoutDate:layoutDate,
				layoutName:layoutName,
				userId:userId
			},
			url: './savePolyPackingDetails/',
			success: function (data) {

				alert(data);
		        //refreshAction();
		        
			}
		});
		
		
	}
	

}

function drawItemTable(dataList, employeeResult,sizelist) {

	const employeeList = getOptions(employeeResult);
	const polyList = getPolyType();

	var length = dataList.length;
	var sizeLength = sizelist.length;

	
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

				<th  class='width-120'>Line Name </th>
				<th  class='width-120'>Operator Name</th>
				<th  class='t-col'>Size</th>
				<th  ><p style='width:100px;'>Poly Type</p></th>
				<th  ><p style='width:47px;'>Pcs<p/></th>
				<th  class='t-col'><p style='width:47px;'>Blister</p></th>			
				<th  class='t-col'><p style='width:47px;'>Total</th>
				<th  class='t-col'><p style='width:47px;'>Used Poly</th>
				<th  class='t-col'>Edit</th>
				<th  class='t-col'>+Size Entry</th>
				</tr>
				</thead>
				<tbody id="dataList">`

		}

		
		
		tables += "<tr class='itemRow accordion-toggle collapsed'  data-id='"+ item.lineId +"' id='accordion1-"+item.lineId+"' data-toggle='collapse' data-parent='#accordion1-"+item.lineId+"' href='#collapseOne-"+item.lineId+"'>" +
		"<td class='width-120'>" + item.lineName + "</br><input  type='hidden' class='from-control min-height-20 sewingline-"+item.lineId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></td>" +
		"<th><select id='employee-" + item.lineId + "'  class='selectpicker employee-width tableSelect employee-"+item.lineId + " col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + employeeList + "</select></th>" + 
		"<td class='t-col'></td>"+ 
		"<td style='width:50px;'></td>"+ 
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+
		"<td class='t-col'></td>"+
		"<td class='t-col'></td> <td class='t-col expand-button'></td></tr>"
		
		tables+="<tr class='hide-table-padding'  ><td colspan='12'>"
			
			tables+="<div id='collapseOne-"+item.lineId+"' class='collapse'>"
			tables+="<table table-hover table-bordered table-sm mb-0 small-font>"
		
		for (var j = 0; j < sizeLength; j++) {
			var sizeitem = sizelist[j];
			

			//tables+="<tr class='hide-table-padding'><td colspan='2'>"+
			
			
			tables+="<tr class='lineItemRow' data-id='"+item.lineId+"*"+sizeitem.colorId+"*"+sizeitem.sizeId+"'>"+
			"<td style='width:120px;'></td>"+
			"<td style='width:145px;'>"+sizeitem.colorName+"</td>"+
			"<td style='width:70px;'>"+sizeitem.sizeName+"</td>" +
			"<th><select onchange='setBlisterDefaulQty("+item.lineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' style='width:50px;' id='polytype-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"'  class='selectpicker  tableSelect polytype-" + item.lineId + " col-md-12 px-0 polytype-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>" + polyList + "</select></th>" +
			"<td class='t-col'><input style='width:100px;' type='text' onkeyup='setTotalQty("+item.lineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col  min-height-20 polypcs-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"'  value='' /></td>"+
			"<td class='t-col'><input style='width:105px;' type='text' id='unitqty-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"' onkeyup='setTotalQty("+item.lineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col  min-height-20 unitqty-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"'  value='' /></td>"+
			"<td class='t-col'><input style='width:100px;' type='text' readonly id='prouduction-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-total' class='from-control min-height-20  prouduction-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"'/></td>"+
			"<td class='t-col'><input style='width:100px;' type='text' readonly id='poly-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"' class='from-control min-height-20  poly-"+item.lineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"'/></td>"+
			"<td class='t-col'></td>"+
			"<td class='t-col'></td></tr>"

			

		}
		tables+="</table>"
		tables+="</div></td>" 
		tables+="</tr>"

	}


	tables += "</tbody></table> </div></div>";
	
	document.getElementById("tableList").innerHTML = tables;
	$('.tableSelect').selectpicker('refresh');
}

function setBlisterDefaulQty(lineId,colorId,sizeId){
	
	
	var blisterUnitQty="#unitqty-"+lineId+"-"+colorId+"-"+sizeId;

	
	var polytype=$("#polytype-"+lineId+"-"+colorId+"-"+sizeId).val();
	
	
	if(polytype=='1'){
		$(blisterUnitQty).val('1');
		$(blisterUnitQty).prop('disabled', true);
	}
	else{
		$(blisterUnitQty).val('');
		$(blisterUnitQty).prop('disabled', false);
	}
	
	

	
	
}
function setTotalQty(lineId,colorId,sizeId){
	
	var totalQtyLineId="#prouduction-"+lineId+"-"+colorId+"-"+sizeId+"-total";
	
	//alert("totalQtyLineId "+totalQtyLineId);
	
	var pcsQty=parseFloat(($(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()));
	
	var unitqty=parseFloat(($(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()));

	$(totalQtyLineId).val(pcsQty*unitqty);
	
	var totalployId="#poly-"+lineId+"-"+colorId+"-"+sizeId;
	var polytype=$("#polytype-"+lineId+"-"+colorId+"-"+sizeId).val();
	
	if(polytype=='1'){
		var pcsQty=parseFloat(($(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()));
		
		var unitqty=parseFloat(($(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()));

		var totalpoly=(pcsQty*unitqty);
		
		$(totalployId).val(totalpoly);
	}
	else if(polytype=='3'){
		
		var pcsQty=parseFloat(($(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".polypcs-"+lineId+"-"+colorId+"-"+sizeId).val()));
		
		var unitqty=parseFloat(($(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()));

		var totalpoly=(pcsQty*unitqty)+unitqty;
		
		$(totalployId).val(totalpoly);

	}
	else{
		var unitqty=parseFloat(($(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()==''?"0":$(".unitqty-"+lineId+"-"+colorId+"-"+sizeId).val()));

		$(totalployId).val(unitqty);
	}
	
	
	


}

var today = new Date();
document.getElementById("layoutDate").value = today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2);

