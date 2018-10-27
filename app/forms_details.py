# forms_details.py
from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, SubmitField
from wtforms.validators import DataRequired
class Login(FlaskForm):
	location = StringField('Location',validators = [DataRequired()])
	typeof = StringField('Type Of Emergency',validators = [DataRequired()])
	number = StringField('Number Of People',validators = [DataRequired()])
	name = StringField('Name Of Caller',validators = [DataRequired()])
	phone = StringField('Phone Number Of Caller',validators = [DataRequired()])
	submit = SubmitField('Submit')