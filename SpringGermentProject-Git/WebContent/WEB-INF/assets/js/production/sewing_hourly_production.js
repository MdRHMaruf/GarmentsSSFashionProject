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
				drawItemTable(data.result);
			}
		}
	});
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


		tables += "<tr class='itemRow' data-id='"+ item.sewingLineAutoId +"'>" +
		"<th rowspan='2'>" + item.lineName + "</br> Sewing<input  type='hidden' class='from-control min-height-20 sewingline-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></th>" +
		"<th rowspan='2'>" + item.styleNo + "</th>"+ 
		"<th rowspan='2'>" + parseFloat(item.dailyLineTarget).toFixed() + "<input  type='hidden' class='from-control min-height-20 sewingdailytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.dailyLineTarget).toFixed()+"' /></th>"+
		"<th rowspan='2'>"+ parseFloat(item.hourlyTarget).toFixed() +"<input  type='hidden' class='from-control min-height-20 sewinghourlytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.hourlyTarget).toFixed()+"' /></th>"+
		"<th rowspan='2'>10</th>"+
		"<th rowspan='2'></th>"+
		"<th rowspan='2'></th>"+
		"<th>Production</th>"+
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
		"<td><button type='button' class='btn btn-outline-dark btn-sm max-height-20'><i class='fa fa-edit'></i></button></td></tr>"
		
		tables += "<tr class='itemRow' data-id='"+ item.sewingLineAutoId +"'>" +
		"<th>Reject</th>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h1'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h2'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h3'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h4'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h5'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h6'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h7'/></td>"+ 
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h8'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h9'/></td>"+
		"<td><input  type='text' class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-h10'/></td>"+
		"<td><input  type='text' readonly class='from-control min-height-20 reject-"+item.sewingLineAutoId+"-total'/></td>"+
		"<td><button type='button' class='btn btn-outline-dark btn-sm max-height-20''><i class='fa fa-edit'></i></button></td></tr>"

	}

	tables += "</tbody></table> </div></div>";
	// tables += "</tbody></table> </div></div>";

	document.getElementById("tableList").innerHTML = tables;


}
