from flask import render_template, flash, redirect
from app.forms_login import LoginForm
from app.forms_details import Login
from app import app
import firebase_admin
from firebase_admin import db
import flask
import json
import os
import time
import googlemaps
import requests
import polyline
import subprocess

os.environ['GOOGLE_APPLICATION_CREDENTIALS']='C://Users/Hp/Desktop/Hack_Death_Troopers-master/app/creds.json'

firebase_admin.initialize_app(options = {
	'databaseURL' : 'https://hackabit-9f6b2.firebaseio.com'
	})

@app.route('/')
@app.route('/index')
def index():
    return render_template('index.html', title = 'Index')
@app.route('/login',methods = ['GET','POST'])
def login():
    form = LoginForm()
    if form.username.data!=None and form.password.data!=None:
        flash('Login requested')
        operators = db.reference('/admins')
        admins = operators.get()
        for i in admins:
        	if (admins[i]["email"] == form.username.data and admins[i]["password"] == form.password.data):
        		return redirect('/'+form.username.data+'/details')
        flash('Username or password incorrect')
        return render_template('login.html',title = 'Login', form = form)
    else:
    	return render_template('login.html',title = 'Login', form = form)
@app.route('/<name>/details',methods = ['GET','POST'])
def details(name):
	form = Login()
	if form.location.data!=None and form.typeof.data!=None and form.number.data!=None and form.name.data!=None and form.phone.data!=None:
		flash('Details Submitted')
		detailer = db.reference()
		new_detail = detailer.child('details').push({
		"location":form.location.data,
		"typeof":form.typeof.data,
		"number":form.number.data,
		"name":form.name.data,
		"phone":form.phone.data
		}).key
		print(type(new_detail))
		return redirect('/'+name+'/'+new_detail+'/drivers')
	return render_template('details.html',title = 'Details', form = form)
@app.route('/<name>/<new_detail>/drivers',methods = ['GET','POST'])
def driver_select(name,new_detail):
	drivers_array = db.reference('/drivers')
	drivers_list = drivers_array.get()
	drivers_available = {}
	for i in drivers_list:
		if drivers_list[i]["availability"]=="True":
			drivers_available[i] = {
				"name":drivers_list[i]["name"],
				"phone":drivers_list[i]["phone"],
				"availability":drivers_list[i]["availability"],
				"ambulance":drivers_list[i]["ambulance"],
				"lat":drivers_list[i]["lat"],
				"lon":drivers_list[i]["lon"]
			}
	return render_template('drivers.html',title = "Index",name = name,new_detail = new_detail, drivers_available = drivers_available)
@app.route('/<name>/<new_detail>/<i>/livetrack',methods = ['GET'])
def live(name,new_detail,i):
	driver_data = db.reference('/drivers')
	driver_ans = driver_data.child(i).get()
	admins = db.reference('/admins')
	for i in admins.get():
		admin = db.reference('/admins').child(i).get()
		lat,lon = 0,0
		if admin["email"] == name:
			lat = admin["lat"]
			lon = admin["lon"]
	new_details = db.reference('/details')
	location_name = new_details.child(new_detail).get()["location"]
	gmaps = googlemaps.Client(key = 'AIzaSyBH7whXsmbq1i8DxDOHICESzsWatstrcOU')
	geocode_result = gmaps.geocode(location_name)
	dlat = (geocode_result[0]['geometry']['location']['lat'])
	dlon = (geocode_result[0]['geometry']['location']['lng'])
	# print(dlat)
	# print(dlon)
	# print(lat)
	# print(lon)
	# requests.get("")
	new_url = "https://maps.googleapis.com/maps/api/directions/json?origin="+str(lat)+"," + str(lon)+"&destination="+str(dlat)+","+str(dlon)+"&sensor=false"+"&key=AIzaSyBH7whXsmbq1i8DxDOHICESzsWatstrcOU"
	router = requests.get(new_url)
	pointer = json.loads(router.text)
	points = polyline.decode(pointer["routes"][0]["overview_polyline"]["points"])
	print(points)
	# for i in range(len(points)-1):
	# 	new_url = "https://maps.googleapis.com/maps/api/directions/json?origin="+str(points[i][0])+"," + str(points[i][1])+"&destination="+str(points[i+1][0])+","+str(points[i+1][1])+"&sensor=false"+"&key=AIzaSyBH7whXsmbq1i8DxDOHICESzsWatstrcOU"
	# 	gade = (polyline.decode(json.loads(requests.get(new_url).text)["routes"][0]["overview_polyline"]["points"]))
	# 	for j in gade:
	# 		points.append(j)
	# print(points)
	fh1 = open('points.txt','w+')
	fh1.write(str(points))
	fh1.close()
	fh2 = open('i.txt','w+')
	fh2.write(i)
	fh2.close()
	os.system('python script.py')
	return render_template("livetrack.html",title="livetrack",points = points, olat = lat, olon = lon,dlat = dlat, dlon = dlon, name = name,new_detail = str(new_detail), driver_ans = driver_ans)
