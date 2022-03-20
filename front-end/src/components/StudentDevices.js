/* eslint-disable no-unused-vars */
import React, {useEffect, useState} from 'react'
import {Navigate} from 'react-router-dom';
import StudentService from "../services/student.service";
import DeviceService from "../services/device.service";

export default function StudentDevices() {

  const [goToHome, setGoToHome] = useState(false);
  const [goToApply, setGoToApply] = useState(false);
  const [accept, setAccept] = useState();

  const [devices, setDevices] = useState({});

  useEffect(() => {
    if (!StudentService.isStudent()) {
      setGoToHome(true);
    } else {
      StudentService.getOfferedDevices().then(response => {
        response.data.devices?.forEach(deviceId => {
          StudentService.loadDevice(deviceId).then(response => {
            setDevices(devices => ({...devices, [deviceId]: response.data}));
          });
        });
      });
    }
  }, []);

  return (
    <div>
      {goToHome && <Navigate to='/'/>}
      {goToApply && <Navigate to='/apply'/>}
      {accept && <Navigate to={'/offered/accept/?device=' + accept}/>}
      <div className="form-container pb-4">
        <h1 className="mt-4 mb-4">Your Devices</h1>
        <div className="row">
          <div className="col-12">
            <button className="btn btn-primary btn-block" onClick={() => setGoToApply(true)}>
              Apply for devices
            </button>
            {/*<button className="btn btn-primary btn-block" onClick={() => {*/}
            {/*  StudentService.setDonationPreferences(['laptop', 'smartphone', 'printer']);*/}
            {/*}}>*/}
            {/*  Apply for Devices*/}
            {/*</button>*/}
          </div>
        </div>
        {devices &&
        Object.keys(devices).length > 0 ?
          Object.values(devices).map((device) => {
            return (
              <div className="card mb-4" key={device.deviceId.$oid}>
                <div className="card-body">
                  <h5 className="card-title">{device.deviceName}</h5>
                  <p className="card-text"><strong>Type:</strong> {DeviceService.mapDeviceTypeToName(device.deviceType)}
                  </p>
                  {device.deviceDescription && (
                    <div>
                      <strong>Description:</strong>
                      <p>{device.deviceDescription}</p>
                    </div>
                  )}
                  {device.deviceLocation && (
                    <p className="card-text"><strong>Location:</strong> {device.deviceLocation}</p>
                  )}
                  <div className="row">
                    <div className="col-6">
                      <button className="btn btn-success btn-block" onClick={() => {
                        StudentService.claimDevice(device.deviceId.$oid)
                          .then(() => {
                            setDevices(devices => {
                              const newDevices = {...devices};
                              delete newDevices[device.deviceId.$oid];
                              return newDevices;
                            });
                            setAccept(device.deviceId.$oid);
                          })
                      }}>Accept
                      </button>
                    </div>
                    <div className="col-6">
                      <button className="btn btn-danger btn-block" onClick={() => {
                        StudentService.declineDevice(device.deviceId.$oid)
                          .then(() => {
                            setDevices(devices => {
                              const newDevices = {...devices};
                              delete newDevices[device.deviceId.$oid];
                              return newDevices;
                            });
                          });
                      }}>Decline
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            );
          }) : (
            <div className="card mb-4" key="no-devices">
              <div className="card-body">
                <h5 className="card-title">No Devices</h5>
                <p className="card-text">You have not been offered any devices.</p>
              </div>
            </div>
          )
        }
      </div>
    </div>

  );

}