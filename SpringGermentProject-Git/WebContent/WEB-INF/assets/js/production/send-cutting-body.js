function printSendCuttingBody(cuttingEntryId){


	var url = `printSendCuttingBody/${cuttingEntryId}`;
	window.open(url, '_blank');

}

function submitAction(){
	
	let userId = $("#userId").val();
	let sendList = "";
	$('.itemRow').each(function () {
		const id = $(this).attr("id").slice(4);
		const linkAutoId = $(this).attr("data-link-auto-id");
		const sizeId = $(this).attr("data-size-id");
		const sendStatus = $("#check-"+id).is(':checked') ? '1' : '0';
		sendList +=  `id : ${id},linkAutoId : ${linkAutoId},sizeId : ${sizeId},sendStatus : ${sendStatus} #`;	
	});
	sendList = sendList.slice(0, -1);
	
	if(confirm("Are you sure to Submit?")){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			data: {
				sendItemList: sendList,
				userId : userId
			},
			url: './sendCuttingPlanBodyQuantity',
			success: function (data) {
				if(data.result == "Successful"){
					alert("Submit Successfully...")
					refreshAction() ;  
				}else{
					alert("Something Wrong...")
				}
				       
			}
		});
	}
	
	
}

function refreshAction() {
	location.reload();
}


function searchCuttingPlan(cuttingEntryId) {

	$('#itemSearchModal').modal('hide');
	$.ajax({
		type: 'GET',
		dataType: 'json',
		data: {
			cuttingEntryId: cuttingEntryId,
			sizeGroupId : ""
		},
		url: './searchCuttingPlanQuantity',
		success: function (data) {
			
			//alert(data);
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawItemTable(data.cuttingPlanQuantityList);
				$("#exampleCuttingModal").modal('hide');
				$("#sendCuttingBodyInfoList").modal('hide');
			}
		}
	});
}

function drawItemTable(dataList) {


	var length = dataList.length;
	sizeGroupId = "";
	let rows = "";

	for (var i = 0; i < length; i++) {
		var item = dataList[i];
		const id = item.autoId+item.sizeId;
		rows += "<tr class='itemRow' id='row-"+id+"' data-link-auto-id='"+item.autoId+"' data-buyer-id='' data-purchase-order='' data-color-id='' data-size-id='"+item.sizeId+"'>" +
			"<td>"+item.colorName+"</td>" +
			"<td>"+item.sizeName+"</td>" +
			"<td>"+parseFloat(item.planQty).toFixed(2)+"</td>" +
			"<td><input type='checkbox' class='check' id='check-"+id+"' "+ (item.status != "0"?'checked':'')+"></td>" +
			"</tr>"
	}

	
	// tables += "</tbody></table> </div></div>";
	document.getElementById("itemSizeList").innerHTML = rows;

	if(length>0){
		$('#purchaseOrder').html(dataList[0].purchaseOrder);
		$('#styleNo').html(dataList[0].styleNo);
		$('#itemName').html(dataList[0].itemName);
	}
	
	
}

function setCheck(){
	var checkvalue = $("#allCheck").is(':checked') ? 'checked' : 'unchecked';
	
	  if (checkvalue=='checked') {
		    $(".check").prop('checked', true);
		  } else {
		    $(".check").prop('checked', false);
		  }
}

