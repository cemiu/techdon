import React, {useState} from 'react';
import { Button, Box, TextField, Checkbox } from '@mui/material';

import { object, string } from 'yup';
import AuthService from "../services/auth.service";
import { useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import { Formik, Field, Form, ErrorMessage } from 'formik';
import {Marginer} from "./Marginer";

export default function Register() {

    const [showhide, setShowhide] = useState('');
    const navigate = useNavigate();
    const handleshowhide = (event) => {
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

            <div className="form-container">
                <Formik initialValues={initialValues}
                        onSubmit = {(data, formikHelpers) => {
                            // Log initialValues in console.
                            console.log(JSON.stringify(data, null, 2));
                            AuthService.register(data.userType, data.firstName, data.lastName, data.email, data.password, data.phone , data.address, data.university).then(() => {
                                    alert("Registered! Try logging in!");
                                    navigate("/Login");
                                },
                                error => {
                                    alert("Registration failed! Try again!");
                                    const resMessage = (error.response && error.response.data && error.response.data.message) || error.message || error.toString();
                                    console.log(resMessage);
                                })
                            // When submitting the form, reset all fields.
                            formikHelpers.resetForm();
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
                            university: Yup.string()
                                .required('University is required'),
                            password: string().required("Password required!")
                                .max(19, "Password must not exceed 19 characters")
                                .matches(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{9,}$/,
                                    "Password must be at least 9 (one uppercase, one lower case, one digit and one special) characters!)"),
                            phone: Yup.string()
                                .min(10, 'Phone number muts be 11 Digits Long Please Include 07')
                                .required('Phone number required'),
                            confirmPassword: Yup.string()
                                .oneOf([Yup.ref('password'), null], 'Passwords must match')
                                .required('Confirm Password is required'),
                            acceptTerms: Yup.bool()
                                .oneOf([true], 'Accept Ts & Cs is required')
                        })}

                >
                    {({errors, status, touched}) => {
                        console.log(errors)
                        return (
                        <Form>
                            <div className="form-row">
                                <div className="form-group col">
                                    <label>User Type</label>
                                    <Field name="title" as="select" className={'form-control' + (errors.userType && touched.userType ? ' is-invalid' : '')} onChange={(e)=>(handleshowhide(e))}>

                                        <option value="">Select User Type</option>
                                        <option value="Student">Student</option>
                                        <option value="Donator">Donator</option>

                                    </Field>
                                    <ErrorMessage name="title" component="div" className="invalid-feedback"/>
                                </div>
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
                            showhide==='Student' && (
                            <div className="form-group">

                                <label htmlFor="email">University</label>
                                <Field placeholder="University" name="university" type="text"
                                       className={'form-control' }/>
                                <ErrorMessage name="University" component="div" className="invalid-feedback"/>
                            </div>
                                ) }

                            <div className="form-group">
                                <label htmlFor="phone">Phone Number</label>
                                <Field placeholder="Phone Number" name="phone" type="number"
                                       className={'form-control' + (errors.phone && touched.phone ? ' is-invalid' : '')}/>
                                <ErrorMessage name="phone" component="div" className="invalid-feedback"/>
                            </div>
                            <div className="form-group ">
                                <label htmlFor="confirmPassword">Address</label>
                                <Field placeholder="Please type in your entire address" name="address" type="address"
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
                        )}
                    }
                </Formik>
            </div>

        );



}