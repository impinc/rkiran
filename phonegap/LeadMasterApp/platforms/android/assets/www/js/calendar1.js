

var calendarPlugin = {
    createEvent: function(title, location, notes, startDate, endDate, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'CalendarPlugin', // mapped to our native <span id="zb8y6pk3n_9" class="zb8y6pk3n">Java class</span> called "CalendarPlugin"
            'addCalendarEntry', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "title": title,
                "description": notes,
                "eventLocation": location,
                "startTimeMillis": startDate.getTime(),
                "endTimeMillis": endDate.getTime()
            }]
        );
     }
};