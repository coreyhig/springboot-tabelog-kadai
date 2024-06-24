let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

let minDate = new Date();
minDate.setDate(minDate.getDate() + 1);

flatpickr('#fromCheckinDate', {
  locale: 'ja',
  minDate: minDate,
  maxDate: maxDate,
});

flatpickr('#fromCheckinTime', {
  locale: 'ja',
  enableTime: true,
  noCalendar: true,
  dateFormat: "H:i",
  time_24hr: true
});