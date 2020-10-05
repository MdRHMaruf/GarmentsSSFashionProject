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
				drawTableItem(data.result,data.sizelistresult);
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
		$('.machineItemRow').each(function(){
			
				var rowdata=$(this).attr("data-id");
				
				console.log("rowdata "+rowdata);

	
				var employeeId="0";
				
				var machineId=rowdata.substring(0,rowdata.indexOf("*"));
				
				var colorId=rowdata.substring(rowdata.indexOf("*")+1,rowdata.lastIndexOf("*"));
				var sizeId=rowdata.substring(rowdata.lastIndexOf("*")+1,rowdata.length);
				
				
				//machone 2025*31*1 etate venge size id ber korte hobe
				
				var proQty1=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h1").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h1").val()));
				var proQty2=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h2").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h2").val()));
				var proQty3=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h3").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h3").val()));
				var proQty4=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h4").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h4").val()));
				var proQty5=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h5").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h5").val()));
				var proQty6=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h6").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h6").val()));
				var proQty7=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h7").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h7").val()));
				var proQty8=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h8").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h8").val()));
				var proQty9=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h9").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h9").val()));
				var proQty10=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h10").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h10").val()));
				
	
				var totalQty=proQty1+proQty2+proQty3+proQty4+proQty5+proQty6+proQty7+proQty8+proQty9+proQty10;
				
				
				var planvalue=proQty1+":"+proQty2+":"+proQty3+":"+proQty4+":"+proQty5+":"+proQty6+":"+proQty7+":"+proQty8+":"+proQty9+":"+proQty10;
	
				resultlist[i]=machineId+"*"+employeeId+"*"+colorId+"*"+sizeId+"*"+totalQty+"*"+planvalue;
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
			url: './saveSewingLayoutDetails/',
			success: function (data) {

				alert(data);
		        //refreshAction();
		        
			}
		});
		
		
	}
	

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
				<div class="table-responsive" >
				<table class="table  table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				<th  class='width-120'>Machine Name </th>
				<th  class='width-120'>Employee Name</th>
				<th  class='width-120'>Process Name</th>
				<th  class='t-col'>Size</th>

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
				<th  class='t-col'>+Size Entry</th>
				</tr>
				</thead>
				<tbody id="dataList">`

		}

		
		
		tables += "<tr class='itemRow accordion-toggle collapsed'  data-id='"+ item.machineId +"' id='accordion1-"+item.machineId+"' data-toggle='collapse' data-parent='#accordion1-"+item.machineId+"' href='#collapseOne-"+item.machineId+"'>" +
		"<td class='width-120'>" + item.machineName + "</br><input  type='hidden' class='from-control min-height-20 sewingline-"+item.machineId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></td>" +
		"<td class='width-120'>" + item.operatorName + "</td>"+ 
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+
		"<td class='t-col'></td>"+
		"<td class='t-col'></td>"+
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+
		"<td class='t-col'></td>"+ 
		"<td class='t-col'></td>"+
		"<td class='t-col'></td>"+
		"<td class='t-col'></td> <td class='t-col expand-button'>+Size Add</td></tr>"
		
		tables+="<tr class='hide-table-padding'  ><td colspan='16'>"
			
			tables+="<div id='collapseOne-"+item.machineId+"' class='collapse'>"
			tables+="<table table-hover table-bordered table-sm mb-0 small-font>"
		
		for (var j = 0; j < sizeLength; j++) {
			var sizeitem = sizeList[j];
			

			//tables+="<tr class='hide-table-padding'><td colspan='2'>"+
			
			
			tables+="<tr class='machineItemRow' data-id='"+item.machineId+"*"+sizeitem.colorId+"*"+sizeitem.sizeId+"'>"+
			"<td class='width-120'></td>"+
			"<td class='width-120'></td>"+
			"<td class='width-120'>"+sizeitem.colorName+"</td>"+
			"<td class='t-col'>"+sizeitem.sizeName+"</td>" +
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col  min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h1'  value='' /></td>"+ 
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h2'  value=''/></td>"+ 
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h3'  value=''/></td>"+ 
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h4'  value=''/></td>"+
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h5'  value=''/></td>"+
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h6'  value=''/></td>"+
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h7'  value=''/></td>"+ 
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h8'  value=''/></td>"+
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h9'  value=''/></td>"+ 
			"<td class='t-col'><input  type='text' onkeyup='setTotalQty("+item.machineId+","+sizeitem.colorId+","+sizeitem.sizeId+")' class='from-control t-col min-height-20 prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-h10'  value=''/></td>"+
			"<td class='t-col'><input  type='text' readonly id='prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-total' class='from-control min-height-20  prouduction-"+item.machineId+"-"+sizeitem.colorId+"-"+sizeitem.sizeId+"-total'/></td>"+
			"<td class='t-col'></td>"+
			"<td class='t-col'></td></tr>"

			

		}
		tables+="</table>"
		tables+="</div></td>" 
		tables+="</tr>"

	}


	tables += "</tbody></table> </div></div>";
	
	document.getElementById("tableList").innerHTML = tables;

}

function setTotalQty(machineId,colorId,sizeId){
	
	var totalQtyLineId="#prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-total";
	
	//alert("totalQtyLineId "+totalQtyLineId);
	
	var Qty1=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h1").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h1").val()));
	var Qty2=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h2").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h2").val()));
	var Qty3=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h3").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h3").val()));
	var Qty4=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h4").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h4").val()));
	var Qty5=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h5").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h5").val()));
	var Qty6=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h6").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h6").val()));
	var Qty7=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h7").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h7").val()));
	var Qty8=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h8").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h8").val()));
	var Qty9=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h9").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h9").val()));
	var Qty10=parseFloat(($(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h10").val()==''?"0":$(".prouduction-"+machineId+"-"+colorId+"-"+sizeId+"-h10").val()));
	
	var totalQty=Qty1+Qty2+Qty3+Qty4+Qty5+Qty6+Qty7+Qty8+Qty9+Qty10;
	
	$(totalQtyLineId).val(totalQty);

}