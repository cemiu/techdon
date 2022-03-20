/* eslint-disable no-unused-vars */
import React, {useEffect, useState} from 'react'
// import '../App.css'
import * as Yup from 'yup';
import {Navigate, withRouter} from 'react-router-dom';
import {ErrorMessage, Field, Form, Formik} from 'formik';
import UserService from "../services/user.service";

export default function Settings() {

  const [goToHome, setGoToHome] = useState(false);
  const [delShow, setDelShow] = useState(false);
  const [updateSuccessShow, setUpdateSuccessShow] = useState(false);
  const [settings, setSettings] = useState({
    userType: UserService.getUserType(),
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: '',
    university: '',
    address: '',
  });

  useEffect(() => {
    if (!UserService.getUserType()) {
      setGoToHome(true);
    } else
      UserService.getPreferences().then(resp => {
        if (resp.status === 200) {
          const pref = resp.data;
          setSettings({
            ...settings,
            firstName: pref.firstName,
            lastName: pref.lastName,
            email: pref.email,
            phone: pref.phone,
            university: pref.university,
            address: pref.address.join(),
          });
        }
      })
  }, []);

  const phoneRegExp = /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/;
  const isStudent = UserService.isStudent();

  return (
    <div>
      {goToHome && <Navigate to='/'/>}
      <div className="form-container pb-4">
        <h1 className="mt-4 mb-4">Change Settings</h1>
        <Formik enableReinitialize initialValues={settings}
                onSubmit={(data, formikHelpers) => {
                  UserService.setPreferences(data.firstName, data.lastName, data.email, data.phone, data.address, data.password, data.university).then(resp => {
                    if (resp.status === 200) {
                      setUpdateSuccessShow(true);
                      setTimeout(() => {
                        setUpdateSuccessShow(false);
                      }, 3000);
                    }
                  });
                }}
                validationSchema={Yup.object().shape({
                  firstName: Yup.string(),
                  lastName: Yup.string(),
                  email: Yup.string()
                    .email('Email must be valid'),
                  phone: Yup.string()
                    .matches(phoneRegExp, 'Phone number must be valid'),
                  address: Yup.string()
                    .required('Address is required'),
                  password: Yup.string()
                    .max(30, "Password must not exceed 30 characters")
                    .matches(/^[A-Za-z\d@$!%*#?&]{8,}$/,
                      "Password must be at least 8 characters long and consist of letters, numbers and @$!%*#?&"),
                  confirmPassword: Yup.string()
                    .test('passwords-match', 'Passwords must match', function (value) {
                      return this.parent.password === value;
                    })
                })}
        >
          {({errors, status, touched}) => {
            return (
              <Form>
                <div className="form-row">
                  <div className="form-group">
                    <label>User Type</label>
                    <Field name="userType" className="form-control mt-3" disabled/>
                  </div>
                </div>
                <div className="form-row">
                  <h5 className="mt-3">Personal Information</h5>
                </div>
                <div className="form-row">
                  <div className="form-group">
                    <label>First Name</label>
                    <Field placeholder="First Name" name="firstName" className="form-control"/>
                  </div>
                  <div className="form-group col-xs-3">
                    <label>Last Name</label>
                    <Field placeholder="Last Name" name="lastName" className="form-control"/>
                  </div>
                </div>

                <div className="form-row">
                  <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <Field placeholder="Email" name="email"
                           className={'form-control' + (errors.email && touched.email ? ' is-invalid' : '')}/>
                    <ErrorMessage name="email" component="div" className="invalid-feedback"/>
                  </div>

                  <div className="form-group col-xs-3">
                    <label htmlFor="phone">Phone Number</label>
                    <Field placeholder="Phone Number" name="phone" type="text"
                           className={'form-control' + (errors.phone && touched.phone ? ' is-invalid' : '')}/>
                    <ErrorMessage name="phone" component="div" className="invalid-feedback"/>
                  </div>
                </div>

                {isStudent && (
                  <div className="form-row">
                    <div className="form-group">
                      <label htmlFor="university">University</label>
                      <Field placeholder="University" name="university" className={'form-control'}/>
                    </div>
                  </div>
                )
                }

                <div className="form-group ">
                  <label>Address</label>
                  <Field placeholder="Address, seperated by commas" name="address" className="form-control"/>
                </div>
                <div className="form-row">
                  <div className="form-group col">
                    <label>Password</label>
                    <Field placeholder="Password" name="password" type="password"
                           className={'form-control' + (errors.password && touched.password ? ' is-invalid' : '')}/>
                    <ErrorMessage name="password" component="div" className="invalid-feedback"/>
                  </div>
                  <div className="form-group col">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <Field placeholder="Confirm Password" name="confirmPassword" type="password"
                           className={'form-control' + (errors.confirmPassword && touched.confirmPassword ? ' is-invalid' : '')}/>
                    <ErrorMessage name="confirmPassword" component="div" className="invalid-feedback"/>
                  </div>
                </div>

                <div className="form-row">
                  <div className="form-group">
                    <button type="submit" className="btn btn-primary mr-2">Update</button>
                  </div>
                  { updateSuccessShow && (
                        <div className="alert alert-success" role="alert">
                          <strong>Success!</strong> Your profile has been updated.
                        </div>
                  )}
                </div>
              </Form>

            )
          }
          }
        </Formik>
        {!delShow ? (
          <div className="mt-5">
            <button type="logout" className="btn btn-warning mr-2" onClick={() => {
              UserService.logout().then(success => setGoToHome(true) )
            }}>Logout</button>
            <button type="delete-acc" className="btn btn-danger" onClick={() => setDelShow(true)}>Delete Account</button>
          </div>
        ) : (
          <div className="mt-5">
            <h5>Are you sure you want to delete your account?</h5>
            <button type="cancel" className="btn btn-secondary mr-2" onClick={() => setDelShow(false)}>Cancel</button>
            <button type="delete" className="btn btn-danger" onClick={() => {
              UserService.deleteUser().then(success => {
                if (success) {
                  setGoToHome(true);
                }
              })
            }}>Confirm Delete
            </button>
          </div>
        )}
      </div>
    </div>

  );

}