var departmentValue=0;
var lineValue=0;


var sizesListByGroup = JSON;

function loadData() {

	  $.ajax({
	    type: 'GET',
	    dataType: 'json',
	    url: './sizesLoadByGroup',
	    data: {},
	    success: function (obj) {
	      sizesListByGroup = [];
	      sizesListByGroup = obj.sizeList;
	    }
	  });

}

window.onload = loadData;

function searchCuttingInformation(cuttingEntryId){
	
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: './setCuttingEntryId',
        data:{cuttingEntryId:cuttingEntryId},
        success: function (data) {
          if (data== "Sucess") {
      		var url = "printCuttingInformationReport";
    		window.open(url, '_blank');

          }
        }
      });
	
/*    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './checkCuttingInformation',
        data:{
        	buyerId:buyerId,
        	buyerorderId:buyerorderId,
        	styleId:styleId,
        	itemId:itemId
        },
        success: function (data) {
          if (data.result == "Something Wrong") {
            dangerAlert("Something went wrong");
          } else if (data.result == "duplicate") {
            dangerAlert("Duplicate Item Name..This Item Name Already Exist")
          } else {
            drawItemTable(data.result);
          }
        }
      });*/
}

function searchProductPlan(buyerId,buyerorderId,styleId,itemId){
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './searchBuyerPoDetails',
        data:{
        	buyerId:buyerId,
        	buyerorderId:buyerorderId,
        	styleId:styleId,
        	itemId:itemId
        },
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

	    if (sizeGroupId != item.sizeGroupId) {
	      if (isClosingNeed) {
	        tables += "</tbody></table> </div></div>";
	      }
	      sizeGroupId = item.sizeGroupId;
	      tables += `<div class="row">
	                        <div class="col-md-12 table-responsive" >
	              <table class="table table-hover table-bordered table-sm mb-0 small-font">
	              <thead class="no-wrap-text bg-light">
	                <tr>

	                  <th scope="col" class="min-width-150">Color</th>
	                  <th scope="col">Type</th>`
	      var sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
	      for (var j = 0; j < sizeListLength; j++) {
	        tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
	      }
	      tables += ` <th scope="col">Total Pcs</th>
	    	  		  <th scope="col" class="min-width-20">DOZEN</th>
	    	  		  <th scope="col">EXCESS</th>
	    	  		  <th scope="col">SHORT</th>
	                  <th scope="col"><i class="fa fa-edit"></i></th>
	                  <th scope="col"><i class="fa fa-trash"></i></th>
	                </tr>
	              </thead>
	              <tbody id="dataList">`
	      isClosingNeed = true;
	    }
	    tables += "<tr class='itemRow' data-id='" + item.autoId + "'><td>" + item.colorName + "</td><td class='min-width-150'>Order Qty</td>"
	  
	    //Order
	    var sizeList = item.sizeList;
	    var sizeListLength = sizeList.length;
	    var totalSizeQty=0;
	    var dzQty=0;
	    for (var j = 0; j < sizeListLength; j++) {
	       groupId=sizeList[j].groupId;
	      totalSizeQty=totalSizeQty+parseFloat(sizeList[j].sizeQuantity);
	      tables += "<td  class='sizeOrderQty1-" + item.autoId + "' sizeOrderQty-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"' data-id='"+sizeList[j].sizeId+"' ><input readonly type='text' value='"+sizeList[j].sizeQuantity+"' class='from-control min-height-20 order_value-"+item.autoId+"-"+sizeList[j].sizeId+" ' /> <input readonly type='hidden' value='"+item.colorId+"' class='from-control min-height-20 color_value-"+item.autoId+"''/> <input readonly type='hidden' value='"+sizeList[j].groupId+"' class='from-control min-height-20 sizegroup_value-"+sizeList[j].sizeId+"''/></td>"
	    }
	    dzQty=totalSizeQty/12;
	    tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'>" + totalSizeQty + "</td><td class='totalorder-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input  id='totalorder-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' value='"+parseFloat(dzQty).toFixed(2)+"' type='text' class='from-control min-height-20'/></td><td class='totalorderexcess-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input readonly id='totalorderexcess-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20'/></td><td class='totalordershort-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input readonly id='totalordershort-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"'  type='text' class='from-control min-height-20'/></td><td><i class='fa fa-edit' onclick='setBuyerPoItemDataForEdit(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteBuyerPoItem(" + item.autoId + ")'> </i></td></tr>";

	    
	    tables += "<tr class='itemRow' data-id='" + item.autoId + "'><td>,,</td><td class='min-width-150'>Ratio</td>"
	  
	    //Ratio..........
	    var sizeList = item.sizeList;
	    var sizeListLength = sizeList.length;
	    var totalSizeQty=0;
	    var groupId=0;
	    for (var j = 0; j < sizeListLength; j++) {
	      //totalSizeQty=totalSizeQty+parseFloat(sizeList[j].sizeQuantity);
	      groupId=sizeList[j].groupId;
	      tables += "<td class='sizeReqRatio1-"+item.autoId+" sizeReqRatio-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"' data-id='"+sizeList[j].sizeId+"' >  <input  onkeyup='setTotalRatio("+item.styleId+","+item.itemId+","+item.colorId+","+sizeList[j].groupId+")' type='text' class='from-control min-height-20 result_value-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"-"+sizeList[j].sizeId+" ratio_value-"+item.autoId+"-"+sizeList[j].sizeId+"''/></td>"
	    }
	    tables += "<td class='totalRatio-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input readonly id='totalRatio-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20 totalRatioPcs-"+item.colorId+"-"+item.autoId+"-"+groupId+"'/></td><td class='totalRatiobox-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input id='totalRatiobox-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20 totalRatioBox-"+item.colorId+"-"+item.autoId+"-"+groupId+"'/></td><td class='totalrationexcess-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input readonly id='totalrationexcess-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20 totalRatioExcess-"+item.colorId+"-"+item.autoId+"-"+groupId+"'/></td><td class='totalratioshort-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input readonly id='totalratioshort-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20 totalRatioShort-"+item.colorId+"-"+item.autoId+"-"+groupId+"'/></td><td><i class='fa fa-edit' '> </i></td><td><i class='fa fa-trash' '> </i></td></tr>";

	    
	    tables += "<tr class='itemRow' data-id='" + item.autoId + "'><td>,,</td><td class='min-width-150'>Cutting Qty</td>"
	    
	    //Cutting..........
	    var sizeList = item.sizeList;
	    var sizeListLength = sizeList.length;
	    var totalSizeQty=0;
	    var groupId=0;
	    for (var j = 0; j < sizeListLength; j++) {
	      //totalSizeQty=totalSizeQty+parseFloat(sizeList[j].sizeQuantity);
	      groupId=sizeList[j].groupId;
	      tables += "<td class='sizeReqWard1-"+item.autoId+" sizeReqWard-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"' data-id='"+sizeList[j].sizeId+"' >  <input readonly  type='text' class='from-control min-height-20 cutting-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"-"+sizeList[j].sizeId+"  cutting_value-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"-"+sizeList[j].sizeId+" cutting_value-"+item.autoId+"-"+sizeList[j].sizeId+"''/></td>"
	    }
	    tables += "<td class='totalCutting-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input   type='text' class='totalCutting-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+" from-control min-height-20 totalCuttingPcs-"+item.colorId+"-"+item.autoId+"-"+groupId+"''/></td><td class='totalcuttingbox-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input readonly id='totalcuttingbox-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20 totalcuttingbox-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+" totalCuttingBox-"+item.colorId+"-"+item.autoId+"-"+groupId+"''/></td><td class='totalcuttingexcess-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input  id='totalcuttingexcess-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20 totalCuttingExcess-"+item.colorId+"-"+item.autoId+"-"+groupId+"''/></td><td class='totalcuttingshort-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input  id='totalcuttingshort-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20 totalCuttingShort-"+item.colorId+"-"+item.autoId+"-"+groupId+"''/></td><td><i class='fa fa-edit' onclick='setBuyerPoItemDataForEdit(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteBuyerPoItem(" + item.autoId + ")'> </i></td></tr>";
	    
	  }
	  tables += "</tbody></table> </div></div>";

	  buyerId=dataList[0].buyerId;
	  poNoValue=dataList[0].purchaseOrder;
	  styleValue=dataList[0].styleId;
	  itemValue=dataList[0].itemId;
	  colorValue=dataList[0].colorId;
	  
	  $('#hid_buyerId').val(dataList[0].buyerId);
	  $('#hid_buyerOrderId').val(dataList[0].buyerOrderId);
	  $('#hid_styleId').val(dataList[0].styleId);
	  $('#hid_itemId').val(dataList[0].itemId);
	  $('#hid_purchaseOrder').val(dataList[0].purchaseOrder);
	  

/*	 $('.selectpicker').selectpicker('refresh');
	 $('#buyerId').val(dataList[0].buyerId).change()*/
	 
	 $('#inchargeId').val(dataList[0].inchargeId);


	  document.getElementById("tableList").innerHTML = tables;
	  
	  $('#exampleModal').modal('hide');
}


function setTotalRatio(styleId,itemId,colorId,groupId){
	

	var value=0;
	
	var size='.sizeReqRatio-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	var totalratio='#totalRatio-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	
	var result='.result_value-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	var cutting='.cutting-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	var totalcutting='.totalCutting-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	var totalcuttingbox='.totalcuttingbox-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	
	$(size).each(function(){
		var id=$(this).attr("data-id");
		
		//alert("id"+id);
		
		value=value+(parseFloat(($(result+"-"+id).val()==''?"0":$(result+"-"+id).val())));
		
	});
	
	$(totalratio).val(value);

	
	var markingLayer=$('#markingLayer').val()==''?"0":$('#markingLayer').val();
	var ratio=0;
	var totalCuttingQty=0;
	$(size).each(function(){
		var id=$(this).attr("data-id");
		
		ratio=parseFloat(($(result+"-"+id).val()==''?"0":$(result+"-"+id).val()));

		var cuttingQty=((markingLayer*value)/value)*ratio;
		totalCuttingQty=totalCuttingQty+cuttingQty;
		
		$(cutting+"-"+id).val(cuttingQty);
		
	});
	
	$(totalcutting).val(totalCuttingQty);
	$(totalcuttingbox).val(parseFloat(totalCuttingQty/12).toFixed());
	value=0;


}

function confrimAction(){
	
	var colorlist=[];
	var m=0;
	
	var sizegrouplist=[];
	var k=0;
	var ratioresultlist=[];
	var i=0;
	var value=0;
	
	var cuttingresultlist=[];

	
	var userId=$('#hid_userId').val();
	var buyerId=$('#hid_buyerId').val();
	var buyerOrderId=$('#hid_buyerOrderId').val();
	var purchaseOrder=$('#hid_purchaseOrder').val();
	var styleId=$('#hid_styleId').val();
	var itemId=$('#hid_itemId').val();
	
	var factoryId=$('#factoryId').val();
	var departmentId=$('#departmentId').val();
	var lineId=$('#lineId').val();
	var inchargeId=$('#inchargeId').val();
	var cuttingNo=$('#cuttingNo').val();
	var cuttingDate=$('#cuttingDate').val();
	var markingLayer=$('#markingLayer').val();
	var markingLength=$('#markingLength').val();
	var markingWidth=$('#markingWidth').val();


	
	if(buyerId!=0 || buyerOrderId!='0' || styleId!='0' || itemId!='0'){
		if(factoryId!='0' || departmentId!='0' || lineId!='0' || cuttingNo!='' || markingLayer!='' ){
			var j=0;
			$(".itemRow").each(function(){
				

				if(j==2){
					var rowid=$(this).attr("data-id");
					
				
					var orderrow='.sizeOrderQty1-'+rowid;
				
					$(orderrow).each(function(){
						
						var id=$(this).attr("data-id");

						var orderclass=".order_value-"+rowid+"-"+id;
						var ratioclass=".ratio_value-"+rowid+"-"+id;
						var cuttingclass=".cutting_value-"+rowid+"-"+id;
						
						var sizeGroupId=$(".sizegroup_value-"+id).val();
						var color=$(".color_value-"+rowid).val();
						
						
						var ratiototalpcs=$(".totalRatioPcs-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalRatioPcs-"+color+"-"+rowid+"-"+sizeGroupId).val();
						var ratiototalbox=$(".totalRatioBox-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalRatioBox-"+color+"-"+rowid+"-"+sizeGroupId).val();
						var ratiototalexcess=$(".totalRatioExcess-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalRatioExcess-"+color+"-"+rowid+"-"+sizeGroupId).val();
						var ratiototalshort=$(".totalRatioShort-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalRatioShort-"+color+"-"+rowid+"-"+sizeGroupId).val();
					
						var cuttingtotalpcs=$(".totalCuttingPcs-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalCuttingPcs-"+color+"-"+rowid+"-"+sizeGroupId).val();
						var cuttingtotalbox=$(".totalCuttingBox-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalCuttingBox-"+color+"-"+rowid+"-"+sizeGroupId).val();
						var cuttingtotalexcess=$(".totalCuttingExcess-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalCuttingExcess-"+color+"-"+rowid+"-"+sizeGroupId).val();
						var cuttingtotalshort=$(".totalCuttingShort-"+color+"-"+rowid+"-"+sizeGroupId).val()==''?"0":$(".totalCuttingShort-"+color+"-"+rowid+"-"+sizeGroupId).val();
						
						var orderQty=parseFloat(($(orderclass).val()==''?"0":$(orderclass).val()));
						var ratioQty=parseFloat(($(ratioclass).val()==''?"0":$(ratioclass).val()));
						var cuttingQty=parseFloat(($(cuttingclass).val()==''?"0":$(cuttingclass).val()));
						
						
					//	var wardQty=parseFloat(($(result_value-"+styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"-"+sizeList[j].sizeId).val()==''?"0":$(".result_value-"+id).val()));
						
						var value=color+":"+sizeGroupId+":"+id+":"+ratioQty;
						var cuttingvalue=color+":"+sizeGroupId+":"+id+":"+cuttingQty;
						var clolorvalue=color+":"+sizeGroupId+":"+ratiototalpcs+":"+ratiototalbox+":"+ratiototalexcess+":"+ratiototalshort+":"+cuttingtotalpcs+":"+cuttingtotalbox+":"+cuttingtotalexcess+":"+cuttingtotalshort;
						
						sizegrouplist[k]=sizeGroupId;
						colorlist[m]=clolorvalue;
						ratioresultlist[i]=value;
						cuttingresultlist[i]=cuttingvalue;
						i++;
						
					});
					m++;
					k++;
					
					j=0;
					
				}
				else{
					j++;
				}
				
				
				
			});
			
			var resultvalue="["+ratioresultlist+"]";
			
			//alert("resultvalue "+resultvalue);
			var sizegroupvalue="["+sizegrouplist+"]";
			//alert("sizegroupvalue "+sizegroupvalue);
			
			var colorlistvalue="["+colorlist+"]";
			var cuttinglistvalue="["+cuttingresultlist+"]";
			
	        $.ajax({
	            type: 'POST',
	            dataType: 'json',
	            url: './cuttingInformationEnty',
	            data: {
	              userId:userId,
	              buyerId : buyerId,
	              buyerOrderId: buyerOrderId,
	              purchaseOrder:purchaseOrder,
	              styleId:styleId,
	              itemId: itemId,
	              factoryId:factoryId,
	              departmentId:departmentId,
	              lineId:lineId,
	              inchargeId:inchargeId,
	              cuttingNo:cuttingNo,
	              cuttingDate:cuttingDate,
	              markingLayer:markingLayer,
	              markingLength:markingLength,
	              markingWidth:markingWidth,
	              resultvalue:resultvalue,
	              sizegroupvalue:sizegroupvalue,
	              colorlistvalue:colorlistvalue,
	              cuttinglistvalue:cuttinglistvalue
	              
	            },
	            success: function (data) {
	              if (data.result == "Something Wrong") {
	                dangerAlert("Something went wrong");
	              } else if (data.result == "duplicate") {
	                dangerAlert("Duplicate Item Name..This Item Name Already Exist")
	              } else {
	                alert(data);
	                refreshAction();
	              }
	            }
	          });
		}
		else{
			warningAlert("Provide Cutting Information");
		}
		
	}
	else{
		warningAlert("At first search production plan");
	}
	
}

function refreshAction(){
	  location.reload();
}

function FactoryWiseDepartmentLoad(){
	var factoryId=$("#factoryId").val();
	if(factoryId!=0){		

		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './factoryWiseDepartmentLoad/'+factoryId,
			success: function (data) {
				console.log("dt "+data.departmentList)
				loadDepartment(data.departmentList);


			},
			error: function (jqXHR, textStatus, errorThrown) {
				//alert("Server Error");
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}
		});
	}
}

function loadDepartment(data){

	var itemList = data;
	var options = "<option id='departmentId' value='0' selected>Select Department</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='departmentId' value='"+itemList[i].departmentId+"'>"+itemList[i].departmentName+"</option>";
	};
	document.getElementById("departmentId").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#departmentId').val(departmentValue).change();
	departmentValue=0;
}

function FactoryDepartmentWiseLine(){
	var factoryId=$("#factoryId").val();
	var departmentId=$("#departmentId").val();

	if(departmentId!='0'){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			data:{
				factoryId:factoryId,
				departmentId:departmentId
				},
			url: './factoryDepartmentWiseLineLoad',
			success: function (data) {
				console.log("dt "+data.lineList)
				loadFactoryDepartment(data.lineList);

			},
			error: function (jqXHR, textStatus, errorThrown) {
				//alert("Server Error");
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}
		});
	}



}

function loadFactoryDepartment(data){

	var itemList = data;
	var options = "<option id='lineId' value='0' selected>Select Line</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='lineId' value='"+itemList[i].lineId+"'>"+itemList[i].lineName+"</option>";
	};
	document.getElementById("lineId").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#lineId').val(lineValue).change();
	lineValue=0;
}


function successAlert(message) {
	var element = $(".alert");
	element.hide();
	element = $(".alert-success");
	document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
	element.show();
}

function warningAlert(message) {
	var element = $(".alert");
	element.hide();
	element = $(".alert-warning");
	document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
	element.show();
}

function dangerAlert(message) {
	var element = $(".alert");
	element.hide();
	element = $(".alert-danger");
	document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
	element.show();
}
