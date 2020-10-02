function searchSewingProduction(buyerId,buyerorderId,styleId,itemId,productionDate){
	
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

function saveAction(){
	var userId=$('#userId').val();
	var buyerId=$('#buyerId').val();
	var buyerorderId=$('#buyerorderId').val();
	var purchaseOrder=$('#purchaseOrder').val();
	var styleId=$('#styleId').val();
	var itemId=$('#itemId').val();
	
	var resultlist=[];

	if(buyerId=='' || buyerorderId=='' || styleId=='' || itemId==''){
		alert("information Incomplete");
	}
	else{
		
		var i=0;
		var value=0;
		var j=0;
		$('.itemRow').each(function(){
			
			if(j%2==0){
				var id=$(this).attr("data-id");
				
				var lineId=$(".sewingline-"+id).val();
				var dailyTarget=$(".sewingdailytarget-"+id).val();
				var sewinghourlytarget=$(".sewinghourlytarget-"+id).val();
				
				
				var proQty1=parseFloat(($(".prouduction-"+id+"-h1").val()==''?"0":$(".prouduction-"+id+"-h1").val()));
				var proQty2=parseFloat(($(".prouduction-"+id+"-h2").val()==''?"0":$(".prouduction-"+id+"-h2").val()));
				var proQty3=parseFloat(($(".prouduction-"+id+"-h3").val()==''?"0":$(".prouduction-"+id+"-h3").val()));
				var proQty4=parseFloat(($(".prouduction-"+id+"-h4").val()==''?"0":$(".prouduction-"+id+"-h4").val()));
				var proQty5=parseFloat(($(".prouduction-"+id+"-h5").val()==''?"0":$(".prouduction-"+id+"-h5").val()));
				var proQty6=parseFloat(($(".prouduction-"+id+"-h6").val()==''?"0":$(".prouduction-"+id+"-h6").val()));
				var proQty7=parseFloat(($(".prouduction-"+id+"-h7").val()==''?"0":$(".prouduction-"+id+"-h7").val()));
				var proQty8=parseFloat(($(".prouduction-"+id+"-h8").val()==''?"0":$(".prouduction-"+id+"-h8").val()));
				var proQty9=parseFloat(($(".prouduction-"+id+"-h9").val()==''?"0":$(".prouduction-"+id+"-h9").val()));
				var proQty10=parseFloat(($(".prouduction-"+id+"-h10").val()==''?"0":$(".prouduction-"+id+"-h10").val()));
	
				var rejectQty1=parseFloat(($(".reject-"+id+"-h1").val()==''?"0":$(".reject-"+id+"-h1").val()));
				var rejectQty2=parseFloat(($(".reject-"+id+"-h2").val()==''?"0":$(".reject-"+id+"-h2").val()));
				var rejectQty3=parseFloat(($(".reject-"+id+"-h3").val()==''?"0":$(".reject-"+id+"-h3").val()));
				var rejectQty4=parseFloat(($(".reject-"+id+"-h4").val()==''?"0":$(".reject-"+id+"-h4").val()));
				var rejectQty5=parseFloat(($(".reject-"+id+"-h5").val()==''?"0":$(".reject-"+id+"-h5").val()));
				var rejectQty6=parseFloat(($(".reject-"+id+"-h6").val()==''?"0":$(".reject-"+id+"-h6").val()));
				var rejectQty7=parseFloat(($(".reject-"+id+"-h7").val()==''?"0":$(".reject-"+id+"-h7").val()));
				var rejectQty8=parseFloat(($(".reject-"+id+"-h8").val()==''?"0":$(".reject-"+id+"-h8").val()));
				var rejectQty9=parseFloat(($(".reject-"+id+"-h9").val()==''?"0":$(".reject-"+id+"-h9").val()));
				var rejectQty10=parseFloat(($(".reject-"+id+"-h10").val()==''?"0":$(".reject-"+id+"-h10").val()));
				
				var productionvalue=proQty1+":"+proQty2+":"+proQty3+":"+proQty4+":"+proQty5+":"+proQty6+":"+proQty7+":"+proQty8+":"+proQty9+":"+proQty10;
				var rejectvalue=rejectQty1+":"+rejectQty2+":"+rejectQty3+":"+rejectQty4+":"+rejectQty5+":"+rejectQty6+":"+rejectQty7+":"+rejectQty8+":"+rejectQty9+":"+rejectQty10;
				
				resultlist[i]=lineId+"*"+dailyTarget+"*"+sewinghourlytarget+"*"+productionvalue+"*"+rejectvalue;
				i++;
				
			}
			j++;

			
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
				resultlist:resultlist,
				userId:userId
			},
			url: './saveSewingProductionDetails/',
			success: function (data) {

				alert(data);
		        refreshAction();

/*				if (data.result == "Something Wrong") {
					dangerAlert("Something went wrong");
				} else if (data.result == "duplicate") {
					dangerAlert("Duplicate Item Name..This Item Name Already Exist")
				} else {
					drawItemTable(data.result);
				}*/
			}
		});
		
		
	}
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
	$.ajax({
		type: 'GET',
		dataType: 'json',
		data:{
			lineId:lineId
		},
		url: './lineWiseMachineList/',
		success: function (data) {

		
			
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawTableItem(data.result,data.sizelistresult);
			}
		}
	});
}

function drawTableItem(dataList,sizeList) {

	var length = dataList.length;
	
	var sizeLength = sizeList.length;

	
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];


		if(i==0){
			tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				<th scope="col" class="min-width-120">Machine Name </th>
				<th scope="col">Employee Name</th>


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
				<th scope="col">+Size Entry</th>
				</tr>
				</thead>
				<tbody id="dataList">`

		}

		
		
		tables += "<tr class='itemRow' data-id='"+ item.sewingLineAutoId +"' class='accordion-toggle collapsed' id='accordion1' data-toggle='collapse' data-parent='#accordion1' href='#collapseOne'>" +
		"<th >" + item.machineName + "</br><input  type='hidden' class='from-control min-height-20 sewingline-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></th>" +
		"<th >" + item.operatorName + "</th>"+ 

		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h1'  value='' /></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h2'  value=''/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h3'  value=''/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h4'  value=''/></td>"+
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h5'  value=''/></td>"+
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h6'  value=''/></td>"+
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h7'  value=''/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h8'  value=''/></td>"+
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h9'  value=''/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 prouduction-"+item.sewingLineAutoId+"-h10'  value=''/></td>"+
		"<td><input  type='text' readonly class='from-control min-height-20  prouduction-"+item.sewingLineAutoId+"-total'/></td>"+
		"<td><button type='button' class='btn btn-outline-dark btn-sm max-height-20'><i class='fa fa-edit'></i></button></td> <td class='expand-button'>+Size Add</td></tr>"
		
/*		for (var i = 0; i < length; i++) {
			
		}*/
		
		tables+="<tr class='hide-table-padding'>+<td></td><td colspan='1'>"+
		"<div id='collapseOne' class='collapse in p-3'>"+
		"<div class='row'>"+
		"<div class='col-2'>label</div>"+
		"<div class='col-6'>value 1</div>"+
		"</div>"+
		"<div class='row'>"+
		"<div class='col-2'>label</div>"+
		"<div class='col-6'>value 2</div>"+
		"</div>"+

		"</div></td>"+
		"</tr>"

	}


	tables += "</tbody></table> </div></div>";
	
	document.getElementById("tableList").innerHTML = tables;

}