function getOrganizationName(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './getOrganizationName',
		data: {

		},
		success: function (data) {
			setData(data);
		}
	});
}

function setData(data){

	$("#organizationId").val(data[0].organizationId);
	$("#organizationName").val(data[0].organizationName);
	$("#organizationContact").val(data[0].organizationContact);
	$("#organizationAddress").val(data[0].organizationAddress);

}

function changeAddress(){

	var organizationId = $("#organizationId").val();
	var organizationName = $("#organizationName").val();
	var organizationContact = $("#organizationContact").val();
	var organizationAddress = $("#organizationAddress").val();
	var userId = $("#userId").val();

	if (organizationName !='') {
		if (organizationContact !='') {
			if (organizationAddress !='') {
				$.ajax({
					type: 'POST',
					dataType: 'json',
					url: './saveOrganizationName',
					data: {

						organizationId:organizationId,
						organizationName: organizationName,
						organizationContact: organizationContact,
						organizationAddress: organizationAddress,
						userId:userId,

					},
					success: function (data) {
						if (data == "Something Wrong") {
							dangerAlert("Something went wrong");
						} else {
							successAlert("Organization Save Successfully");
						}
					}
				});
			} else {
				warningAlert("Empty Organization Address... Please Enter Organization Address");
			}
		} else {
			warningAlert("Empty Organization Contact... Please Enter Organization Contact");
		}
	} else {
		warningAlert("Empty Organization Name... Please Enter Organization Name");
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

function dangerAlert(message) {
	var element = $(".alert");
	element.hide();
	element = $(".alert-danger");
	document.getElementById("dangerAlert").innerHTML = "<strong>Duplicate!</strong> "+message+"..";
	element.show();
	setTimeout(() => {
		element.toggle('fade');
	}, 2500);

}