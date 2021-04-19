window.onload = ()=>{
	document.title = "Supplier Create";
	maxsupplierId();
} 


$("#suppier_id").attr('disabled', true);

function maxsupplierId(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './max_supplierid',
		data: {},
		success: function (data) {
			$("#suppier_id").val(data);
			getAllsuppliers();

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
			//   $("#tag").removeClass('ac_loading');
		},
		select: function (e, ui) {
		}
	});

}



function insertSupplier(){
	let user=$("#user_hidden").val();

	let suppier_id=$("#suppier_id").val();
	let supplier_name=$("#suppier_name").val();
	let supplier_code=$("#suppier_code").val();
	let contact_person=$("#contact_person").val();
	
	let supplier_address=$("#suppier_address").val();
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
	let aaccounts_no=$("#accounts_no").val();
	
	let accounts_name=$("#accounts_name").val();
	let bankaddress=$("#bank_address").val();
	
	let swiftcode=$("#swift_code").val();
	
	let bankcountry=$("#bankCOuntry").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);
	
	console.log("user "+user);
	console.log("bcountry "+bankcountry);

	if (supplier_name=='') {
		alert("Supplier Name Cannot be Empty");
	}else if (supplier_address=='') {
		alert("Supplier Address Cannot be Empty");
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
			url: './insertSupplier',
			data: {
				user:user,
				suppliername:supplier_name,
				supplierid:suppier_id,
				suppliercode:supplier_code,
				supplieraddress:supplier_address,
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
				bankcountry:bankcountry,
				contcatPerson:contact_person,
			 	accountno:aaccounts_no,
			 	accountname:accounts_name


			},
			success: function (data) {
				console.log(data);
				if(data==true){
					alert("Supplier Created Successfully");
					reloadPage();
				}else{
					alert("Supplier Creation Failed. Could be Duplicate Supplier Name Problem");
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


function SupplierList(v){

	let value=$(v).val();
	console.log(value);
	$(v).autocomplete({
		source: function (request, response) {
			$.ajax({
				url: "./suppliersearch/"+value,
				type: 'GET',
				dataType: "json",
				data: {

				},
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

function SupplierDetails(value){

	//let value=$("#suppierSearch").val();
	//console.log(value);

	if (value=='') {
		alert("Select Buyer")
	}else{
		//value=value.substring(value.lastIndexOf("*")+1,value.length);
		$.ajax({
			url: "./supplierDetails/"+value,
			type: 'POST',
			dataType: "json",
			data: {

			},
			success: function (data) {

				console.log(data);
				setData(data);
				$("#save").hide();
				$("#edit").show();

			}
		});
	}


}

function createNewEvent(){
	$("#save").show();
	$("#edit").hide();
}


function setData(data){
	$("#suppier_id").val(data[0]);
	$("#suppier_name").val(data[1]);
	$("#suppier_code").val(data[2]);
	$("#contact_person").val(data[3]);
	$("#suppier_address").val(data[4]);
	$("#consignee_address").val(data[5]);
	$("#notify_address").val(data[6]);
	$("#countries1").val(data[7]);

	$("#telphone").val(data[8]);
	$("#mobile").val(data[9]);
	$("#fax").val(data[10]);
	$("#e_mail").val(data[11]);
	$("#skype_id").val(data[12]);

	$("#bank_name").val(data[13]);
	$("#bank_address").val(data[14]);
	$("#accounts_no").val(data[15]);
	$("#accounts_name").val(data[16]);
	$("#swift_code").val(data[17]);
	$("#bankCOuntry").val(data[18]);
	
	

}


function editSupplier(){
	let user=$("#user_hidden").val();

	let suppier_id=$("#suppier_id").val();
	let supplier_name=$("#suppier_name").val();
	let supplier_code=$("#suppier_code").val();
	let contact_person=$("#contact_person").val();
	
	let supplier_address=$("#suppier_address").val();
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
	let aaccounts_no=$("#accounts_no").val();
	
	let accounts_name=$("#accounts_name").val();
	let bankaddress=$("#bank_address").val();
	
	let swiftcode=$("#swift_code").val();
	
	let bankcountry=$("#bankCOuntry").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);
	
	console.log("user "+user);
	console.log("bcountry "+bankcountry);

	if (supplier_name=='') {
		alert("Supplier Name Cannot be Empty");
	}else if (supplier_address=='') {
		alert("Supplier Address Cannot be Empty");
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
			url: './editsupplier',
			data: {
				user:user,
				suppliername:supplier_name,
				supplierid:suppier_id,
				suppliercode:supplier_code,
				supplieraddress:supplier_address,
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
				bankcountry:bankcountry,
				contcatPerson:contact_person,
			 	accountno:aaccounts_no,
			 	accountname:accounts_name


			},
			success: function (data) {
				console.log(data);
				if(data==true){
					alert("Supplier Edited Successfully");
					reloadPage();
				}else{
					alert("Supplier Editing Failed. Could be Duplicate Supplier Name Problem");
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


function getAllsuppliers(){
	//$("#itemtable").addClass('ac_loading');
	$.ajax({

		type:'POST',
		dataType:'json',
		url:'./getSuppliers',
		success:function(data)
		{
			
			$("#supplierTable").empty();
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

	$("#supplierTable").append(rows);
}

function drawRow(rowData,c) {
	let row = $("<tr />")
	row.append($("<td>" + rowData.id+ "</td>"));
	row.append($("<td>" + rowData.name+ "</td>"));
	row.append($("<td>" + rowData.code+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=\"SupplierDetails(" + rowData.id + ")\" class=\"btn btn-primary\" data-toggle=\"modal\"data-target=\"#exampleModal\"> </i></td>"));
	return row;
}


$(document).ready(function () {
	  $("#search").on("keyup", function () {
	    let value = $(this).val().toLowerCase();
	    $("#supplierTable tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});

function reloadPage(){
	location.reload();
}


let idListMicro = ["suppier_code","suppier_name","contact_person","suppier_address","consignee_address","notify_address","countries1","telphone",
	"mobile","fax","e_mail","skype_id","bank_name","accounts_no","bank_address","accounts_name","swift_code","bankCOuntry","btnSave"];
idListMicro.forEach((id,index)=>{
  $('#'+id).keyup(function(event){
    if(event.keyCode ===13){
      event.preventDefault();
      $("#"+idListMicro[index+1]).focus();
    }
  });
})