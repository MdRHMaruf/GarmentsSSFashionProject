var i=1;

let itemlist=null;
var unitlist=null;

window.onload = () => {

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './getCostingItemList',
        data: {
        },
        success: function (data) {
        	itemlist = data.costingFabricsList.map(rowData => rowData.itemName);
          //  let item1 = data.costingItemList.map(rowData => rowData.itemName);
            
	    	  unitlist=getUnitOptions(data.unitList);
		  	  for(var a=0;a<10;a++){
					$('#dataList_costing').append("<tr class='itemrow' data-id='"+i+"'>" +
							"<td style='width:60px;'>"+i+"</td>" +
							"<td style='width:120px;'><select id='groupType-"+i+"'  class='selectpicker groupType-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'  ><option value='1'>Fabrics</option><option value='2'>Others</option></select></td>" +
							"<td style='width:460px;'><input type='text' class='form-control form-control-sm itemId' placeholder='Search Item' aria-describedby='findButton' id='itemId-"+i+"' ></td>" +
							"<td style='width:120px;'><select id='unitId-"+i+"'  class='selectpicker unitId-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray' onchange='EditUnit("+i+")' >" + unitlist + "</select></td>" +
							"<td style='width:60px;'><input  id='width-"+i+"' style='width:60px;' type='number'   class='form-control-sm width-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input  id='yard-"+i+"' style='width:80px;' type='number'  class='form-control-sm yard-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input readonly id='gsm-"+i+"' style='width:80px;' type='number'   class='form-control-sm gsm-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input  id='consumption-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm consumption-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input  id='rate-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm rate-"+i+"'  value=''/></td>" +
							"<td style='width:80px;'><input readonly id='amount-"+i+"' style='width:80px;' type='number'   class='form-control-sm amount-"+i+"'  value=''/></td>" +
							"<td ><i class='fa fa-trash' onclick='costingItemSet('${id}','new')' style='cursor:pointer;'></i></td>"+ 
									"</tr>");
						i++;
						
						$('.tableSelect').selectpicker('refresh');
						
					     
			  }
            
		  	 $(".itemId").autocomplete({
	                source: itemlist
	            });
	
        }
    });
};

function addNewRow(){
	$('#dataList_costing').append("<tr class='itemrow' data-id='"+i+"'>" +
			"<td style='width:60px;'>"+i+"</td>" +
			"<td style='width:120px;'><select id='groupType-"+i+"'  class='selectpicker groupType-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'  ><option value='1'>Fabrics</option><option value='2'>Others</option></select></td>" +
			"<td style='width:460px;'><input type='text' class='form-control form-control-sm itemId' placeholder='Search Item' aria-describedby='findButton' id='itemId-"+i+"' ></td>" +
			"<td style='width:120px;'><select id='unitId-"+i+"'  class='selectpicker unitId-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray' onchange='EditUnit("+i+")' >" + unitlist + "</select></td>" +
			"<td style='width:60px;'><input  id='width-"+i+"' style='width:60px;' type='number'   class='form-control-sm width-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input  id='yard-"+i+"' style='width:80px;' type='number'  class='form-control-sm yard-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input readonly id='gsm-"+i+"' style='width:80px;' type='number'   class='form-control-sm gsm-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input  id='consumption-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm consumption-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input  id='rate-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm rate-"+i+"'  value=''/></td>" +
			"<td style='width:80px;'><input readonly id='amount-"+i+"' style='width:80px;' type='number'   class='form-control-sm amount-"+i+"'  value=''/></td>" +
			"<td ><i class='fa fa-trash' onclick='costingItemSet('${id}','new')' style='cursor:pointer;'></i></td>"+ 
					"</tr>");
		i++;
		
		$('.tableSelect').selectpicker('refresh');
		
		 $(".itemId").autocomplete({
             source: itemlist
         });
}

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





function CostingConfrim(){
	if (confirm("Are you sure to Submit?")) {		
		
		  let userId = $("#userId").val();
		  let styleNo = $("#styleNo").val();
		  let itemName = $("#itemName").val();
		  let commission = $("#commission").val() == "" ? 0 : $("#commission").val();
		  let submissionDate=$("#submissionDate").val();
		
		  if(styleNo!=''){
			  if(itemName!=''){
				  if(submissionDate!=''){
						var resultList = [];
						$('.itemrow').each(function () {

							var id = $(this).attr("data-id");

							var itemId=$('#itemId-'+id).val();
							
							if(itemId!=''){
								var groupType=$('#groupType-'+id).val();
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
						    	submissionDate:submissionDate,
						    	resultList:resultList
						    },
						    success: function (data) {
						    	if(data=='Costing Create Succesfully'){
						    		alert("Costing Create Succesfully");
						    		refreshAction();
						    	}
						    	else{
						    		alert(data);
						    	}
						    	
						    }
						  });
				  }
				  else{
					  alert("Provide Submission Date");
				  }
			  }
			  else{
				  alert("Provide Item Name");
			  }
		  }
		  else{
			  alert("Provide Style No");
		  }
		  

	}
	
}


function UpdateConfrimedCosting(){
	if (confirm("Are you sure to Submit?")) {		
		  
		  let costingNo = $("#costingNo").val();
		  let userId = $("#userId").val();
		  let styleNo = $("#styleNo").val();
		  let itemName = $("#itemName").val();
		  let commission = $("#commission").val() == "" ? 0 : $("#commission").val();
		  let submissionDate=$("#submissionDate").val();
		
		  if(styleNo!=''){
			  if(itemName!=''){
				  if(submissionDate!=''){
						var resultList = [];
						$('.itemrow').each(function () {

							var id = $(this).attr("data-id");

							var itemId=$('#itemId-'+id).val();
							
							if(itemId!=''){
								var groupType=$('#groupType-'+id).val();
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
						    url: './updateConfirmCostingNewVersion',
						    data:{
						    	costingNo:costingNo,
						    	userId:userId,
						    	styleNo:styleNo,
						    	itemName:itemName,
						    	commission:commission,
						    	submissionDate:submissionDate,
						    	resultList:resultList
						    },
						    success: function (data) {
						    	if(data=='Costing Create Succesfully'){
						    		alert("Costing Create Succesfully");
						    		refreshAction();
						    	}
						    	else{
						    		alert(data);
						    	}
						    	
						    }
						  });
				  }
				  else{
					  alert("Provide Submission Date");
				  }
			  }
			  else{
				  alert("Provide Item Name");
			  }
		  }
		  else{
			  alert("Provide Style No");
		  }

	}
}


function cloneButtonAction() {
	  let styleId = $("#styleName").val();
	  let itemId = $("#itemName").val();
	  if (styleId != 0) {
	    if (itemId != 0) {
	      $('#cloneModal').modal('show');
	      let element = $(".alert");
	      element.hide();
	      
	      
	    } else {
	      alert("Item Type not selected... Please Select Item Type");
	      $("#itemName").focus();
	    }
	  } else {
		  alert("Style No not selected... Please Select Style No");
	    $("#styleName").focus();
	  }
	}


function cloningCosting(costingNo) {
	 $('#cloneModal').modal('hide');
	
	  let styleNo = $("#styleNo").val();
	  let itemName = $("#itemName").val();
	  let userId = $("#userId").val();
	  
	  $.ajax({
		    type: 'POST',
		    dataType: 'json',
		    url: './cloneCostingNewVersion',
		    data:{
		    	costingNo:costingNo,
		    	userId:userId,
		    	styleNo:styleNo,
		    	itemName:itemName
		    },
		    success: function (data) {
		    	$('#dataList_costing').empty();	
		    	fetechDataRow(data.result);	
		    }
		});
	  
}

function refreshAction() {
	location.reload();
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


$(document).ready(function () {
	  $("input:text").focus(function () { $(this).select(); });
	});
	$(document).ready(function () {
	  $("input").focus(function () { $(this).select(); });
	});
	$(document).ready(function () {
	  $("#searchCosting").on("keyup", function () {
	    let value = $(this).val().toLowerCase();
	    $("#costingListTable tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});

	$(document).ready(function () {
	  $("#cloneCostingSearch").on("keyup", function () {
	    let value = $(this).val().toLowerCase();
	    $("#cloneCostingTable tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});
	
function itemWiseCostingReport(costingNo) {

	  let url = `printNewCostingReport/${costingNo}`;
	  window.open(url, '_blank');

	}

function searchCosting(costingNo){
	$('#searchModal').modal('hide');
	
	$.ajax({
	    type: 'POST',
	    dataType: 'json',
	    url: './searchCostingNewVersion',
	    data:{
	    	costingNo:costingNo,
	    },
	    success: function (data) {
	    	
	    	$('#dataList_costing').empty();	
	    	fetechDataRow(data.result);
	    	
	    }
	  });
}


function fetechDataRow(datalist){
	 const length = datalist.length;
	 for (let i = 0; i < length; i++) {
		 const rowData = datalist[i];
			$('#dataList_costing').append("<tr class='itemrow' data-id='"+i+"'>" +
					"<td style='width:60px;'>"+(i+1)+"</td>" +
					"<td style='width:120px;'><select id='groupType-"+i+"'  class='selectpicker groupType-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray'  ><option value='1'>Fabrics</option><option value='2'>Others</option></select></td>" +
					"<td style='width:460px;'><input type='text' class='form-control form-control-sm' placeholder='Search Item' aria-describedby='findButton' id='itemId-"+i+"' name='itemId-"+i+"' ></td>" +
					"<td style='width:120px;'><select id='unitId-"+i+"'  class='selectpicker unitId-"+i+" employee-width tableSelect  col-md-12 px-0' data-live-search='true'  data-style='btn-light btn-sm border-light-gray' onchange='EditUnit("+i+")' >" + unitlist + "</select></td>" +
					"<td style='width:60px;'><input  id='width-"+i+"' style='width:60px;' type='number'  ' class='form-control-sm width-"+i+"'  value=''/></td>" +
					"<td style='width:80px;'><input  id='yard-"+i+"' style='width:80px;' type='number'   class='form-control-sm yard-"+i+"'  value=''/></td>" +
					"<td style='width:80px;'><input readonly id='gsm-"+i+"' style='width:80px;' type='number'   class='form-control-sm gsm-"+i+"'  value=''/></td>" +
					"<td style='width:80px;'><input  id='consumption-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm consumption-"+i+"'  value=''/></td>" +
					"<td style='width:80px;'><input  id='rate-"+i+"' style='width:80px;' type='number'  onkeyup='setAmount("+i+")' class='form-control-sm rate-"+i+"'  value=''/></td>" +
					"<td style='width:80px;'><input readonly id='amount-"+i+"' style='width:80px;' type='number'   class='form-control-sm amount-"+i+"'  value=''/></td>" +
					"<td ><i class='fa fa-trash' onclick='costingItemSet('${id}','new')' style='cursor:pointer;'></i></td>"+ 
							"</tr>");
			
			$('#itemId-'+i).val(rowData.particularName);
			$('#groupType-'+i).val(rowData.particularType);
			//var groupType=$('#itemId-'+i).find('option:selected').attr('data-group');
			
			//console.log("groupType "+groupType);
			
			$('#unitId-'+i).val(rowData.unitId);
			$('#width-'+i).val(rowData.width);
			$('#yard-'+i).val(rowData.yard);
			$('#gsm-'+i).val(rowData.gsm);
			$('#consumption-'+i).val(rowData.consumption);
			$('#rate-'+i).val(rowData.unitPrice);
			$('#amount-'+i).val(rowData.amount);
			
			$('#styleNo').val(rowData.styleNo);
			$('#itemName').val(rowData.itemName);
			$('#commission').val(rowData.commission);
			
			$('#costingNo').val(rowData.costingNo);
			
			
			  $("#btnNewCosting").hide();
			  $("#btnEditCosting").show();
	 }
	 

		
		$('.tableSelect').selectpicker('refresh');
}





var today = new Date();
$("#submissionDate").val(today.getFullYear() + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + ('0' + today.getDate()).slice(-2)).change();

