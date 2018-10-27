# forms_details.py
from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, SubmitField
from wtforms.validators import DataRequired
class LoginDrivers(FlaskForm):
	submit = SubmitField('Submit')