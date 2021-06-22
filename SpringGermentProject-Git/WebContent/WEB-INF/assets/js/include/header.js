
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
		url: './updateNotificationToSeen',
		data: {
			notificationId : notificationNo,
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
	let targetId = $("#userId").val();
    console.log("Target Id",targetId);
    $.ajax({
		type: 'POST',
		dataType: 'json',
		url: './notificationSeen',
		data: {
			targetId : targetId
		},
		success: function (data) {
			$("#notificationCount").text('0');
			//$("#notificationList").html('');
			//loadNotification(data.notificationList);
		}
	});
}

function clearAllFunction(){
	let targetId = $("#userId").val();
    console.log("Target Id",targetId);
    $.ajax({
		type: 'POST',
		dataType: 'json',
		url: './notificationSeen',
		data: {
			targetId : targetId
		},
		success: function (data) {
			$("#notificationCount").text('0');
			//$("#notificationList").html('');
			//loadNotification(data.notificationList);
		}
	});
}

function loadNotification(data){
    let length = data.length;
	let listItem = '';
	for(let i = 0; i<length ;i++){
		let li = data[i];
		console.log(li);
		listItem += `<li onclick='${li.targetSeen==0?`notificationClickAction("${li.notificationId})`:""}' class="${li.targetSeen==0?'unseen':'seen'}" style="cursor: pointer;"><p>${li.createdBy}
									${li.subject} ${li.content} Time-${li.createdTime}</p></li>`;
	}
	$("#notificationList").append(listItem);
}