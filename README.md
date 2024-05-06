GPS Tracker Using Arduino UNO

# APP
## INTRODUCTION
The app has been made on Android Studio using Kotlin. This app uses REST API to get data from ThingSpeak and display in text views at the press of a button.

## IMPLEMENTATION
### REST API
RESTful API is an API Architecture which through HTTP protocols performs CRUD (Creating, Reading, Updating, Deleting) functions within or from a database.

### CODE
ROBO POJO Generator Plugin has been used to create a kt class compaitible with the format of data sent back from the cloud via a JSON file. The format of the response (which uses GET Protocol) was found on the the ThingSpeak API documentation page - https://in.mathworks.com/help/thingspeak/readlastentry.html The Retrofit library has been used to easily make the API calls which is handled by the ApiService.kt file which has the required queries in the getCurrentLocation function. The only query we have is a constant one which is nothing but the API Key which is already a string saved in the ApiConfig.kt file. Said file also calls ApiService class and establishes contact with the API. Finally, the MainViewModel is the final model which helps us unify the UI and the backend. It calls the ApiConfig file, handles the loading case, error case and also stores the loaction in _LocaitonData which is a LiveData object. In the MainActivity.kt, the string for the final response is created which is appended to the response from the CurrentLocaitonResponse file which now has the live values of the Latitude and Longitude and also time is another output which we get from a standard Java library. The UI was made using the design view of the activity_main.xml in ConstraintLayout.
