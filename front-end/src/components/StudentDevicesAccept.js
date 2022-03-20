/* eslint-disable no-unused-vars */
import React, {useEffect, useState} from 'react'
import {Navigate, useSearchParams} from 'react-router-dom';
import StudentService from "../services/student.service";
import DeviceService from "../services/device.service";

export default function StudentDevicesAccept() {

  const [goToHome, setGoToHome] = useState(false);
  const [goToDevices, setGoToDevices] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();
  const [device, setDevice] = useState();

  useEffect(() => {
    if (!StudentService.isStudent()) {
      setGoToHome(true);
    } else {
      const deviceId = searchParams.get('device');
      StudentService.claimDevice(deviceId).then(response => {
        console.log(response.data);
        setDevice(response.data);
      }).catch(() => setGoToDevices(true));
    }
  }, []);

  return (
    <div>
      {goToHome && <Navigate to='/'/>}
      {goToDevices && <Navigate to='/offered'/>}
      <div className="form-container pb-4">
        <h1 className="mt-4 mb-4">Accepted Device</h1>
        <label className="mt-4 mb-4">
          <h4>Congratulations, you've been donated a device</h4>
          <p>
            You can find the donor's contact information below, to arrange the transfer.
            They will also receive a notification that you've accepted their device,
            along with your contact details.
          </p>
        </label>
        { device && (
          <div className="card mb-4">
            <div className="card-body">
              <h5 className="card-title">Donor Contact Details</h5>
              <p className="card-text"><strong>Name:</strong> {device.donorName}</p>
              <p className="card-text"><strong>Email:</strong> {device.donorEmail}</p>
              <p className="card-text"><strong>Phone No:</strong> {device.donorPhone}</p>
            </div>
            <div className="card-body">
              <h5 className="card-title">Device Details</h5>
              <p className="card-text"><strong>Type:</strong> {DeviceService.mapDeviceTypeToName(device.deviceType)}</p>
                <p className="card-text"><strong>Name:</strong> {device.deviceName}</p>
              {device.deviceDescription && (
                <div>
                  <strong>Description:</strong>
                  <p>{device.deviceDescription}</p>
                </div>
              )}
              {device.deviceLocation && (
                <p className="card-text"><strong>Location:</strong> {device.deviceLocation}</p>
              )}
            </div>
          </div>
        ) }
      </div>
    </div>

  );

}