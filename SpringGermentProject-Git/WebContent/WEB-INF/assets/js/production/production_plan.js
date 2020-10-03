function printProductPlan(buyerId,styleId,buyerorderId){
	
	//alert("buyerId "+buyerId);
	 var url = "getProductionPlan/"+buyerId+"/"+styleId+"/"+buyerorderId;
     window.open(url, '_blank');
}

function searchBuyerPoDetails(buyerId,buyerorderId,styleId,itemId){

    $.ajax({
      type: 'POST',
      dataType: 'json',
      data:{
			buyerId:buyerId,
			buyerorderId:buyerorderId,
			styleId:styleId,
			itemId:itemId
			},
      url: './searchBuyerPoDetails',
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
	                  <th scope="col" class="min-width-150">Style</th>
	                  <th scope="col" class="min-width-150">Item Name</th>
	                  <th scope="col" class="min-width-150">Color</th>
	                  <th scope="col">Purchase Order</th>
	                  <th scope="col">Type</th>`
	      var sizeListLength = sizesListByGroup['groupId' + sizeGroupId].length;
	      for (var j = 0; j < sizeListLength; j++) {
	        tables += "<th class=\"min-width-60 mx-auto\"scope=\"col\">" + sizesListByGroup['groupId' + sizeGroupId][j].sizeName + "</th>";
	      }
	      tables += `<th scope="col">Total Units</th>
	                  <th scope="col"><i class="fa fa-edit"></i></th>
	                  <th scope="col"><i class="fa fa-trash"></i></th>
	                </tr>
	              </thead>
	              <tbody id="dataList">`
	      isClosingNeed = true;
	    }
	    tables += "<tr class='itemRow' data-id='" + item.autoId + "'><td>" + item.styleNo + "</td><td>" + item.itemName + "</td><td>" + item.colorName + "</td><td>" + item.purchaseOrder + "</td><td class='min-width-150'>Order Qty</td>"
	    var sizeList = item.sizeList;
	    var sizeListLength = sizeList.length;
	    var totalSizeQty=0;
	    for (var j = 0; j < sizeListLength; j++) {
	      totalSizeQty=totalSizeQty+parseFloat(sizeList[j].sizeQuantity);
	      tables += "<td  class='sizeOrderQty1-" + item.autoId + "' sizeOrderQty-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"' data-id='"+sizeList[j].sizeId+"' ><input readonly type='text' value='"+sizeList[j].sizeQuantity+"' class='from-control min-height-20 order_value-"+sizeList[j].sizeId+"''/> <input readonly type='hidden' value='"+sizeList[j].groupId+"' class='from-control min-height-20 sizegroup_value-"+sizeList[j].sizeId+"''/></td>"
	    }
	    tables += "<td class='totalUnit' id='totalUnit" + item.autoId + "'>" + totalSizeQty + "</td><td><i class='fa fa-edit' onclick='setBuyerPoItemDataForEdit(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteBuyerPoItem(" + item.autoId + ")'> </i></td></tr>";

	    
	    tables += "<tr class='itemRow' data-id='" + item.autoId + "'><td>,,</td><td>,,</td><td>,,</td><td>,,</td><td class='min-width-150'>Required Ward For Pcs</td>"
	    var sizeList = item.sizeList;
	    var sizeListLength = sizeList.length;
	    var totalSizeQty=0;
	    var groupId=0;
	    for (var j = 0; j < sizeListLength; j++) {
	      //totalSizeQty=totalSizeQty+parseFloat(sizeList[j].sizeQuantity);
	      groupId=sizeList[j].groupId;
	      tables += "<td class='sizeReqWard1-"+item.autoId+" sizeReqWard-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+sizeList[j].groupId+"' data-id='"+sizeList[j].sizeId+"' >  <input  onkeyup='setTotalWard("+item.styleId+","+item.itemId+","+item.colorId+","+sizeList[j].groupId+")' type='text' class='from-control min-height-20 result_value-"+sizeList[j].sizeId+"''/></td>"
	    }
	    tables += "<td class='totalWard-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' ><input id='totalWard-"+item.styleId+"-"+item.itemId+"-"+item.colorId+"-"+groupId+"' type='text' class='from-control min-height-20'/></td><td><i class='fa fa-edit' onclick='setBuyerPoItemDataForEdit(" + item.autoId + ")'> </i></td><td><i class='fa fa-trash' onclick='deleteBuyerPoItem(" + item.autoId + ")'> </i></td></tr>";

	    
	  }
	  tables += "</tbody></table> </div></div>";

	  buyerId=dataList[0].buyerId;
	  poNoValue=dataList[0].purchaseOrder;
	  styleValue=dataList[0].styleId;
	  itemValue=dataList[0].itemId;
	  colorValue=dataList[0].colorId;
	  

	 $('.selectpicker').selectpicker('refresh');
	 $('#buyerId').val(dataList[0].buyerId).change()
	 
	 $('#sampleId').val(dataList[0].sampleId);
	 $('#instruction').val(dataList[0].instruction);
	 $('#inchargeId').val(dataList[0].inchargeId);
	 $('#marchendizerId').val(dataList[0].marchendizerId);

	  document.getElementById("tableList").innerHTML = tables;
	  
	  $('#exampleModal').modal('hide');
	}



function searchProductPlan(buyerId,styleId,buyerorderId){

	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		data:{
			buyerId:buyerId,
			buyerorderId:buyerorderId,
			styleId:styleId
			},
		url: './searchProductionPlan',
		success: function (data) {
	          $("#production_plan").empty();
	          $("#production_plan").append(drawDataTable(data.result));
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

function drawDataTable(data) {
	  var rows = [];
	  var length = data.length;

	  for (var i = 0; i < length; i++) {
	    rows.push(drawRowDataTable(data[i], i+1));
	  }

	  return rows;
	}

	function drawRowDataTable(rowData, c) {

	  var row = $("<tr />")
	  row.append($("<td>" + c + "</td>"));
	  row.append($("<td>" + rowData.buyerName + "</td>"));
	  row.append($("<td>" + rowData.purchaseOrder + "</td>"));
	  row.append($("<td>" + rowData.styleNo + "</td>"));
	  row.append($("<td>" + rowData.itemName + "</td>"));
	  row.append($("<td>" + rowData.orderQty + "</td>"));
	  row.append($("<td>" + rowData.planQty + "</td>"));
	  row.append($("<td>" + rowData.fileSample + "</td>"));
	  row.append($("<td>" + rowData.ppStatus + "</td>"));
	  row.append($("<td>" + rowData.startDate + "</td>"));
	  row.append($("<td>" + rowData.endDate + "</td>"));
	  
	  return row;
	}

function refreshAction(){
	location.reload();
}
function saveAction(){
	var userId=$("#userId").val();
	var buyerId=$("#buyerId").val();
	var buyerorderId=$("#purchaseOrder").val();
	var purchaseOrder=$("#purchaseOrder option:selected").text();
	

	var styleId=$("#styleNo").val();
	var itemId =$('#itemName').val();
	var orderQty =$('#orderQty').val();
	var planQty =$('#planQty').val();
	var shipDate =$('#shipDate').val();
	var merchendizerId =$('#merchendizerId').val();
	var fileSample =$('#fileSample').val();
	var ppStatus =$('#ppStatus').val();
	var accessoriesInhouse =$('#accessoriesInhouse').val();
	var fabricsInhouse =$('#fabricsInhouse').val();
	var startDate =$('#startDate').val();
	var endDate =$('#endDate').val();
	
	if(buyerId!='0'){
		if(purchaseOrder!='0'){
			if(styleId!='0'){
				if(itemId!='0'){
					if(planQty!=''){
						if(shipDate!=''){
							if(startDate!=''){
								if(endDate!=''){
									$.ajax({
										type: 'POST',
										dataType: 'json',
										data:{
											userId:userId,
											buyerId:buyerId,
											buyerorderId:buyerorderId,
											purchaseOrder:purchaseOrder,
											styleId:styleId,
											itemId:itemId,
											orderQty:orderQty,
											planQty:planQty,
											shipDate:shipDate,
											merchendizerId:merchendizerId,
											fileSample:fileSample,
											ppStatus:ppStatus,
											accessoriesInhouse:accessoriesInhouse,
											fabricsInhouse:fabricsInhouse,
											startDate:startDate,
											endDate:endDate,
											},
										url: './productionPlanSave',
										success: function (data) {
											if(data.result=='Successfull!!'){
												successAlert(data.result);
											}
											else{
												warningAlert(data.result);
											}
									
											

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
								else{
									 warningAlert("Please provide end date");
								}
							}
							else{
								 warningAlert("Please provide start date");
							}
						}
						else{
							 warningAlert("Please provide shiping date");
						}
					}
					else{
						 warningAlert("Please provide plant Qty");
					}
				}
				else{
					 warningAlert("Please Select Item Name");
				}
			}
			else{
				 warningAlert("Please Select Style No");
			}
		}
		else{
			 warningAlert("Please Select Purchase Order");
		}
	}
	else{
		 warningAlert("Please Select Buyer Name..");
	}
}



function successAlert(message) {
	  var element = $(".alert");
	  element.hide();
	  element = $(".alert-success");
	  document.getElementById("successAlert").innerHTML = "<strong>Success!</strong> " + message + "...";
	  element.show();
	  setTimeout(() => {
		element.toggle('fade');
	  }, 2500);
	}

	function warningAlert(message) {
	  var element = $(".alert");
	  element.hide();
	  element = $(".alert-warning");
	  document.getElementById("warningAlert").innerHTML = "<strong>Warning!</strong> " + message + "..";
	  element.show();
	  setTimeout(() => {
		element.toggle('fade');
	  }, 2500);
	}

	function dangerAlert(message) {
	  var element = $(".alert");
	  element.hide();
	  element = $(".alert-danger");
	  document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> " + message + "..";
	  element.show();
	  setTimeout(() => {
		element.toggle('fade');
	  }, 2500);
	}
	
	
function buyerWisePoLoad(){
	var buyerId=$("#buyerId").val();
	if(buyerId!=0){		

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './buyerWisePoLoad/'+buyerId,
			success: function (data) {
				console.log("dt "+data.result)
				loadPoNo(data.result);


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

function loadPoNo(data){

	var itemList = data;
	var options = "<option id='purchaseOrder' value='0' selected>Select Purchase Order</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='purchaseOrder' value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("purchaseOrder").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#purchaseOrder').val(poNoValue).change();
	poNoValue=0;
}



function poWiseStyles(){

	var po=$("#purchaseOrder").val();

	console.log("po "+po)
	if(po!=0){		

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './poWiseStyles/'+po,
			data: {




			},
			success: function (data) {
				console.log("dt "+data.result)
				loadStyles(data.result);


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

function loadStyles(data){

	var itemList = data;
	var options = "<option id='itemType' value='0' selected>Select Style</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='itemType' value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	console.log("style "+styleValue);
	$('#styleNo').val(styleValue).change();
	styleValue=0;

}

function styleWiseItems(){


	var buyerorderid=$("#purchaseOrder").val();
	var style=$("#styleNo").val();

/*	alert("buyerorderid "+buyerorderid);
	alert("style "+style);*/

	if(style!=0 && buyerorderid!='0'){		

		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './stylewiseitems',
			data: {
				buyerorderid:buyerorderid,
				style:style

			},
			success: function (data) {

				loatItems(data.result);


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


function loatItems(data){

	var itemList = data;
	var options = "<option id='itemType' value='0' selected>Select Item Type</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='itemType'  value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("itemName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#itemName').val(itemValue).change();
	itemValue=0;

}

function styleItemsWiseColor(){
	var buyerorderid=$("#purchaseOrder").val();
	var style=$("#styleNo").val();
	var item =$('#itemName').val();
	
	
	if(item!='0'){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './styleItemsWiseOrder/',
			data:{
				buyerorderid:buyerorderid,
				style:style,
				item:item
			},
			success: function (data) {
				//alert(data);
				$('#orderQty').val(data);
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


