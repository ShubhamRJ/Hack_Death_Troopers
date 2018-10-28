# Hack_Death_Troopers

General Overview:
Traffic congestion in India is a major problem. Due to this, Emergency Medical Services are most affected due to this problem. Our idea is to clear the path for the on-duty Ambulance (by notifying others around to make way)

Implementation:
1) Mobile Application (for the citizens):
        This would take in the basic information of every user and also their locations
        A notification is send on to this user, if it is in the path of an on-duty ambulance.
        
2) Website (for the Hospital and 108 operator):
        Takes down plaintiff and caller details and then it picks an available driver to make the trip.
        Sets the start location, end location, etc (credentials in general)      
        Live tracking and route detection of the Ambulance dispatched.
        
3) Computer Vision:
        Takes a live picture and calculates the current traffic density and once its above a certain treshold, notifications only send once the treshold is surpassed.
        This ensures that when the roads are clear and congession is inexistent, sending a notification to the few users on the route is not optimal.
        
Corner Cases Encountered:
1) Vehicles that are going only in the direction of motion of the ambulance must be send a location.

2) People in buildings and establishments along the route must not be send this notification.

3) Vehicles only in the path of our trajectory will be notified of an enroute Ambulance.


Tools and Technologies used:
1) The mobile app was build on an Android platform.

2) Flask, JavaScript, HTML5 and Boot Strap 4 together brought the website in order.

3) The Database management system was setup on Firebase.

4) Computer Vision was implemented using a python script.

5) The following APIs were used: Google Maps API, Vision API, Firebase API, Google Geocoding API, etc.


Instructions to Run project:

The Android Application(Ambulance-Routing) can be run directly by building the APK for the project and resolving the required dependencies.

The app folder is actually a flask module app that can be run by setting the FLASK_APP environment variable to microblog.py.

export FLASK_APP=microblog.py

The app can be run from the command prompt then by using the function "flask run"

flask run


Brief Description of Application:
1) Mobile Application:
        This would be used by the citizens/masses. 
        The app aloows the user to either login or sign-up. 
        The basic info and location (stored temporarily) gets stored in the firebase database. 
        
2) Website:
        This is for the Hospitals/108 Operators.
        Any emergency call made, the details are noted and an available driver(ambulance) is dispatched.
        The location of the Ambulance is tracked LIVE !
        The plaintiff details and live location of Ambulance is stored in the firebase database.

3) Computer Vision: 
        A picture is taken by the phone on the Ambulance, this is then processed using the Google Vision API.
        A traffic density ratio is generated and if the traffic congestion is above a certain level, the notifications will be send only then.
        A python script is used here along with firebase API and Vision API.


Future Scope of the Project:
This setup has the potential to save thousands of lives. The current setup is very time consupming and urgently needs upgradation. Also this system could expand to help users know the route's traffic status. This can be used for Fire-Emergency Services too. And can provide important notification during other disaster management scenarios. The mass users can benifit from the traffic status the phone tells us. This would help enable a bigger demographic. And also continue with overall emergency sevices for saving lives everyday !!








Copyright 









