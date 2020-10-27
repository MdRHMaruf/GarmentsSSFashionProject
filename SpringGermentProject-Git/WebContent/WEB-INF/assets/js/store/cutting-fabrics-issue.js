function printCuttingUsedFabricsRequisition(cuttingEntryId){


	var url = `printCuttingUsedFabricsInfo/${cuttingEntryId}`;
	window.open(url, '_blank');

}

function submitAction(){
	
	var userId = $('#userId').val();
	var cuttingEntryId = $('#cuttingEntryId').val();
	var i = 0;
	var resultList = [];
	$('.itemRow').each(function () {

		var id = $(this).attr("data-id");

	
		var fabricsId = $("#fabricsId-"+id).val();
		var colorId = $("#colorId-"+id).val();
		var unitId = $("#unitId-"+id).val();
		var usedFabrics = parseFloat(($("#usedFabrics-"+id).val() == '' ? "0" : $("#usedFabrics-" + id).val()));
		var requistionReq = $("#req-"+id).is(':checked') ? '1' : '0';
		
		resultList[i] =  fabricsId + "*" + colorId+"*"+unitId+"*"+ usedFabrics+"*"+requistionReq;
		i++;
	});
	resultList = "[" + resultList + "]";
	
	if(confirm("Are you sure to Submit?")){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			data: {
				cuttingEntryId: cuttingEntryId,
				resultList: resultList,
				userId: userId
			},
			url: './sendCuttingFabricsRequistion/',
			success: function (data) {

				alert(data);
				refreshAction() ;      
			}
		});
	}
	
	
}

function refreshAction() {
	location.reload();
}


function searchCuttingUsedFabrics(cuttingEntryId) {

	$('#itemSearchModal').modal('hide');
	$.ajax({
		type: 'GET',
		dataType: 'json',
		data: {
			cuttingEntryId: cuttingEntryId
		},
		url: './searchCuttingUsedFabricsRequisition',
		success: function (data) {
			
			//alert(data);
			if (data.result == "Something Wrong") {
				dangerAlert("Something went wrong");
			} else if (data.result == "duplicate") {
				dangerAlert("Duplicate Item Name..This Item Name Already Exist")
			} else {
				drawItemTable(data.result,"checked");
			}
		}
	});
}

function drawItemTable(dataList,isChecked = "") {


	var length = dataList.length;
	sizeGroupId = "";
	var tables = "";
	var isClosingNeed = false;
	for (var i = 0; i < length; i++) {
		var item = dataList[i];
		if (i == 0) {
			tables += `<div class="row">
				<div class="col-md-12 table-responsive" >
				<table class="table table-hover table-bordered table-sm mb-0 small-font">
				<thead class="no-wrap-text bg-light">

				<tr>

				<th scope="col" class="min-width-120">Fabrics Item </th>
				<th scope="col">Color</th>
				<th scope="col">Unit</th>
				<th scope="col">Total Used Fabrics</th>
				<th scope="col">Issue <input type="checkbox" id="allCheck" onclick="setCheck()" /></th>
				
				</tr>
				</thead>
				<tbody id="dataList">`

			//<th scope="col">Edit</th>

		}

		tables += "<tr class='itemRow' data-id='"+i+"'>" +
			"<td>"+item.fabricsItem+"<input type='hidden' id='fabricsId-"+i+"' value='"+item.fabricsId+"'/></td>" +
			"<td>"+item.colorName+"<input type='hidden' id='colorId-"+i+"' value='"+item.colorId+"'/></td>" +
			"<td>"+item.unitName+"<input type='hidden' id='unitId-"+i+"' value='"+item.unitId+"'/></td>" +
			"<td>"+parseFloat(item.requisitionQty).toFixed(2)+"<input type='hidden' id='usedFabrics-"+i+"' value='"+item.requisitionQty+"'/></td>" +
			"<td><input type='checkbox' class='check' id='req-"+i+"'></td>" +

			"</tr>"
		

	}

	tables += "</tbody></table> </div></div>";
	
	// tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
	
	$('#purchaseOrder').html(dataList[0].purchaseOrder);
	$('#styleNo').html(dataList[0].styleNo);
	$('#itemName').html(dataList[0].itemName);

	$('#cuttingEntryId').val(dataList[0].cuttingEntryId);
}

function setCheck(){
	var checkvalue = $("#allCheck").is(':checked') ? 'checked' : 'unchecked';
	
	  if (checkvalue=='checked') {
		    $(".check").prop('checked', true);
		  } else {
		    $(".check").prop('checked', false);
		  }
}

