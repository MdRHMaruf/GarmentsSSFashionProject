function viewSewingFinishingProduction(buyerId,buyerorderId,styleId,itemId,productionDate){
	
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
      		var url = "printSewingFinishingHourlyReport";
    		window.open(url, '_blank');

          }
        }
      });

}


function searchSewingFinishingProduction(buyerId,buyerorderId,styleId,itemId){
	
	var productionDate=$('#production'+itemId).html();

	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: './viewSewingFinishingProduction',
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


function drawItemTable(dataList) {


	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	
	var j=0;
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
		

		
		
		var LineTitle="";
		if(j==0){
			LineTitle='Sewing'
				tables += "<tr class='itemRow' data-id='"+item.sewingLineAutoId+"'>" +
				"<th rowspan='2'>" + item.lineName + "</br> "+LineTitle+ "<input  type='hidden' class='from-control min-height-20 finishline-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></th>" +
				"<th rowspan='2'>" + item.styleNo + "</th>"+ 
				"<th rowspan='2'>" + parseFloat(item.dailyLineTarget).toFixed() +"<input  type='hidden' class='from-control min-height-20 sewingdailytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.dailyLineTarget).toFixed()+"' /></th>"+
				"<th rowspan='2'>" + parseFloat(item.hourlyTarget).toFixed() +"<input  type='hidden' class='from-control min-height-20 sewinghourlytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.hourlyTarget).toFixed()+"' /></th>"+
				"<th rowspan='2'>10</th>"+
				"<th rowspan='2'></th>"+
				"<th rowspan='2'></th>"+
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
					
		}
		else if(j==1){
			LineTitle='Sewing';
			tables += "<tr class='itemRow' data-id='"+item.sewingLineAutoId+"'>" +

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
			
		}
		else if(j==2){
			LineTitle='Finising'
				tables += "<tr class='itemRow' data-id='"+item.sewingLineAutoId+"'>" +
				"<th rowspan='2'>" + item.lineName + "</br> "+LineTitle+ "<input  type='hidden' class='from-control min-height-20 finishline-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></th>" +
				"<th rowspan='2'>" + item.styleNo + "</th>"+ 
				"<th rowspan='2'>" + parseFloat(item.dailyLineTarget).toFixed() +"<input  type='hidden' class='from-control min-height-20 sewingdailytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.dailyLineTarget).toFixed()+"' /></th>"+
				"<th rowspan='2'>" + parseFloat(item.hourlyTarget).toFixed() +"<input  type='hidden' class='from-control min-height-20 sewinghourlytarget-"+item.sewingLineAutoId+"'  value='"+parseFloat(item.hourlyTarget).toFixed()+"' /></th>"+
				"<th rowspan='2'>10</th>"+
				"<th rowspan='2'></th>"+
				"<th rowspan='2'></th>"+
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
					
		}
		else if(j==3){
			LineTitle='Finising';
			tables += "<tr class='itemRow' data-id='"+item.sewingLineAutoId+"'>" +

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
			
		}
		if(j==3){
			j=0;
		}
		else{
			j++;
		}
		

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
