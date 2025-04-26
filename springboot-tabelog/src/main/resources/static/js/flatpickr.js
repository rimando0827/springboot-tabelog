

let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

 fetch(`/shops/${shopId}/closedDays`)
  .then(response => response.json())
  .then(closedDayNumber => {
flatpickr('#fromCheckinDate', {
 locale: 'ja',
 minDate: 'today',
 maxDate: maxDate,
 disable: [
            function(date) {
              // 曜日がclosedDayNumberで指定されている場合、その曜日を無効にする
              return date.getDay() === closedDayNumber;
            }
          ]
       
      })
      .catch(error => console.error('Error fetching closed days:', error));
});