  #include <WiFi.h>
#include <TinyGPS++.h>
#include <SoftwareSerial.h>
#include <HTTPClient.h>

static const int RXPin = 16, TXPin = 17;
static const uint32_t GPSBaud = 9600;

double latitude;
double longitude;
// The TinyGPS++ object
TinyGPSPlus gps;

// The serial connection to the GPS device
SoftwareSerial ss(RXPin, TXPin);

const char* ssid = "Redmi Note 10S";

const char* password = "gagan123";

// Domain Name with full URL Path for HTTP POST Request

const char* serverName = "http://api.thingspeak.com/update";

// write API Key provided by thingspeak

String apiKey = "M6WM5NWRF4VU0SFM";

void setup(){
  Serial.begin(9600);
  ss.begin(GPSBaud);
  WiFi.begin(ssid, password);
  Serial.println("Connecting");

  while(WiFi.status() != WL_CONNECTED) {

    delay(500);

    Serial.print(".");

    }

  Serial.println("");

  Serial.print("Connected to WiFi network with IP Address: ");

  Serial.println(WiFi.localIP());
}

void loop(){
  // This sketch displays information every time a new sentence is correctly encoded.
  while (ss.available() > 0 and WiFi.status()== WL_CONNECTED){
    WiFiClient client;

    HTTPClient http;
    gps.encode(ss.read());
    if (gps.location.isUpdated()){
      Serial.print("Latitude= ");
      latitude=gps.location.lat(); 
      Serial.print(latitude);
      Serial.print(" Longitude= "); 
      longitude=gps.location.lng();
      Serial.println(longitude);

      http.begin(client, serverName);
      http.addHeader("Content-Type", "application/x-www-form-urlencoded");
      String httpRequestData1 = "api_key=" + apiKey + "&field1=" + String(latitude);
      int httpResponseCode1 = http.POST(httpRequestData1);
      String httpRequestData2 = "api_key=" + apiKey + "&field2=" + String(longitude);
      int httpResponseCode2 = http.POST(httpRequestData2);
      Serial.print("HTTP Response code: ");

      Serial.println(httpResponseCode1);
      http.end();
    }
  }
}
