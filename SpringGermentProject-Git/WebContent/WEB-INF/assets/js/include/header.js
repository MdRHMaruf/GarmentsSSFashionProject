
setTimeout(()=>{
	let targetId = $("#userId").val();
    console.log("Target Id",targetId);
    $.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getNotificationList',
		data: {
			targetId : targetId
		},
		success: function (data) {
			$("#notificationCount").text(data.notificationList.length);
			$("#notificationList").html('');
			loadNotification(data.notificationList);
		}
	});
},100);
setInterval(() => {

    let targetId = $("#userId").val();
    console.log("Target Id",targetId);
    $.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getNotificationList',
		data: {
			targetId : targetId
		},
		success: function (data) {
			$("#notificationCount").text(data.notificationList.length);
			$("#notificationList").html('');
			loadNotification(data.notificationList);
		}
	});
}, 120000);

function notificationClickAction(notificationNo){
	let targetId = $("#userId").val();
    console.log("Target Id",targetId);
    $.ajax({
		type: 'GET',
		dataType: 'json',
		url: './getNotificationList',
		data: {
			targetId : targetId
		},
		success: function (data) {
			$("#notificationCount").text(data.notificationList.length);
			$("#notificationList").html('');
			loadNotification(data.notificationList);
		}
	});
}

function notificationUpdate(){

}

function loadNotification(data){
    let length = data.length;
	let listItem = '';
	for(let i = 0; i<length ;i++){
		let li = data[i];
		console.log(li);
		listItem += `<li onclick='notificationClickAction("${li.notificationId}")' style="cursor: pointer;"><p>${li.createdBy}
									${li.subject} ${li.content} Time-${li.createdTime}</p></li>`;
	}
	$("#notificationList").append(listItem);
}