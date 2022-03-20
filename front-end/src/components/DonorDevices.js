/* eslint-disable no-unused-vars */
import React, {useEffect, useState} from 'react'
import {Navigate} from 'react-router-dom';
import DonorService from "../services/donor.service";
import DeviceService from "../services/device.service";

export default function DonorDevices() {

  const [goToHome, setGoToHome] = useState(false);
  const [goToNewDevice, setGoToNewDevice] = useState(false);
  const [devices, setDevices] = useState({});

  useEffect(() => {
    if (!DonorService.isDonor()) {
      setGoToHome(true);
    } else {
      DonorService.getListedDevices().then(response => {
        response.data.devices?.forEach(deviceId => {
          DonorService.loadDevice(deviceId).then(response => {
            setDevices(devices => ({...devices, [deviceId]: response.data}));
          });
        });
      });
    }
  }, []);

  return (
    <div>
      {goToHome && <Navigate to='/'/>}
      {goToNewDevice && <Navigate to='/devices/add'/>}
      <div className="form-container pb-4">
        <h1 className="mt-4 mb-4">Your Devices</h1>
        <div className="row">
          <div className="col-12">
            <button className="btn btn-primary btn-block" onClick={() => setGoToNewDevice(true)}>
              New Device
            </button>
            {/*<button className="btn btn-primary btn-block" onClick={() => {*/}
            {/*  DonorService.newDevice('iPad', 'iPad Air 3', 'Some scratches.');*/}
            {/*}}>*/}
            {/*  New Device test*/}
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
                  <p className="card-text"><strong>Type:</strong> {DeviceService.mapDeviceTypeToName(device.deviceType)}</p>
                  { device.deviceDescription && (
                    <div>
                      <strong>Description:</strong>
                      <p>{device.deviceDescription}</p>
                    </div>
                  )}
                  { device.deviceLocation && (
                    <p className="card-text"><strong>Location:</strong> {device.deviceLocation}</p>
                  )}
                  {/*<button className="btn btn-primary mr-2" onClick={() => {*/}
                  {/*  // TODO redirect to device edit page*/}
                  {/*}}>Edit</button>*/}
                  <button className="btn btn-danger" onClick={() => {
                    DonorService.deleteDevice(device.deviceId.$oid).then(response => {
                      if (response.status === 200) {
                        setDevices(devices => {
                          const newDevices = {...devices};
                          delete newDevices[device.deviceId.$oid];
                          return newDevices;
                        });
                      }
                    });
                  }}>Delete</button>
                </div>
              </div>
            );
          }) : (
            <div className="card mb-4" key="no-devices">
              <div className="card-body">
                <h5 className="card-title">No Devices</h5>
                <p className="card-text">You have not donated any devices yet.</p>
              </div>
            </div>
          )
        }
      </div>
    </div>

  );

}