var i=1;

var itemlist=null;
var unitlist=null;


function getUnitOptions(dataUnitList) {

	let options = "";
	var length = dataUnitList.length;

	options += "<option value='0'>Select Unit</option>"
	for (var i = 0; i < length; i++) {
		var item = dataUnitList[i];
		options += "<option  value='" + item.unitId + "'>" + item.unitName + "</option>"
	}
	
	
	return options;
};

function getOptions(dataFebricList,dataCostingItemList) {

	let options = "";
	var lengthFabrics = dataFebricList.length;
	var lengthCosting = dataCostingItemList.length;
	
	options += "<option value='0'>Select Item</option>"
	for (var i = 0; i < lengthFabrics; i++) {
		var item = dataFebricList[i];
		options += "<option data-group='1'  value='" + item.itemId + "'>" + item.itemName + "</option>"
	}
	
	for (var i = 0; i < lengthCosting; i++) {
		var item = dataCostingItemList[i];
		options += "<option data-group='2'  value='" + item.itemId + "'>" + item.itemName + "</option>"
	}
	
	return options;
};

function loadRow() {
	
	  $.ajax({
		    type: 'GET',
		    dataType: 'json',
		    url: './getCostingItemList',
		    success: function (data) {
		    	
		    	itemlist = getOptions(data.costingFabricsList,data.costingItemList);
		    	unitlist=getUnitOptions(data.unitList);
		  	  for(var a=0;a<10;a++){
					$('#dataList_costing').append("<tr class='itemrow' data-id='"+i+"'>" +
							"<td style='width:60px;'>"+i+"</td>" +
							"<td style='width:460px;'><select id='itemId-"+i+"'  class='selectpicker itemId-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray' >" + itemlist + "</select></td>" +
							"<td style='width:120px;'><select id='unitId-"+i+"'  class='selectpicker unitId-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray' onchange='EditUnit("+i+")' >" + unitlist + "</select></td>" +
							"<td style='width:60px;'><input  id='width-"+i+"' style='width:60px;' type='number'   class='form-control-sm width-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input  id='yard-"+i+"' style='width:80px;' type='number'  class='form-control-sm yard-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input readonly id='gsm-"+i+"' style='width:80px;' type='number'   class='form-control-sm gsm-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input  id='consumption-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm consumption-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input  id='rate-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm rate-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input readonly id='amount-"+i+"' style='width:80px;' type='number'   class='form-control-sm amount-"+i+"'  value=''/></td>" +
							"<td ><i class='fa fa-edit' onclick='costingItemSet('${id}','new')' style='cursor:pointer;'></i></td>" +
							"<td ><i class='fa fa-edit' onclick='costingItemSet('${id}','new')' style='cursor:pointer;'></i></td>"+ 
									"</tr>");
						i++;
						
						$('.tableSelect').selectpicker('refresh');
			  }
		    	
		    	//getOptions(data.ledgerresult);
		    }
		  });
	  
}

window.onload = loadRow();


function addNewRow(){
	$('#dataList_costing').append("<tr class='itemrow' data-id='"+i+"'>" +
			"<td style='width:60px;'>"+i+"</td>" +
			"<td style='width:460px;'><select id='itemId-"+i+"'  class='selectpicker itemId-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray' >" + itemlist + "</select></td>" +
			"<td style='width:120px;'><select id='unitId-"+i+"'  class='selectpicker unitId-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray' onchange='EditUnit("+i+")' >" + unitlist + "</select></td>" +
			"<td style='width:60px;'><input  id='width-"+i+"' style='width:60px;' type='number'  ' class='form-control-sm width-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input  id='yard-"+i+"' style='width:80px;' type='number'   class='form-control-sm yard-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input readonly id='gsm-"+i+"' style='width:80px;' type='number'   class='form-control-sm gsm-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input  id='consumption-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm consumption-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input  id='rate-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm rate-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input readonly id='amount-"+i+"' style='width:80px;' type='number'   class='form-control-sm amount-"+i+"'  value=''/></td>" +
			"<td ><i class='fa fa-edit' onclick='costingItemSet('${id}','new')' style='cursor:pointer;'></i></td>" +
			"<td ><i class='fa fa-edit' onclick='costingItemSet('${id}','new')' style='cursor:pointer;'></i></td>"+ 
					"</tr>");
		i++;
		
		$('.tableSelect').selectpicker('refresh');
}

function CostingConfrim(){
	if (confirm("Are you sure to Submit?")) {		
		
		  let userId = $("#userId").val();
		  let styleNo = $("#styleNo").val();
		  let itemName = $("#itemName").val();
		  let commission = $("#commission").val() == "" ? 0 : $("#commission").val();
		
			var resultList = [];
			$('.itemrow').each(function () {

				var id = $(this).attr("data-id");

				var itemId=$('#itemId-'+id).val();
				
				if(itemId!='0'){
					var groupType=$('#itemId-'+id).find('option:selected').attr('data-group');
					var unitId=$('#unitId-'+id).val();
					
					var width=$('#width-'+id).val()==''?"0":$('#width-'+id).val();
					var yard=$('#yard-'+id).val()==''?"0":$('#yard-'+id).val();
					var gsm=$('#gsm-'+id).val()==''?"0":$('#gsm-'+id).val();
					var consumption=$('#consumption-'+id).val()==''?"0":$('#consumption-'+id).val();
					var rate=$('#rate-'+id).val()==''?"0":$('#rate-'+id).val();
					var amount=$('#amount-'+id).val()==''?"0":$('#amount-'+id).val();

					resultList[i] = itemId + "*" + groupType + "*" + unitId+ "*" + width+ "*" + yard+ "*" + gsm+ "*" + consumption+ "*" + rate+ "*" + amount;
					i++;
				}
			
				
			});

			resultList = "[" + resultList + "]"
		  
			$.ajax({
			    type: 'POST',
			    dataType: 'json',
			    url: './confirmCostingNewVersion',
			    data:{
			    	userId:userId,
			    	styleNo:styleNo,
			    	itemName:itemName,
			    	commission:commission,
			    	resultList:resultList
			    },
			    success: function (data) {
			    	alert("Costinig Confrim Successfully");
			    }
			  });
	}
	
}

function setAmount(number){
	var consumption=parseFloat($('#consumption-'+number).val()==''?"0":$('#consumption-'+number).val());
	var rate=parseFloat($('#rate-'+number).val()==''?"0":$('#rate-'+number).val());
	
	var total=consumption*rate;
	
	$('#amount-'+number).val(total.toFixed(2));
	
}
function EditUnit(number){
	var unitId=$('#unit-'+number).val();

	if(unitId=='13'){
		$('.gsm-'+number).prop('readonly', false);
	}
	else{
		$('.gsm-'+number).prop('readonly', true);
	}
}