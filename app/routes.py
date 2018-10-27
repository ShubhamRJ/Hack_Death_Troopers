from flask import render_template, flash, redirect
from app.forms_login import LoginForm
from app.forms_details import Login
from app import app
import firebase_admin
from firebase_admin import db
import flask
import json

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
def livetrack(name,new_detail,i):
	driver_data = db.reference('/drivers')
	driver_ans = driver_data.child(i).get()
	return render_template('livetrack.html',i = i,new_detail = new_detail, name = name, driver_ans = driver_ans)

