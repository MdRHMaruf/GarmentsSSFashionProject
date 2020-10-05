
function printLayoutDetails(buyerId,buyerorderId,styleId,itemId,productionDate){
	
	var layoutDate=$('#layout'+itemId).html();
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: './searchLayoutInfo',
        data:{
        	buyerId:buyerId,
        	buyerorderId:buyerorderId,
        	styleId:styleId,
        	itemId:itemId,
        	layoutDate:layoutDate
        	},
        success: function (data) {
          if (data== "Success") {
      		var url = "printLayoutInfo";
    		window.open(url, '_blank');

          }
        }
      });

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
				
				drawItemTable(data.result,data.employeeresult);
			}
		}
	});
}


function getOptions(dataList){
	let options = "";
	var length = dataList.length;
	
	options += "<option value='0'>Select Employee</option>" 
	for (var i = 0; i < length; i++) {
		var item = dataList[i];
		
	    options += "<option  value='"+item.employeeId+"'>"+item.employeeName+"</option>" 
	}
	

	 return options;
};

function drawItemTable(dataList,employeeresult) {

	const employeeList= getOptions(employeeresult);
	
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
				</tr>
				</thead>
				<tbody id="dataList">`

		}


		tables += "<tr class='itemRow' data-id='"+ item.lineId +"'>" +
		"<th >" + item.lineName + "</br><input  type='hidden' class='from-control min-height-20 line-"+item.lineId+"'  value='"+parseFloat(item.lineId).toFixed()+"' /></th>" +
		"<th><select id='employee-"+ item.lineId +"'  class='selectpicker employee-width tableSelect employee-"+ item.lineId +" col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'>"+employeeList+"</select></th>"+ 
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h1'  value='' /></td>"+ 
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h2'  value=''/></td>"+ 
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h3'  value=''/></td>"+ 
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h4'  value=''/></td>"+
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h5'  value=''/></td>"+
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h6'  value=''/></td>"+
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h7'  value=''/></td>"+ 
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h8'  value=''/></td>"+
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h9'  value=''/></td>"+ 
		"<td><input  type='text' onkeyup='setTotalQty("+item.lineId+")' class='from-control min-height-20 layout-"+item.lineId+"-h10'  value=''/></td>"+
		"<td><input  type='text' id='layout-"+item.lineId+"-total' readonly class='from-control min-height-20  layout-"+item.lineId+"-total'/></td>"+
		"<td><button type='button' class='btn btn-outline-dark btn-sm max-height-20'><i class='fa fa-edit'></i></button></td></tr>"
		
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

function setTotalQty(id){
	
	var totalQtyLineId="#layout-"+id+'-total';
	
	//alert("totalQtyLineId "+totalQtyLineId);
	
	var Qty1=parseFloat(($(".layout-"+id+"-h1").val()==''?"0":$(".layout-"+id+"-h1").val()));
	var Qty2=parseFloat(($(".layout-"+id+"-h2").val()==''?"0":$(".layout-"+id+"-h2").val()));
	var Qty3=parseFloat(($(".layout-"+id+"-h3").val()==''?"0":$(".layout-"+id+"-h3").val()));
	var Qty4=parseFloat(($(".layout-"+id+"-h4").val()==''?"0":$(".layout-"+id+"-h4").val()));
	var Qty5=parseFloat(($(".layout-"+id+"-h5").val()==''?"0":$(".layout-"+id+"-h5").val()));
	var Qty6=parseFloat(($(".layout-"+id+"-h6").val()==''?"0":$(".layout-"+id+"-h6").val()));
	var Qty7=parseFloat(($(".layout-"+id+"-h7").val()==''?"0":$(".layout-"+id+"-h7").val()));
	var Qty8=parseFloat(($(".layout-"+id+"-h8").val()==''?"0":$(".layout-"+id+"-h8").val()));
	var Qty9=parseFloat(($(".layout-"+id+"-h9").val()==''?"0":$(".layout-"+id+"-h9").val()));
	var Qty10=parseFloat(($(".layout-"+id+"-h10").val()==''?"0":$(".layout-"+id+"-h10").val()));

	var totalQty=Qty1+Qty2+Qty3+Qty4+Qty5+Qty6+Qty7+Qty8+Qty9+Qty10;
	
	$(totalQtyLineId).val(totalQty);

}
function saveAction(){
	var userId=$('#userId').val();
	var buyerId=$('#buyerId').val();
	var buyerorderId=$('#buyerorderId').val();
	var purchaseOrder=$('#purchaseOrder').val();
	var styleId=$('#styleId').val();
	var itemId=$('#itemId').val();
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
		$('.itemRow').each(function(){
			
				var id=$(this).attr("data-id");
				
				var lineId=$(".line-"+id).val();
				var employeeId=$("#employee-"+id).val();
				
				
				
				var proQty1=parseFloat(($(".layout-"+id+"-h1").val()==''?"0":$(".layout-"+id+"-h1").val()));
				var proQty2=parseFloat(($(".layout-"+id+"-h2").val()==''?"0":$(".layout-"+id+"-h2").val()));
				var proQty3=parseFloat(($(".layout-"+id+"-h3").val()==''?"0":$(".layout-"+id+"-h3").val()));
				var proQty4=parseFloat(($(".layout-"+id+"-h4").val()==''?"0":$(".layout-"+id+"-h4").val()));
				var proQty5=parseFloat(($(".layout-"+id+"-h5").val()==''?"0":$(".layout-"+id+"-h5").val()));
				var proQty6=parseFloat(($(".layout-"+id+"-h6").val()==''?"0":$(".layout-"+id+"-h6").val()));
				var proQty7=parseFloat(($(".layout-"+id+"-h7").val()==''?"0":$(".layout-"+id+"-h7").val()));
				var proQty8=parseFloat(($(".layout-"+id+"-h8").val()==''?"0":$(".layout-"+id+"-h8").val()));
				var proQty9=parseFloat(($(".layout-"+id+"-h9").val()==''?"0":$(".layout-"+id+"-h9").val()));
				var proQty10=parseFloat(($(".layout-"+id+"-h10").val()==''?"0":$(".layout-"+id+"-h10").val()));
	
				var totalQty=proQty1+proQty2+proQty3+proQty4+proQty5+proQty6+proQty7+proQty8+proQty9+proQty10;
				
				
				var layoutvalue=proQty1+":"+proQty2+":"+proQty3+":"+proQty4+":"+proQty5+":"+proQty6+":"+proQty7+":"+proQty8+":"+proQty9+":"+proQty10;
	
				resultlist[i]=employeeId+"*"+lineId+"*"+totalQty+"*"+layoutvalue;
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
			url: './saveLineInceptionLayoutDetails/',
			success: function (data) {

				alert(data);
		        //refreshAction();
		        
			}
		});
		
		
	}
	

}

function refreshAction(){
	 location.reload();
}

