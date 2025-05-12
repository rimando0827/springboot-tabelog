

let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);


flatpickr('#checkinDate', {
	mode: "single", //選択日付は1日のみ
	locale: 'ja',
	minDate: 'today',
	maxDate: maxDate,
	

});

let reservationTime = document.querySelector('#reservationTime');
let hiddenOpeningTime = document.getElementsByName('hiddenOpeningTime')[0].value;
let hiddenClosedTime = document.getElementsByName('hiddenClosedTime')[0].value;
if (reservationTime != null) {
	reservationTime.min = hiddenOpeningTime;
}
if (reservationTime != null) {
	reservationTime.max = hiddenClosedTime;
}

