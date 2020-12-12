window.onload = ()=>{
	document.title = "Cutting Fabrics Requisition";
  } 

function printCuttingUsedFabricsRequisition(cuttingEntryId){
	let url = `printCuttingUsedFabricsInfo/${cuttingEntryId}`;
	window.open(url, '_blank');

}

function submitAction(){
	
	let userId = $('#userId').val();
	let cuttingEntryId = $('#cuttingEntryId').val();
	let i = 0;
	let resultList = [];
	$('.itemRow').each(function () {

		let id = $(this).attr("data-id");

	
		let fabricsId = $("#fabricsId-"+id).val();
		let colorId = $("#colorId-"+id).val();
		let unitId = $("#unitId-"+id).val();
		let usedFabrics = parseFloat(($("#usedFabrics-"+id).val() == '' ? "0" : $("#usedFabrics-" + id).val()));
		let requistionReq = $("#req-"+id).is(':checked') ? '1' : '0';
		
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

	$('#exampleModal').modal('hide');
	$.ajax({
		type: 'GET',
		dataType: 'json',
		data: {
			cuttingEntryId: cuttingEntryId
		},
		url: './searchCuttingUsedFabrics',
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


	let length = dataList.length;
	sizeGroupId = "";
	let tables = "";
	let isClosingNeed = false;
	for (let i = 0; i < length; i++) {
		let item = dataList[i];
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
				<th scope="col">Requistion <input type="checkbox" id="allCheck" onclick="setCheck()" /></th>
				
				</tr>
				</thead>
				<tbody id="dataList">`

			//<th scope="col">Edit</th>

		}

		tables += "<tr class='itemRow' data-id='"+i+"'>" +
			"<td>"+item.fabricsItem+"<input type='hidden' id='fabricsId-"+i+"' value='"+item.fabricsId+"'/></td>" +
			"<td>"+item.colorName+"<input type='hidden' id='colorId-"+i+"' value='"+item.colorId+"'/></td>" +
			"<td>"+item.unitName+"<input type='hidden' id='unitId-"+i+"' value='"+item.unitId+"'/></td>" +
			"<td>"+parseFloat(item.usedFabrics).toFixed(2)+"<input type='hidden' id='usedFabrics-"+i+"' value='"+item.usedFabrics+"'/></td>" +
			"<td><input type='checkbox' class='check' id='req-"+i+"'></td>" +

			"</tr>"
		

	}

	tables += "</tbody></table> </div></div>";
	
	// tables += "</tbody></table> </div></div>";
	document.getElementById("tableList").innerHTML = tables;
	
/*	//$('#purchaseOrder').html(dataList[0].purchaseOrder);
	$('#styleNo').html(dataList[0].styleNo);
	$('#itemName').html(dataList[0].itemName);

	$('#cuttingEntryId').val(dataList[0].cuttingEntryId);*/
}

function setCheck(){
	let checkvalue = $("#allCheck").is(':checked') ? 'checked' : 'unchecked';
	
	  if (checkvalue=='checked') {
		    $(".check").prop('checked', true);
		  } else {
		    $(".check").prop('checked', false);
		  }
}

