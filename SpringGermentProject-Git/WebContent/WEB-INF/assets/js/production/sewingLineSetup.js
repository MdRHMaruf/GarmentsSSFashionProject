var lineId=0;
var departmentId=0;

function factoryWiseLine(){
	  var factoryId = $("#factoryId").val();
	  if(factoryId!=0){
		    $.ajax({
		        type: 'GET',
		        dataType: 'json',
		        url: './factorytWiseDepartment/'+factoryId,
		        success: function (data) {
		        	loadDepartment(data.departmentList);
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
	$('#departmentId').val(departmentId).change();
	departmentId=0;

}

function departmentWiseLine(){
	
	  var departmentId = $("#departmentId").val();
	  
	  if(departmentId!=0){
		    $.ajax({
		        type: 'GET',
		        dataType: 'json',
		        url: './departmentWiseLine/'+departmentId,
		        success: function (data) {
		        	loadLine(data.lineList);
		        }
		      });
	  }
}

function loadLine(data){

	var itemList = data;
	var options = "<option id='lineId' value='0' selected>Select Line</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='lineId' value='"+itemList[i].lineId+"'>"+itemList[i].lineName+"</option>";
	};
	document.getElementById("lineId").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	$('#lineId').val(lineId).change();
	lineId=0;

}

function setProductPlanInfoForSewing(buyerId,buyerorderId,styleId,itemId){
	
	
	var buyername=$('#buyerId'+buyerId).html();
	var purchaseOrder=$('#purchaseOrder'+buyerorderId).html();
	var styleNo=$('#styleId'+styleId).html();
	var itemName=$('#itemId'+itemId).html();
	
	$('#buyerName').html(buyername);
	$('#purchaseOrder').html(purchaseOrder);
	$('#styleNo').html(styleNo);
	$('#itemName').html(itemName);
	

	$('#styleId').val(styleId);
	$('#itemId').val(itemId);
	$('#buyerorderId').val(buyerorderId);

}

function duration(){

	var start=new Date($("#start").val());
	var end=new Date($("#end").val());

	var Difference_In_Time = end.getTime() - start.getTime(); 

	// To calculate the no. of days between two dates 
	//var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);

	var diffDays= Math.abs((end-start)/(1000*60*60*24))+1;

	$("#duration").val(diffDays);

}


function saveAction(){
	var i=0;
	var hexvalues = [];
	$('#lineId :selected').each(function(i, selectedElement) {
		 hexvalues[i]=$(selectedElement).val();
		var txt=$(selectedElement).text();
		i++;
	});

	
	
	var user=$("#userId").val();
	var style=$("#styleId").val();
	var itemId=$("#itemId").val();
	var poNo=$("#purchaseOrder").html();
	var buyerOrderId=$("#buyerorderId").val();
	var start=$("#start").val();
	var end=$("#end").val();
	var duration=$("#duration").val();
	
	
	
	var conv_list={
			
			Line:hexvalues,
		
	}
	


	if(style=='' ||  start=='' || end==''){
		alert("Information Incomplete");
	}
	else{

		console.log(hexvalues);

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './InsertLines',
			data:{
				user:user,
				style:style,
				itemId:itemId,
				poNo:poNo,
				buyerOrderId:buyerOrderId,
				Line:hexvalues,
				start:start,
				end:end,
				duration:duration
			}
			,
			success: function (data) {
					if (data.includes!="Successful") {
						alert(" Select Line(s) "+data+" is/are Already in Use")
					}else{
						alert(data)
						//RetrieveLines();
					}
			}
		});
		
	}

}


function RetrieveLines(){

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: "./Lines",
			data:{
				
			},
			success: function (data) {
				
						console.log(" data "+data)
						$("#dataList").empty();
						patchData(data.result);
					
			}
		});
	


}


function patchData(data){
	var rows = [];


	for (var i = 0; i < data.length; i++) {
		//ert("ad "+data[i].aquisitionValue);
		rows.push(drawRow(data[i],i+1));

	}

	$("#datalist").append(rows);
	
}

function drawRow(rowData,c) {

	//alert(rowData.aquisitionValue);

	var row = $("<tr />")
	row.append($("<td>" + rowData.selectid+ "</td>"));
	row.append($("<td>" + rowData.style+ "</td>"));
	row.append($("<td>" + rowData.lines+ "</td>"));
	row.append($("<td>" + rowData.start+ "</td>"));
	row.append($("<td>" + rowData.end+ "</td>"));
	row.append($("<td ><i class='fa fa-download' onclick=download('" + encodeURIComponent(rowData.filename) + "')  class=\"btn btn-primary\"> </i></td>"));
	row.append($("<td ><i class='fa fa-trash' onclick=del('" + encodeURIComponent(rowData.filename) + "')  class=\"btn btn-primary\"> </i></td>"));
	

	return row;
}



/*function saveAction(){
	var i=0;
	var hexvalues = [];
	$('#lines :selected').each(function(i, selectedElement) {
		var value=$(selectedElement).val();
		var txt=$(selectedElement).text();
		//hexvalues[i] = [value,txt];
		var data_set={
				id:value,
				name:txt,
		}
		hexvalues.push(data_set);
		//labelvalues[i] = $(selectedElement).text();
	});

	var user=$("#userId").val();
	var style=$("#stylename").val();
	var start=$("#start").val();
	var end=$("#end").val();
	var duration=$("#duration").val();
	
	var conv_list={
			
			id:1,
			Line:hexvalues,
			
				
	}
	
	var list=JSON.stringify(conv_list);

	if (start=="") {
		alert("Select Starting Date");
	}else if(end==""){
		alert("Select Ending Date");
	}else{
		var data = JSON.stringify({
			 user:user,
			 styleid:style,
			 start:start,
			 end:end,
			 duration:duration,
			 Lines:hexvalues,
		});
		
		console.log(list);

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './InsertLines',
			data:{
				user:user,
				start:start,
				end:end,
				duration,
				Line:list,
			}
			,
			success: function (data) {

			}
		});
	}

}*/