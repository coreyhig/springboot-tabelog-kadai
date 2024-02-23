 let maxDate = new Date();
 maxDate = maxDate.setMonth(maxDate.getMonth() + 3);
 
 flatpickr('#fromCheckinDate', {
   locale: 'ja',
   minDate: 'today',
   maxDate: maxDate,
 });
 
 flatpickr('#fromCheckinTime', {
	locale: 'ja',
	enableTime: true,
	noCalendar: true,
	dateFormat: "H:i",
	time_24hr: true
});
 