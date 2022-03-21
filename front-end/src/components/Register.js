/* eslint-disable no-unused-vars */
import React, {useState} from 'react';
import '../App.css'
import * as Yup from 'yup';
import {string} from 'yup';
import UserService from "../services/user.service";
import {useNavigate} from 'react-router-dom';
import {ErrorMessage, Field, Form, Formik} from 'formik';
import Banner from "./Banner/Banner";
import mobileImg from "../images/Pic1.png";
import defaultImg from "../images/Pic1.png";

export default function Register() {

  const [showhide, setShowhide] = useState('');
  const navigate = useNavigate();
  const handleshowhide = (event) => {
    const getuser = event.target.value;
    setShowhide(getuser);
  }
  const donor = (event) => {
    const getuser = event.target.value;
    setShowhide(getuser);
  }
  const initialValues = {
    userType: '',
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: '',
    university: '',
    address: '',
    acceptTerms: false
  }

  return (
    <div>
      <Banner
        mobileSrc={mobileImg}
        tabletSrc={defaultImg}
        desktopSrc={defaultImg}
        alt="my banner"
        bannerClass="banner_img"
      />

      <div className="form-container">
        <h1>Register</h1>
        <Formik initialValues={initialValues}
                onSubmit={(data, formikHelpers) => {
                  // Log initialValues in console.
                  UserService.register(data.userType, data.firstName, data.lastName, data.email, data.password, data.phone, data.address, data.university !== "" ? data.university : undefined).then(resp => {
                      formikHelpers.resetForm();
                      navigate("/");
                    },
                    error => {
                      alert("Registration failed! Try again!");
                      const resMessage = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();
                      console.log(resMessage);
                    })
                  // When submitting the form, reset all fields.
                }}
                validationSchema={Yup.object().shape({
                  address: Yup.string()
                    .required('Address is required'),

                  firstName: Yup.string()
                    .required('First Name is required'),
                  lastName: Yup.string()
                    .required('Last Name is required'),
                  email: Yup.string()
                    .email('Email is invalid')
                    .required('Email is required'),
                  password: string().required("Password required!")
                    .max(30, "Password must not exceed 30 characters")
                    .matches(/^[A-Za-z\d@$!%*#?&]{8,}$/,
                      "Password must be at least 8 characters long, and contain letters, numbers and @$!%*#?&"),
                  phone: Yup.string()
                    .min(10, 'Phone number must be at least 10 digits long')
                    .required('Phone number required'),
                  confirmPassword: Yup.string()
                    .oneOf([Yup.ref('password'), null], 'Passwords must match')
                    .required('Confirm Password is required'),
                  acceptTerms: Yup.bool()
                    .oneOf([true], 'Accept Ts & Cs is required')
                })}

        >
          {({errors, status, touched}) => {
            // console.log(errors)
            return (
              <Form>
                <div className="form-row">
                  <div className="form-group col" onChange={(e) => (handleshowhide(e))}>
                    User Type
                    <div role="group" aria-labelledby="my-radio-group">
                      <label>
                        <Field type="radio" name="userType" value="student"/>
                        Student
                      </label>
                      <label>
                        <Field type="radio" name="userType" value="donor"/>
                        Donor
                      </label>
                    </div>
                  </div>
                </div>
                <div className="form-row">
                  <div className="form-group col-5">
                    <label htmlFor="firstName">First Name</label>
                    <Field placeholder="First Name " name="firstName" type="text"
                           className={'form-control' + (errors.firstName && touched.firstName ? ' is-invalid' : '')}/>
                    <ErrorMessage name="firstName" component="div" className="invalid-feedback"/>
                  </div>
                  <div className="form-group col-5">
                    <label htmlFor="lastName">Last Name</label>
                    <Field placeholder="Last Name" name="lastName" type="text"
                           className={'form-control' + (errors.lastName && touched.lastName ? ' is-invalid' : '')}/>
                    <ErrorMessage name="lastName" component="div" className="invalid-feedback"/>
                  </div>
                </div>
                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <Field placeholder="Email" name="email" type="text"
                         className={'form-control' + (errors.email && touched.email ? ' is-invalid' : '')}/>
                  <ErrorMessage name="email" component="div" className="invalid-feedback"/>
                </div>

                {
                  showhide === 'student' && (
                    <div className="form-group">

                      <label htmlFor="university">University</label>
                      <Field placeholder="University" name="university" type="text"
                             className={'form-control'}/>
                      <ErrorMessage name="university" component="div" className="invalid-feedback"/>
                    </div>
                  )}

                <div className="form-group">
                  <label htmlFor="phone">Phone Number</label>
                  <Field placeholder="Phone Number" name="phone" type="text"
                         className={'form-control' + (errors.phone && touched.phone ? ' is-invalid' : '')}/>
                  <ErrorMessage name="phone" component="div" className="invalid-feedback"/>
                </div>
                <div className="form-group ">
                  <label htmlFor="confirmPassword">Address</label>
                  <Field placeholder="Please type in your entire address" name="address" type="text"
                         className={'form-control' + (errors.address && touched.address ? ' is-invalid' : '')}/>

                </div>
                <div className="form-row">
                  <div className="form-group col">
                    <label htmlFor="password">Password</label>
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

                <div className="form-group form-check">
                  <Field type="checkbox" name="acceptTerms"
                         className={'form-check-input ' + (errors.acceptTerms && touched.acceptTerms ? ' is-invalid' : '')}/>
                  <label htmlFor="acceptTerms" className="form-check-label">Accept Terms &
                    Conditions</label>
                  <ErrorMessage name="acceptTerms" component="div" className="invalid-feedback"/>
                </div>

                <div className="form-group">
                  <button type="submit" className="btn btn-primary mr-2">Register</button>
                  <button type="reset" className="btn btn-secondary">Reset</button>
                </div>
              </Form>
            )
          }
          }
        </Formik>
      </div>
    </div>

  );
}
