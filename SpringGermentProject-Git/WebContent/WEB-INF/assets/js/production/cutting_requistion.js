
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

function searchSampleReuisition(v){

    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './searchSampleReuisition/'+v,
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

function setTotalWard(styleId,itemId,colorId,groupId){
	
	
	var resultlist=[];
	var i=0;
	var value=0;
	
	var order='.sizeOrderQty-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	var size='.sizeReqWard-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	var totalward='#totalWard-'+styleId+"-"+itemId+'-'+colorId+'-'+groupId;
	
	$(size).each(function(){
		var id=$(this).attr("data-id");
		
		
		var orderQty=parseFloat(($(".order_value-"+id).val()==''?"0":$(".order_value-"+id).val()));
		
		value=value+(orderQty*parseFloat(($(".result_value-"+id).val()==''?"0":$(".result_value-"+id).val())));
		
	});
	

	$(totalward).val(value);
	value=0;
	
}

function confrimAction(){
	
	var sizegrouplist=[];
	var k=0;
	var resultlist=[];
	var i=0;
	var value=0;
	
	var userId=$('#userId').val();
	var buyerId=$('#buyerId').val();
	var styleId=$('#styleNo').val();
	var purchaseOrder=$('#purchaseOrder').val();
	var itemName=$('#itemName').val();
	var colorName=$('#colorName').val();
	var cuttingno=$('#cuttingNo').val();
	var cuttingDate=$('#cuttingDate').val();
	var inchargeId=$('#inchargeId').val();
	var fabricsId=$('#fabricsId').val();

	
	if(buyerId!=0){
		if(purchaseOrder!=''){
			if(itemName!=0){
				if(colorName!=0){
					if(cuttingno!=''){
						if(cuttingDate!=''){
							if(inchargeId!='0'){
								if(fabricsId!='0'){
									var j=0;
									$(".itemRow").each(function(){
										var rowid=$(this).attr("data-id");
										
										var orderrow='.sizeOrderQty1-'+rowid;
										
										if(j%2==0){
											$(orderrow).each(function(){
												
												var id=$(this).attr("data-id");

												var sizeGroupId=$(".sizegroup_value-"+id).val();
												var orderQty=parseFloat(($(".order_value-"+id).val()==''?"0":$(".order_value-"+id).val()));
												var wardQty=parseFloat(($(".result_value-"+id).val()==''?"0":$(".result_value-"+id).val()));
												
												var value=sizeGroupId+":"+id+":"+orderQty+":"+wardQty;
												sizegrouplist[k]=sizeGroupId;
												resultlist[i]=value;
												i++;
												
											});
											k++;
										}
										
										j++;
										
									});
									
									var resultvalue="["+resultlist+"]";
									var sizegroupvalue="["+sizegrouplist+"]";
									
								
									
						            $.ajax({
						                type: 'POST',
						                dataType: 'json',
						                url: './cuttingRequisitionEnty',
						                data: {
						                  buyerId : buyerId,
						                  purchaseOrder: purchaseOrder,
						                  styleId:styleId,
						                  itemName: itemName,
						                  colorName : colorName,
						                  cuttingno: cuttingno,
						                  cuttingDate : cuttingDate,
						                  inchargeId : inchargeId,
						                  fabricsId:fabricsId,
						                  resultvalue : resultvalue,
						                  sizegroupvalue:sizegroupvalue,
						                  userId : userId
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
									warningAlert("Please Select Fabrics Name...");
								}
							}
							else{
						        warningAlert("Please Select Incharge Name..");
							}
						}
						else{
					        warningAlert("Please Select Cutting Date..");
						}
					}
					else{
				        warningAlert("Please provide cutting no..");
					}
				}
				else{
			        warningAlert("Please Select Color Name..");
				}
			}
			else{
		        warningAlert("Please Select Item Name..");
			}
		}
		else{
	        warningAlert("Please Select Purchase Order..");
		}
	}
	else{
        warningAlert("Please Select BuyerId..");
	}
	
	
	
}

function refreshAction(){
	  location.reload();
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
			type: 'GET',
			dataType: 'json',
			url: './styleItemsWiseColor/',
			data:{
				buyerorderid:buyerorderid,
				style:style,
				item:item
			},
			success: function (data) {
				loatItemsWiseColor(data.result);
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



function loatItemsWiseColor(data){

	var itemList = data;
	var options = "<option id='colorName' value='0' selected>Select Color Type</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='colorName'  value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("colorName").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#colorName').val(colorValue).change();
	colorValue=0;

}