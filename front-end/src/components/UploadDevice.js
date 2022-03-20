import React, {useEffect, useState} from 'react';
import * as Yup from 'yup';
import "bootstrap/dist/css/bootstrap.min.css";

import {Field, FormikProvider, useFormik} from "formik";
import DonorService from "../services/donor.service";
import {Navigate} from "react-router-dom";

function DeviceNew() {

  const [goToHome, setGoToHome] = useState(false);
  const [goToDevices, setGoToDevices] = useState(false);

  useEffect(() => {
    if (!DonorService.isDonor()) {
      setGoToHome(true);
    }
  }, []);

  const validationSchema = Yup.object().shape({
    deviceName: Yup.string().required("Device Name is required"),
    description: Yup.string(),
  });

  const formik = useFormik({
    initialValues: {
      deviceType: "laptop",
      deviceName: "",
      description: "",
    },

    validationSchema,
    onSubmit: (data) => {
      // console.log("test");
      console.log(data);
      const description = data.description ? data.description : undefined;
        DonorService.newDevice(data.deviceType, data.deviceName, description)
          .then(response => {
            if (response.status === 200)
              setGoToDevices(true);
          });
    },
  });

  return (
    <div className="register-form">
      {goToHome && <Navigate to='/'/>}
      {goToDevices && <Navigate to='/devices'/>}
      <h1 className="mt-4 mb-4">Add Device</h1>
      <form onSubmit={formik.handleSubmit}>
        <div className="form-group">
          <label>Device Type</label>
          <FormikProvider value={formik}>
            <Field as="select" name="deviceType"className="form-control">
              <option value="laptop">Laptop</option>
              <option value="desktopComputer">Desktop Computer</option>
              <option value="smartphone">Smartphone</option>
              <option value="iPad">iPad</option>
              <option value="tablet">Tablet</option>
              <option value="printer">Printer</option>
              <option value="monitor">Monitor</option>
              <option value="hardDrive">Hard Drive</option>
              <option value="keyboard">Keyboard</option>
              <option value="mouse">Mouse</option>
              <option value="usbStick">USB Stick</option>
            </Field>
          </FormikProvider>
        </div>
        <div className="form-group">
          <label>Device Name</label>
          <input
            name="deviceName"
            type="text"
            className="form-control"
            onChange={formik.handleChange}
            value={formik.values.deviceName}
          />
          <div className="text-danger">
            {formik.errors.deviceName ? formik.errors.deviceName : null}
          </div>
        </div>

        <div className="form-group">
          <label>Device Description</label>
          <input
            name="description"
            type="text"
            className="form-control"
            onChange={formik.handleChange}
            value={formik.values.description}
          />
          <div className="text-danger">
            {formik.errors.description ? formik.errors.description : null}
          </div>
        </div>

        <div className="form-group">
          <button type="submit" className="btn btn-primary">
            Upload
          </button>
          <button
            type="button"
            className="btn btn-warning float-right"
            onClick={formik.handleReset}
          >
            Reset
          </button>
        </div>
      </form>
    </div>
  );

}

export default DeviceNew;

