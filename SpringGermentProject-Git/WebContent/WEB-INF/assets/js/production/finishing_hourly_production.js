function searchFinishingProduction(buyerId,buyerorderId,styleId,itemId,productionDate){
	
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
      		var url = "printFinishingHourlyReport";
    		window.open(url, '_blank');

          }
        }
      });

}

function searchSewingProduction(buyerId,buyerorderId,styleId,itemId){
	
	var productionDate=$('#production'+itemId).html();

	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: './viewSewingProduction',
        data:{
        	buyerId:buyerId,
        	buyerorderId:buyerorderId,
        	styleId:styleId,
        	itemId:itemId,
        	productionDate:productionDate
        	},
        success: function (data) {
        	drawItemTable(data.result);
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
			
				if(j==0){
					var id=$(this).attr("data-id");
					
					var lineId=$(".finishline-"+id).val();
					var dailyTarget=$(".sewingdailytarget-"+id).val();
					var sewinghourlytarget=$(".sewinghourlytarget-"+id).val();
					var passQty1=parseFloat(($(".passed-"+id+"-h1").val()==''?"0":$(".passed-"+id+"-h1").val()));
					var passQty2=parseFloat(($(".passed-"+id+"-h2").val()==''?"0":$(".passed-"+id+"-h2").val()));
					var passQty3=parseFloat(($(".passed-"+id+"-h3").val()==''?"0":$(".passed-"+id+"-h3").val()));
					var passQty4=parseFloat(($(".passed-"+id+"-h4").val()==''?"0":$(".passed-"+id+"-h4").val()));
					var passQty5=parseFloat(($(".passed-"+id+"-h5").val()==''?"0":$(".passed-"+id+"-h5").val()));
					var passQty6=parseFloat(($(".passed-"+id+"-h6").val()==''?"0":$(".passed-"+id+"-h6").val()));
					var passQty7=parseFloat(($(".passed-"+id+"-h7").val()==''?"0":$(".passed-"+id+"-h7").val()));
					var passQty8=parseFloat(($(".passed-"+id+"-h8").val()==''?"0":$(".passed-"+id+"-h8").val()));
					var passQty9=parseFloat(($(".passed-"+id+"-h9").val()==''?"0":$(".passed-"+id+"-h9").val()));
					var passQty10=parseFloat(($(".passed-"+id+"-h10").val()==''?"0":$(".passed-"+id+"-h10").val()));
		
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
					
					var passvalue=passQty1+":"+passQty2+":"+passQty3+":"+passQty4+":"+passQty5+":"+passQty6+":"+passQty7+":"+passQty8+":"+passQty9+":"+passQty10;
					var rejectvalue=rejectQty1+":"+rejectQty2+":"+rejectQty3+":"+rejectQty4+":"+rejectQty5+":"+rejectQty6+":"+rejectQty7+":"+rejectQty8+":"+rejectQty9+":"+rejectQty10;
					
					resultlist[i]=lineId+"*"+dailyTarget+"*"+sewinghourlytarget+"*"+passvalue+"*"+rejectvalue;
					i++;
				
				}
				
				if(j==3){
					j=0;
				}
				else{
					j++;
				}
			
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
			url: './saveFinishProductionDetails/',
			success: function (data) {

				alert(data);
		        refreshAction();
		        
			}
		});
		
		
	}
}


function refreshAction(){
	  location.reload();
}


function drawItemTable(dataList) {


	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];

		/*      if (isClosingNeed) {
	        tables += "</tbody></table> </div></div>";
	      }*/

		if(i==0){
			tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				<th scope="col" class="min-width-120">Line </th>
				<th scope="col">Style no</th>
				<th scope="col">Daily Line </br> Wise Target</th>
				<th scope="col">Hours </br>Target</th>
				<th scope="col">Hours</th>
				<th scope="col">Sew.Sup.</br>Signature</th>
				<th scope="col">Q.C.</br>Signature</th>
				<th scope="col">Qty </br>Type</th>
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

		}


		tables += "<tr class='itemRow' data-id='"+item.sewingLineAutoId+"'>" +
		"<th rowspan='3'>" + item.lineName + "</br> Finishing<input  type='hidden' class='from-control min-height-20 finishline-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></th>" +
		"<th rowspan='3'>" + item.styleNo + "</th>"+ 
		"<th rowspan='3'>" + parseFloat(item.dailyLineTarget).toFixed() +"<input  type='hidden' class='from-control min-height-20 sewingdailytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.dailyLineTarget).toFixed()+"' /></th>"+
		"<th rowspan='3'>" + parseFloat(item.hourlyTarget).toFixed() +"<input  type='hidden' class='from-control min-height-20 sewinghourlytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.hourlyTarget).toFixed()+"' /></th>"+
		"<th rowspan='3'>10</th>"+
		"<th rowspan='3'></th>"+
		"<th rowspan='3'></th>"+
		"<th>Production</th>"+
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour1).toFixed() +"' /></td>"+ 
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour2).toFixed() +"'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour3).toFixed() +"'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour4).toFixed() +"'/></td>"+
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour5).toFixed() +"'/></td>"+
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour6).toFixed() +"'/></td>"+
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour7).toFixed() +"'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour8).toFixed() +"'/></td>"+
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour9).toFixed() +"'/></td>"+
		"<td><input  type='text' class='from-control min-height-20' readonly value='"+ parseFloat(item.hour10).toFixed() +"'/></td>"+
		"<td><input  type='text' readonly class='from-control min-height-20'/></td>"+
		"<td><button type='button' class='btn btn-outline-dark btn-sm max-height-20'><i class='fa fa-edit'></i></button></td></tr>"
		
		tables += "<tr class='itemRow' data-id='"+item.sewingLineAutoId+"'>" +
		"<th>Passed</th>"+
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h1'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h2'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h3'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h4'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h5'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h6'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h7'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h8'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h9'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-h10'/></td>"+ 
		"<td><input  type='text' readonly class='from-control min-height-20 passed-"+item.sewingLineAutoId+"-total'/></td>"+
		"<td><button type='button' class='btn btn-outline-dark btn-sm max-height-20''><i class='fa fa-edit'></i></button></td></tr>"

		tables += "<tr class='itemRow' data-id='"+item.sewingLineAutoId+"'>" +
		"<th>Reject</th>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h1' /></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h2' /></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h3' /></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h4' /></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h5' /></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h6' /></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h7' /></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h8' /></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h9' /></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h10' /></td>"+ 
		"<td><input  type='text' readonly class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-total'/></td>"+
		"<td><button type='button' class='btn btn-outline-dark btn-sm max-height-20''><i class='fa fa-edit'></i></button></td></tr>"

	}

	tables += "</tbody></table> </div></div>";
	// tables += "</tbody></table> </div></div>";
	
	$('#buyerName').val(dataList[0].buyerName);
	$('#purchaseOrder').val(dataList[0].purchaseOrder);
	$('#styleNo').val(dataList[0].styleNo);
	$('#itemName').val(dataList[0].itemName);
	
	$('#buyerId').val(dataList[0].buyerId);
	$('#buyerorderId').val(dataList[0].buyerorderId);
	$('#styleId').val(dataList[0].styleId);
	$('#itemId').val(dataList[0].itemId);

	document.getElementById("tableList").innerHTML = tables;


}
