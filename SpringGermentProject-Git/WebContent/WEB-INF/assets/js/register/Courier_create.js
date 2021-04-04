
window.onload = ()=>{
	document.title = "Courier Create";
	maxCourierId();
} 

$("#courier_id").attr('disabled', true);

function maxCourierId(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './max_courierId',
		data: {},
		success: function (data) {
			$("#courier_id").val(data);
			getAllCouriers();
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

function CountriesSearch(v){
	let value=$(v).val();
	console.log(value);
	$(v).autocomplete({
		source: function (request, response) {
			$.ajax({
				url: "./countryNames/"+value,
				type: 'GET',
				dataType: "json",
				data: {
					key:value
				},
				success: function (data) {
					console.log("abc="+data)
					response(data);
				}
			});
		},
		select: function (e, ui) {
		}
	});

}


function insertCourier(){
	let user=$("#user_hidden").val();

	let courierid=$("#courier_id").val();
	let couriername=$("#courier_name").val();
	let courier_code=$("#courier_code").val();
	let courieraddress=$("#courier_address").val();
	let consigneeaddress=$("#consignee_address").val();
	let notifyaddress=$("#notify_address").val();	
	let country=$("#countries1").val();
	country=country.substring(country.lastIndexOf("*")+1,country.length);
	
	let telephone=$("#telphone").val();
	let mobile=$("#mobile").val();
	let fax=$("#fax").val();
	let email=$("#e_mail").val();
	let skypeid=$("#skype_id").val();
	let bankname=$("#bank_name").val();
	let bankaddress=$("#bank_address").val();
	let swiftcode=$("#swift_code").val();
	let bankcountry=$("#bank_country").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);
	
	console.log("user "+user);
	console.log("bcountry "+bankcountry);

	if (couriername=='') {
		alert("Courier Name Cannot be Empty");
	}else if (courieraddress=='') {
		alert("Courier Address Cannot be Empty");
	}else if (country=='') {
		alert("Country Cannot be Empty");
	}else if (telephone=='' && mobile=='') {
		alert("Telephone and Mobile Cannot be Empty");
	}else if (email=='') {
		alert("E-Mail Address Cannot be Empty");
	}else{
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './insertCourier',
			data: {
				user:user,
				couriername:couriername,
				courierid:courierid,
				couriercode:courier_code,
				courierAddress:courieraddress,
				consigneeAddress:consigneeaddress,
				notifyAddress:notifyaddress,
				country:country,
				telephone:telephone,
				mobile:mobile,
				email:email,
				fax:fax,				
				skypeid:skypeid,
				bankname:bankname,
				bankaddress:bankaddress,
				swiftcode:swiftcode,
				bankcountry:bankcountry


			},
			success: function (data) {
				console.log(data);
				if(data==true){
					alert("Courier Created Successfully");
					reloadPage();
				}else{
					alert("Courier Creation Failed. Could be Duplicate Courier Name Problem");
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

}


function CourierList(v){

	let value=$(v).val();
	console.log(value);
	$(v).autocomplete({
		source: function (request, response) {
			$.ajax({
				url: "./courierList/"+value,
				type: 'GET',
				dataType: "json",
				data: {},
				success: function (data) {
					console.log("abc="+data)
					response(data);
				}
			});
			//   $("#tag").removeClass('ac_loading');
		},
		select: function (e, ui) {
		}
	});

}


function courieriDetails(value){


	if (value=='') {
		alert("Select Buyer")
	}else{
		//value=value.substring(value.lastIndexOf("*")+1,value.length);
		$.ajax({
			url: "./courierdetails/"+value,
			type: 'POST',
			dataType: "json",
			data: {},
			success: function (data) {

				console.log(data);
				setData(data);
				$("#save").hide();
				$("#edit").show();

			}
		});
	}


}


function setData(data){
	$("#courier_id").val(data[0]);
	$("#courier_name").val(data[1]);
	$("#courier_code").val(data[2]);
	$("#courier_address").val(data[3]);
	$("#consignee_address").val(data[4]);
	$("#notify_address").val(data[5]);
	$("#countries1").val(data[6]);

	$("#telphone").val(data[7]);
	$("#mobile").val(data[8]);
	$("#fax").val(data[10]);
	$("#e_mail").val(data[9]);
	$("#skype_id").val(data[11]);

	$("#bank_name").val(data[12]);
	$("#bank_address").val(data[13]);
	$("#swift_code").val(data[14]);
	$("#bank_country").val(data[15]);
	
	

}


function editCourier(){
	let user=$("#user_hidden").val();

	let courierid=$("#courier_id").val();
	let couriername=$("#courier_name").val();
	let courier_code=$("#courier_code").val();
	
	let courieraddress=$("#courier_address").val();
	let consigneeaddress=$("#consignee_address").val();
	let notifyaddress=$("#notify_address").val();
	
	let country=$("#countries1").val();
	country=country.substring(country.lastIndexOf("*")+1,country.length);
	
	let telephone=$("#telphone").val();
		
	
	let mobile=$("#mobile").val();	
	let fax=$("#fax").val();	
	let email=$("#e_mail").val();
	let skypeid=$("#skype_id").val();
	let bankname=$("#bank_name").val();
	let bankaddress=$("#bank_address").val();
	let swiftcode=$("#swift_code").val();
	let bankcountry=$("#bank_country").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);
	
	console.log("user "+user);
	console.log("bcountry "+bankcountry);

	if (couriername=='') {
		alert("Courier Name Cannot be Empty");
	}else if (courieraddress=='') {
		alert("Courier Address Cannot be Empty");
	}else if (country=='') {
		alert("Country Cannot be Empty");
	}else if (telephone=='' && mobile=='') {
		alert("Telephone and Mobile Cannot be Empty");
	}else if (email=='') {
		alert("E-Mail Address Cannot be Empty");
	}else{
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './editCourier',
			data: {
				user:user,
				couriername:couriername,
				courierid:courierid,
				couriercode:courier_code,
				courierAddress:courieraddress,
				consigneeAddress:consigneeaddress,
				notifyAddress:notifyaddress,
				country:country,
				telephone:telephone,
				mobile:mobile,
				email:email,
				fax:fax,				
				skypeid:skypeid,
				bankname:bankname,
				bankaddress:bankaddress,
				swiftcode:swiftcode,
				bankcountry:bankcountry


			},
			success: function (data) {
				console.log(data);
				if(data==true){
					alert("Courier Created Successfully");
					reloadPage();
				}else{
					alert("Courier Creation Failed. Could be Duplicate Courier Name Problem");
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

}

function getAllCouriers(){
	
	$.ajax({
		type:'POST',
		dataType:'json',
		url:'./getCouriers',
		success:function(data)
		{	
			$("#couriertable").empty();
			patchdata(data.result);
		}
	});


}


function patchdata(data){
	let rows = [];
	for (let i = 0; i < data.length; i++) {
		//ert("ad "+data[i].aquisitionValue);
		rows.push(drawRow(data[i],i+1));
	}
	$("#couriertable").append(rows);
}

function drawRow(rowData,c) {
	//alert(rowData.aquisitionValue);
	let row = $("<tr />")
	row.append($("<td>" + rowData.id+ "</td>"));
	row.append($("<td>" + rowData.name+ "</td>"));
	row.append($("<td>" + rowData.code+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick='courieriDetails(" + rowData.id + ")' class=\"btn btn-primary\" data-toggle=\"modal\"data-target=\"#exampleModalCenter\"> </i></td>"));
	return row;
}

$(document).ready(function () {
	  $("#search").on("keyup", function () {
	    let value = $(this).val().toLowerCase();
	    $("#couriertable tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});

function reloadPage(){
	location.reload();
}

let idListMicro = ["courier_name","courier_code","courier_address","consignee_address","notify_address","countries1",
	"telphone","mobile","fax","e_mail","skype_id","bank_name","bank_address","swift_code","bank_country","save"];
idListMicro.forEach((id,index)=>{
  $('#'+id).keyup(function(event){
    if(event.keyCode ===13){
      event.preventDefault();
      $("#"+idListMicro[index+1]).focus();
    }
  });
})
