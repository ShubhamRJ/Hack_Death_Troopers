import os
import firebase_admin
from firebase_admin import db
from twilio.rest import Client

os.environ['GOOGLE_APPLICATION_CREDENTIALS']='C://Users/Hp/Desktop/Hack_Death_Troopers-master/app/creds.json'

firebase_admin.initialize_app(options = {
	'databaseURL' : 'https://hackabit-9f6b2.firebaseio.com'
	})


fh1 = open('points.txt','r')
fh2 = open('i.txt','r')
i = fh2.read()
points = list(fh1.read())
drivers = db.reference('/drivers')
drivers_list = drivers.get()
# print(drivers_list)
driver_phone = drivers_list["-LPm2j1I41iW7-beFLdc"]["phone"]
driver_lat = drivers_list["-LPm2j1I41iW7-beFLdc"]["lat"]
driver_lon = drivers_list["-LPm2j1I41iW7-beFLdc"]["lon"]
user_locs = db.reference('/user_loc')
user_loc = user_locs.get()
print(user_loc)
for key in list(user_loc.keys()):
	email = user_loc[key]["email"]
	lat = user_loc[key]["lat"]
	lon =user_loc[key]["lon"]
	if abs(driver_lat-lat)+abs(driver_lon-lon)<0.002:
		

# Your Account SID from twilio.com/console
		account_sid = "AC0183b71d0c61595fed9421b1511e2772"
# Your uth Token from twilio.com/console
		auth_token  = "1b5900d3bb98e2a13dba7af212c3dcd9"

		client = Client(account_sid, auth_token)

		message = client.messages.create(
			to="+917004991837", 
			from_="+16692011825",
   			body="Hello from Python!")

		print(message.sid)

